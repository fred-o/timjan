package timjan.syntax;

import java.util.*;
import junit.framework.*;

public class QualifiedIdentifierTest extends TestCase {
    
	public void testGetLastPart() throws Exception {
	    QualifiedIdentifier qi = new QualifiedIdentifier(Arrays.asList("java", "util", "List"));
		assertEquals("List", qi.getLastPart());
	}
}