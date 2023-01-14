package de.uulm.compiler;

public interface AstElement {

  <T> T accept(AstVisitor<T> visitor);
}
