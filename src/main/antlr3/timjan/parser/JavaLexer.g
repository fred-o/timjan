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
PERIOD: '.';
ASTERISK: '*';

CLASS: 'class ';
INTERFACE: 'interface ';
IMPORT: 'import ';
PACKAGE: 'package ';

VISIBILITY: ('public'|'protected'|'private');
STATIC: 'static';

WORD: ('a'..'z'|'A'..'Z'|'0'..'9')+;

WS : (' ' | '\r' | '\n') { $channel = HIDDEN; };
