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

PACKAGE_PART: ('a'..'z'|'A'..'Z')+;
CLASS_NAME: ('a'..'z'|'A'..'Z')+;

WS : (' ' | '\r' | '\n') { $channel = HIDDEN; };
