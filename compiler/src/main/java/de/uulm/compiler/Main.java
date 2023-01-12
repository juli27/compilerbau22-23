package de.uulm.compiler;

import picocli.CommandLine;

final class Main {

  public static void main(String[] args) {
    var commandLine = new CommandLine(new Compiler());
    int exitCode = commandLine.execute(args);

    System.exit(exitCode);
  }
}
