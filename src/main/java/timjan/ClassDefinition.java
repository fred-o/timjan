package timjan;

/**
 * @author fredrik
 */
public class ClassDefinition {
    final private String visibility;
    final private boolean staticClass;
    final private String className;

    public ClassDefinition(String visibility, boolean staticClass, String className) {
        this.visibility = visibility;
        this.staticClass = staticClass;
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public boolean isStatic() {
        return staticClass;
    }

    public String getVisibility() {
        return visibility;
    }
}
