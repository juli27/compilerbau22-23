package de.uulm.parsing;

public interface Token<C> {

  C tokenClass();

  static Token<Character> trivial(final char c) {
    return () -> c;
  }
}
