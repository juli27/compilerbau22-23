package de.uulm.compiler;

import de.uulm.compiler.ast.Expression;
import de.uulm.compiler.ast.Expression.Id;
import de.uulm.compiler.ast.Expression.Literal;
import de.uulm.compiler.ast.Program;
import de.uulm.compiler.ast.Statement;
import de.uulm.compiler.ast.Statement.Assignment;
import de.uulm.compiler.ast.Statement.ProcCall;
import de.uulm.compiler.ast.Statement.VarDecl;
import de.uulm.compiler.parser.KurzBaseVisitor;
import de.uulm.compiler.parser.KurzParser.AssignmentContext;
import de.uulm.compiler.parser.KurzParser.ExprContext;
import de.uulm.compiler.parser.KurzParser.ProcCallContext;
import de.uulm.compiler.parser.KurzParser.ProcParamsContext;
import de.uulm.compiler.parser.KurzParser.ProgramContext;
import de.uulm.compiler.parser.KurzParser.StatementContext;
import de.uulm.compiler.parser.KurzParser.VarDeclContext;
import java.util.List;

public class AstTranslator extends KurzBaseVisitor<Object> {

  @Override
  public Program visitProgram(ProgramContext ctx) {
    var statements = ctx.statement()
        .stream()
        .map(this::visitStatement)
        .toList();

    return new Program(statements);
  }

  @Override
  public Statement visitStatement(StatementContext ctx) {
    return (Statement) super.visitStatement(ctx);
  }

  @Override
  public VarDecl visitVarDecl(VarDeclContext ctx) {
    var name = ctx.ID(0).getSymbol();
    var type = ctx.ID(1).getSymbol();
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
