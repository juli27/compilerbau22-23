package de.uulm.compiler;

import java.util.List;
import org.antlr.v4.runtime.Token;

public interface Statement extends AstElement {

  record VarDecl(Token name, Token type, Expression value) implements Statement {

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
      return visitor.visitVarDecl(this);
    }
  }

  record Assignment(Token name, Expression value) implements Statement {

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
      return visitor.visitAssignment(this);
    }
  }

  record ProcCall(Token name, List<Expression> params) implements Statement {

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
      return visitor.visitProcCall(this);
    }
  }
}
