package timjan;

import java.util.*;

/**
 * @author fredrik
 */
public class ClassDefinition {
	private Visibility visibility;
	private boolean staticClass;
	private String className;
	private Type extendsClass;
	private List<Type> implementsInterfaces;
	private List<String> annotations = new ArrayList<String>();

	public ClassDefinition(List<String> modifiers, String className, List<Type> extendsClasses, 
			List<Type> implementsInterfaces) {
		for (String m : modifiers) {
			if ("static".equals(m)) {
				staticClass = true;
			} else if (m.startsWith("@")) {
				annotations.add(m);
			} else {
				try {
					this.visibility = Visibility.valueOf(m.toUpperCase());
				} catch (IllegalArgumentException iae) {
				}
			}
		}
		this.className = className;
		if (extendsClasses.size() > 0) {
			this.extendsClass = extendsClasses.get(0);
		}
		this.implementsInterfaces = implementsInterfaces;
	}

	public String getClassName() {
		return className;
	}

	public boolean isStatic() {
		return staticClass;
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public Type getExtendsClass() {
		return extendsClass;
	}

	public List<Type> getImplementsInterfaces() {
	    return implementsInterfaces;
	}
}
