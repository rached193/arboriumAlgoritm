package es.arcri.arborium;

import java.util.List;

public class Main {
	
	public static void main2(String[] args) {
		ArbolSufijos n;
		List<Hoja>r;
		switch (args.length) {
		case 2:
			n=new ArbolSufijos();
			n.add(args[1], 0);
			r=n.substring(args[0]);
			for (Hoja hoja : r) {
				System.out.println(r);
			}
			break;
		case 3:
			n=new ArbolSufijos();
			for (String string : args[0].split("$")) {
				
			}
			r=n.substring(args[1]);
			for (Hoja hoja : r) {
				System.out.println(hoja);
			}
			break;

		default:
			System.out.println("/main  ");
			return;
		}
		
	}
	
    public static void main(String[] args) {
    	ArbolSufijos arbol = new ArbolSufijos();
        String in="holalalala";
        int id=0;
		arbol.add(in,id);
        System.out.println(arbol);
        List<Hoja> r=arbol.substring("la");
		for (Hoja hoja : r) {
			System.out.println(hoja);
		}
		System.out.println(arbol.matching("hola"));
        
    }
}
