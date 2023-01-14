package de.uulm.compiler.ast;

import de.uulm.compiler.ast.Expression.Id;
import de.uulm.compiler.ast.Expression.Literal;
import de.uulm.compiler.ast.Statement.Assignment;
import de.uulm.compiler.ast.Statement.ProcCall;
import de.uulm.compiler.ast.Statement.VarDecl;

public interface AstVisitor<T> {

  T visitProgram(Program program);

  T visitVarDecl(VarDecl varDecl);

  T visitAssignment(Assignment assignment);

  T visitProcCall(ProcCall procCall);

  T visitLiteral(Literal literal);

  T visitId(Id id);
}
