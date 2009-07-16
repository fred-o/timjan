package timjan.syntax;

import java.util.*;
import timjan.util.*;

public class QualifiedIdentifier {
    private List<String> parts;
	private String text;

	public QualifiedIdentifier(List<String> parts) {
		this.parts = parts;
		this.text = StringUtil.nullSafeJoin(parts, ".");
	}

	public List<String> getParts() {
	    return parts;
	}

	public String getLastPart() {
		return parts.get(parts.size() -1);
	}

	@Override
	public String toString() {
		return text;
	}
	
}