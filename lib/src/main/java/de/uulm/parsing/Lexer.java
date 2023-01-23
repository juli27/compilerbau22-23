package de.uulm.parsing;

public interface Lexer<C> {

  Token<C> nextToken();

  static Lexer<Character> trivial(final String input) {
    return new StringLexer<>(input) {
      @Override
      public Token<Character> nextToken() {
        if (isAtEnd()) {
          return Token.trivial('\0');
        }

        return Token.trivial(advance());
      }
    };
  }
}
