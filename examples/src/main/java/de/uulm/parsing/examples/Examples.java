package de.uulm.parsing.examples;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public final class Examples {

  public static void forEachLine(Consumer<String> lineConsumer) {
    var inReader = new BufferedReader(new InputStreamReader(System.in));

    System.out.print("> ");

    inReader.lines()
        .forEach(lineConsumer
            .andThen(s -> System.out.print("> ")));
  }
}
