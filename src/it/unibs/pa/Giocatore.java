package it.unibs.pa;

import java.util.ArrayDeque;
import java.util.Deque;

// il tamaGolem in prima posizione e' il golem attivo
public class Giocatore {

	private String nome;
	private Deque <TamaGolem> squadra;
	
	public Giocatore(String nome) {
		this.nome = nome;
		this.squadra =  new ArrayDeque <>();
		for(int i = 0; i < Main.numeroGolemPerGiocatore; i++) 
			squadra.addFirst(new TamaGolem());			//inzializza la squadra
		
	} 

	public String getNome() {
		return nome;
	}

	public Deque<TamaGolem> getSquadra() {
		return squadra;
	}
	
	public void rimuoviGolem() {	//rimuove il golem attivo
		squadra.removeFirst();
	}
	
	

}
