package com.parallaxsoftblockmatchup.game.pieces;

import com.parallaxsoftblockmatchup.game.paraSoftSquare;

import android.content.Context;

public class OParaSoftPiece extends paraSoftPiece4 {

	private paraSoftSquare oparaSoftSquare;
	
	public OParaSoftPiece(Context c) {
		super(c);
		oparaSoftSquare = new paraSoftSquare(paraSoftPiece.paraSofttype_O,c);
		paraSoftpattern[1][1] = oparaSoftSquare;
		paraSoftpattern[1][2] = oparaSoftSquare;
		paraSoftpattern[2][1] = oparaSoftSquare;
		paraSoftpattern[2][2] = oparaSoftSquare;
		paraSoftreDraw();
	}

	@Override
	public void reset(Context c) {
		super.reset(c);
		paraSoftpattern[1][1] = oparaSoftSquare;
		paraSoftpattern[1][2] = oparaSoftSquare;
		paraSoftpattern[2][1] = oparaSoftSquare;
		paraSoftpattern[2][2] = oparaSoftSquare;
		paraSoftreDraw();
	}

}
