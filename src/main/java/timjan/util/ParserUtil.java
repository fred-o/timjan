package timjan.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.Tree;

import timjan.ClassFile;
import timjan.parser.JavaLexer;
import timjan.parser.JavaParser;
import timjan.parser.JavaWalker;

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

	public static ClassFile parseClass(List<File> classDirs, String packageName, String className) throws IOException,
			RecognitionException {
		CommonTokenStream tokens = tokenize(ClassUtil.readClass(classDirs, packageName, className));
		Tree ast = parse(tokens);
		CommonTreeNodeStream nodes = new CommonTreeNodeStream(ast);
		nodes.setTokenStream(tokens);
		JavaWalker walker = new JavaWalker(nodes);
		return walker.javaSource();
	}
}