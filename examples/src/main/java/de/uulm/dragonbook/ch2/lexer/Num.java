package de.uulm.dragonbook.ch2.lexer;

public record Num(int value) implements de.uulm.parsing.Token<TokenClass> {

  @Override
  public TokenClass tokenClass() {
    return TokenClass.NUM;
  }
}
