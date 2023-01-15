# Language Spec

## Examples

```
var x: i8 = 1
procedure(x)
x = 2
procedure(x)
```

## Lexical Structure
### Whitespace
```
WS -> [ \t]
NL -> ('\r'? '\n' | '\r')+
```
Tokens of type `WS` are discarded.

### Punctuation
```
LPAREN -> '('
RPAREN -> ')'
EQ -> '='
COMMA -> ','
COLON -> ':'
```

### Keywords
```
VAR -> 'var'
```

### Identifier
```
ID -> Letter (AlphaNum)*
```
An identifier is a name for one of the following program elements:
1. [type](#types)
2. [variable](#variables)
3. [function](#functions)

### Literals
```
INT -> Digit+
```

### Fragments
```
AlphaNum -> Letter | Digit
Letter -> [a-zA-Z]
Digit -> [0-9];
```

## Types

## Variables
### Declaration
A variable is introduced via a variable declaration statement:
```
varDecl -> 'var' ID ':' ID '=' expr NL
```

### Referencing

## Functions

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
