package timjan;

import java.util.Collections;
import java.util.List;

/**
 * This class represents a java class file.
 * 
 * @author fredrik
 */
public class ClassFile {
    final private PackageStatement packageName;
    final private List<ImportStatement> imports;
    final private String className;

    public ClassFile(PackageStatement packageName, List<ImportStatement> imports, String className) {
        this.packageName = packageName;
        this.imports = imports != null ? Collections.unmodifiableList(imports) : Collections.<ImportStatement>emptyList();
        this.className = className;
    }

    public List<ImportStatement> getImports() {
        return imports;
    }

    public PackageStatement getPackageStatement() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

}
