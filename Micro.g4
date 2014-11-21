grammar Micro;

@members {
  public SymbolTableTree symbolTree = new SymbolTableTree();
}

// Program
program
  : 'PROGRAM' 
    id 
    'BEGIN' 
    {
    // GLOBAL scope table is created implicitly
    // when first SymbolTableTree is initialized
    }
    pgm_body 
    'END';

id returns [String identifier]
  : IDENTIFIER { 
      $identifier = (String)$IDENTIFIER.text; 
    };

pgm_body
  : decl func_declarations;

decl
  : string_decl decl 
  | var_decl decl
  | //empty
  ;

// Global String Declaration
string_decl
  : 'STRING' id ':=' str ';'
  {
    symbolTree.addString($id.text, $str.text);
  }
  ;

str: STRINGLITERAL;

// Variable Declaration
var_decl
  : var_type id_list ';'
    { 
    // add to symbol table in current scope
    symbolTree.addLocals($id_list.ids, $var_type.text);
    }
  ;

var_type 
  : 'FLOAT'
  | 'INT'  
  ;

any_type
  : var_type 
  | 'VOID'
  ;

id_list returns [ArrayList<String> ids]
  : id 
    id_tail {
      $ids = $id_tail.ids_list;

      // add the first variable
      $ids.add(0, $id.text);
    }
    ;

id_tail returns [ArrayList<String> ids_list]
  : ',' 
    id 
    recurse_tail = id_tail {
      $ids_list = $recurse_tail.ids_list;
      $ids_list.add(0, $id.text);
    }
  | // empty (base case)
    { 
      $ids_list = new ArrayList<String>(); 
    }
  ;

// Function Parameter list
param_decl_list
  : param_decl param_decl_tail
  | // empty
  ;

param_decl
  : var_type id 
    { 
      symbolTree.addParameter($id.text, $var_type.text);
    }
  ;

param_decl_tail
  : ',' param_decl param_decl_tail
  | // empty
  ;

// Function Declarations
func_declarations
  : func_decl func_declarations
  | // empty
  ;

func_decl
  : 'FUNCTION' 
    any_type id { symbolTree.createScope($id.text); }
    '(' 
    param_decl_list 
    ')' 
    'BEGIN' 
    func_body 
    'END' { symbolTree.exitScope(); }
    ;

func_body: decl stmt_list;

// Statement List
stmt_list
  : stmt stmt_list 
  | // empy;
  ;

stmt
  : base_stmt
  | if_stmt
  | while_stmt
  ;

base_stmt
  : assign_stmt
  | read_stmt
  | write_stmt
  | return_stmt
  ;

// Basic Statements
assign_stmt: assign_expr ';' ;
assign_expr: id ':=' expr;
read_stmt: 'READ' '(' id_list ')' ';' ;
write_stmt: 'WRITE' '(' id_list ')' ';' ;
return_stmt: 'RETURN' expr ';' ;

// Expressions
expr: expr_prefix factor;

expr_prefix
  : expr_prefix factor addop 
  | // empty
  ;

factor: factor_prefix postfix_expr;

factor_prefix
  : factor_prefix postfix_expr mulop 
  | // empty
  ;

postfix_expr
  : primary
  | call_expr
  ;

call_expr: id '(' expr_list ')';

expr_list
  : expr expr_list_tail
  | // empty
  ;

expr_list_tail
  : ',' expr expr_list_tail
  | //empty
  ;

primary
  : '(' expr ')' 
  | id
  | INTLITERAL
  | FLOATLITERAL
  ;

addop
  : '+'
  | '-'
  ;

mulop
  : '*'
  | '/'
  ;

// Complex Statements and Condition
if_stmt
  : 'IF' { symbolTree.createScope(); } 
    '(' cond ')' 
    decl 
    stmt_list  { symbolTree.exitScope(); }
    else_part 
    'ENDIF' 
  ;

else_part
  : 'ELSE' { symbolTree.createScope(); }
    decl 
    stmt_list { symbolTree.exitScope(); }
  | // empty
  ;

cond: expr compop expr;

compop
  : '<'
  | '>'
  | '='
  | '!='
  | '<='
  | '>='
  ;

// While
while_stmt
  : 'WHILE' { symbolTree.createScope(); }
    '(' cond ')' 
    decl 
    stmt_list 
    'ENDWHILE' { symbolTree.exitScope(); }
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

