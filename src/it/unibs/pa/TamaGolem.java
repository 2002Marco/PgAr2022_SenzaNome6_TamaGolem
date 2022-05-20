package it.unibs.pa;


import java.util.ArrayList;
import java.util.Collections;


public class TamaGolem {
	
	public static final int VITA_INIZIALE = 10;
	private int puntiVita;
	private ArrayList <String> pietre;
	private int indice;		//indica che pietra deve lanciare
	
	public TamaGolem () {
		this.puntiVita = VITA_INIZIALE;
		this.pietre = new ArrayList<>();
		this.indice = 0;
	}
	
	public void aggiungiPietra(String s) {
		pietre.add(s);
	}
	
	public void prendiDanno(int d) {
		puntiVita -= d;
	}
	
	public boolean isMorto() {
		if (puntiVita <= 0)
			return true;
		else 
			return false;
	}

	public int getPuntiVita() {
		return puntiVita;
	}

	public ArrayList<String> getPietre() {
		return pietre;
	}

	public int getIndice() {
		return indice;
	}

	public void aumentaIndice() {
		this.indice += 1;
	}
	
	public void resettaIndice() {
		this.indice = 0;
	} 

	public void mescolaPietre(ArrayList <String> altrePietre) { //continua a mescolare le pietre fino che non ottiene una combinazione valida
		do 
			Collections.shuffle(pietre);
		while(controlloPietre(altrePietre));
	}
	
	public boolean controlloPietre(ArrayList <String> altrePietre) { //controlla se i due set di pietre sono uguali(stesso ordine delle pietre)
		if (this.pietre.equals(altrePietre))
			return true;
		else 
			return false;
	}
}
