package timjan.util;

import java.io.*;
import java.util.*;

import junit.framework.*;

import org.antlr.runtime.tree.*;

import timjan.syntax.*;

public class ParserUtilTest extends TestCase {
	public void testTokenizeSource() throws Exception {
		Tree t = ParserUtil.parse(ClassUtil.readClass(Arrays.asList(new File("src/test/java")), "test.classes",
				"SimpleClass1"));
		System.out.println(t.toStringTree());
	}

	public void testParseSimpleJavaFile() throws Exception {
		JavaSource cf = ParserUtil.parseClass(Arrays.asList(new File("src/test/java")), "test.classes", "SimpleClass1");
		assertNotNull(cf);
		assertEquals("test.classes", cf.getPackageStatement().getPackageName());
		assertEquals(3, cf.getImports().size());
		ImportStatement i1 = cf.getImports().get(0);
		assertEquals("java.util.regex.Pattern.DOTALL", i1.getIdentifier().toString());
		assertFalse(i1.isStar());
		assertTrue(i1.isStatic());
		ImportStatement i2 = cf.getImports().get(1);
		assertEquals("java.util", i2.getIdentifier().toString());
		assertTrue(i2.isStar());
		assertFalse(i2.isStatic());
		ImportStatement i3 = cf.getImports().get(2);
		assertEquals("java.util.regex", i3.getIdentifier().toString());
		assertTrue(i3.isStar());
		assertFalse(i3.isStatic());
		ClassDefinition cdef = cf.getClassDefininitions().get(0);
		assertNotNull(cdef);
		assertEquals(Visibility.PUBLIC, cdef.getVisibility());
		assertFalse(cdef.isStatic());
		assertEquals("SimpleClass1", cdef.getClassName());
		// Extends
		Type ec = cdef.getExtendsClass();
		assertNotNull(ec);
		assertEquals("Thread", ec.getName());
		// Implements
		List<ClassReference> ii = cdef.getImplementsInterfaces();
		assertEquals(2, ii.size());
		assertEquals("Runnable", ii.get(0).getName());
		assertNull(ii.get(0).getTypeArguments());
		assertEquals("Comparator", ii.get(1).getName());
		assertEquals(1, ii.get(1).getTypeArguments().size());
		TypeArgument ta1 = ii.get(1).getTypeArguments().get(0);
		assertNotNull(ta1);
		assertEquals("Object", ta1.getType().getName());
		// Annotations
		List<AnnotationStatement> ann = cdef.getAnnotations();
		assertNotNull(ann);
		assertEquals(1, ann.size());
		AnnotationStatement as = ann.get(0);
		assertEquals("SuppressWarnings", as.getIdentifier().toString());
		assertEquals("\"unused\"", as.getInitializers().get("default").getExpression());
		// Methods
		List<MethodDeclaration> methods = cdef.getMethodDeclarations();
		assertNotNull(methods);
		assertEquals(3, methods.size());
		assertEquals("unused", methods.get(0).getName());
		assertNull(methods.get(0).getReturnType());
		assertEquals("getList", methods.get(1).getName());
		assertEquals("IllegalArgumentException", methods.get(1).getThrows().get(0).toString());
		ClassReference rt = (ClassReference) methods.get(1).getReturnType();
		assertEquals("List", rt.getName());
		assertEquals("String", rt.getTypeArguments().get(0).getType().getName());
		assertEquals(PrimitiveTypes.INT, methods.get(2).getReturnType());
		assertEquals("compare", methods.get(2).getName());
		// Method arguments
		List<MethodArgument> args = methods.get(2).getArguments();
		assertEquals(2, args.size());
		assertEquals("o1", args.get(0).getName());
		assertEquals("Object", args.get(0).getType().getName());
		assertEquals("o2", args.get(1).getName());
		assertEquals("Object", args.get(1).getType().getName());
	}

	public void testParseMyself() throws Exception {
		JavaSource cf = ParserUtil.parseClass(Arrays.asList(new File("src/test/java")), "timjan.util",
				"ParserUtilTest");
		assertNotNull(cf);
	}
}