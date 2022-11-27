package de.uulm.buehler.julian;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

  private static final String test = "(2 + 3) * 4^(2)";

  public static void main(String[] args) {
    var in = new BufferedReader(new InputStreamReader(System.in));

    System.out.print("expr> ");

    in.lines()
        .map(Lexer::new)
        .map(Parser::new)
        .mapToDouble(Parser::parse)
        .forEach(x -> {
          System.out.println(x);

          System.out.print("expr> ");
        });
  }
}
