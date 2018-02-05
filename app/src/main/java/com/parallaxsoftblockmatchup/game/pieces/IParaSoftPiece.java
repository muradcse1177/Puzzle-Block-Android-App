package com.parallaxsoftblockmatchup.game.pieces;

import com.parallaxsoftblockmatchup.game.paraSoftSquare;

import android.content.Context;

public class IParaSoftPiece extends paraSoftPiece4 {
	
	private paraSoftSquare iparaSoftSquare;

	public IParaSoftPiece(Context c) {
		super(c);
		iparaSoftSquare = new paraSoftSquare(paraSoftPiece.paraSofttype_I,c);
		paraSoftpattern[2][0] = iparaSoftSquare;
		paraSoftpattern[2][1] = iparaSoftSquare;
		paraSoftpattern[2][2] = iparaSoftSquare;
		paraSoftpattern[2][3] = iparaSoftSquare;
		paraSoftreDraw();
	}
	
	@Override
	public void reset(Context c) {
		super.reset(c);
		paraSoftpattern[2][0] = iparaSoftSquare;
		paraSoftpattern[2][1] = iparaSoftSquare;
		paraSoftpattern[2][2] = iparaSoftSquare;
		paraSoftpattern[2][3] = iparaSoftSquare;
		paraSoftreDraw();
	}

}
