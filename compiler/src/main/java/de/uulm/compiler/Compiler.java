package de.uulm.compiler;

import de.uulm.compiler.parser.KurzLexer;
import de.uulm.compiler.parser.KurzParser;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

public final class Compiler implements Callable<Integer> {

  @Parameters(index = "0", description = "Kurz source file")
  private Path inputFile;

  @Option(names = "--print-ast", description = "Pretty-print the AST, then exit", defaultValue = "false")
  private boolean printAst;

  @Override
  public Integer call() throws Exception {
    // TODO: validate the file path (existence, extension)

    var charStream = CharStreams.fromPath(inputFile, StandardCharsets.UTF_8);
    var lexer = new KurzLexer(charStream);
    var parser = new KurzParser(new CommonTokenStream(lexer));

    var astGenerator = new AstGenerator();
    var ast = astGenerator.visitProgram(parser.program());

    if (printAst) {
      ast.accept(new AstPrettyPrinter());
    }

    return 0;
  }
}
