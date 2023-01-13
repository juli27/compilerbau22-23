package de.uulm.compiler;

import de.uulm.compiler.Expression.Id;
import de.uulm.compiler.Expression.Literal;
import de.uulm.compiler.Statement.Assignment;
import de.uulm.compiler.Statement.ExpressionList;
import de.uulm.compiler.Statement.ProcCall;
import de.uulm.compiler.Statement.VarDecl;
import de.uulm.compiler.parser.KurzBaseVisitor;
import de.uulm.compiler.parser.KurzLexer;
import de.uulm.compiler.parser.KurzParser.AssignmentContext;
import de.uulm.compiler.parser.KurzParser.ExprContext;
import de.uulm.compiler.parser.KurzParser.ProcCallContext;
import de.uulm.compiler.parser.KurzParser.ProcParamsContext;
import de.uulm.compiler.parser.KurzParser.ProgramContext;
import de.uulm.compiler.parser.KurzParser.StatementContext;
import de.uulm.compiler.parser.KurzParser.StatementsContext;
import de.uulm.compiler.parser.KurzParser.VarDeclContext;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.tree.TerminalNode;

public class AstTranslator extends KurzBaseVisitor<Object> {

  @Override
  public Object visitProgram(ProgramContext ctx) {
    var statements = ctx.statements();

    if (statements == null) {
      return new Program(List.of());
    }

    return visit(statements);
  }

  @Override
  public Object visitStatements(StatementsContext ctx) {
    List<Statement> statements = new ArrayList<>();

    var statement = (Statement) visit(ctx.statement());
    statements.add(statement);

    if (ctx.statements() != null) {
      var program = (Program) visit(ctx.statements());
      statements.addAll(program.statements());
    }

    return new Program(statements);
  }

  @Override
  public Object visitStatement(StatementContext ctx) {
    return super.visitStatement(ctx);
  }

  @Override
  public Object visitVarDecl(VarDeclContext ctx) {
    var id = (Id) visit(ctx.ID());
    var type = (Id) visit(ctx.TYPE());
    var expr = (Expression) visit(ctx.expr());

    return new VarDecl(id, type, expr);
  }

  @Override
  public Object visitAssignment(AssignmentContext ctx) {
    var id = (Id) visit(ctx.ID());
    var expr = (Expression) visit(ctx.expr());

    return new Assignment(id, expr);
  }

  @Override
  public Object visitProcCall(ProcCallContext ctx) {
    var id = (Id) ctx.ID().accept(this);
    var params = (ExpressionList) ctx.procParams().accept(this);

    return new ProcCall(id, params);
  }

  @Override
  public Object visitProcParams(ProcParamsContext ctx) {
    List<Expression> expressions = new ArrayList<>();

    for (int i = 0;; ++i) {
      ExprContext expr = ctx.expr(i);
      if (expr == null) {
        break;
      }

      expressions.add((Expression) expr.accept(this));
    }

    return new ExpressionList(expressions);
  }

  @Override
  public Object visitExpr(ExprContext ctx) {
    return super.visitExpr(ctx);
  }

  @Override
  public Object visitTerminal(TerminalNode node) {
    var token = node.getSymbol();

    return switch (token.getType()) {
      case KurzLexer.ID, KurzLexer.TYPE -> new Id(token);
      case KurzLexer.INT -> new Literal(token);
      default -> super.visitTerminal(node);
    };
  }
}
