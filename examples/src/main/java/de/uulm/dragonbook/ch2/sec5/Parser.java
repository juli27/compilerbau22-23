package de.uulm.dragonbook.ch2.sec5;

import de.uulm.parsing.Lexer;
import de.uulm.parsing.ParseException;
import de.uulm.parsing.RecursiveDescentParser;
import de.uulm.parsing.examples.Examples;

final class Parser extends RecursiveDescentParser<Character> {

  public static void main(String[] args) {
    Examples.forEachLine(s -> {
      Parser parser = new Parser(Lexer.trivial(s));

      try {
        // output as side effect, ugh
        parser.parse();
      } catch (ParseException pe) {
        System.out.println("parsing error: " + pe.getMessage());
      }
    });
  }

  private Parser(Lexer<Character> lexer) {
    super(lexer);
  }

  private void parse() throws ParseException {
    advance();

    expr();

    consume('\0');

    System.out.println();
  }

  private void expr() throws ParseException {
    term();

    while (true) {
      if (match('+')) {
        term();
        System.out.print('+');
      } else if (match('-')) {
        term();
        System.out.print('-');
      } else {
        return;
      }
    }
  }

  private void term() throws ParseException {
    var tokenClass = peek().tokenClass();

    if (Character.isDigit(tokenClass)) {
      System.out.print(tokenClass);

      advance();
    } else {
      throw makeError("syntax error");
    }
  }
}
