package timjan.syntax;

import java.util.*;
import junit.framework.*;

public class ClassReferenceTest extends TestCase {
    
	public void testGetClassName() throws Exception {
	    ClassReference cr = new ClassReference("java.util.List", new LinkedList<TypeArgument>());
		assertEquals("java.util.List", cr.getName());
		assertEquals("List", cr.getClassName());
	}

	public void testGetClassNameWithNoPackage() throws Exception {
	    ClassReference cr = new ClassReference("List", new LinkedList<TypeArgument>());
		assertEquals("List", cr.getName());
		assertEquals("List", cr.getClassName());
	}

}