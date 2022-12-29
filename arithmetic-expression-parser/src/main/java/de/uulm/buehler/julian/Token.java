package de.uulm.buehler.julian;

import static java.util.Objects.requireNonNull;

public interface Token {

  static Token plus() {
    return new Common(TokenClass.PLUS);
  }

  static Token minus() {
    return new Common(TokenClass.MINUS);
  }

  static Token mul() {
    return new Common(TokenClass.MUL);
  }

  static Token div() {
    return new Common(TokenClass.DIV);
  }

  static Token pow() {
    return new Common(TokenClass.POW);
  }

  static Token leftPar() {
    return new Common(TokenClass.LEFT_PAR);
  }

  static Token rightPar() {
    return new Common(TokenClass.RIGHT_PAR);
  }

  static Token num(int value) {
    return new NumberLiteral(value);
  }

  static Token eof() {
    return new Common(TokenClass.EOF);
  }

  TokenClass tokenClass();

  record Common(TokenClass tokenClass) implements Token {

    public Common {
      requireNonNull(tokenClass);
    }
  }

  record NumberLiteral(int value) implements Token {

    public NumberLiteral(int value) {
      this.value = value;
    }

    @Override
    public TokenClass tokenClass() {
      return TokenClass.NUM;
    }
  }
}
