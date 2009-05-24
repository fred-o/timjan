lexer grammar JavaLexer;

options {
    filter = true;
}

@header {
    package timjan.parser;
}


LEFT_CURLY: '{';
RIGHT_CURLY: '}';
SEMI: ';';

CLASS: 'class';
INTERFACE: 'interface';
IMPORT: 'import';
PACKAGE: 'package';

VISIBILITY: ('public'|'protected'|'private');

WS : (' ' | '\r' | '\n') { $channel = HIDDEN; };
