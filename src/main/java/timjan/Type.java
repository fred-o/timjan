package timjan;

import java.util.*;

public class Type {
	private String name;
	private List<TypeArgument> typeArguments;

	public Type(String name, List<TypeArgument> typeArguments) {
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
