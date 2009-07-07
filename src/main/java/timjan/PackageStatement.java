package timjan;

import java.util.List;

import timjan.util.StringUtil;

/**
 * @author fredrik
 */
public class PackageStatement {
	final private String packageName;

	public PackageStatement(List<String> packageComponents) {
		this.packageName = StringUtil.nullSafeJoin(packageComponents, ".");
	}

	public PackageStatement(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageName() {
		return packageName;
	}
}
