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
 */
public class ArbolSufijos {

	private Node raiz=new Node();
	
	public void add(String in,int id){
		for (int i = 0; i < in.length(); i++) {
			raiz.add(in.substring(i), i, id);
		}
	}
	
    public boolean matching(String v){
    	return raiz.matching(v);
    }
    
	public List<Hoja> substring(String v){
		return raiz.substring(v);
	}

	public void compacto() {
		raiz.compacto();
	}

    
}
