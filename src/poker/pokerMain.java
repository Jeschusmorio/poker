package poker;

import java.util.Random;

public class pokerMain {

	public static void main(String[] args) {
		karten(52);
		ziehung(5);
		System.out.println();
		if (onePair()) System.out.println("One Pair");
		if (twoPair()) System.out.println("Two Pair");
		if (threeOfAKind()) System.out.println("Three of a Kind");
		if (fourOfAKind()) System.out.println("Four of a Kind");
		if (flush()) System.out.println("Flush");
	}
	static int[] karten;
	static int kANZAHL;
	static int[] hand={0,1,2,3,4};
	static int hANZAHL;
	
	public static void karten(int LAENGE) {
		kANZAHL=LAENGE;
		karten=new int[kANZAHL];
		for (int i=0;i<kANZAHL;i++) {
			karten[i]=i;
		}
	}
	public static void ziehung(int gezogeneKarten) {
		hANZAHL=gezogeneKarten;
		Random zufall=new Random();
		/*hand=new int[hANZAHL];
		int moeglKarten=kANZAHL;
		for (int i=0;i<hANZAHL;i++) {
			int z=zufall.nextInt(kANZAHL);
			int[] ph=karten;
			hand[i]=ph[z];
			ph[z]=karten[moeglKarten-1];
			ph[moeglKarten-1]=karten[z];
			moeglKarten--;
		}*/
		System.out.print("Gezogene Karten : ");
		for (int i=0;i<hANZAHL;i++) {
			System.out.print(hand[i]+" ");
		}
		System.out.println();
	}
	public static int farbe(int karte) {
		return karte/13;
	}
	public static int kartensymbolik(int karte) {
		return karte%13;
	}
	public static boolean highCard() {
		boolean highCard=true;
		return highCard;
	}
	public static boolean onePair() {
		boolean onePair=false;
		int zaehler=0;
		for(int i=0;i<hANZAHL;i++) {
			for (int j=(i+1);j<hANZAHL;j++) {
				if(kartensymbolik(hand[i])==kartensymbolik(hand[j])) zaehler++;
			}
		}
		if (zaehler==1) onePair=true;
		return onePair;
	}
	public static boolean twoPair() {
		boolean twoPair=false;
		int zaehler=0;
		int paare=0;
		int alteKarte=14;						//Sicherstellung, dass zaehler beim ersten Schleifendurchlauf reseted wird
		for (int i=0;i<hANZAHL;i++) {
			for (int j=(i+1);j<hANZAHL;j++) {
				if (kartensymbolik(hand[i])==kartensymbolik(hand[j])) {
					int neueKarte=kartensymbolik(hand[i]);
					if (alteKarte!=neueKarte) {
						if (zaehler<2) paare++;
						if (zaehler==2) paare--;
						zaehler=0;
						alteKarte=neueKarte;
					}
					zaehler++;
				}
			}
		}
		if (paare==2) twoPair=true;
		return twoPair;
	}
	public static boolean threeOfAKind() {
		boolean threeOfAKind=false;
		int zaehler=0;
		for (int i=0;i<hANZAHL;i++) {
			for (int j=0;j<hANZAHL;j++) {
				if (i==j) continue;
				if (kartensymbolik(hand[i])==kartensymbolik(hand[j])) {
					zaehler++;
					if (zaehler==2) threeOfAKind=true;
					if (zaehler>2) threeOfAKind=false;
				}
			}
			zaehler=0;
		}
		return threeOfAKind;
	}
	public static boolean fourOfAKind() {
		boolean fourOfAKind=false;
		int zaehler=0;
		for (int i=0;i<hANZAHL;i++) {
			for (int j=0;j<hANZAHL;j++) {
				if (i==j) continue;
				if (kartensymbolik(hand[i])==kartensymbolik(hand[j])) {
					zaehler++;
					if (zaehler==3) fourOfAKind=true;
					if (zaehler>3) fourOfAKind=false;
				}
			}
			zaehler=0;
		}
		return fourOfAKind;
	}
	public static boolean flush() {
		boolean flush=true;
		for (int i=0;i<hANZAHL;i++) {
			for (int j=0;j<hANZAHL;j++) {
				if (i==j) continue;
				if (farbe(hand[i])!=farbe(hand[j])) flush=false;
			}
		}
		return flush;
	}
}