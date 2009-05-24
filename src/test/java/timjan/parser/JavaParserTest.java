/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timjan.parser;

import java.io.File;
import java.util.Arrays;
import junit.framework.TestCase;
import timjan.ClassFile;

/**
 *
 * @author fredrik
 */
public class JavaParserTest extends TestCase {

    public void testParseSimpleJavaFile() throws Exception {
        ClassFile cf = JavaParser.parseClass(Arrays.asList(new File("src/test/java")),
                "test.classes", "SimpleClass1");
        assertNotNull(cf);
        assertEquals("test.classes", cf.getPackageStatement().getPackageName());
        assertEquals(1, cf.getImports().size());
    }
}
