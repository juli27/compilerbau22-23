package de.uulm.compiler;

import org.antlr.v4.runtime.Token;
import java.util.List;

public interface Statement {

  record VarDecl(Token name, Token type, Expression value) implements Statement {

  }

  record Assignment(Token name, Expression value) implements Statement {

  }

  record ProcCall(Token name, List<Expression> params) implements Statement {

  }
}
