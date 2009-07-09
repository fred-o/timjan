package timjan.syntax;

import java.util.*;

public class AnnotationStatement implements Modifier {
    
	private List<String> identifier;
	private Map<String, AnnotationInitializer> initializers;

	public AnnotationStatement(List<String> identifier, Map<String, AnnotationInitializer> initializers) {
		this.identifier = identifier;
		this.initializers = initializers;
	}

	public List<String> getIdentifier() {
	    return identifier;
	}

	public Map<String, AnnotationInitializer> getInitializers() {
	    return initializers;
	}

}