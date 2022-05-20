package it.unibs.pa;


import java.util.Random;

public class Equilibro {
	
	public static int [][] matriceAdiacenza;
	
	public Equilibro() {
		do {
			inizializzaMatrice();
			for (int i = 0; i < Main.numeroElementi  -1; i ++)	//genero fino alla penultima riga (l'ultima è gia completa)
				generaRiga(i);
		}while(!isValido());
		
	}
		
	public int [][] inizializzaMatrice (){
		matriceAdiacenza = new int  [Main.numeroElementi][Main.numeroElementi];
		for(int i = 0; i < Main.numeroElementi; i++) 	//imposto la diagonale uguale a zero e il resto a 200
			for(int k = 0;  k< Main.numeroElementi; k++) {
				if (i == k)
					matriceAdiacenza[i][k] = 0;
				else
					matriceAdiacenza[i][k] = 200;
			}
			
	return matriceAdiacenza;	
	
	}
	
	public void generaRiga(int riga) {  //funzione che genera la riga 
		int sommaRiga = 0;
		int sommaColonna = 0;
		int elementoDaGenerare = riga +1;
		int righeRimaste = Main.numeroElementi - riga -1;
		int colonneRimaste = Main.numeroElementi - elementoDaGenerare -1;
		
		
		for(int i = 0; i<Main.numeroElementi; i++) 		//calcola la somma degli elementi della riga corrente
			if (matriceAdiacenza[riga][i] != 200)
			sommaRiga += matriceAdiacenza[riga][i];
		
		if (riga == Main.numeroElementi-2) {			// se è la penultima riga non serve generare nessun valore
			matriceAdiacenza[riga][elementoDaGenerare] =  -sommaRiga;
			matriceAdiacenza[elementoDaGenerare][riga] = sommaRiga;
			return;
		}
		
		int i = 0;
		int valore = 0;
		
		while(elementoDaGenerare < Main.numeroElementi -1) { //rimando nel ciclo fintanto che non rimane un elemento per concludere la riga
			do {
				if (i > 50) {		//per numeri alti può succedere che entri in un loop infinito perchè non essite un valore che soddisfi 
					return;			//le condizioni (succede raramente)
				}
				valore = estraiIntero(-TamaGolem.VITA_INIZIALE, TamaGolem.VITA_INIZIALE );	//estraggo un numero casuale fra -10  e 10 e lo assegno al primo posto senza un valore 
				matriceAdiacenza[riga][elementoDaGenerare] = valore;
				matriceAdiacenza[elementoDaGenerare][riga] = -valore;
				
				sommaColonna = sommaColonne(elementoDaGenerare); // calcolo la somma dei valori sulla colonna corrente
				i++;
			}while(valore == 0 || Math.abs(sommaRiga + valore) > TamaGolem.VITA_INIZIALE*(colonneRimaste) || (sommaRiga + valore == 0 && colonneRimaste == 1)
					|| (sommaColonna == 0 && righeRimaste == 1) || (Math.abs(sommaColonna) > TamaGolem.VITA_INIZIALE*(righeRimaste-1)));
		
			/*controllo che il valore generato sia diverso da zero, che la somma dei valori della riga (colonna) possa essere azzerata e
			  che la somma della riga (colonna) non sia zero se manca un solo elemento  */
		
		colonneRimaste--;
		sommaRiga += valore;
		elementoDaGenerare++;
	}
	matriceAdiacenza[riga][elementoDaGenerare] =  -sommaRiga; 
	matriceAdiacenza[elementoDaGenerare][riga] = sommaRiga;
		
	}
	
	public  int estraiIntero(int min, int max) //funzione per estrarre un intero fra un massimo e un minimo 
	{
		Random rand = new Random();
		
		
	 int range = max + 1 - min;

	 int casual = rand.nextInt(range);
	 return casual + min;
	}
	
	public  int sommaColonne(int colonna) { // funzione che calcola la somma di una colonna
		int somma=0;
		for(int i= 0; i < Main.numeroElementi; i++) {
			if(matriceAdiacenza[i][colonna] != 200)
				somma += matriceAdiacenza[i][colonna];
		}
		return somma;
	}
	
	public boolean isValido() {	//funzione che controlla che ogni elemento della matrice (tranne la diagonale) sia diverso da 200
		for(int i = 0; i < Main.numeroElementi; i++) {	
			for(int k = 0;  k< Main.numeroElementi; k++) {
				if ( (matriceAdiacenza[i][k] > 10 || matriceAdiacenza[i][k] < -10) || (matriceAdiacenza[i][k] == 0 && i!=k)) 
					
					return false;
				
			}
		}
		
		return true;
	}

	public int[][] getMatriceAdiacenza() {
		return matriceAdiacenza;
	}
	
	public  void stampaEquilibrio() {
			
		int j;
		
		
		for(int i= -1; i < Main.numeroElementi; i++) {
			for(j = 0; j < Main.numeroElementi ; j++) {
				if (i == -1)
					System.out.print("\t" + Main.elementi[j]);
				else
					System.out.print("\t  " +  matriceAdiacenza[i][j]);
			}
			System.out.print((i==Main.numeroElementi -1) ? "" : "\n" + Main.elementi[i + 1]);
		}
	}
	
	
	
	
}
