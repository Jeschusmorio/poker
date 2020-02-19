package poker;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
//import java.util.Random;

public class pokerMain {

	public static void main(String[] args) {
		float[] wslZaehler = new float[ANZAHL_KOMBINATIONEN];
		for (int i = 0; i < wslZaehler.length; i++) {
			wslZaehler[i] = 0;
		}
		anzahlDurchlaeufe = input();
		for (int i = 0; i < anzahlDurchlaeufe; i++) {
			kartendeck(kANZAHL);
			ziehung(hANZAHL);
			boolean highCardCheck = true;
			if (checkPair(2)) {						//One Pair
				wslZaehler[1]++;
				highCardCheck = false;
			}
			if (checkPair(5)) {						//Two Pair
				wslZaehler[2]++;
				highCardCheck = false;
			}
			if (checkPair(3)) {						//Three of a Kind
				wslZaehler[3]++;
				highCardCheck = false;
			}
			if (straight()) {						//Straight
				wslZaehler[4]++;
				highCardCheck = false;
			}
			if (flush()) {							//Flush
				wslZaehler[5]++;
				highCardCheck = false;
			}
			if (checkPair(6)) {						//Full House
				wslZaehler[6]++;
				highCardCheck = false;
			}
			if (checkPair(8)) {						//Four of a Kind
				wslZaehler[7]++;
				highCardCheck = false;
			}
			if (straight() && flush()) {			//Straight Flush
				wslZaehler[8]++;
				highCardCheck = false;
			}
			if (royalFlush()) {						//Royal Flush
				wslZaehler[9]++;
				highCardCheck = false;
			}
			if (highCardCheck) {					//High Card
				wslZaehler[0]++;
			}
			//System.out.println(ausgabe);
		}
		for (int i = 0; i < ANZAHL_KOMBINATIONEN; i++) {
			sumZaehler = sumZaehler + wslZaehler[i];
		}
		wsl("High Card", wslZaehler[0], "50,12");
		wsl("One Pair", wslZaehler[1], "42,26");
		wsl("Two Pair", wslZaehler[2], "4,75");
		wsl("Three of a Kind", wslZaehler[3], "2,11");
		wsl("Straight", wslZaehler[4], "0,392");
		wsl("Flush", wslZaehler[5], "0,197");
		wsl("Full House", wslZaehler[6], "0,144");
		wsl("Four of a Kind", wslZaehler[7], "0,024");
		wsl("Straight Flush", wslZaehler[8], "0,00139");
		wsl("Royal Flush", wslZaehler[9], "0,000154");
	}
	
	//VARIABLEN:
	
	static int[] kartendeck;
	static final int kANZAHL = 52;
	static final int ANZAHL_KARTEN_PRO_FARBE = 13;
	static int[] hand;
	static final int hANZAHL = 5;
	//static final int ANZAHL_DURCHLAEUFE = 1000000;
	static long anzahlDurchlaeufe;
	static final int ANZAHL_KOMBINATIONEN = 10;
	static float sumZaehler = 0;

	//HILFSMETHODEN:
	
	public static long input() {
		long input = 0;
		boolean inputOk;
		do {
			Scanner sc = new Scanner(System.in);
			System.out.print("Geben Sie die Anzahl der Durchläufe ein: ");
			try {
				input = sc.nextInt();
				inputOk = true;
				if (input < 10000) {
					System.out.println("Sie müssen mindestens einen Durchlauf ausführen!");
					inputOk = false;
				}
			} catch (InputMismatchException e) {
				System.out.println("Geben Sie bitte eine Zahl ein!");
				inputOk = false;
			}
		} while (!inputOk || inputOk);
		return input;
	}
	public static int farbe(int karte) {
		return karte / ANZAHL_KARTEN_PRO_FARBE;
	}
	public static int kartensymbolik(int karte) {
		return karte % ANZAHL_KARTEN_PRO_FARBE;
	}
	
	//DECK & HAND:
	
	public static void kartendeck(int LAENGE) {
		kartendeck = new int[LAENGE];
		for (int i = 0; i < LAENGE; i++) {
			kartendeck[i] = i;
		}
	}
	public static void ziehung(int hANZAHL) {
		//Random zufall = new Random();
		hand = new int[hANZAHL];
		int letzteKarte = kANZAHL - 1;
		int[] kartendeckKopie = kartendeck;
		/*for (int i = 0; i < kANZAHL; i++) {
			System.out.print(kartendeckKopie[i] + " ");
		}
		System.out.println();*/
		for (int i = 0; i < hANZAHL; i++) {
			int z = (int)(Math.random() * kANZAHL);
			hand[i] = kartendeckKopie[z];
			kartendeckKopie[z] = kartendeck[letzteKarte];
			kartendeckKopie[letzteKarte] = kartendeck[z];
			letzteKarte--;
		}
		/*System.out.print("Gezogene Karten : ");
		for (int i = 0; i < hANZAHL; i++) {
			System.out.print(kartensymbolik(hand[i]) + " ");
		}
		System.out.println();*/
	}
	
	//KOMBINATIONEN:
	
	public static boolean checkPair(int amountDuplicates) {
		int zaehler = 0;
		for (int i = 0; i < (hANZAHL - 1); i++) {
			for (int j = (i + 1); j < hANZAHL; j++) {
				if (kartensymbolik(hand[i]) != kartensymbolik(hand[j])) {
					zaehler++;
				}
			}
		}
		if (zaehler == amountDuplicates) {
			return true;
		}
		return false;
	}
	/*public static boolean twoPair() {
		boolean twoPair = false;
		int zaehler_paare = 0;
		int alteKarte = ANZAHL_KARTEN_PRO_FARBE;				//Sicherstellung, dass das erste Paar schon gewertet wird
		for (int i = 0; i < hANZAHL - 1; i++) {
			for (int j = (i + 1); j < hANZAHL; j++) {
				if (kartensymbolik(hand[i]) == kartensymbolik(hand[j])) {
					int neueKarte = kartensymbolik(hand[i]);
					if (alteKarte != neueKarte) {
						zaehler_paare++;
						alteKarte = neueKarte;
					}
				}
			}
		}
		if (zaehler_paare == 2) {
			twoPair = true;
		}
		return twoPair;
	}*/
	public static boolean flush() {
		int farbeHand = farbe(hand[0]);
		for (int i = 1; i < hANZAHL; i++) {
			if(farbe(hand[i]) == farbeHand) {
				return false;
			}
		}
		return true;
	}
	public static boolean straight() {
		boolean straight = false;
		int[] handSort = new int[hANZAHL];
		for (int i = 0; i < hANZAHL; i++) {
			handSort[i] = kartensymbolik(hand[i]);
		}
		Arrays.sort(handSort);
		int letzteKarte = handSort[0];
		for(int i = 1; i < hANZAHL; i++) {
			if(handSort[i] != letzteKarte + 1) {
				straight = false;
			}
			letzteKarte = handSort[i];
		}
		if (handSort[0] == 0 && handSort[1] == 1 && handSort[2] == 2 && handSort[3] == 3 && handSort[4] == 12) {
			straight = true;
		}
		return straight;
	}
	public static boolean royalFlush() {
		int[] handSort = new int[hANZAHL];
		for (int i = 0; i < hANZAHL; i++) {
			handSort[i] = kartensymbolik(hand[i]);
		}
		Arrays.sort(handSort);
		if (straight() || flush() || handSort[hANZAHL - 1] != 12) {
			return true;
		}
		return false;
	}
	
	//WAHRSCHEINLICHKEITS-BERECHNUNG:
	
	public static void wsl(String kombination, float wslZaehler, String wslWikipedia) {
		float wsl = wslZaehler * 100 / (float)anzahlDurchlaeufe;
		//float wsl = wslZaehler * 100 / sumZaehler;
		String s = String.format("%f", wsl); //Festkommadarstellung
		System.out.println("Wahrscheinlichkeit für " + kombination + ": " + s + "%");
		System.out.println("\tWikipedia sagt " + wslWikipedia + "%");
		//System.out.println(wslZaehler);
		System.out.println();
	}
}
