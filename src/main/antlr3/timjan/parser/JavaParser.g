parser grammar JavaParser;

options {
    output = AST;
    tokenVocab = JavaLexer;
}

@header {
    package timjan.parser;
}

@members {
    public static Tree parse(String str) throws RecognitionException {
        return parse(tokenize(str));
    }

    public static Tree parse(CommonTokenStream stream) throws RecognitionException {
        JavaParser parser = new JavaParser(stream);
        return (Tree)parser.class_file().getTree();
    }

    public static CommonTokenStream tokenize(String str) throws RecognitionException {
        JavaLexer lexer = new JavaLexer(new ANTLRStringStream(str));
        return new CommonTokenStream(lexer);
    }

    public static Tree parseFile(String str) throws RecognitionException {
        return (Tree)new JavaParser(tokenize(str)).class_file().getTree();
    }
}

class_file
    : import_statement
    ;

import_statement
    : IMPORT SEMI
    ;
