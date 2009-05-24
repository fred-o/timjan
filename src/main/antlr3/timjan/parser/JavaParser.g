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
        return parse(ClassUtils.readClass(classDirs, packageName, className));
    }

}

class_file returns [ClassFile cf]
    : ps=package_statement
        imst=import_statements
        class_definition 
        { cf = new ClassFile(ps, imst, null); }
    ;

package_statement returns [PackageStatement ps]
    : PACKAGE pp+=PACKAGE_PART (PERIOD pp+=PACKAGE_PART)+ SEMI
        { ps = new PackageStatement($pp); }
    ;

import_statements returns [List<ImportStatement> iss = new ArrayList<ImportStatement>()]
    : (is=import_statement)+ 
        { iss.add(is); }
    ;

import_statement returns [ImportStatement imst]
    : IMPORT PACKAGE_PART (PERIOD PACKAGE_PART)+ PERIOD (cn=CLASS_NAME | ASTERISK) SEMI
        { imst = new ImportStatement(null, $cn.text); }
    ;

class_definition returns [ClassDefinition cd]
    : VISIBILITY? STATIC? CLASS CLASS_NAME
        { cd = new ClassDefinition(); }
    ;
