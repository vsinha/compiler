grammar Micro;

// Program

program: 'PROGRAM' id 'BEGIN' pgm_body 'END';

id: IDENTIFIER;

pgm_body: ;

/*
decl: string_decl decl 
  | var_decl decl
  | empty
  ;

// Global String Declaration
string_decl: STRING id := str;

str: STRINGLITERAL;

var_decl_list: ;
*/


/*
KEYWORD
  : 'PROGRAM' | 'BEGIN' | 'END' 
  | 'FUNCTION' | 'READ' | 'WRITE' 
  | 'IF' | 'ELSE' | 'ENDIF' 
  | 'WHILE' | 'ENDWHILE' | 'CONTINUE' 
  | 'BREAK' | 'RETURN' | 'INT' 
  | 'VOID' | 'STRING' | 'FLOAT' 
  | 'DO' | 'ELSIF' | 'TRUE' | 'FALSE'
  ;
*/

WHITESPACE:  (' ' | '\t' | '\n' | '\r' | '\f')+ -> skip; 

IDENTIFIER: [A-z_][A-z0-9_]*;

INTLITERAL: [0-9]+;

FLOATLITERAL: [0-9]*?['.'][0-9]*;

STRINGLITERAL: '"'(~('\n'|'\r'))*?'"';

COMMENT: '--'(~('\r'|'\n'))* -> skip;

OPERATOR: (':=' | '+' | '-' | '/' | '=' | '*'
         | '!=' | '<' | '>' | '(' | ')' 
         | ';' | ',' | '<=' | '>=');

