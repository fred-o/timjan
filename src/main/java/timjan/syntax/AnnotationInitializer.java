package timjan.syntax;

import java.util.*;;

public class AnnotationInitializer {
	private String expression;
	private AnnotationStatement annotation;
	private List<AnnotationInitializer> initializers;

	public AnnotationInitializer(String expression) {
		this.expression = expression;
	}
		
	public AnnotationInitializer(AnnotationStatement annotation) {
		this.annotation = annotation;
	}

	public AnnotationInitializer(List<AnnotationInitializer> initializers) {
		this.initializers = initializers;
	}

	public String getExpression() {
	    return expression;
	}

	public AnnotationStatement getAnnotation() {
	    return annotation;
	}

	public List<AnnotationInitializer> getInitializers() {
	    return initializers;
	}
}