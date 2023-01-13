package de.uulm.compiler;

import de.uulm.compiler.parser.KurzLexer;
import de.uulm.compiler.parser.KurzParser;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command
public final class Compiler implements Callable<Integer> {

  @Parameters(index = "0")
  private Path inputFile;

  @Override
  public Integer call() throws Exception {
    // TODO: validate the file path (existence, extension)

    var charStream = CharStreams.fromPath(inputFile, StandardCharsets.UTF_8);
    var lexer = new KurzLexer(charStream);
    var parser = new KurzParser(new CommonTokenStream(lexer));

    var visitor = new AstTranslator();
    var ast = visitor.visitProgram(parser.program());

    System.out.println(ast);

    return 0;
  }
}
