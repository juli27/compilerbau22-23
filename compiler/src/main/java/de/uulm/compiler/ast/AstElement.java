package de.uulm.compiler.ast;

public interface AstElement {

  <T> T accept(AstVisitor<T> visitor);
}
