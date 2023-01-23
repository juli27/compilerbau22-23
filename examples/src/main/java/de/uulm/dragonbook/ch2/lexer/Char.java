package de.uulm.dragonbook.ch2.lexer;

public record Char(char c) implements de.uulm.parsing.Token<TokenClass> {

  @Override
  public TokenClass tokenClass() {
    return TokenClass.CHAR;
  }
}
