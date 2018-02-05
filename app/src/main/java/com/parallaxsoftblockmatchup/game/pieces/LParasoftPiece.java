package com.parallaxsoftblockmatchup.game.pieces;

import com.parallaxsoftblockmatchup.game.paraSoftSquare;

import android.content.Context;

public class LParasoftPiece extends parasoftPiece3 {

	private paraSoftSquare lparaSoftSquare;
	
	public LParasoftPiece(Context c) {
		super(c);
		lparaSoftSquare = new paraSoftSquare(paraSoftPiece.paraSofttype_L,c);
		paraSoftpattern[1][0] = lparaSoftSquare;
		paraSoftpattern[1][1] = lparaSoftSquare;
		paraSoftpattern[1][2] = lparaSoftSquare;
		paraSoftpattern[2][0] = lparaSoftSquare;
		paraSoftreDraw();
	}

	@Override
	public void reset(Context c) {
		super.reset(c);
		paraSoftpattern[1][0] = lparaSoftSquare;
		paraSoftpattern[1][1] = lparaSoftSquare;
		paraSoftpattern[1][2] = lparaSoftSquare;
		paraSoftpattern[2][0] = lparaSoftSquare;
		paraSoftreDraw();
	}

}
