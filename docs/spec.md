# Language Spec

## Examples

```
var x: i8 = 1
procedure(x)
x = 2
procedure(x)
```

## Lexical Structure
```
VAR -> 'var'
```

## Context-Free Grammar

## Abstract Syntax
```
Expression = Literal | Var
Statement = VarDecl | Assigmnent | ProcCall
ExpressionList * Expression
StatementList * Statement

Literal(Token token)
Var(Token token)

VarDecl(Token name, Token type, Expression value)
Assignment(Token name, Expression value)
ProcCall(Token name, ExpressionList params)
```

### Translation
```
expr -> INT
  = Literal(INT)
expr -> ID
  = Var(ID)
procParams: expr (COMMA expr)*
  = ExpressionList(expr_1, ..., expr_n)
procCall: ID LPAREN procParams? RPAREN NL
  = ProcCall(ID, procParams or ExpressionList())
```
