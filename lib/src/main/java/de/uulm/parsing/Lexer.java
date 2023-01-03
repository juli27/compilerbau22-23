package de.uulm.parsing;

public interface Lexer<C> {

  Token<C> nextToken();

  static Lexer<Character> trivial(final String input) {
    return new Lexer<>() {
      private int currentPos = 0;

      @Override
      public Token<Character> nextToken() {
        if (isAtEnd()) {
          return Token.trivial('\0');
        }

        return Token.trivial(advance());
      }

      private boolean isAtEnd() {
        return currentPos >= input.length();
      }

      private char advance() {
        return input.charAt(currentPos++);
      }
    };
  }
}
