package es.arcri.arborium;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public class Main {

	public static final String SEPARATOR_OUT = "|";

	public static void main(String[] args) {
		ArbolSufijos n = null;
		List<Hoja> r;

		String patron = null;
		List<String> docs = new LinkedList<String>();
		int i = 0;
		boolean file = false;

		for (String s : args) {
			// System.out.println(s);
			if (s.equals("-f")) {
				file = true;
			} else if (i == 0) {
				patron = s;
				i++;
			} else if (args.length > 2 && i == 1) {
				i++;
			} else {
				docs.add(s);
			}
		}
		if (file) {
			System.out.println("Leyendo ficheros...");
			for (int j = 0; j < docs.size(); j++) {
				String s = "";
				String line = "";
				BufferedReader reader = null;
				// System.out.println("----------------------"+j);
				try {
					reader = new BufferedReader(new InputStreamReader(
							new FileInputStream(docs.get(j))));
					while ((line = reader.readLine()) != null) {
						// System.out.println(line);
						s += line + "\n";
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				// System.out.println(s);
				docs.set(i, s);
			}
		}

		if (patron == null || docs.size() == 0) {
			System.err.println("/main (-f) [patron] [text]");
			System.err.println("/main (-f) [patron] [N] [t1] [t2] [tN]");
			System.err.println("-f: los textos son ficheros");
			return;
		}
		n = new ArbolSufijos();
		System.out.println("Creando arbol de sufijos...");
		for (int j = 0; j < docs.size(); j++) {
			n.add(docs.get(j), j);
		}
		System.out.println("Compactando arbol de sufijos...");
		n.compacto();
		System.out.println("textos: " + docs.size());
		System.out.println("Patron: " + patron);
		System.out.println("///Substring///");
		r = n.substring(args[0]);
		prinResult(r, docs, patron);
		System.out.println("///Maching///");
		r = n.matching(args[0]);
		if (r.size() == 0) {
			System.out.println("No se ha encontrado el patron");
			return;
		}
		for (Hoja h : r) {
			System.out.println("Documento: " + h.getDocId());
		}
	}

	public static void prinResult(List<Hoja> r, List<String> docs, String patron) {
		if (r.size() == 0) {
			System.out.println("No se ha encontrado el patron");
			return;
		}
		HashMap<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
		for (Hoja hoja : r) {
			if (!map.containsKey(hoja.getDocId())) {
				map.put(hoja.getDocId(), new LinkedList<Integer>());
			}
			map.get(hoja.getDocId()).add(hoja.getPosition());
		}
		for (Entry<Integer, List<Integer>> l : map.entrySet()) {
			System.out.println("///Doc: " + l.getKey());
			// System.out.println(arr[l.getKey()]);
			Collections.sort(l.getValue());
			String s = docs.get(l.getKey());
			for (int i = 0; i < l.getValue().size(); i++) {
				s = s.substring(0, l.getValue().get(i) + (i * 2))
						+ SEPARATOR_OUT
						+ s.substring(l.getValue().get(i) + (i * 2), l
								.getValue().get(i) + (i * 2) + patron.length())
						+ SEPARATOR_OUT
						+ s.substring(l.getValue().get(i) + (i * 2)
								+ patron.length());
			}
			System.out.println(s);

		}
	}

	public static void main2(String[] args) {
		ArbolSufijos arbol = new ArbolSufijos();
		String in = "holalalala";
		int id = 0;
		arbol.add(in, id);
		arbol.compacto();

		System.out.println(arbol);
		List<Hoja> r = arbol.substring("la");
		System.out.println("--------");
		for (Hoja hoja : r) {
			System.out.println(hoja);
		}
		System.out.println(arbol.matching("holalalala"));
	}
}
