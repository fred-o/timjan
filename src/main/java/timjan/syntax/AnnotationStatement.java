package timjan.syntax;

import java.util.*;

public class AnnotationStatement implements Modifier {
    
	private List<String> identifier;

	public AnnotationStatement(List<String> identifier) {
		this.identifier = identifier;
	}

	public List<String> getIdentifier() {
	    return identifier;
	}

}