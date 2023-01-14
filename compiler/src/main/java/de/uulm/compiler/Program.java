package de.uulm.compiler;

import java.util.List;

public record Program(List<Statement> statements) implements AstElement {

  @Override
  public <T> T accept(AstVisitor<T> visitor) {
    return visitor.visitProgram(this);
  }
}
