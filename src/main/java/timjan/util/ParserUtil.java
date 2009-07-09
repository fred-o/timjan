package timjan.util;

import java.io.*;
import java.util.*;

import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;

import timjan.parser.*;
import timjan.syntax.*;

public class ParserUtil {
	public static Tree parse(String str) throws RecognitionException {
		return parse(tokenize(str));
	}

	public static Tree parse(CommonTokenStream stream) throws RecognitionException {
		JavaParser parser = new JavaParser(stream);
		return (Tree) parser.javaSource().getTree();
	}

	public static CommonTokenStream tokenize(String str) throws RecognitionException {
		JavaLexer lexer = new JavaLexer(new ANTLRStringStream(str));
		return new CommonTokenStream(lexer);
	}

	public static JavaSource parseClass(List<File> classDirs, String packageName, String className) throws IOException,
			RecognitionException {
		CommonTokenStream tokens = tokenize(ClassUtil.readClass(classDirs, packageName, className));
		Tree ast = parse(tokens);
		CommonTreeNodeStream nodes = new CommonTreeNodeStream(ast);
		nodes.setTokenStream(tokens);
		JavaWalker walker = new JavaWalker(nodes);
		return walker.javaSource();
	}
}