package de.uulm.compiler;

import de.uulm.compiler.Expression.Id;
import de.uulm.compiler.Expression.Literal;
import de.uulm.compiler.Statement.Assignment;
import de.uulm.compiler.Statement.ProcCall;
import de.uulm.compiler.Statement.VarDecl;
import de.uulm.compiler.parser.KurzBaseVisitor;
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
import org.antlr.v4.runtime.Token;

public class AstTranslator extends KurzBaseVisitor<Object> {

  @Override
  public Program visitProgram(ProgramContext ctx) {
    var statements = ctx.statements();

    if (statements == null) {
      return new Program(List.of());
    }

    return visitStatements(statements);
  }

  @Override
  public Program visitStatements(StatementsContext ctx) {
    List<Statement> statements = new ArrayList<>();

    statements.add(visitStatement(ctx.statement()));

    if (ctx.statements() != null) {
      var program = visitStatements(ctx.statements());
      statements.addAll(program.statements());
    }

    return new Program(statements);
  }

  @Override
  public Statement visitStatement(StatementContext ctx) {
    return (Statement) super.visitStatement(ctx);
  }

  @Override
  public VarDecl visitVarDecl(VarDeclContext ctx) {
    var name = ctx.ID(0).getSymbol();

    Token type = null;
    if (ctx.ID(1) != null) {
      type = ctx.ID(1).getSymbol();
    }

    var expr = visitExpr(ctx.expr());

    return new VarDecl(name, type, expr);
  }

  @Override
  public Assignment visitAssignment(AssignmentContext ctx) {
    var name = ctx.ID().getSymbol();
    var expr = visitExpr(ctx.expr());

    return new Assignment(name, expr);
  }

  @Override
  public ProcCall visitProcCall(ProcCallContext ctx) {
    var name = ctx.ID().getSymbol();
    var params = visitProcParams(ctx.procParams());

    return new ProcCall(name, params);
  }

  @Override
  public List<Expression> visitProcParams(ProcParamsContext ctx) {
    return ctx.expr()
        .stream()
        .map(this::visitExpr)
        .toList();
  }

  @Override
  public Expression visitExpr(ExprContext ctx) {
    if (ctx.ID() != null) {
      return new Id(ctx.ID().getSymbol());
    }

    return new Literal(ctx.INT().getSymbol());
  }
}
