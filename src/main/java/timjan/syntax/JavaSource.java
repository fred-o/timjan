package timjan.syntax;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a java class file.
 * 
 * @author fredrik
 */
public class JavaSource {
	final private PackageStatement packageStatement;
	final private List<ImportStatement> imports;
	final private List<ClassDefinition> classDefininitions = new ArrayList<ClassDefinition>();

	public JavaSource(PackageStatement packageName, List<ImportStatement> imports, List<Object> definitions) {
		this.packageStatement = packageName;
		this.imports = imports != null ? Collections.unmodifiableList(imports) : Collections
				.<ImportStatement> emptyList();
		for (Object def : definitions) {
			if (def instanceof ClassDefinition)
				this.classDefininitions.add((ClassDefinition) def);
		}
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
