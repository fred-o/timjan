package timjan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author fredrik
 */
public class ClassUtils {

    private ClassUtils() {
    }

    public static File packageToFile(File baseDir, String packageName) {
        if (baseDir == null) {
            throw new NullPointerException();
        }
        return new File(baseDir, packageName.replace(".", "/"));
    }

    /**
     * @param classDirs a list of directories where java sources might be found.
     * @param packageName
     * @param className
     * @return
     * @throws java.io.IOException
     */
    public static String readClass(List<File> classDirs, String packageName, String className) throws IOException {
        for (File dir : classDirs) {
            File f = new File(packageToFile(dir, packageName), className + ".java");
            if (f.exists()) {
                FileInputStream fis = new FileInputStream(f);
                try {
                    return slurp(fis);
                } finally {
                    fis.close();
                }
            }
        }
        throw new NoClassDefFoundError("Class " + packageName + "." + className + " could not be found.");
    }

    /**
     * Read everything from an {@link InputStream} and treat the
     * contents as a string.
     */
    static String slurp(InputStream s) throws IOException {
        StringBuilder b = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(s));
        String l;
        while ((l = br.readLine()) != null) {
            b.append(l).append("\n");
        }
        return b.toString();
    }
}
