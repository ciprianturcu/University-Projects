%{
  #include <ctype.h> 
  #include <stdio.h> 

  #define YYDEBUG 1

  int productions[1024];
  int production_index = 0;

  void addProduction(int production) 
  {
    productions[production_index++] = production;
  }

  void printProductions() 
  {
    int i;
    for (i = 0; i < production_index; i++) 
    {
      printf("%d ", productions[i]);
    }
    printf("\n");
  }
%}

%token INTEGER
%token STRING
%token CHAR
%token FOR
%token RETURN
%token IN
%token OUT
%token IF
%token ELIF
%token ELSE
%token VECTOR
%token WHILE
%token AND
%token OR

%token IDENTIFIER
%token NUMBER_CONST
%token STRING_CONST
%token CHAR_CONST

%token ADD
%token SUB
%token MUL
%token DIV
%token MOD

%token EQ
%token NE
%token LT
%token GT
%token LE
%token GE

%token ASSIGN


%token OPEN_CURLY
%token CLOSE_CURLY
%token OPEN_BRACKET
%token CLOSE_BRACKET
%token OPEN_SQUARE
%token CLOSE_SQUARE

%token DOLLAR_SIGN
%token SEMICOLON
%token COMMA

%%

program : DOLLAR_SIGN stmtlist DOLLAR_SIGN
	;
	
stmtlist : stmt SEMICOLON {addProduction(1);}
	 | stmt SEMICOLON stmtlist {addProduction(2);}
	 ;

stmt : assignstmt {addProduction(3);}
     | declrstmt {addProduction(4);}
     | iostmt {addProduction(5);}
     | ifstmt {addProduction(6);}
     | forstmt {addProduction(7);}
     | whilestmt {addProduction(8);}
     | returnstmt {addProduction(9);}
     ;
     
exp : exp ADD term { addProduction(10); }
    | exp SUB term { addProduction(11); }
    | term { addProduction(12); }
    ;

term : term MUL factor { addProduction(13); }
     | term DIV factor { addProduction(14); }
     | term MOD factor { addProduction(15); }
     | factor { addProduction(16); }
     ;

factor : OPEN_BRACKET exp CLOSE_BRACKET { addProduction(17); }
       | IDENTIFIER {addProduction(18);}
       | NUMBER_CONST {addProduction(19);}
       ;
       
arrayint : NUMBER_CONST { addProduction(20); }
         | arrayint COMMA NUMBER_CONST { addProduction(21); }
         ;
arraystring : STRING_CONST { addProduction(23); }
            | arraystring COMMA STRING_CONST { addProduction(24); }
            ;

assignstmt : identifier ASSIGN exp { addProduction(25); }
           | identifier ASSIGN OPEN_SQUARE arrayint CLOSE_SQUARE { addProduction(26); }
           | identifier ASSIGN OPEN_SQUARE arraystring CLOSE_SQUARE { addProduction(27); }
           ;

declrstmt : type identifierlist { addProduction(28); }
          ;

identifier : IDENTIFIER { addProduction(29); }
           ;
           
identifierlist : identifier { addProduction(30); }
              | identifier COMMA identifierlist { addProduction(31); }
              ;

type : simpletype { addProduction(32); }
     | arraytype { addProduction(33); }
     ;

simpletype : INTEGER { addProduction(34); }
           | STRING { addProduction(35); }
           | CHAR { addProduction(36); }
           ;

arraytype : VECTOR OPEN_SQUARE simpletype CLOSE_SQUARE { addProduction(37); }
           ;

iostmt : IN OPEN_BRACKET identifier CLOSE_BRACKET { addProduction(38); }
       | OUT OPEN_BRACKET identifier CLOSE_BRACKET { addProduction(39); }
       | OUT OPEN_BRACKET STRING_CONST CLOSE_BRACKET { addProduction(40); }
       ;

ifstmt : IF OPEN_BRACKET condition CLOSE_BRACKET OPEN_CURLY stmtlist CLOSE_CURLY { addProduction(41); }
       | IF OPEN_BRACKET condition CLOSE_BRACKET OPEN_CURLY stmtlist CLOSE_CURLY ELIF OPEN_BRACKET condition CLOSE_BRACKET OPEN_CURLY stmtlist CLOSE_CURLY { addProduction(42); }
       | IF OPEN_BRACKET condition CLOSE_BRACKET OPEN_CURLY stmtlist CLOSE_CURLY ELSE OPEN_CURLY stmtlist CLOSE_CURLY { addProduction(43); }
       | IF OPEN_BRACKET condition CLOSE_BRACKET OPEN_CURLY stmtlist CLOSE_CURLY ELIF OPEN_BRACKET condition CLOSE_BRACKET OPEN_CURLY stmtlist CLOSE_CURLY ELSE OPEN_CURLY stmtlist CLOSE_CURLY { addProduction(44); }
       ;

condition : exp relation exp { addProduction(45); }
          ;

relation : LT { addProduction(46); }
         | GT { addProduction(47); }
         | EQ { addProduction(48); }
         | NE { addProduction(49); }
         | LE { addProduction(50); }
         | GE { addProduction(51); }
         ;

forstmt : FOR OPEN_BRACKET assignmentstmt SEMICOLON condition SEMICOLON assignmentstmt CLOSE_BRACKET OPEN_CURLY stmtlist CLOSE_CURLY { addProduction(52); }
        ;

assignmentstmt : identifier ASSIGN exp { addProduction(53); }
               ;

whilestmt : WHILE OPEN_BRACKET condition CLOSE_BRACKET OPEN_CURLY stmtlist CLOSE_CURLY { addProduction(54); }
          ;

returnstmt : RETURN exp { addProduction(55); }
           ;

%%

yyerror(char *s)
{
    printf("%s\n", s);
}

extern FILE *yyin;

int main(int argc, char** argv) 
{
  if (argc > 1) 
  {
    yyin = fopen(argv[1], "r");
    if (!yyin) 
    {
      printf("'%s': Could not open specified file\n", argv[1]);
      return 1;
    }
  }

  if (argc > 2 && strcmp(argv[2], "-d") == 0) 
  {
    printf("Debug mode on\n");
    yydebug = 1;
  }

  printf("Starting parsing...\n");

  if (yyparse() == 0) 
  {
    printf("Parsing successful!\n");
    printProductions();
    return 0;
  }

  printf("Parsing failed!\n");
  return 0;
} 
