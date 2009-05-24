parser grammar JavaParser;

options {
    output = AST;
    tokenVocab = JavaLexer;
}

@header {
    package timjan.parser;
    import java.io.*;
    import java.util.List;
    import timjan.*;
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

    public Tree parseClass(List<File> classDirs, String packageName, String className) throws IOException, RecognitionException {
        return parse(ClassUtils.readClass(classDirs, packageName, className));
    }

}

class_file
    : import_statement
    ;

import_statement
    : IMPORT SEMI
    ;
