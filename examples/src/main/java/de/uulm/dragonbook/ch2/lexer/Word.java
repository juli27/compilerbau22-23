package de.uulm.dragonbook.ch2.lexer;

import static java.util.Objects.requireNonNull;

public record Word(TokenClass tokenClass, String lexeme) implements de.uulm.parsing.Token<TokenClass> {

  public Word(TokenClass tokenClass, String lexeme) {
    this.tokenClass = requireNonNull(tokenClass);
    this.lexeme = lexeme;
  }
}
