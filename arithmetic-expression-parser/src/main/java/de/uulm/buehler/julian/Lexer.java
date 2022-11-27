package de.uulm.buehler.julian;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

final class Lexer {

  private final String input;

  private int start = 0;
  private int currentPos = 0;

  public Lexer(String input) {
    this.input = requireNonNull(input);
  }

  public List<Token> getRemainingTokens() {
    var tokens = new ArrayList<Token>();

    Token nextToken;

    do {
      nextToken = nextToken();
      while (nextToken == null) {
        nextToken = nextToken();
      }

      tokens.add(nextToken);

    } while (nextToken.getTokenClass() != TokenClass.EOF);

    return tokens;
  }

  public Token nextToken() {
    if (!hasNext()) {
      return Token.eof();
    }

    start = currentPos;

    char c = advance();

    return switch (c) {
      case '+' -> Token.plus();
      case '-' -> Token.minus();
      case '*' -> Token.mul();
      case '/' -> Token.div();
      case '^' -> Token.pow();
      case '(' -> Token.leftPar();
      case ')' -> Token.rightPar();
      default -> {
        if (Character.isWhitespace(c)) {
          yield null;
        }

        if (Character.isDigit(c)) {
          while (Character.isDigit(peek())) {
            advance();
          }

          yield Token.num(Integer.parseInt(input.substring(start, currentPos)));
        }

        // TODO: keep scanning in order to find more errors
        throw new RuntimeException("unexpected character");
      }
    };
  }

  private boolean hasNext() {
    return currentPos < input.length();
  }

  private char peek() {
    if (!hasNext()) {
      return '\0';
    }

    return input.charAt(currentPos);
  }

  private char advance() {
    return input.charAt(currentPos++);
  }
}
