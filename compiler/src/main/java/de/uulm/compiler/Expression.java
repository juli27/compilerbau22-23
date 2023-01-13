package de.uulm.compiler;

import org.antlr.v4.runtime.Token;

public interface Expression {
  record Literal(Token token) implements Expression {
  }

  record Id(Token token) implements Expression {
  }
}
