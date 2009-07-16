package timjan.syntax;

import java.util.*;

public class ClassReference implements Type {
	private String name;
	private String className;
	private List<TypeArgument> typeArguments;

	public ClassReference(String name, List<TypeArgument> typeArguments) {
		String[] nameComponents = name.split("\\.");
		this.name = name;
		this.className = nameComponents[nameComponents.length -1];
		this.typeArguments = typeArguments;
	}

	public String getName() {
	    return name;
	}

	public String getClassName() {
	    return className;
	}

	public List<TypeArgument> getTypeArguments() {
	    return typeArguments;
	}
}
