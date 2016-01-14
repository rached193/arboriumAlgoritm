package es.arcri.arborium;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public class Main {

	public static void main(String[] args) {
		ArbolSufijos n;
		List<Hoja> r;
		switch (args.length) {
		case 2:
			n = new ArbolSufijos();
			String[] arr = { args[1] };
			n.add(args[1], 0);
			String patron = args[0];
			r = n.substring(args[0]);
			prinResult(r, arr, patron);
			
			break;
		case 3:
			n = new ArbolSufijos();
			arr = args[0].split("$");
			for (int i = 0; i < arr.length; i++) {
				n.add(arr[i], i);
			}
			patron = args[1];
			r = n.substring(patron);
			prinResult(r, arr, patron);

			break;

		default:
			System.out.println("/main  ");
			return;
		}

	}

	public static void prinResult(List<Hoja> r,String[] arr, String patron) {
		HashMap<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
		for (Hoja hoja : r) {
			if (!map.containsKey(hoja.getDocId())) {
				map.put(hoja.getDocId(), new LinkedList<Integer>());
			}
			map.get(hoja.getDocId()).add(hoja.getPosition());
		}
		String SEPARATOR="|";
		for (Entry<Integer, List<Integer>> l : map.entrySet()) {
			System.out.println("///Doc: " + l.getKey());
			//System.out.println(arr[l.getKey()]);
			Collections.sort(l.getValue());
			String s=arr[l.getKey()];
			for (int i = 0; i < l.getValue().size(); i++) {
				s=s.substring(0,l.getValue().get(i)+(i*2))
						+SEPARATOR+
						s.substring(l.getValue().get(i)+(i*2),
								l.getValue().get(i)+(i*2)+patron.length())+SEPARATOR+
						s.substring(l.getValue().get(i)+(i*2)+patron.length());
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
