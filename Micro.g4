grammar Micro;

program: PROGRAM id BEGIN pgm_body END;

id: IDENTIFIER;

pgm_body: ;

KEYWORD
  : 'PROGRAM'
  | 'BEGIN'
  | 'END'
  ;


/*
decl: string_decl_list {decl} 
  | var_decl_list {decl}
  | empty;
*/

INTLITERAL: [0-9]+;
