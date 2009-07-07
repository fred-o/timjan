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
	}

	public void testParseSimpleJavaFile() throws Exception {
		ClassFile cf = ParserUtil.parseClass(Arrays.asList(new File("src/test/java")), "test.classes", "SimpleClass1");
		assertNotNull(cf);
		assertEquals("test.classes", cf.getPackageStatement().getPackageName());
		assertEquals(3, cf.getImports().size());
		ImportStatement i1 = cf.getImports().get(0);
		assertEquals("java.util", i1.getIdentifier());
		assertTrue(i1.isStar());
		assertFalse(i1.isStatic());
		ImportStatement i2 = cf.getImports().get(1);
		assertEquals("java.util.regex.Pattern", i2.getIdentifier());
		assertFalse(i2.isStar());
		assertFalse(i2.isStatic());
		ImportStatement i3 = cf.getImports().get(2);
		assertEquals("java.util.regex.Pattern.DOTALL", i3.getIdentifier());
		assertFalse(i3.isStar());
		assertTrue(i3.isStatic());
		ClassDefinition cdef = cf.getClassDefininitions().get(0);
		assertNotNull(cdef);
		assertEquals("public", cdef.getVisibility());
		assertFalse(cdef.isStatic());
		assertEquals("SimpleClass1", cdef.getClassName());
	}

	public void testParseMyself() throws Exception {
		ClassFile cf = ParserUtil.parseClass(Arrays.asList(new File("src/test/java")), "timjan", "ParserUtilTest");
		assertNotNull(cf);
	}
}