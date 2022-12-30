package de.uulm.buehler.julian;

import static java.util.Objects.requireNonNull;

public abstract class RecursiveDescentParser {

  private final Lexer lexer;

  private Token currentToken;

  protected RecursiveDescentParser(Lexer lexer) {
    this.lexer = requireNonNull(lexer);
  }

  protected ParseException makeError(String reason) {
    return new ParseException(reason);
  }

  public Token peek() {
    return currentToken;
  }

  protected boolean check(TokenClass tokenClass) {
    return currentToken.tokenClass() == tokenClass;
  }

  protected Token advance() {
    currentToken = lexer.nextToken();

    return currentToken;
  }

  protected boolean match(TokenClass tokenClass) {
    if (!check(tokenClass)) {
      return false;
    }

    advance();

    return true;
  }

  protected Token consume(TokenClass tokenClass) throws ParseException {
    if (!check(tokenClass)) {
      throw makeError(tokenClass + " expected");
    }

    return advance();
  }
}
