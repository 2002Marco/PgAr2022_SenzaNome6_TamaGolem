package it.unibs.pa;

import java.util.ArrayList;

public class Scorta {
	
	private ArrayList <String>  scorta;
	public static int numPietreScorta;
	public static int numPietrePerElemento;
	
	public Scorta () {
		numPietreScorta = (int) (Math.ceil((2 * Main.numeroGolemPerGiocatore * Main.numeroPietrePerGolem)/ (double)Main.numeroElementi) * Main.numeroElementi);
		numPietrePerElemento = numPietreScorta / Main.numeroElementi;
		this.scorta = creaScorta();
	}
	
	public ArrayList <String> creaScorta() {		//riempie la scorta 
		
		scorta = new ArrayList <>();
		
		for(int  i = 0; i < Main.numeroElementi; i++) {
			for(int j = 0;  j < numPietrePerElemento; j++) {
				scorta.add(Main.elementi[i]);
			}
		}
		
		return scorta;
	}
	
	public void stampaScorta() {		//stampa la scorta
		
		for(int i = 0; i < Main.numeroElementi; i++) {
			int conta = 0;
			for(int j = 0; j < scorta.size(); j++) {
				if (scorta.get(j).equals(Main.elementi[i]))
					conta ++;		
			}
			System.out.println(Main.elementi[i] + ": " + conta + "x");
		}
		
	}
	
	public boolean trovaElemento (String s) {	//rimuove la pietra selezionata
	 
		 s = s.toUpperCase();
		for(int  i= 0; i < scorta.size(); i++) 
			if (scorta.get(i).equals(s)) {
				scorta.remove(i);
				return true;
			}
		return false;		//torna false se ho inserito un dato non valido (non e' presente nella scorta)
	}
	
	
}
	

