package de.uulm.parsing;

public interface Lexer<C> {

  Token<C> nextToken();
}
