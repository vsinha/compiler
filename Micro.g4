grammar Micro;

program: PROGRAM id BEGIN pgm_body END;

id: IDENTIFIER;

pgm_body: ;

KEYWORD
  : 'PROGRAM'
  | 'BEGIN'
  | 'END'
  ;


// TODO we should be able to send this to a 'hidden' channel 
WHITESPACE
  :  (' ' | '\t' | '\n' | '\r' | '\f')+ 
  ; 

INTLITERAL: [0-9]+;

// TODO this should limit identifiers to 30 characters
IDENTIFIER: [A-z][A-z0-9_]+;

OPERATOR: (':=' | '+' | '-' | '/' | '=' 
         | '!=' | '<' | '>' | '(' | ')' 
         | ';' | ',' | '<=' | '>=');


STRINGLITERAL: "[^"]{0,80}";



