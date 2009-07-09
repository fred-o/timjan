package timjan.syntax;

import java.util.*;

/**
 * @author fredrik
 */
public abstract class AbstractMember {
    protected String name;
	protected List<Modifier> modifiers;

	protected AbstractMember(String name, List<Modifier> modifiers) {
		this.name = name;
		this.modifiers = modifiers;
	}

	public String getName() {
	    return name;
	}

	public List<Modifier> getModifiers() {
	    return modifiers;
	}
}