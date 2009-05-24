package timjan;

import java.util.List;
import org.antlr.runtime.Token;
import timjan.util.StringUtil;

/**
 * @author fredrik
 */
public class ImportStatement {
    private String packageName;
    private String qualifier;

    public ImportStatement(List<Token> packageComponents, String qualifier) {
        this.packageName = StringUtil.nullSafeJoin(packageComponents, ".");
        this.qualifier = qualifier;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getQualifier() {
        return qualifier;
    }

}
