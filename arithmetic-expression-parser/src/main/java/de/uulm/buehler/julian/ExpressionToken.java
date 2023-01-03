package de.uulm.buehler.julian;

import static java.util.Objects.requireNonNull;

public interface ExpressionToken {

  static ExpressionToken plus() {
    return new Common(TokenClass.PLUS);
  }

  static ExpressionToken minus() {
    return new Common(TokenClass.MINUS);
  }

  static ExpressionToken mul() {
    return new Common(TokenClass.MUL);
  }

  static ExpressionToken div() {
    return new Common(TokenClass.DIV);
  }

  static ExpressionToken pow() {
    return new Common(TokenClass.POW);
  }

  static ExpressionToken leftPar() {
    return new Common(TokenClass.LEFT_PAR);
  }

  static ExpressionToken rightPar() {
    return new Common(TokenClass.RIGHT_PAR);
  }

  static ExpressionToken num(int value) {
    return new NumberLiteral(value);
  }

  static ExpressionToken eof() {
    return new Common(TokenClass.EOF);
  }

  TokenClass tokenClass();

  record Common(TokenClass tokenClass) implements ExpressionToken {

    public Common {
      requireNonNull(tokenClass);
    }
  }

  record NumberLiteral(int value) implements ExpressionToken {

    public NumberLiteral(int value) {
      this.value = value;
    }

    @Override
    public TokenClass tokenClass() {
      return TokenClass.NUM;
    }
  }
}
