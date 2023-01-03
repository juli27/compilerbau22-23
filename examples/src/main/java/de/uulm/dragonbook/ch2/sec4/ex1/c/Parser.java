package de.uulm.dragonbook.ch2.sec4.ex1.c;

import de.uulm.parsing.Lexer;
import de.uulm.parsing.ParseException;
import de.uulm.parsing.RecursiveDescentParser;
import de.uulm.parsing.examples.Examples;

/**
 * FIRST sets are not disjoint
 * <blockquote><pre>
 *  S -> 0 S 1 | 0 1
 * </pre></blockquote>
 * <blockquote><pre>
 *  S -> 0 R
 *  T -> S 1 | 1
 * </pre></blockquote>
 *
 * <p>problems: input 0 -> error: '0' expected
 */
final class Parser extends RecursiveDescentParser<Character> {

  public static void main(String[] args) {
    Examples.forEachLineWithAcceptingParser(s -> {
      Parser parser = new Parser(Lexer.trivial(s));

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
    consume('0');

    t();
  }

  private void t() throws ParseException {
    if (!match('1')) {
      s();

      consume('1');
    }
  }
}
