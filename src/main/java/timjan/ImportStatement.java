package timjan;

import java.util.List;

import timjan.util.StringUtil;

/**
 * @author fredrik
 */
public class ImportStatement {
	private String identifier;
	private boolean isStatic;
	private boolean star;

	public ImportStatement(List<String> identifier, boolean isStatic, boolean star) {
		this.identifier = StringUtil.nullSafeJoin(identifier, ".");
		this.isStatic = isStatic;
		this.star = star;
	}

	public String getIdentifier() {
		return identifier;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public boolean isStar() {
		return star;
	}
}
