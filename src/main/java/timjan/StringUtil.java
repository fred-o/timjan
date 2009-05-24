package timjan;

import org.antlr.runtime.Token;

/**
 * The obligatory StringUtil class.
 * 
 * @author fredrik
 * 
 */
public class StringUtil {

    private StringUtil() {
    }

    /**
     * Provides a null-safe interface for {@link String#split}.
     *
     * @param string
     * @param pattern
     * @return null, if string is null. Otherwise, the results of
     * calling {@link String#split} on string with pattern as argument.
     */
    public static String[] nullSafeSplit(String string, String pattern) {
        if (string == null) {
            return null;
        }
        return string.split(pattern);
    }

    /**
     * Provides a null-safe interface for {@link StringUtil#join}.
     */
    public static String nullSafeJoin(String[] strings, String glue) {
        if (strings == null) {
            return null;
        }
        return join(strings, glue);
    }

    /**
     * Provides a null-safe interface for {@link StringUtil#join}.
     */
    public static String nullSafeJoin(Iterable<? extends Object> objs, String glue) {
        if (objs == null) {
            return null;
        }
        return join(objs, glue);
    }

    public static String join(String[] strings, String glue) {
        StringBuilder b = new StringBuilder();
        for (String string : strings) {
            if (b.length() > 0) {
                b.append(glue);
            }
            b.append(string);
        }
        return b.toString();
    }

    public static String join(Iterable<? extends Object> objs, String glue) {
        StringBuilder b = new StringBuilder();
        for (Object obj : objs) {
            if (b.length() > 0) {
                b.append(glue);
            }
            if (obj instanceof Token) {
                b.append(((Token) obj).getText());
            } else {
                b.append(obj.toString());
            }
        }
        return b.toString();
    }

    /**
     * @return true if s is null, an empty string or consists only of whitespace.
     */
    public static boolean isBlank(String s) {
        return s == null || "".equals(s.trim());
    }
}
