package test.classes;

import static java.util.regex.Pattern.DOTALL;

import java.util.*;
import java.util.regex.*;

@SuppressWarnings("unused")
public class SimpleClass1 extends Thread {
	public static final Pattern p = Pattern.compile("test");
	int intVar = DOTALL;

	private void unused() {
	}

	public static List<String> getList() {
		return null;
	}
}
