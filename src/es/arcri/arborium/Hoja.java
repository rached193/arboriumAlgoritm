/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.arcri.arborium;

/**
 *
 * @author Aron
 * @author cristian
 */
public class Hoja {
    private int docID;
    private int position;
    
    public Hoja(int position,int docID){
        this.docID=docID;
        this.position=position;
    }
    
    public int getDocId() {
		return docID;
	}
	public int getPosition() {
		return position;
	}
	@Override
	public String toString(){
		return docID+": "+position;
	}
}
