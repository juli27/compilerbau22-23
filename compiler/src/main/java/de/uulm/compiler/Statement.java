package de.uulm.compiler;

import de.uulm.compiler.Expression.Id;
import java.util.List;

public interface Statement {

  record ExpressionList(List<Expression> expressions) {
  }

  record VarDecl(Id name, Id type, Expression value) implements Statement {
  }

  record Assignment(Expression name, Expression value) implements Statement {
  }

  record ProcCall(Id name, ExpressionList params) implements Statement {
  }
}
