package es.arcri.arborium;

import java.util.LinkedList;
import java.util.List;

public class Test {
	private static final String data = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final int MAX_SIZE = 200;
	private static final int MIN_SIZE = 150;
	private static final int NUM = 100;
	private static final int TEST_SUBSTRING = 100000;
	private static final int TEST_RANDOM_SUBSTRING = 100000;
	private static final int TEST_RANDOM_MACHING = 100000;
	private static final boolean VERVOSE = false;
	private static final boolean CHECK = true;

	private static long time_generate = 0;
	private static long time_compactar = 0;
	private static long time_substring = 0;
	private static long time_maching = 0;

	public static void main(String[] args) {

		ArbolSufijos arbol = new ArbolSufijos();
		String[] strings = new String[NUM];

		/*
		 * Creando arbol
		 */
		System.out.println("Creando arbol...");
		System.out.println("Textos: " + NUM);
		System.out.println("Tamaño cadenas: " + MIN_SIZE + "-" + MAX_SIZE);
		for (int i = 0; i < NUM; i++) {
			strings[i] = getRandomString((int) Math.round(Math.random()
					* (MAX_SIZE - MIN_SIZE) + MIN_SIZE));
		}
		long start_time = System.currentTimeMillis();
		for (int i = 0; i < strings.length; i++) {
			arbol.add(strings[i], i);
		}
		time_generate = System.currentTimeMillis() - start_time;

		/*
		 * Compactando arbol
		 */
		System.out.println("Compactando arbol...");
		start_time = System.currentTimeMillis();
		arbol.compacto();
		time_compactar = System.currentTimeMillis() - start_time;

		/*
		 * Substring de partes de textos
		 */
		System.out.println(TEST_SUBSTRING
				+ " tests de substrings de partes de los documentos...");
		String[] tests = new String[TEST_SUBSTRING];

		for (int i = 0; i < TEST_SUBSTRING; i++) {
			int document = (int) Math.round(Math.random() * (NUM - 1));
			int start = (int) Math.round(Math.random()
					* (strings[document].length() - 2));
			int end = (int) Math.round(Math.random()
					* (strings[document].length() - start - 2) + (start + 1));

			tests[i] = strings[document].substring(start, end);

		}
		try {
			testSubstring(arbol, tests, strings);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		/*
		 * substring de cadenas aleatorias
		 */

		System.out.println(TEST_RANDOM_SUBSTRING
				+ " tests de substrings con cadenas aleatorias...");

		tests = new String[TEST_RANDOM_SUBSTRING];

		for (int i = 0; i < TEST_RANDOM_SUBSTRING; i++) {
			tests[i] = getRandomString((int) Math.round(Math.random()
					* (MAX_SIZE - 1) + 1));

		}
		try {
			testSubstring(arbol, tests, strings);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		/*
		 * Maching de documentos existentes
		 */
		System.out.println(NUM + " tests de maching con los documentos...");

		// create tests
		tests = strings;

		try {
			testMaching(arbol, tests, strings);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * Maching de cadenas aleatorias
		 */
		System.out.println(TEST_RANDOM_MACHING
				+ " tests de maching con cadenas aleatorias...");

		tests = new String[TEST_RANDOM_MACHING];
		// create tests
		for (int i = 0; i < tests.length; i++) {
			tests[i] = getRandomString((int) Math.round(Math.random()
					* (MAX_SIZE - MIN_SIZE) + MIN_SIZE));
		}
		try {
			testMaching(arbol, tests, strings);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("///Tiempos///");
		System.out.println("Tiempo de generacion de arbol: " + time_generate
				+ "ms");
		System.out.println("Tiempo de compactacion de arbol: " + time_compactar
				+ "ms");
		System.out.println("Tiempo total substring: " + time_substring + "ms");
		long mean = (time_substring / (TEST_RANDOM_SUBSTRING + TEST_SUBSTRING));
		System.out.println("Tiempo medio substring: "
				+ (mean == 0 ? "<1" : mean) + "ms");
		System.out.println("Tiempo total maching: " + time_maching + "ms");
		mean = (time_maching / (TEST_RANDOM_MACHING + NUM));
		System.out.println("Tiempo medio maching: " + (mean == 0 ? "<1" : mean)
				+ "ms");

		System.out
				.println("AVISO: los tiempos no incluyen los tiempos de comprobaciones");
	}

	public static void testSubstring(ArbolSufijos arbol, String[] tests,
			String[] strings) throws Exception {
		List<List<Hoja>> outs = new LinkedList<List<Hoja>>();
		// test
		long start_time = System.currentTimeMillis();

		for (int i = 0; i < tests.length; i++) {
			outs.add(arbol.substring(tests[i]));
		}
		time_substring += System.currentTimeMillis() - start_time;

		// check
		if (CHECK) {
			for (int i = 0; i < tests.length; i++) {
				testSubstring(outs.get(i), tests[i], strings);
			}
		}
	}

	public static void testSubstring(List<Hoja> out, String sub,
			String[] strings) throws Exception {
		if (VERVOSE) {
			System.out.println("//Search substring of:");
			System.out.println(sub);
			System.out.println("//Result:");
		}

		for (Hoja hoja : out) {
			if (VERVOSE) {
				System.out.println("Document " + hoja.getDocId()
						+ " contain the substring, in position "
						+ hoja.getPosition());
			}
			if (!strings[hoja.getDocId()].substring(hoja.getPosition(),
					hoja.getPosition() + sub.length()).equals(sub)) {
				System.out.println(strings[hoja.getDocId()]);
				System.out.println(strings[hoja.getDocId()].substring(
						hoja.getPosition(), hoja.getPosition() + sub.length()));
				System.out.println(sub);
				new Exception("Substring failed").printStackTrace();
				return;
			}
		}
		int sn = 0;
		for (int j = 0; j < strings.length; j++) {
			sn += count(strings[j], sub);
		}

		if (sn != out.size()) {
			throw new Exception("Substring failed");
		}
	}

	public static int count(String a, String s) {
		int i = -1, count = 0;
		if (s.length() == 0) {
			return count;
		}
		while ((i = a.indexOf(s, i + 1)) != -1) {
			count++;
		}
		return count;
	}

	public static void testMaching(ArbolSufijos arbol, String[] tests,
			String[] strings) throws Exception {
		List<List<Hoja>> outs = new LinkedList<List<Hoja>>();
		// test
		long start_time = System.currentTimeMillis();
		for (int i = 0; i < tests.length; i++) {
			outs.add(arbol.matching(tests[i]));
		}
		time_maching += System.currentTimeMillis() - start_time;
		// check
		if (CHECK) {
			for (int i = 0; i < tests.length; i++) {
				testMaching(outs.get(i), tests[i], strings);
			}
		}
	}

	public static void testMaching(List<Hoja> out, String string,
			String[] strings) throws Exception {

		if (VERVOSE) {
			System.out.println("//Search string");
			System.out.println(string);
			System.out.println("//Result:");
		}
		List<Integer> mcs = new LinkedList<Integer>();

		for (Hoja hoja : out) {
			mcs.add(hoja.getDocId());
			if (VERVOSE) {
				System.out.println("Document " + hoja.getDocId() + " maching");
			}
			if (!strings[hoja.getDocId()].equals(string)) {
				new Exception("Maching failed").printStackTrace();
				return;
			}
		}
		for (int o = 0; o < strings.length; o++) {
			if (!mcs.contains(o) && string.equals(strings[o])) {
				throw new Exception("Maching failed, no detected document " + o);
			}
		}
	}

	public static String getRandomString(int i) {
		String s = "";
		for (int j = 0; j < i; j++) {
			s += data.charAt((int) Math.round(Math.random()
					* (data.length() - 1)));
		}
		return s;
	}

}
