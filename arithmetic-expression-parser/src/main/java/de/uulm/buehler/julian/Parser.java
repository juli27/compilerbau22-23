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
      error();
    }

    return result;
  }

  private double e() {
    double result = t();

    if (currentToken.getTokenClass() == TokenClass.PLUS) {
      readToken();

      return result + e();
    } else if (currentToken.getTokenClass() == TokenClass.MINUS) {
      readToken();

      return result - e();
    }

    return result;
  }

  private double t() {
    double result = p();

    if (currentToken.getTokenClass() == TokenClass.MUL) {
      readToken();

      return result * t();
    } else if (currentToken.getTokenClass() == TokenClass.DIV) {
      readToken();

      return result / t();
    }

    return result;
  }

  private double p() {
    double result = f();

    if (currentToken.getTokenClass() == TokenClass.POW) {
      readToken();
      if (currentToken.getTokenClass() != TokenClass.LEFT_PAR) {
        error();
      } else {
        readToken();

        double power = e();
        result = Math.pow(result, power);

        if (currentToken.getTokenClass() != TokenClass.RIGHT_PAR) {
          error();
        }

        readToken();

        return result;
      }
    }

    return result;
  }

  private double f() {
    if (currentToken.getTokenClass() == TokenClass.LEFT_PAR) {
      readToken();

      double result = e();

      if (currentToken.getTokenClass() == TokenClass.RIGHT_PAR) {
        readToken();

        return result;
      } else {
        error();
      }
    } else if (currentToken.getTokenClass() == TokenClass.NUM) {
      double value = currentToken.getValue();

      readToken();

      return value;
    }

    return 0.0;
  }

  private Token readToken() {
    do {
      currentToken = lexer.nextToken();
    } while (currentToken == null);

    return currentToken;
  }

  private void error() {
    throw new RuntimeException("parsing error");
  }
}
