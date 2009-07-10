package timjan.syntax;

import java.util.List;

import timjan.util.StringUtil;

/**
 * @author fredrik
 */
public class ImportStatement {
	private QualifiedIdentifier identifier;
	private boolean isStatic;
	private boolean star;

	public ImportStatement(QualifiedIdentifier identifier, boolean isStatic, boolean star) {
		this.identifier = identifier;
		this.isStatic = isStatic;
		this.star = star;
	}

	public QualifiedIdentifier getIdentifier() {
		return identifier;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public boolean isStar() {
		return star;
	}
}
