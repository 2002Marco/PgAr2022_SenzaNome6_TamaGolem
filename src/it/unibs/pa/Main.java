package it.unibs.pa;



public class Main {
	
	
	private static final String MSG_RIVINCITA = "\n\n Vuoi giocare ancora? (si/no)";


	public static String[] elementi = {"FUOCO", "TERRA", "ACQUA" , "ARIA" , "LOTTA" , "ACCIAO", "SPETTRO" , "ERBA", "DRAGO" , "BUIO"};
	
	
	private static final String NOME_GIOCATORE1 = "Giocatore 1 inserisci il tuo nome ->";
	private static final String NOME_GIOCATORE2 = "Giocatore 2 inserisci il tuo nome ->";
	private static final int MAX_ELEMENTI = 10;
	private static final int MIN_ELEMENTI = 3;
	private static final String STR_ELEMENTI = "Inserisci con quanti elementi vuoi giocare ->";
	private static final String MSG_STESSO_ORDINE = " hai inserito le stesse pietre nello stesso ordine, verranno mescolate casualmente";
	private static final String MSG_VITTORIA = " hai vinto";
	private static final String MSG_MORTE = " il golem è morto";
	private static final String MSG_STESSO_ELEMENTO = "Stesso elemento, nessuno danno";
	private static final String MSG_LANCIO = " ha lanciato una pietra ";
	public static  int numeroElementi = InputDati.leggiIntero(STR_ELEMENTI, MIN_ELEMENTI ,MAX_ELEMENTI);
	public static int numeroPietrePerGolem= (int) Math.ceil((numeroElementi +1)/3.0) + 1;
	public static int numeroGolemPerGiocatore = (int) Math.ceil((Main.numeroElementi -1) * (Main.numeroElementi  -2) / (2.0*Main.numeroPietrePerGolem));
	
	
	public static void main(String[] args) {
		
		
		boolean flag;
		
		do {
			Giocatore giocatore1 = new Giocatore(InputDati.leggiStringaNonVuota(NOME_GIOCATORE1));
			Giocatore giocatore2 = new Giocatore(InputDati.leggiStringaNonVuota(NOME_GIOCATORE2));
			Equilibro e = new Equilibro();
			Scorta sc = new Scorta();
			int golemVincitore = -1;
			int vincitore = 0;
			flag = false;
	
	
			
			System.out.println("\nNumero golem ha disposizione : " + numeroGolemPerGiocatore + "\n");
			
			evocaTamaGolem(giocatore1, sc, giocatore2);		//evoca i primi golem
			evocaTamaGolem(giocatore2, sc, giocatore1);
			
			
			while(true) {
				
				if (golemVincitore == 1) {			//SE HA VINTO IL GOLEM del giocatore 1 allora giocatore 2 deve evocare un'altro golem
					evocaTamaGolem(giocatore2, sc, giocatore1);
				}
				if (golemVincitore == 2)			
					evocaTamaGolem(giocatore1, sc, giocatore2);
				
				golemVincitore = combattimento(giocatore1, giocatore2);	
				
				if (giocatore1.getSquadra().isEmpty()) {	//controllo se la squadra è vuota, quindi ha perso
					vincitore = 2;
					break;
				}
				
				if (giocatore2.getSquadra().isEmpty()) {
					vincitore = 1;
					break;
				}
				System.out.println("\n" + giocatore1.getNome() + " ti sono rimasti -> " + giocatore1.getSquadra().size()  +" golem");
				System.out.println(giocatore2.getNome() + " ti sono rimasti -> " + giocatore2.getSquadra().size()+ " golem\n");

			}
			
			System.out.println((vincitore == 1) ? (giocatore1.getNome() + MSG_VITTORIA) : (giocatore2.getNome() + MSG_VITTORIA));
			
			e.stampaEquilibrio();
			
			String rivincita = InputDati.leggiStringaNonVuota(MSG_RIVINCITA).toLowerCase(); //ogni altra risposta vale come no
			if (rivincita.equals("si"))
				flag = true;
			else if (rivincita.equals("no"))
				flag = false;
				
		}while(flag);
		
	}
	
	public static void evocaTamaGolem(Giocatore g, Scorta sc, Giocatore g2) {
		

		System.out.println("\n" + g.getNome() + " scegli " + numeroPietrePerGolem + " pietre per il tuo Golem ");
		TamaGolem t = g.getSquadra().getFirst();
		TamaGolem t2 = g2.getSquadra().getFirst();
		System.out.println();
	
		for(int  i= 0; i < numeroPietrePerGolem; i++) {
			sc.stampaScorta();
			String pietraSelezionata = InputDati.leggiStringaNonVuota("Scrivi il nome della pietra ->");
			if(!sc.trovaElemento(pietraSelezionata)) {	//controllo se il dato inserito è valido
				System.out.println("Il dato inserito è invalido");
				i--;
			}
			else {
				t.aggiungiPietra(pietraSelezionata);
				
			}
		}
		
		if(t.controlloPietre(t2.getPietre())) {		//controllo che le pietre non siano nello stesso ordine
			System.out.println(g.getNome() + MSG_STESSO_ORDINE);	
			t.mescolaPietre(t2.getPietre());   		//altrimenti le mescolo
		}
	}
	

	public static int combattimento(Giocatore g1, Giocatore g2) {	//COMBATTIMENTO FRA DUE TAMAGOLEM
		TamaGolem t1 = g1.getSquadra().getFirst();
		TamaGolem t2 = g2.getSquadra().getFirst();
		while(true) {
			
			if (t1.getIndice() == numeroPietrePerGolem ) t1.resettaIndice();	//se ha finito il ciclo delle pietre allora lo fa ripartire da 0
			
			if(t2.getIndice() == numeroPietrePerGolem ) t2.resettaIndice();
			
			
			int p1 = prendiIlNumeroDellaPietra(t1.getPietre().get(t1.getIndice()));
			int p2 = prendiIlNumeroDellaPietra(t2.getPietre().get(t2.getIndice()));
		
			int danno = Equilibro.matriceAdiacenza[p1][p2];
			
			System.out.println(g1.getNome() + MSG_LANCIO + elementi[p1] +" " + g2.getNome() + MSG_LANCIO + elementi [p2]);
			if (danno > 0) {
				t2.prendiDanno(danno);
				if (t2.isMorto()) {
					t1.aumentaIndice();
					g2.rimuoviGolem();
					System.out.println(g2.getNome() + MSG_MORTE);
					return 1;
				}
						
				System.out.println(t1.getPietre().get(t1.getIndice()) + " vince su " + t2.getPietre().get(t2.getIndice()));
				System.out.println("Infliggendo " + danno + " al golem di " + g2.getNome());
				System.out.println();
			}
			else if (danno < 0) {
				t1.prendiDanno(-danno);
				if (t1.isMorto()) {
					t2.aumentaIndice();
					g1.rimuoviGolem();
					System.out.println(g1.getNome() + MSG_MORTE);
					return 2;
				}
				
				System.out.println(t2.getPietre().get(t2.getIndice()) + " vince su " + t1.getPietre().get(t1.getIndice()));
				System.out.println("Infliggendo " + -danno + " al golem di " + g1.getNome());
				System.out.println();
			}
			else {
				System.out.println(MSG_STESSO_ELEMENTO);
				System.out.println();
			}
			
			t1.aumentaIndice();	//passa alla pietra successiva
			t2.aumentaIndice();
			

		
		}		
		
	}
	
	public static int prendiIlNumeroDellaPietra(String s) {	//ritorna la posizione dell'elemento nella matrice 
		s = s.toUpperCase();
		for(int  i= 0; i < numeroElementi; i++) {
			if (s.equals(elementi[i]))
				return i;
		}
		return -1;
	}
	
	
	
}
