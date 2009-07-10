package timjan.syntax;

public enum PrimitiveTypes implements Type {
    BOOLEAN("boolean"), CHAR("char"), BYTE("byte"), SHORT("short"), INT("int"), 
		LONG("long"), FLOAT("float"), DOUBLE("double");

	private String name;

	private PrimitiveTypes(String name) {
		this.name = name;
	}

	public String getName() {
	    return name;
	}
}