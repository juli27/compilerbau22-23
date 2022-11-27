package de.uulm.buehler.julian;

import static java.util.Objects.requireNonNull;

public class Token {

  public static Token plus() {
    return new Token(TokenClass.PLUS);
  }

  public static Token minus() {
    return new Token(TokenClass.MINUS);
  }

  public static Token mul() {
    return new Token(TokenClass.MUL);
  }

  public static Token div() {
    return new Token(TokenClass.DIV);
  }

  public static Token pow() {
    return new Token(TokenClass.POW);
  }

  public static Token leftPar() {
    return new Token(TokenClass.LEFT_PAR);
  }

  public static Token rightPar() {
    return new Token(TokenClass.RIGHT_PAR);
  }

  public static Token num(int value) {
    return new Token(TokenClass.NUM, value);
  }

  public static Token eof() {
    return new Token(TokenClass.EOF);
  }

  private final TokenClass tokenClass;
  private final int value;

  private Token(TokenClass tokenClass) {
    this(tokenClass, 0);
  }

  private Token(TokenClass tokenClass, int value) {
    this.tokenClass = requireNonNull(tokenClass);
    this.value = value;
  }

  public TokenClass getTokenClass() {
    return tokenClass;
  }

  public int getValue() {
    if (tokenClass != TokenClass.NUM) {
      throw new IllegalStateException("de.uulm.buehler.julian.Token is not a number");
    }

    return value;
  }

  @Override
  public String toString() {
    return "Token{" +
        "tokenClass=" + tokenClass +
        ", value=" + value +
        '}';
  }
}
