package de.uulm.dragonbook.ch2.sec4.ex1.a;

import de.uulm.parsing.Lexer;
import de.uulm.parsing.ParseException;
import de.uulm.parsing.RecursiveDescentParser;
import de.uulm.parsing.examples.Examples;

/**
 * S -> + S S | - S S | a
 */
final class Parser extends RecursiveDescentParser<Character> {

  public static void main(String[] args) {
    Examples.forEachLine(s -> {
      Parser parser = new Parser(Lexer.trivial(s));

      try {
        parser.parse();

        System.out.println("accepted");
      } catch (ParseException pe) {
        System.out.println("parsing error: " + pe.getMessage());
      }
    });
  }

  public Parser(Lexer<Character> lexer) {
    super(lexer);
  }

  public void parse() throws ParseException {
    advance();

    s();

    consume('\0');
  }

  private void s() throws ParseException {
    if (match('+')) {
      s();
      s();
    } else if (match('-')) {
      s();
      s();
    } else {
      consume('a');
    }
  }
}
