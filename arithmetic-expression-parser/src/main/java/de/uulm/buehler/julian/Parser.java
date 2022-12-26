package de.uulm.buehler.julian;

import static de.uulm.buehler.julian.Result.err;
import static de.uulm.buehler.julian.Result.ok;
import static java.util.Objects.requireNonNull;

final class Parser {

  private final Lexer lexer;

  private Token currentToken;

  Parser(Lexer lexer) {
    this.lexer = requireNonNull(lexer);
  }

  Result<Double, ParseError> parse() {
    readToken();

    return s();
  }

  private Result<Double, ParseError> s() {
    var result = e();

    if (currentToken.getTokenClass() != TokenClass.EOF) {
      return err(makeError("end of expression expected"));
    }

    return result;
  }

  private Result<Double, ParseError> e() {
    var result = t();

    while (currentToken.getTokenClass() == TokenClass.PLUS || currentToken.getTokenClass() == TokenClass.MINUS) {
      if (currentToken.getTokenClass() == TokenClass.PLUS) {
        readToken();

        result = result.flatMap(lhs -> t().map(rhs -> lhs + rhs));
      } else if (currentToken.getTokenClass() == TokenClass.MINUS) {
        readToken();

        result = result.flatMap(lhs -> t().map(rhs -> lhs - rhs));
      }
    }

    return result;
  }

  private Result<Double, ParseError> t() {
    var result = p();

    while (currentToken.getTokenClass() == TokenClass.MUL || currentToken.getTokenClass() == TokenClass.DIV) {
      if (currentToken.getTokenClass() == TokenClass.MUL) {
        readToken();

        result = result.flatMap(lhs -> p().map(rhs -> lhs * rhs));
      } else if (currentToken.getTokenClass() == TokenClass.DIV) {
        readToken();

        result = result.flatMap(lhs -> p().map(rhs -> lhs / rhs));
      }
    }

    return result;
  }

  private Result<Double, ParseError> p() {
    var left = f();

    if (currentToken.getTokenClass() == TokenClass.POW) {
      readToken();
      if (currentToken.getTokenClass() == TokenClass.LEFT_PAR) {
        readToken();

        var right = e();

        if (currentToken.getTokenClass() != TokenClass.RIGHT_PAR) {
          return err(makeError("')' expected"));
        }

        readToken();

        return left.flatMap(a -> right.map(b -> Math.pow(a, b)));
      } else {
        return err(makeError("'(' expected"));
      }
    }

    return left;
  }

  private Result<Double, ParseError> f() {
    if (currentToken.getTokenClass() == TokenClass.LEFT_PAR) {
      readToken();

      var result = e();

      if (currentToken.getTokenClass() == TokenClass.RIGHT_PAR) {
        readToken();

        return result;
      } else {
        return err(makeError("')' expected"));
      }
    } else if (currentToken.getTokenClass() == TokenClass.NUM) {
      double value = currentToken.getValue();

      readToken();

      return ok(value);
    } else {
      return err(makeError("expression expected"));
    }
  }

  private Token readToken() {
    do {
      currentToken = lexer.nextToken();
    } while (currentToken == null);

    return currentToken;
  }

  private ParseError makeError(String reason) {
    return new ParseError(reason);
  }
}
