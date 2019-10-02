package poker;

import java.util.Arrays;
import java.util.Random;

public class pokerMain {

	public static void main(String[] args) {
		float[] wslZaehler = new float[ANZAHL_KOMBINATIONEN];
		for (int i = 0; i < wslZaehler.length; i++) {
			wslZaehler[i] = 0;
		}
		for (int i = 0; i < ANZAHL_DURCHLAEUFE; i++) {
			kartendeck(52);
			ziehung(5);
			//String ausgabe="High Card";
			if (checkPair(1)) {wslZaehler[1]++;} 					//One Pair
			if (twoPair()) {wslZaehler[2]++;} 						//Two Pair;
			if (checkPair(2)) {wslZaehler[3]++;}					//Three of a Kind
			if (straight()) {wslZaehler[4]++;}						//Straight
			if (flush()) {wslZaehler[5]++;}							//Flush
			if (checkPair(2) && checkPair(1)) {wslZaehler[6]++;} 	//Full House
			if (checkPair(3)) {wslZaehler[7]++;} 					//Four of a Kind
			if (straight() && flush()) {wslZaehler[8]++;}			//Straight Flush
			if (royalFlush()) {wslZaehler[9]++;} 					//Royal Flush
			//System.out.println(ausgabe);
		}
		float sumZaehler=0;
		for (int i = 1; i < ANZAHL_KOMBINATIONEN; i++) {
			sumZaehler= sumZaehler + wslZaehler[i];
		}
		wslZaehler[0] = ANZAHL_DURCHLAEUFE - sumZaehler;			//High Card
		wslAusgabe("High Card", wslZaehler[0]);
		wslAusgabe("One Pair", wslZaehler[1]);
		wslAusgabe("Two Pair", wslZaehler[2]);
		wslAusgabe("Three of a Kind", wslZaehler[3]);
		wslAusgabe("Straight", wslZaehler[4]);
		wslAusgabe("Flush", wslZaehler[5]);
		wslAusgabe("Full House", wslZaehler[6]);
		wslAusgabe("Four of a Kind", wslZaehler[7]);
		wslAusgabe("Straight Flush", wslZaehler[8]);
		wslAusgabe("Royal Flush", wslZaehler[9]);
	}
	static int[] kartendeck;
	static int kANZAHL;
	static final int ANZAHL_KARTEN_PRO_FARBE=13;
	static int[] hand;
	static int hANZAHL;
	static final int ANZAHL_DURCHLAEUFE=1000000;
	static final int ANZAHL_KOMBINATIONEN=10;

	public static void kartendeck(int LAENGE) {
		kANZAHL=LAENGE;
		kartendeck=new int[kANZAHL];
		for (int i=0;i<kANZAHL;i++) {
			kartendeck[i]=i;
		}
	}
	public static void ziehung(int gezogeneKarten) {
		hANZAHL=gezogeneKarten;
		Random zufall=new Random();
		hand=new int[hANZAHL];
		int moeglKarten=kANZAHL;
		for (int i=0;i<hANZAHL;i++) {
			int z=zufall.nextInt(kANZAHL);
			int[] ph=kartendeck;
			hand[i]=ph[z];
			ph[z]=kartendeck[moeglKarten-1];
			ph[moeglKarten-1]=kartendeck[z];
			moeglKarten--;
		}
		/*System.out.print("Gezogene Karten : ");
		for (int i=0;i<hANZAHL;i++) {
			System.out.print(hand[i]+" ");
		}
		System.out.println();*/
	}
	public static int farbe(int karte) {
		return karte/ANZAHL_KARTEN_PRO_FARBE;
	}
	public static int kartensymbolik(int karte) {
		return karte%ANZAHL_KARTEN_PRO_FARBE;
	}
	public static boolean checkPair(int gleiche) {
		boolean pair=false;
		int zaehler=0;
		for (int i=0;i<hANZAHL;i++) {
			for (int j=(i+1);j<hANZAHL;j++) {
				if (kartensymbolik(hand[i])==kartensymbolik(hand[j])) {
					zaehler++;
					if (zaehler==gleiche) {
						pair=true;
					}
					if (zaehler>gleiche) {
						pair=false;
					}
				}
			}
			zaehler=0;
			if (pair==true) { //damit z.B.: bei einem Full House bei der Suche nach OnePair die Funktion nicht fälschlicherwiese nur für Three of a Kind "true" zurückgibt
				break;
			}
		}
		return pair;
	}
	public static boolean twoPair() {
		boolean twoPair=false;
		int zaehler=0;
		int paare=0;
		int alteKarte=ANZAHL_KARTEN_PRO_FARBE;				//Sicherstellung, dass zaehler beim ersten Schleifendurchlauf reseted wird
		for (int i=0;i<hANZAHL;i++) {
			for (int j=(i+1);j<hANZAHL;j++) {
				if (kartensymbolik(hand[i])==kartensymbolik(hand[j])) {
					int neueKarte=kartensymbolik(hand[i]);
					if (alteKarte!=neueKarte) {
						if (zaehler<2) {
							paare++;
						}
						if (zaehler==2) {
							paare--;
						}
						zaehler=0;
						alteKarte=neueKarte;
					}
					zaehler++;
				}
			}
		}
		if (paare==2) {
			twoPair=true;
		}
		return twoPair;
	}
	public static boolean flush() {
		boolean flush=true;
		int farbeHand=farbe(hand[0]);
		for (int i=1;i<hANZAHL;i++) {
			if(farbe(hand[i])!=farbeHand) {
				flush=false;
			}
		}
		return flush;
	}
	public static boolean straight() {
		boolean straight=true;
		int[] handSort=new int[hANZAHL];
		for (int i=0;i<hANZAHL;i++) {
			handSort[i]=kartensymbolik(hand[i]);
		}
		Arrays.sort(handSort);
		int letzteKarte=handSort[0];
		for(int i=1;i<hANZAHL;i++) {
			if(handSort[i]!=letzteKarte+1) {
				straight=false;
			}
			letzteKarte=handSort[i];
		}
		return straight;
	}
	public static boolean royalFlush() {
		boolean royalFlush=false;
		int[] handSort=new int[hANZAHL];
		for (int i=0;i<hANZAHL;i++) {
			handSort[i]=kartensymbolik(hand[i]);
		}
		Arrays.sort(handSort);
		if (straight() && flush() && handSort[hANZAHL-1]==12) {
			royalFlush=true;
		}
		return royalFlush;
	}
	public static void wslAusgabe(String kombi, float zaehler) {
		float wsl = (zaehler / (float)ANZAHL_DURCHLAEUFE) * 100;
		String s = String.format("%f", wsl); //Festkommadarstellung
		System.out.println("Wahrscheinlichkeit für "+kombi+": "+s+"%");
		//System.out.println(zaehler);
		System.out.println();
	}
}