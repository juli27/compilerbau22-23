package de.uulm.buehler.julian;

import static java.util.Objects.requireNonNull;

final class Parser {

  private final Lexer lexer;

  private Token currentToken;

  Parser(Lexer lexer) {
    this.lexer = requireNonNull(lexer);
  }

  double parse() {
    readToken();

    return s();
  }

  private double s() {
    double result = e();

    if (currentToken.getTokenClass() != TokenClass.EOF) {
      error("end of expression expected");
    }

    return result;
  }

  private double e() {
    double result = t();

    while (currentToken.getTokenClass() == TokenClass.PLUS || currentToken.getTokenClass() == TokenClass.MINUS) {
      if (currentToken.getTokenClass() == TokenClass.PLUS) {
        readToken();

        result += t();
      } else if (currentToken.getTokenClass() == TokenClass.MINUS) {
        readToken();

        result -= t();
      }
    }

    return result;
  }

  private double t() {
    double result = p();

    while (currentToken.getTokenClass() == TokenClass.MUL || currentToken.getTokenClass() == TokenClass.DIV) {
      if (currentToken.getTokenClass() == TokenClass.MUL) {
        readToken();

        result *= p();
      } else if (currentToken.getTokenClass() == TokenClass.DIV) {
        readToken();

        result /= p();
      }
    }

    return result;
  }

  private double p() {
    double left = f();

    if (currentToken.getTokenClass() == TokenClass.POW) {
      readToken();
      if (currentToken.getTokenClass() != TokenClass.LEFT_PAR) {
        error("'(' expected");
      } else {
        readToken();

        double right = e();

        if (currentToken.getTokenClass() != TokenClass.RIGHT_PAR) {
          error("')' expected");
        }

        readToken();

        return Math.pow(left, right);
      }
    }

    return left;
  }

  private double f() {
    if (currentToken.getTokenClass() == TokenClass.LEFT_PAR) {
      readToken();

      double result = e();

      if (currentToken.getTokenClass() == TokenClass.RIGHT_PAR) {
        readToken();

        return result;
      } else {
        error("')' expected");

        return 0.0;
      }
    } else if (currentToken.getTokenClass() == TokenClass.NUM) {
      double value = currentToken.getValue();

      readToken();

      return value;
    } else {
      error("expression expected");

      return 0.0;
    }
  }

  private Token readToken() {
    do {
      currentToken = lexer.nextToken();
    } while (currentToken == null);

    return currentToken;
  }

  private void error(String reason) {
    throw new RuntimeException("parsing error: " + reason);
  }
}
