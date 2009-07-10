package timjan.syntax;

import java.util.*;

public class MethodDeclaration extends AbstractMember {

	private Type returnType;
	private List<QualifiedIdentifier> throwsClause;
	
	
	public MethodDeclaration(List<Modifier> modifiers, Type returnType, String name, List<QualifiedIdentifier> throwsClause) {
		super(name, modifiers);
		this.returnType = returnType;
		this.throwsClause = throwsClause;
	}

	public Type getReturnType() {
	    return returnType;
	}

	public List<QualifiedIdentifier> getThrows() {
	    return throwsClause;
	}
    
}