/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.arcri.arborium;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Aron
 */
public class Node {
    
    private HashMap<Character, Node> hijos = new HashMap<Character, Node>();
    private List<Hoja> hojas = new LinkedList<Hoja>();    
    private String value = "";
    
	private static final Character COMODIN='*';
	
    public void add(String v,int cont, int id){
           if(v.length()==0){//Es el ultimo
                hojas.add(new Hoja(cont,id));
           }else{
                if(hijos.containsKey(v.charAt(0))){
                    hijos.get(v.charAt(0)).add(v.substring(1), cont, id);
             }else{
                hijos.put(v.charAt(0), new Node());
                hijos.get(v.charAt(0)).add(v.substring(1), cont, id);           
            }
        }
    }
    
    public void compacto(){
        if(hijos.size()==1){
            value+=hijos.values().iterator().next().value;
            hijos=hijos.values().iterator().next().hijos;
        }
        for(Node n: hijos.values()){
            n.compacto();
        }
    }
    
    public boolean matching(String v){
		if (v.length()==0) {
			for (Hoja h : hojas) {
				if (h.getPosition()==0) {
					return true;
				}
			}
			return false;
		}else{
			for (int i = 0; i < value.length() && v.length()!=0; i++) {
				if (v.charAt(0)==COMODIN || v.charAt(0)==value.charAt(0)) {
					v=v.substring(1);
				}else{
					return false;
				}
			}
			if (v.length()==0) {
				return true;
			}
			Character c=v.charAt(0);
			if (hijos.containsKey(c)) {
				return hijos.get(c).matching(v.substring(1));
			}
			return false;
		}
	}
	
	public List<Hoja> substring(String v){
		if (v.length()==0) {
			List<Hoja>result=new LinkedList<Hoja>(hojas);
			for (Node node : hijos.values()) {
				result.addAll(node.substring(v));
			}
			return result;
		}else{
			for (int i = 0; i < value.length() && v.length()!=0; i++) {
				if (v.charAt(0)==COMODIN || v.charAt(0)==value.charAt(0)) {
					v=v.substring(1);
				}else{
					return new LinkedList<Hoja>();
				}
			}
			if (v.length()==0) {
				return hojas;
			}

			Character c=v.charAt(0);
			if (c.equals(COMODIN)) {
				List<Hoja>result=new LinkedList<Hoja>(hojas);
				for (Node node : hijos.values()) {
					result.addAll(node.substring(v.substring(1)));
				}
				return result;
			}else{
				if (hijos.containsKey(c)) {
					return hijos.get(c).substring(v.substring(1));
				}else{
					return new LinkedList<Hoja>();
				}
			}
		}
	}
}
