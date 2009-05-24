package timjan;

import timjan.util.StringUtil;
import java.util.List;
import org.antlr.runtime.Token;

/**
 * @author fredrik
 */
public class PackageStatement {
    final private String packageName;

    public PackageStatement(List<Token> packageComponents) {
        this.packageName = StringUtil.nullSafeJoin(packageComponents, ".");
    }

    public PackageStatement(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

}
