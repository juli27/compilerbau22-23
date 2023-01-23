package de.uulm.parsing;

import static java.util.Objects.requireNonNull;

public abstract class StringLexer<C> implements Lexer<C> {

  private final String input;
  private int currentPos = 0;

  protected StringLexer(String input) {
    this.input = requireNonNull(input);
  }

  protected char peek() {
    if (isAtEnd()) {
      return '\0';
    }

    return input.charAt(currentPos);
  }

  protected char advance() {
    if (isAtEnd()) {
      return '\0';
    }

    return input.charAt(currentPos++);
  }

  protected boolean isAtEnd() {
    return currentPos >= input.length();
  }
}
