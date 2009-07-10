package timjan.syntax;

import java.util.*;

public class MethodArgument {
	
	private String name;
	private Type type;
	private List<AnnotationStatement> annotations = new LinkedList<AnnotationStatement>();
	private boolean staticMethod = false;
	private boolean varArg;

	public MethodArgument(String name, Type type, List<Modifier> modifiers, boolean varArg) {
		this.name = name;
		this.type = type;
		this.varArg = varArg;
		for(Modifier m: modifiers) {
			if (Modifiers.STATIC == m) {
				this.staticMethod = true;
			}
			else if(m instanceof AnnotationStatement) {
				annotations.add((AnnotationStatement) m);
			}
		}
	}

	public String getName() {
	    return name;
	}

	public Type getType() {
	    return type;
	}

	public List<AnnotationStatement> getAnnotations() {
	    return annotations;
	}

	public boolean isStatic() {
		return staticMethod;
	}

	public boolean isVarArg() {
	    return varArg;
	}
}