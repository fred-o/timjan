package timjan;

import java.io.*;
import java.util.*;

import junit.framework.*;

import org.antlr.runtime.tree.*;

import timjan.util.*;

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
		assertEquals("java.util.regex.Pattern.DOTALL", i1.getIdentifier());
		assertFalse(i1.isStar());
		assertTrue(i1.isStatic());
		ImportStatement i2 = cf.getImports().get(1);
		assertEquals("java.util", i2.getIdentifier());
		assertTrue(i2.isStar());
		assertFalse(i2.isStatic());
		ImportStatement i3 = cf.getImports().get(2);
		assertEquals("java.util.regex", i3.getIdentifier());
		assertTrue(i3.isStar());
		assertFalse(i3.isStatic());
		ClassDefinition cdef = cf.getClassDefininitions().get(0);
		assertNotNull(cdef);
		assertEquals(Visibility.PUBLIC, cdef.getVisibility());
		assertFalse(cdef.isStatic());
		assertEquals("SimpleClass1", cdef.getClassName());
		Type ec = cdef.getExtendsClass();
		assertNotNull(ec);
		assertEquals("Thread", ec.getType());
	}

	public void testParseMyself() throws Exception {
		ClassFile cf = ParserUtil.parseClass(Arrays.asList(new File("src/test/java")), "timjan", "ParserUtilTest");
		assertNotNull(cf);
	}
}