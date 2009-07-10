package timjan.syntax;

import java.util.List;

import timjan.util.StringUtil;

/**
 * @author fredrik
 */
public class PackageStatement {
	private QualifiedIdentifier identifier;

	public PackageStatement(QualifiedIdentifier identifier) {
		this.identifier = identifier;
	}

	public String getPackageName() {
		return identifier.toString();
	}
}
