package timjan;

import java.io.File;
import java.util.Arrays;

import junit.framework.TestCase;

import org.antlr.runtime.tree.Tree;

import timjan.util.ClassUtil;
import timjan.util.ParserUtil;

public class ParserUtilTest extends TestCase {
	public void testTokenizeSource() throws Exception {
		Tree t = ParserUtil.parse(ClassUtil.readClass(Arrays.asList(new File("src/test/java")), "test.classes",
				"SimpleClass1"));
		System.out.println(t.toStringTree());
		// JavaParser.parseClass(Arrays.asList(new File("src/test/java")),
		// "test.classes", "SimpleClass1");
	}
}