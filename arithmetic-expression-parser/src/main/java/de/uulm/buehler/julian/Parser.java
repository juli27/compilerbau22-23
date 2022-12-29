package de.uulm.buehler.julian;

import static java.util.Objects.requireNonNull;

final class Parser {

  private final Lexer lexer;

  private Token currentToken;

  Parser(Lexer lexer) {
    this.lexer = requireNonNull(lexer);
  }

  double parse() throws ParseException {
    readToken();

    return s();
  }

  private double s() throws ParseException {
    var result = e();

    consumeToken(TokenClass.EOF);

    return result;
  }

  private double e() throws ParseException {
    var result = t();

    while (check(TokenClass.PLUS) || check(TokenClass.MINUS)) {
      if (match(TokenClass.PLUS)) {
        result += t();
      } else if (match(TokenClass.MINUS)) {
        result -= t();
      }
    }

    return result;
  }

  private double t() throws ParseException {
    var result = p();

    while (check(TokenClass.MUL) || check(TokenClass.DIV)) {
      if (match(TokenClass.MUL)) {
        result *= p();
      } else if (match(TokenClass.DIV)) {
        result /= p();
      }
    }

    return result;
  }

  private double p() throws ParseException {
    var result = f();

    if (!match(TokenClass.POW)) {
      return result;
    }

    consumeToken(TokenClass.LEFT_PAR);

    result = Math.pow(result, e());

    consumeToken(TokenClass.RIGHT_PAR);

    return result;
  }

  private double f() throws ParseException {
    if (match(TokenClass.LEFT_PAR)) {
      var result = e();

      consumeToken(TokenClass.RIGHT_PAR);

      return result;
    }

    if (currentToken instanceof Token.NumberLiteral(var value)) {
      readToken();

      return value;
    }

    throw makeError("expression expected");
  }

  private boolean match(TokenClass tokenClass) {
    if (currentToken.tokenClass() == tokenClass) {
      readToken();

      return true;
    }

    return false;
  }

  private boolean check(TokenClass tokenClass) {
    return currentToken.tokenClass() == tokenClass;
  }

  private Token consumeToken(TokenClass tokenClass) throws ParseException {
    if (!check(tokenClass)) {
      throw makeError(tokenClass + " expected");
    }

    return readToken();
  }

  private Token readToken() {
    currentToken = lexer.nextToken();

    return currentToken;
  }

  private ParseException makeError(String reason) {
    return new ParseException(reason);
  }
}
