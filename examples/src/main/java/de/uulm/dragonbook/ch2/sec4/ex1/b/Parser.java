package de.uulm.dragonbook.ch2.sec4.ex1.b;

import de.uulm.parsing.Lexer;
import de.uulm.parsing.ParseException;
import de.uulm.parsing.RecursiveDescentParser;
import de.uulm.parsing.examples.Examples;

/**
 * Ambiguous, left recursive grammar
 * <blockquote><pre>
 *  S -> S ( S ) S | ε
 *  </pre></blockquote>
 * without left recursion, unambiguous:
 * <blockquote><pre>
 *  S -> R
 *  R -> ( S ) S | ε
 *  </pre></blockquote>
 * or simplified
 * <blockquote><pre>
 *  S -> ( S ) S | ε
 *  </pre></blockquote>
 */
final class Parser extends RecursiveDescentParser<Character> {

  public static void main(String[] args) {
    Examples.forEachLineWithAcceptingParser(s -> {
      var parser = new Parser(Lexer.trivial(s));

      return parser::parse;
    });
  }

  private Parser(Lexer<Character> lexer) {
    super(lexer);
  }

  private void parse() throws ParseException {
    advance();

    s();

    consume('\0');
  }

  private void s() throws ParseException {
    if (match('(')) {
      s();

      consume(')');

      s();
    }

    // ε
  }
}
