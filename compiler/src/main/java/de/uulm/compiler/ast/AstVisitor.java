package de.uulm.compiler.ast;

import de.uulm.compiler.ast.Expression.Literal;
import de.uulm.compiler.ast.Expression.Var;
import de.uulm.compiler.ast.Statement.Assignment;
import de.uulm.compiler.ast.Statement.FuncCall;
import de.uulm.compiler.ast.Statement.VarDecl;

public interface AstVisitor<T> {

  T visitProgram(Program program);

  T visitVarDecl(VarDecl varDecl);

  T visitAssignment(Assignment assignment);

  T visitFuncCall(FuncCall funcCall);

  T visitLiteral(Literal literal);

  T visitVar(Var var);
}
