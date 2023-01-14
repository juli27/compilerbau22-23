package de.uulm.compiler;

import org.antlr.v4.runtime.Token;

public interface Expression extends AstElement {

  record Literal(Token token) implements Expression {

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
      return visitor.visitLiteral(this);
    }
  }

  record Id(Token token) implements Expression {

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
      return visitor.visitId(this);
    }
  }
}
