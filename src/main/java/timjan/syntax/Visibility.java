package timjan.syntax;

public enum Visibility {
	PUBLIC, PRRIVATE, PROTECTED;
	public static Visibility fromString(String str) {
		try {
			return valueOf(str.toUpperCase());
		} catch (IllegalArgumentException iae) {
			return null;
		}
	}
}
