package timjan.syntax;

import java.util.*;

public class MethodDeclaration extends AbstractMember {

	private Type returnType;
	private List<MethodArgument> arguments;
	private List<QualifiedIdentifier> throwsClause;
	
	public MethodDeclaration(List<Modifier> modifiers, Type returnType, String name, List<MethodArgument> arguments, 
			List<QualifiedIdentifier> throwsClause) {
		super(name, modifiers);
		this.returnType = returnType;
		this.arguments = arguments;
		this.throwsClause = throwsClause;
	}

	public Type getReturnType() {
	    return returnType;
	}

	public List<MethodArgument> getArguments() {
	    return arguments;
	}

	public List<QualifiedIdentifier> getThrows() {
	    return throwsClause;
	}
    
}