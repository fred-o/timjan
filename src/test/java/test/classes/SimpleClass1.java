package test.classes;

import static java.util.regex.Pattern.DOTALL;

import java.util.*;
import java.util.regex.*;

@SuppressWarnings("unused")
public class SimpleClass1 extends Thread implements Runnable, Comparator<Object> {
	public static final Pattern p = Pattern.compile("test");
	int intVar = DOTALL;

	private void unused() {
	}

	public static List<String> getList() {
		return null;
	}

	public int compare(Object o1, Object o2) {
		return 0;
	}
}
