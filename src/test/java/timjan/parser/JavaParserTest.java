/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package timjan.parser;

import java.io.File;
import java.util.Arrays;
import junit.framework.TestCase;
import org.antlr.runtime.tree.Tree;

/**
 *
 * @author fredrik
 */
public class JavaParserTest extends TestCase {

    public void testParseSimpleJavaFile() throws Exception {
        Tree t = JavaParser.parseClass(Arrays.asList(new File("src/test/java")),
                "test.classes", "SimpleClass1");
        assertNotNull(t);
        assertEquals("package", t.getChild(0).getText());
        assertEquals("test", t.getChild(0).getChild(0).getText());
        assertEquals("classes", t.getChild(0).getChild(1).getText());
    }
}
