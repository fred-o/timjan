package timjan;

/**
 * @author fredrik
 */
public class ImportStatement {
    private String packageName;
    private String className;

    public ImportStatement(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public String getPackageName() {
        return packageName;
    }
}
