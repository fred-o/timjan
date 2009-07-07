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

	public void testParseSimpleJavaFile() throws Exception {
		ClassFile cf = ParserUtil.walk(ParserUtil.parseClass(Arrays.asList(new File("src/test/java")), "test.classes",
				"SimpleClass1"));
		assertNotNull(cf);
		assertEquals("test.classes", cf.getPackageStatement().getPackageName());
		assertEquals(1, cf.getImports().size());
		assertEquals("java.util", cf.getImports().get(0).getPackageName());
		assertEquals("*", cf.getImports().get(0).getQualifier());
		ClassDefinition cdef = cf.getClassDefininition();
		assertNotNull(cdef);
		assertEquals("public", cdef.getVisibility());
		assertFalse(cdef.isStatic());
		assertEquals("SimpleClass1", cdef.getClassName());
	}
}