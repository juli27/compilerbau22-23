package de.uulm.dragonbook.ch2.lexer;

import de.uulm.parsing.StringLexer;
import de.uulm.parsing.Token;
import de.uulm.parsing.examples.Examples;
import java.util.HashMap;
import java.util.Map;

public final class ExpressionLexer extends StringLexer<TokenClass> {

  public static void main(String[] args) {
    Examples.forEachLine(s -> {
      var lexer = new ExpressionLexer(s);
      var token = lexer.nextToken();
      while (!(token instanceof Char(char c) && c == '\0')) {
        System.out.println(token);

        token = lexer.nextToken();
      }

      System.out.println(token);
    });
  }

  public int line = 1;

  private final Map<String, Word> words = new HashMap<>();

  public ExpressionLexer(String input) {
    super(input);

    reserve(new Word(TokenClass.TRUE, "true"));
    reserve(new Word(TokenClass.FALSE, "false"));
  }

  @Override
  public Token<TokenClass> nextToken() {
    // skip leading whitespace
    if (isWhitespace(peek())) {
      whitespace();
    }

    if (Character.isDigit(peek())) {
      return num();
    }

    if (Character.isLetter(peek())) {
      return idOrKeyword();
    }

    return new Char(advance());
  }

  private boolean isWhitespace(char c) {
    return c == ' ' || c == '\t' || c == '\n';
  }

  private void whitespace() {
    while (isWhitespace(peek())) {
      var c = advance();
      if (c == '\n') {
        line++;
      }
    }
  }

  private Num num() {
    var v = 0;

    while (Character.isDigit(peek())) {
      v = 10 * v + Character.digit(advance(), 10);
    }

    return new Num(v);
  }

  private Word idOrKeyword() {
    var lexemeBuilder = new StringBuilder();
    lexemeBuilder.append(advance());

    while (Character.isLetterOrDigit(peek())) {
      lexemeBuilder.append(advance());
    }

    var lexeme = lexemeBuilder.toString();

    return words.computeIfAbsent(lexeme, s -> new Word(TokenClass.ID, s));
  }

  private void reserve(Word w) {
    words.put(w.lexeme(), w);
  }
}
