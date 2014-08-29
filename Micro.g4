grammar Micro;

program: PROGRAM id BEGIN pgm_body END;

id: IDENTIFIER;

pgm_body: ;

KEYWORD
  : 'PROGRAM'
  | 'BEGIN'
  | 'END'
  ;


WHITESPACE
  :  (' ' | '\t' | '\n' | '\r' | '\f')+ 
  ; 

/*
decl: string_decl_list {decl} 
  | var_decl_list {decl}
  | empty;
*/

INTLITERAL: [0-9]+;

// TODO this should limit identifiers to 30 characters
IDENTIFIER: [A-z][A-z0-9_]+;
