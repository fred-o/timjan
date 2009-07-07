package timjan;

import java.util.Collections;
import java.util.List;

/**
 * This class represents a java class file.
 * 
 * @author fredrik
 */
public class ClassFile {
	final private PackageStatement packageStatement;
	final private List<ImportStatement> imports;
	final private List<ClassDefinition> classDefininitions;

	public ClassFile(PackageStatement packageName, List<ImportStatement> imports, List<ClassDefinition> classDefinitions) {
		this.packageStatement = packageName;
		this.imports = imports != null ? Collections.unmodifiableList(imports) : Collections
				.<ImportStatement> emptyList();
		this.classDefininitions = classDefinitions;
	}

	public List<ImportStatement> getImports() {
		return imports;
	}

	public PackageStatement getPackageStatement() {
		return packageStatement;
	}

	public List<ClassDefinition> getClassDefininitions() {
		return classDefininitions;
	}
}
