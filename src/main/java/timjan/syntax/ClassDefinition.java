package timjan.syntax;

import java.util.*;

/**
 * @author fredrik
 */
public class ClassDefinition {
	private Visibility visibility;
	private boolean staticClass;
	private String className;
	private ClassReference extendsClass;
	private List<ClassReference> implementsInterfaces;
	private Set<Modifier> modifiers = new HashSet<Modifier>();
	private List<AnnotationStatement> annotations = new ArrayList<AnnotationStatement>();
	private List<MethodDeclaration> methodDeclarations = new ArrayList<MethodDeclaration>();

	public ClassDefinition(List<Modifier> modifiers, String className, List<ClassReference> extendsClasses, 
			List<ClassReference> implementsInterfaces, List<AbstractMember> members) {
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
		for (AbstractMember member: members) {
		    if (member instanceof MethodDeclaration) {
			    this.methodDeclarations.add((MethodDeclaration) member);
			}
		}
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

	public ClassReference getExtendsClass() {
		return extendsClass;
	}

	public List<ClassReference> getImplementsInterfaces() {
	    return implementsInterfaces;
	}

	public List<AnnotationStatement> getAnnotations() {
	    return annotations;
	}

	public List<MethodDeclaration> getMethodDeclarations() {
	    return methodDeclarations;
	}
}


