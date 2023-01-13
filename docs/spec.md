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
Expression = Literal | Id
Statement = VarDecl | Assigmnent | ProcCall
ExpressionList * Expression
StatementList * Statement

Literal(Token token)
Id(Token token)

VarDecl(Id name, Id type, Expression value)
Assignment(Identifier name, Expression value)
ProcCall(Id name, ExpressionList params)
```

### Translation
```
expr -> INT
  = Literal(INT)
expr -> ID
  = Id(ID)
procParams: expr (COMMA expr)*
  = ExpressionList(expr_1, ..., expr_n)
procCall: ID LPAREN procParams? RPAREN NL
  = ProcCall(ID, procParams or ExpressionList())
```
