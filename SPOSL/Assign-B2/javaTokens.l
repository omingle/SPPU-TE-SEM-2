%{

FILE* yyin;
%}
DIGIT [0-9]
NUMBER {DIGIT}+
REAL {DIGIT}*[.]{NUMBER}
TEXT [A-Za-z]
KEYWORDS "class"|"static"
DATATYPE "int"|"float"|"double"|"long"|"void"|"String"
CONDITIONAL "if"|"else"|"else if"|"switch"|"case"
ITERATIVE "for"|"while"|"do"
IMPORT_STATEMENT "import"[^\n]*";"
SC ";"
IDENTIFIER {TEXT}({DIGIT}|{TEXT}|"_")*
NONIDENTIFIER {DIGIT}({TEXT}|{DIGIT}|"_")*
ARITH_OP "+"|"-"|"/"|"%"|"*";
LOGICAL_OP "&&"|"||"|"!"|"!="
REL_OP "<"|">"|"<="|">="|"=="
UNARY "++"|"--"
ACCESS "public"|"private"|"protected"|""
FUNCTION {ACCESS}{DATATYPE}{IDENTIFER}"("({DATATYPE}{IDENTIFIER})*")"

%%
[ \n\t]+ ;
{IMPORT_STATEMENT} {printf("%s\t-> IMPORT STATEMENT\n",yytext);}
{CONDITIONAL} { printf("%s\t-> CONDITIONAL\n",yytext);}
{ITERATIVE} {printf("%s\t-> ITERATIVE CONSTRUCT\n",yytext);}
{DATATYPE}  {printf("%s\t-> DATATYPE\n",yytext);}
{ACCESS} {printf("%s\t-> ACCESS SPECIFIER\n",yytext);}
{KEYWORDS} {printf("%s\t-> KEYWORDS\n",yytext);}
{IDENTIFIER} {printf("%s\t-> IDENTIFIER\n",yytext);}
{REAL} {printf("%s\t-> REAL CONSTANT\n",yytext);}
{NUMBER} {printf("%s\t-> CONSTAINT INTEGER\n",yytext);}
{NONIDENTIFIER} {printf("%s\t-> NON IDENTIFIER\n",yytext);}
{SC} {printf("%s\t-> DELIMITER\n",yytext);}
{UNARY} {printf("%s\t-> UNARY OPERATOR\n",yytext);}
{ARITH_OP} {printf("%s\t-> ARITHMETIC OPERATOR\n",yytext);}
{LOGICAL_OP} {printf("%s\t-> LOGICAL OPERATOR\n",yytext);}
{REL_OP} {printf("%s\t-> RELATIONAL OPERATOR\n",yytext);}
"=" {printf("%s\t-> ASSIGNMENT OPERATOR\n",yytext);}
"{"  {printf("%s\t-> BLOCK BEGIN\n",yytext);}
"}"  {printf("%s\t-> BLOCK END\n",yytext);}
"("  {printf("%s\t-> PARANTHESIS BEGIN\n",yytext);}
")"  {printf("%s\t-> PARENTHESIS END\n",yytext);}
. ;

%%
int yywrap()
{
	return 1;
}

int main(int argc,char* argv[])
{
	yyin=fopen(argv[1],"r");
	yylex();
	fclose(yyin);
}

