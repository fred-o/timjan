package timjan.util;

import timjan.syntax.*;

public class SyntaxUtil {
    
	private SyntaxUtil() {}

	public static Modifier getModifier(String text) {
		if(text == null)
			throw new NullPointerException();
		String upper = text.toUpperCase();
		try {
			return Modifiers.valueOf(upper);
		} catch (IllegalArgumentException e1) {
			try {
				return Visibility.valueOf(upper);
			}
			catch (IllegalArgumentException e2) {
				return null;
			}
		}
	}

}