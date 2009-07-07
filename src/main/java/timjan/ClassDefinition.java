package timjan;

import java.util.List;

/**
 * @author fredrik
 */
public class ClassDefinition {
	private String visibility;
	private boolean staticClass;
	private String className;

	public ClassDefinition(List<String> modifiers, String className) {
		this.visibility = modifiers.get(0);
		this.className = className;
	}

	public ClassDefinition(String visibility, boolean staticClass, String className) {
		this.visibility = visibility;
		this.staticClass = staticClass;
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	public boolean isStatic() {
		return staticClass;
	}

	public String getVisibility() {
		return visibility;
	}
}
