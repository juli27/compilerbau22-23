package de.uulm.compiler;

import de.uulm.compiler.ast.AstVisitor;
import de.uulm.compiler.ast.Expression;
import de.uulm.compiler.ast.Expression.Id;
import de.uulm.compiler.ast.Expression.Literal;
import de.uulm.compiler.ast.Program;
import de.uulm.compiler.ast.Statement;
import de.uulm.compiler.ast.Statement.Assignment;
import de.uulm.compiler.ast.Statement.FuncCall;
import de.uulm.compiler.ast.Statement.VarDecl;
import java.io.PrintStream;
import java.util.List;
import java.util.function.Consumer;

public final class AstPrettyPrinter implements AstVisitor<Void> {

  private final PrintStream out = System.out;
  private int indentLevel = 0;

  @Override
  public Void visitProgram(Program program) {
    out.print("Program[statements=");

    printList(program.statements(), statement -> {
      out.println();
      indent();
      statement.accept(this);
    });

    out.println("]");

    return null;
  }

  @Override
  public Void visitVarDecl(VarDecl varDecl) {
    out.print(varDecl);

    return null;
  }

  @Override
  public Void visitAssignment(Assignment assignment) {
    out.print(assignment);

    return null;
  }

  @Override
  public Void visitFuncCall(FuncCall funcCall) {
    out.print("FuncCall[name=");
    out.print(funcCall.name());
    out.print(", params=");

    printList(funcCall.params(), expression -> {
      out.println();
      indent();
      expression.accept(this);
    });

    out.print("]");

    return null;
  }

  @Override
  public Void visitLiteral(Literal literal) {
    out.print(literal);

    return null;
  }

  @Override
  public Void visitId(Id id) {
    out.print(id);

    return null;
  }

  private Void visit(Object o) {
    if (o instanceof Program p) {
      return p.accept(this);
    }

    if (o instanceof Expression e) {
      return e.accept(this);
    }

    if (o instanceof Statement s) {
      return s.accept(this);
    }

    throw new RuntimeException("Not part of the AST");
  }

  private <T> void printList(List<T> list, Consumer<T> printElement) {
    out.print('[');
    indentLevel++;

    punctuate(',', list, printElement);

    indentLevel--;
    out.print(']');
  }

  private <T> void punctuate(char p, List<T> list, Consumer<T> element) {
    if (!list.isEmpty()) {
      var allButLast = list.subList(0, list.size() - 1);

      for (var e : allButLast) {
        element.accept(e);
        out.print(p + " ");
      }

      // print the last element without trailing comma
      element.accept(list.get(list.size() - 1));
    }
  }

  private void indent() {
    for (int i = 0; i < indentLevel; ++i) {
      out.print("  ");
    }
  }
}
