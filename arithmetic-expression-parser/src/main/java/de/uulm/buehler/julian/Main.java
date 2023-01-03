package de.uulm.buehler.julian;

import java.io.BufferedReader;
import java.io.InputStreamReader;

final class Main {

  public static void main(String[] args) {
    var in = new BufferedReader(new InputStreamReader(System.in));

    System.out.print("expr> ");

    in.lines()
        .map(ExpressionLexer::new)
        .map(ExpressionParser::new)
        .forEach(parser -> {
          try {
            double value = parser.parse();

            System.out.println(value);
          } catch (ParseException pe) {
            System.out.println("parsing error: " + pe.getMessage());
          }

          System.out.print("expr> ");
        });
  }
}
