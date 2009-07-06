package timjan;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import timjan.util.ClassUtil;

/**
 * @author fredrik
 */
public class ClassUtilsTest extends TestCase {
	public void testPackageToFile() {
		File p = ClassUtil.packageToFile(new File("/"), "test.package");
		assertNotNull(p);
		assertEquals("/test/package", p.getAbsolutePath());
	}

	public void testPackageToFileWithNulls() {
		try {
			ClassUtil.packageToFile(null, "test.package");
			fail();
		} catch (NullPointerException npe) {
		}
		try {
			ClassUtil.packageToFile(new File("/"), null);
			fail();
		} catch (NullPointerException npe) {
		}
	}

	public void testReadClass() throws Exception {
		List<File> dirs = Arrays.asList(new File("src/main/java"));
		assertTrue(dirs.get(0).exists());
		String code = ClassUtil.readClass(dirs, "timjan.util", "ClassUtil");
		assertNotNull(code);
		assertTrue(code.startsWith("package timjan.util;"));
	}

	public void testReadClassWithMultipleDirectories() throws Exception {
		List<File> dirs = Arrays.asList(new File("src/test/java"), new File("/apa"), new File("src/main/java"));
		assertTrue(dirs.get(2).exists());
		String code = ClassUtil.readClass(dirs, "timjan.util", "ClassUtil");
		assertNotNull(code);
		assertTrue(code.startsWith("package timjan.util;"));
	}

	public void testReadClassNoClassFound() throws Exception {
		List<File> dirs = Arrays.asList(new File("src/test/java"), new File("/apa"), new File("src/main/java"));
		assertTrue(dirs.get(2).exists());
		try {
			ClassUtil.readClass(dirs, "timjan", "Teleledningsanka");
			fail();
		} catch (NoClassDefFoundError e) {
		}
	}
}
