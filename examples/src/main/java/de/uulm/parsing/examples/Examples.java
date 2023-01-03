package de.uulm.parsing.examples;

import de.uulm.parsing.ParseException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Consumer;
import java.util.function.Function;

public final class Examples {

  public static void forEachLineWithAcceptingParser(Function<String, AcceptingParser> parserFactory) {
    var inReader = new BufferedReader(new InputStreamReader(System.in));

    System.out.print("> ");

    inReader.lines()
        .map(parserFactory)
        .forEach(parser -> {
          try {
            parser.parse();

            System.out.println("accepted");
          } catch (ParseException pe) {
            System.out.println("parsing error: " + pe.getMessage());
          }

          System.out.print("> ");
        });
  }

  public static void forEachLine(Consumer<String> lineConsumer) {
    var inReader = new BufferedReader(new InputStreamReader(System.in));

    System.out.print("> ");

    inReader.lines()
        .forEach(lineConsumer
            .andThen(s -> System.out.print("> ")));
  }
}
