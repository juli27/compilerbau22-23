package de.uulm.compiler.ast;

public interface KurzAst {

  <T> T accept(AstVisitor<T> visitor);
}
