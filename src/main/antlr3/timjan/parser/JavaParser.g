parser grammar JavaParser;

options {
    tokenVocab = JavaLexer;
}

tokens {
    CLASS_FILE;
}

@header {
    package timjan.parser;
    import java.io.*;
    import java.util.List;
    import timjan.*;
    import timjan.util.*;
}

@members {
    public static ClassFile parse(String str) throws RecognitionException {
        return parse(tokenize(str));
    }

    public static ClassFile parse(CommonTokenStream stream) throws RecognitionException {
        JavaParser parser = new JavaParser(stream);
        return parser.class_file();
    }

    public static CommonTokenStream tokenize(String str) throws RecognitionException {
        JavaLexer lexer = new JavaLexer(new ANTLRStringStream(str));
        return new CommonTokenStream(lexer);
    }

    public static ClassFile parseClass(List<File> classDirs, String packageName, String className) throws IOException, RecognitionException {
        return parse(ClassUtil.readClass(classDirs, packageName, className));
    }

}

class_file returns [ClassFile cf]
    : ps=package_statement
        imst=import_statements
        cd=class_definition 
        { cf = new ClassFile(ps, imst, cd); }
    ;

package_statement returns [PackageStatement ps]
    : PACKAGE pp+=WORD (PERIOD pp+=WORD)+ SEMI
        { ps = new PackageStatement($pp); }
    ;

import_statements returns [List<ImportStatement> iss = new ArrayList<ImportStatement>()]
    : (is=import_statement)+ 
        { iss.add(is); }
    ;

import_statement returns [ImportStatement imst]
    : IMPORT pp+=WORD (PERIOD pp+=WORD)+ PERIOD q=(WORD | ASTERISK) SEMI
        { imst = new ImportStatement($pp, $q.text); }
    ;

class_definition returns [ClassDefinition cd]
    : vis=VISIBILITY cl=CLASS name=WORD
        { cd = new ClassDefinition($vis.text, false, $name.text); }
    ;
