package timjan.syntax;

import java.text.*;
import java.text.spi.*;
import java.util.*;
import junit.framework.*;

public class JavaSourceTest extends TestCase {

	public void testResolvesTo() throws Exception {
		PackageStatement ps = new PackageStatement(new QualifiedIdentifier(
					Arrays.asList("test", "classes")));
		List<ImportStatement> imports = Arrays.asList(
			new ImportStatement(new QualifiedIdentifier(Arrays.asList("java", "util", "List")), false, false), 
			new ImportStatement(new QualifiedIdentifier(Arrays.asList("java", "text")), false, true));
		List<Object> definitions = Arrays.<Object>asList(
			new ClassDefinition(Arrays.<Modifier>asList(Visibility.PUBLIC), "SimpleClass1", Collections.<ClassReference>emptyList(), 
					Collections.<ClassReference>emptyList(), Collections.<AbstractMember>emptyList()));
	    JavaSource js = new JavaSource(ps, imports, definitions);
		
		assertFalse(js.resolvesTo(new ClassReference("class.NonExistant", null), List.class));
		assertTrue(js.resolvesTo(new ClassReference("java.util.List", null), List.class));
		assertTrue(js.resolvesTo(new ClassReference("List", null), List.class));
		assertFalse(js.resolvesTo(new ClassReference("LinkedList", null), List.class));
		assertFalse(js.resolvesTo(new ClassReference("bogus.Class.List", null), List.class));

		assertTrue(js.resolvesTo(new ClassReference("SimpleDateFormat", null), SimpleDateFormat.class));
		assertFalse(js.resolvesTo(new ClassReference("Format", null), SimpleDateFormat.class));
		assertTrue(js.resolvesTo(new ClassReference("Format", null), Format.class));
		assertFalse(js.resolvesTo(new ClassReference("CollatorProvider", null), CollatorProvider.class));
		
		assertTrue(js.resolvesTo(new ClassReference("SimpleClass1", null), test.classes.SimpleClass1.class));
	}
    
}