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
    
    public void add(String v,int cont, int id){
           if(v.length()==0){//Es el ultimo
                hojas.add(new Hoja(cont,id));
           }else{
                if(hijos.containsKey(v.charAt(0))){
                    hijos.get(v.charAt(0)).add(v.substring(1), cont+1, id);
             }else{
                hijos.put(v.charAt(0), new Node());
                hijos.get(v.charAt(0)).add(v.substring(1), cont+1, id);           
            }
        }
    }
}
