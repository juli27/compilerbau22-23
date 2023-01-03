package de.uulm.buehler.julian.experimental;

import de.uulm.buehler.julian.ExpressionLexer;
import de.uulm.buehler.julian.experimental.Result.Err;
import de.uulm.buehler.julian.experimental.Result.Ok;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Entry point for the experimental parser version.
 */
final class Main2 {

  public static void main(String[] args) {
    var in = new BufferedReader(new InputStreamReader(System.in));

    System.out.print("expr> ");

    in.lines()
        .map(ExpressionLexer::new)
        .map(Parser2::new)
        .map(Parser2::parse)
        .forEach(parseResult -> {
          switch (parseResult) {
            case Ok<Double, ParseError>(var value) -> System.out.println(value);
            case Err<Double, ParseError>(var error) -> System.out.println("parsing error: " + error.message());
            default -> throw new IllegalStateException("Unexpected value: " + parseResult);
          }

          System.out.print("expr> ");
        });
  }
}
