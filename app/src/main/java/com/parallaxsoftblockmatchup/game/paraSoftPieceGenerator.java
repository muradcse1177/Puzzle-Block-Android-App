package com.parallaxsoftblockmatchup.game;
import java.util.Random;

public class paraSoftPieceGenerator {

	public static final int paraSoftSTRAT_RANDOM = 0;
	public static final int paraSoftSTRAT_7BAG = 1;
	
	int paraSoftstrategy;
	int bag[];
	int paraSoftbagPointer;
	private Random rndgen;
	
	public paraSoftPieceGenerator(int strat) {
		bag = new int[7];
		for(int i = 0; i < 7; i++)
			bag[i] = i;
		
		rndgen = new Random(System.currentTimeMillis());
		if(strat==paraSoftSTRAT_RANDOM)
			this.paraSoftstrategy = paraSoftSTRAT_RANDOM;
		else
			this.paraSoftstrategy = paraSoftSTRAT_7BAG;
		for(int i = 0; i < 6; i++) {
			int c = rndgen.nextInt(7-i);
			int t = bag[i]; bag[i] = bag[i+c]; bag[i+c] = t;
		}
		paraSoftbagPointer = 0;
	}

	public int next() {
		if(paraSoftstrategy== paraSoftSTRAT_RANDOM)
			return rndgen.nextInt(7);
		else {
			if(paraSoftbagPointer < 7) {
				paraSoftbagPointer++;
				return bag[paraSoftbagPointer - 1];
			} else {
				for(int i = 0; i < 6; i++) {
					int c = rndgen.nextInt(7-i);
					int t = bag[i]; bag[i] = bag[i+c]; bag[i+c] = t;	/* swap */
				}
				paraSoftbagPointer = 1;
				return bag[paraSoftbagPointer - 1];
			}
		}
	}
}