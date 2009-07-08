package timjan;


public class TypeArgument {
	private Type type;
	private Type wildCardExtends;
	private Type wildCardSuper;

	public TypeArgument(Type type, Type wildCardExtends, Type wildCardSuper) {
		this.type = type;
		this.wildCardExtends = wildCardExtends;
		this.wildCardSuper = wildCardSuper;
	}

	public Type getType() {
	    return type;
	}

	public Type getWildCardExtends() {
	    return wildCardExtends;
	}

	public Type getWildCardSuper() {
	    return wildCardSuper;
	}
}