package timjan.parser;

import java.io.IOException;
import java.util.List;

import org.antlr.runtime.RecognitionException;

import timjan.syntax.JavaSource;

/**
 * Service provider interface for the java source parser in order to make us
 * less dependent on the exact ANTLR parser/walker implementation.
 * 
 * @author fredrik
 */
public interface JavaSourceParser {
	public List<JavaSource> execute() throws IOException, RecognitionException;
}