package de.uulm.dragonbook.ch2;

import de.uulm.parsing.Lexer;
import de.uulm.parsing.ParseException;
import de.uulm.parsing.RecursiveDescentParser;
import de.uulm.parsing.examples.Examples;

final class Parser extends RecursiveDescentParser<Character> {

  public static void main(String[] args) {
    Examples.forEachLine(s -> {
      Parser parser = new Parser(Lexer.trivial(s));

      try {
        System.out.println(parser.parse());
      } catch (ParseException pe) {
        System.out.println("parsing error: " + pe.getMessage());
      }
    });
  }

  private Parser(Lexer<Character> lexer) {
    super(lexer);
  }

  private String parse() throws ParseException {
    advance();

    var string = expr();

    consume('\0');

    return string.toString();
  }

  private StringBuilder expr() throws ParseException {
    var string = term();

    while (true) {
      if (match('+')) {
        string.append(term());
        string.append('+');
      } else if (match('-')) {
        string.append(term());
        string.append('-');
      } else {
        return string;
      }
    }
  }

  private StringBuilder term() throws ParseException {
    var tokenClass = peek().tokenClass();

    if (Character.isDigit(tokenClass)) {
      advance();

      return new StringBuilder(String.valueOf(tokenClass));
    } else {
      throw makeError("syntax error");
    }
  }
}
