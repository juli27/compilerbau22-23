package de.uulm.compiler.ast;

import org.antlr.v4.runtime.Token;

public interface Expression extends KurzAst {

  record Literal(Token token) implements Expression {

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
      return visitor.visitLiteral(this);
    }
  }

  record Var(Token name) implements Expression {

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
      return visitor.visitVar(this);
    }
  }
}
