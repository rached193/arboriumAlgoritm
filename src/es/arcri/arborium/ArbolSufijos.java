/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.arcri.arborium;

import java.util.List;

/**
 * 
 * @author Aron
 * @author cristian
 */
public class ArbolSufijos {

	private Node raiz = new Node();
	private static final String SEPARATOR = "";

	public void add(String in, int id) {
		String[] arr;
		if (SEPARATOR!=null&&SEPARATOR.length()>0) {
			arr = in.split(SEPARATOR);
		} else {
			arr=new String[]{in};
		}
		int count = 0;
		for (int j = 0; j < arr.length; j++) {
			String dat = arr[j];
			for (int i = 0; i < dat.length(); i++) {
				raiz.add(dat.substring(i), i + count, id);
				// System.out.println(in.substring(i));
			}
			count += dat.length() + 1;
		}

	}

	public List<Hoja> matching(String v) {
		return raiz.matching(v);
	}

	public List<Hoja> substring(String v) {
		return raiz.substring(v);
	}

	public void compacto() {
		raiz.compacto();
	}

	public String toString() {
		return raiz.toString();
	}

}
