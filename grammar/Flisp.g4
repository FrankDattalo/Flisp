grammar Flisp;

@header {
package io.github.frankdattalo.flisp.antlr4;
}

program: expression+ EOF;

expression: atom | list;

list: '(' expression* ')';

atom: string | symbol | number;

number: NUMBER;

string: STRING;

symbol: SYMBOL;

NUMBER: '-'? NUMBER_HEAD NUMBER_DECIMAL?;
NUMBER_HEAD: ( [1-9] [0-9]+ | [0-9] );
NUMBER_DECIMAL: ( '.' [0-9]+ );

STRING: '"' ( ~'"' )* '"';

SYMBOL: ([a-zA-Z0-9] | '*' | '<' | '>' | '-' | '+' | '=' | '/')+;

WS : [ \n\t\r]+ -> skip;

ERROR_TOKEN: . ;
