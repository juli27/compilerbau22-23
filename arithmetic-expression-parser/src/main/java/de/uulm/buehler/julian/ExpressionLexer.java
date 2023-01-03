package de.uulm.buehler.julian;

import static java.util.Objects.requireNonNull;

import de.uulm.parsing.Lexer;

public final class ExpressionLexer implements Lexer<TokenClass> {

  private final String input;

  private int start = 0;
  private int currentPos = 0;

  public ExpressionLexer(String input) {
    this.input = requireNonNull(input);
  }

  @Override
  public ExpressionToken nextToken() {
    // skip whitespace
    while (!isAtEnd() && Character.isWhitespace(peek())) {
      advance();
    }

    if (isAtEnd()) {
      return ExpressionToken.eof();
    }

    start = currentPos;

    char c = advance();

    return switch (c) {
      case '+' -> ExpressionToken.plus();
      case '-' -> ExpressionToken.minus();
      case '*' -> ExpressionToken.mul();
      case '/' -> ExpressionToken.div();
      case '^' -> ExpressionToken.pow();
      case '(' -> ExpressionToken.leftPar();
      case ')' -> ExpressionToken.rightPar();
      default -> {
        if (Character.isDigit(c)) {
          while (Character.isDigit(peek())) {
            advance();
          }

          yield ExpressionToken.num(Integer.parseInt(input.substring(start, currentPos)));
        }

        // TODO: keep scanning in order to find more errors
        throw new RuntimeException("unexpected character");
      }
    };
  }

  private boolean isAtEnd() {
    return currentPos >= input.length();
  }

  private char peek() {
    if (isAtEnd()) {
      return '\0';
    }

    return input.charAt(currentPos);
  }

  private char advance() {
    return input.charAt(currentPos++);
  }
}
