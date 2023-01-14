package de.uulm.compiler;

import de.uulm.compiler.Expression.Id;
import de.uulm.compiler.Expression.Literal;
import de.uulm.compiler.Statement.Assignment;
import de.uulm.compiler.Statement.ProcCall;
import de.uulm.compiler.Statement.VarDecl;

public interface AstVisitor<T> {

  T visitProgram(Program program);

  T visitVarDecl(VarDecl varDecl);

  T visitAssignment(Assignment assignment);

  T visitProcCall(ProcCall procCall);

  T visitLiteral(Literal literal);

  T visitId(Id id);
}
