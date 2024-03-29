package de.uulm.compiler.ast;

import java.util.List;

public record Program(List<Statement> statements) implements KurzAst {

  @Override
  public <T> T accept(AstVisitor<T> visitor) {
    return visitor.visitProgram(this);
  }
}
