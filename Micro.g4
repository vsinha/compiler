grammar Micro;

program: PROGRAM id BEGIN program_body END;

id: IDENTIFIER;

program_body: ;

KEYWORD
  : 'PROGRAM' | 'BEGIN' | 'END' | 'FUNCTION' | 'READ' 
  | 'WRITE' | 'IF' | 'ELSIF' | 'ENDIF' | 'DO' | 'WHILE'
  | 'CONTINUE' | 'BREAK' | 'RETURN' | 'INT' | 'VOID' 
  | 'STRING' | 'FLOAT' | 'TRUE' | 'FALSE'
  ;

COMMENT: '--'(~('\r'|'\n'))* ;

// TODO we should be able to send this to a 'hidden' channel 
WHITESPACE
  :  (' ' | '\t' | '\n' | '\r' | '\f')+ 
  ; 

INTLITERAL: [0-9]+;

// TODO this should limit identifiers to 30 characters
IDENTIFIER: [A-z_][A-z0-9_]+;

OPERATOR: (':=' | '+' | '-' | '/' | '=' 
         | '!=' | '<' | '>' | '(' | ')' 
         | ';' | ',' | '<=' | '>=');


STRINGLITERAL: "[^"]{0,80}";



