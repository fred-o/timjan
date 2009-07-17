package timjan.parser;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.Tree;

import timjan.syntax.JavaSource;

/**
 * Simple concrete implementation of a {@link JavaSourceParser} that scans a
 * list of directories for files matching a certain criteria and returns a list
 * of {@link JavaSource} representations.
 * 
 * @author fredrik
 */
public class JavaSourceParserImpl implements JavaSourceParser {
	private File[] baseDirectories;
	private FileFilter filter;

	public void setBaseDirectories(List<File> baseDirectories) {
		this.baseDirectories = baseDirectories.toArray(new File[baseDirectories.size()]);
	}

	public void setFilter(FileFilter filter) {
		this.filter = filter;
	}

	JavaSource parseFile(File f) throws IOException, RecognitionException {
		CommonTokenStream tokenStream = new CommonTokenStream(new JavaLexer(new ANTLRFileStream(f.getAbsolutePath())));
		Tree ast = (Tree) new JavaParser(tokenStream).javaSource().getTree();
		CommonTreeNodeStream nodes = new CommonTreeNodeStream(ast);
		nodes.setTokenStream(tokenStream);
		JavaWalker walker = new JavaWalker(nodes);
		return walker.javaSource();
	}

	void crawl(File[] files, List<JavaSource> ret) throws IOException, RecognitionException {
		for (File fileOrDir : files) {
			if (fileOrDir.isDirectory()) {
				crawl(fileOrDir.listFiles(), ret);
			} else if (fileOrDir.isFile() && filter.accept(fileOrDir)) {
				ret.add(parseFile(fileOrDir));
			}
		}
	}

	public List<JavaSource> execute() throws IOException, RecognitionException {
		List<JavaSource> ret = new ArrayList<JavaSource>();
		crawl(baseDirectories, ret);
		return ret;
	}
}