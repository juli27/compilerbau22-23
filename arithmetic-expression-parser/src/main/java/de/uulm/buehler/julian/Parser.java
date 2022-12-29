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
    return e()
        .flatMap(value -> consumeToken(TokenClass.EOF)
            .map(token -> value));
  }

  private Result<Double, ParseError> e() {
    var result = t();

    if (result.isErr()) {
      return result;
    }

    while (check(TokenClass.PLUS) || check(TokenClass.MINUS)) {
      if (match(TokenClass.PLUS)) {
        result = result.flatMap(lhs -> t().map(rhs -> lhs + rhs));
      } else if (match(TokenClass.MINUS)) {
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

    while (check(TokenClass.MUL) || check(TokenClass.DIV)) {
      if (match(TokenClass.MUL)) {
        result = result.flatMap(lhs -> p().map(rhs -> lhs * rhs));
      } else if (match(TokenClass.DIV)) {
        result = result.flatMap(lhs -> p().map(rhs -> lhs / rhs));
      }
    }

    return result;
  }

  private Result<Double, ParseError> p() {
    return f()
        .flatMap(lhs -> {
          if (!match(TokenClass.POW)) {
            return ok(lhs);
          }

          return consumeToken(TokenClass.LEFT_PAR)
              .then(this::e)
              .flatMap(rhs -> consumeToken(TokenClass.RIGHT_PAR)
                  .map(rParen -> Math.pow(lhs, rhs)));
        });
  }

  private Result<Double, ParseError> f() {
    if (match(TokenClass.LEFT_PAR)) {
      return e()
          .flatMap(value -> consumeToken(TokenClass.RIGHT_PAR)
              .then(() -> ok(value)));
    }

    if (currentToken instanceof Token.NumberLiteral(var value)) {
      readToken();

      return ok((double) value);
    }

    return err(makeError("expression expected"));
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

  private Result<Token, ParseError> consumeToken(TokenClass tokenClass) {
    if (!check(tokenClass)) {
      return err(makeError(tokenClass + " expected"));
    }

    return ok(readToken());
  }

  private Token readToken() {
    currentToken = lexer.nextToken();

    return currentToken;
  }

  private ParseError makeError(String reason) {
    return new ParseError(reason);
  }
}
