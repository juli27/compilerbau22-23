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

WS: [ \t] -> skip;
NL: ('\r'? '\n' | '\r')+;

LPAREN: '(';
RPAREN: ')';
EQ: '=';
COMMA: ',';
COLON: ':';

VAR: 'var';

ID: Letter (AlphaNum)*;
INT: Digit+;

fragment AlphaNum: Letter | Digit;
fragment Letter: [a-zA-Z];
fragment Digit: [0-9];
