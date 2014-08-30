grammar Micro;

/* Program */

program: PROGRAM id BEGIN pgm_body END;

id: IDENTIFIER;

pgm_body: ;

/*
decl: string_decl_list {decl} 
  | var_decl_list {decl}
  | empty
  ;

string_decl_list: ;

var_decl_list: ;
*/


KEYWORD
  : 'PROGRAM' | 'BEGIN' | 'END' | 'FUNCTION' | 'READ' | 'WRITE' 
  | 'IF' | 'ELSE' | 'ENDIF' | 'WHILE' | 'ENDWHILE' | 'CONTINUE' 
  | 'BREAK' | 'RETURN' | 'INT' | 'VOID' | 'STRING' | 'FLOAT' 
  ;

WHITESPACE:  (' ' | '\t' | '\n' | '\r' | '\f')+ -> skip; 

IDENTIFIER: [A-z_][A-z0-9_]*;

INTLITERAL: [0-9]+;

FLOATLITERAL: [0-9]*?['.'][0-9]*;

STRINGLITERAL: '"'(~('\n'|'\r'))*?'"';

COMMENT: '--'(~('\r'|'\n'))* -> skip;

OPERATOR: (':=' | '+' | '-' | '/' | '=' | '*'
         | '!=' | '<' | '>' | '(' | ')' 
         | ';' | ',' | '<=' | '>=');

