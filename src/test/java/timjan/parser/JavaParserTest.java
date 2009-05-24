/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package timjan.parser;

import junit.framework.TestCase;
import org.antlr.runtime.tree.Tree;

/**
 *
 * @author fredrik
 */
public class JavaParserTest extends TestCase {

    public void testParseSimpleJavaFile() throws Exception {
        Tree t = JavaParser.parse("import;");
    }
}
