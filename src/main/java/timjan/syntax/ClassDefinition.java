package timjan.syntax;

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
	private Set<Modifier> modifiers = new HashSet<Modifier>();
	private List<AnnotationStatement> annotations = new ArrayList<AnnotationStatement>();

	public ClassDefinition(List<Modifier> modifiers, String className, List<Type> extendsClasses, 
			List<Type> implementsInterfaces) {
		for (Modifier m : modifiers) {
			if (m instanceof AnnotationStatement) {
				annotations.add((AnnotationStatement) m);
			} else if (m instanceof Visibility) {
				visibility = (Visibility) m;
			} else {
				modifiers.add(m);
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
		return modifiers.contains(Modifiers.STATIC);
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

	public List<AnnotationStatement> getAnnotations() {
	    return annotations;
	}
}
