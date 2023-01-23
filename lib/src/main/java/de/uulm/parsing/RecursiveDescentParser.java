package de.uulm.parsing;

import static java.util.Objects.requireNonNull;

public abstract class RecursiveDescentParser<C> {

  private final Lexer<C> lexer;

  private Token<C> currentToken;

  protected RecursiveDescentParser(Lexer<C> lexer) {
    this.lexer = requireNonNull(lexer);
  }

  protected ParseException makeError(String reason) {
    return new ParseException(reason);
  }

  protected Token<C> peek() {
    return currentToken;
  }

  protected boolean check(C tokenClass) {
    return currentToken.tokenClass().equals(tokenClass);
  }

  @SafeVarargs
  protected final boolean checkOneOf(C... tokenClasses) {
    for (var tokenClass : tokenClasses) {
      if (check(tokenClass)) {
        return true;
      }
    }

    return false;
  }
  protected Token<C> advance() {
    var previousToken = currentToken;
    currentToken = lexer.nextToken();

    return previousToken;
  }

  protected boolean match(C tokenClass) {
    if (!check(tokenClass)) {
      return false;
    }

    advance();

    return true;
  }

  protected Token<C> consume(C tokenClass) throws ParseException {
    if (!check(tokenClass)) {
      throw makeError(tokenClass + " expected");
    }

    return advance();
  }
}
