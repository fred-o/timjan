package timjan.syntax;

import java.util.*;

public class AnnotationStatement implements Modifier {
    
	private QualifiedIdentifier identifier;
	private Map<String, AnnotationInitializer> initializers;

	public AnnotationStatement(QualifiedIdentifier identifier, Map<String, AnnotationInitializer> initializers) {
		this.identifier = identifier;
		this.initializers = initializers;
	}

	public QualifiedIdentifier getIdentifier() {
	    return identifier;
	}

	public Map<String, AnnotationInitializer> getInitializers() {
	    return initializers;
	}

}