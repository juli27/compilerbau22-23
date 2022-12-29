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
    return e().flatMap(value -> {
      if (!match(TokenClass.EOF)) {
        return err(makeError("end of expression expected"));
      }

      return ok(value);
    });
  }

  private Result<Double, ParseError> e() {
    var result = t();

    if (result.isErr()) {
      return result;
    }

    while (match(TokenClass.PLUS) || match(TokenClass.MINUS)) {
      if (match(TokenClass.PLUS)) {
        readToken();

        result = result.flatMap(lhs -> t().map(rhs -> lhs + rhs));
      } else if (match(TokenClass.MINUS)) {
        readToken();

        result = result.flatMap(lhs -> t().map(rhs -> lhs - rhs));
      }
    }

    return result;
  }

  private Result<Double, ParseError> t() {
    var result = p();

    if (result.isErr()) {
      return result;
    }

    while (match(TokenClass.MUL) || match(TokenClass.DIV)) {
      if (match(TokenClass.MUL)) {
        readToken();

        result = result.flatMap(lhs -> p().map(rhs -> lhs * rhs));
      } else if (match(TokenClass.DIV)) {
        readToken();

        result = result.flatMap(lhs -> p().map(rhs -> lhs / rhs));
      }
    }

    return result;
  }

  private Result<Double, ParseError> p() {
    return f().flatMap(lhs -> {
      if (!match(TokenClass.POW)) {
        return ok(lhs);
      }

      readToken();

      if (!match(TokenClass.LEFT_PAR)) {
        return err(makeError("'(' expected"));
      }

      readToken();

      return e().flatMap(rhs -> {
        if (!match(TokenClass.RIGHT_PAR)) {
          return err(makeError("')' expected"));
        }

        readToken();

        return ok(Math.pow(lhs, rhs));
      });
    });
  }

  private Result<Double, ParseError> f() {
    if (match(TokenClass.LEFT_PAR)) {
      readToken();

      return e().flatMap(value -> {
        if (!match(TokenClass.RIGHT_PAR)) {
          return err(makeError("')' expected"));
        }

        readToken();

        return ok(value);
      });
    }

    if (currentToken instanceof Token.NumberLiteral(var value)) {
      readToken();

      return ok((double) value);
    }

    return err(makeError("expression expected"));
  }

  private boolean match(TokenClass tokenClass) {
    return currentToken.tokenClass() == tokenClass;
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
