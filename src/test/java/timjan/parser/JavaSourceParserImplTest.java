package timjan.parser;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import timjan.syntax.AnnotationStatement;
import timjan.syntax.ClassDefinition;
import timjan.syntax.ClassReference;
import timjan.syntax.ImportStatement;
import timjan.syntax.JavaSource;
import timjan.syntax.MethodArgument;
import timjan.syntax.MethodDeclaration;
import timjan.syntax.PrimitiveTypes;
import timjan.syntax.Type;
import timjan.syntax.TypeArgument;
import timjan.syntax.Visibility;

public class JavaSourceParserImplTest extends TestCase {
	public void testParseFiles() throws Exception {
		JavaSourceParserImpl p = new JavaSourceParserImpl();
		p.setBaseDirectories(Arrays.asList(new File("src/test/java")));
		p.setFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.getName().equals("SimpleClass1.java");
			}
		});
		List<JavaSource> r = p.execute();
		assertNotNull(r);
		assertEquals(1, r.size());
		JavaSource cf = r.get(0);
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
}