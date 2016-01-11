/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.arcri.arborium;

/**
 *
 * @author Aron
 */
public class ArbolSufijos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Node raiz = new Node();
        raiz.add("hola", 0, 4);
        raiz.add("ola", 0, 4);
        raiz.add("la", 0, 4);
        raiz.add("a", 0, 4);
        System.out.println(raiz);
    }
    
}
