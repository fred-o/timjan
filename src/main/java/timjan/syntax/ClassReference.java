package timjan.syntax;

import java.util.*;

public class ClassReference implements Type {
	private String name;
	private List<TypeArgument> typeArguments;

	public ClassReference(String name, List<TypeArgument> typeArguments) {
		this.name = name;
		this.typeArguments = typeArguments;
	}

	public String getName() {
	    return name;
	}

	public List<TypeArgument> getTypeArguments() {
	    return typeArguments;
	}
}
