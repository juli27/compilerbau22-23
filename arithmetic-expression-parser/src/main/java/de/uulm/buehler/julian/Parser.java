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

    if (currentToken.tokenClass() != TokenClass.EOF) {
      return err(makeError("end of expression expected"));
    }

    return result;
  }

  private Result<Double, ParseError> e() {
    var result = t();

    while (currentToken.tokenClass() == TokenClass.PLUS || currentToken.tokenClass() == TokenClass.MINUS) {
      if (currentToken.tokenClass() == TokenClass.PLUS) {
        readToken();

        result = result.flatMap(lhs -> t().map(rhs -> lhs + rhs));
      } else if (currentToken.tokenClass() == TokenClass.MINUS) {
        readToken();

        result = result.flatMap(lhs -> t().map(rhs -> lhs - rhs));
      }
    }

    return result;
  }

  private Result<Double, ParseError> t() {
    var result = p();

    while (currentToken.tokenClass() == TokenClass.MUL || currentToken.tokenClass() == TokenClass.DIV) {
      if (currentToken.tokenClass() == TokenClass.MUL) {
        readToken();

        result = result.flatMap(lhs -> p().map(rhs -> lhs * rhs));
      } else if (currentToken.tokenClass() == TokenClass.DIV) {
        readToken();

        result = result.flatMap(lhs -> p().map(rhs -> lhs / rhs));
      }
    }

    return result;
  }

  private Result<Double, ParseError> p() {
    var left = f();

    if (currentToken.tokenClass() == TokenClass.POW) {
      readToken();
      if (currentToken.tokenClass() == TokenClass.LEFT_PAR) {
        readToken();

        var right = e();

        if (currentToken.tokenClass() != TokenClass.RIGHT_PAR) {
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
    if (currentToken.tokenClass() == TokenClass.LEFT_PAR) {
      readToken();

      var result = e();

      if (currentToken.tokenClass() == TokenClass.RIGHT_PAR) {
        readToken();

        return result;
      } else {
        return err(makeError("')' expected"));
      }
    } else if (currentToken instanceof Token.NumberLiteral n) {
      double value = n.value();

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
