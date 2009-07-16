package timjan.syntax;

/**
 * @author fredrik
 */
public interface Type {

	/**
	 * Returns the whole name (including package name) of this type.
	 */
	public String getName();

	/**
	 * Returns the class part of the name.
	 */
	public String getClassName();

}