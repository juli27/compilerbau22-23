grammar Kurz;

@header {
package de.uulm.compiler.parser;
}

program: NL? statement* EOF;
statement: varDecl | assignment | procCall;
varDecl: VAR ID COLON ID EQ expr NL;
assignment: ID EQ expr NL;
procCall: ID LPAREN procParams? RPAREN NL;
procParams: expr (COMMA expr)*;
expr: INT | ID;

VAR: 'var';
LPAREN: '(';
RPAREN: ')';
EQ: '=';
COMMA: ',';
COLON: ':';
ID: Letter (Letter | Digit)*;
INT: '0' | NonZeroDigit Digit*;
NL: ('\r'? '\n' | '\r')+;
WS: [ \t] -> skip;

fragment Letter: [a-zA-Z];
fragment Digit: '0' | NonZeroDigit;
fragment NonZeroDigit: [1-9];
