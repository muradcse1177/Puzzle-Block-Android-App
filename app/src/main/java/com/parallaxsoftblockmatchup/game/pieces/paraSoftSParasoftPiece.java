package com.parallaxsoftblockmatchup.game.pieces;
import com.parallaxsoftblockmatchup.game.paraSoftSquare;

import android.content.Context;

public class paraSoftSParasoftPiece extends parasoftPiece3 {

	private paraSoftSquare sparaSoftSquare;
	
	public paraSoftSParasoftPiece(Context c) {
		super(c);
		sparaSoftSquare = new paraSoftSquare(paraSoftPiece.paraSofttype_S,c);
		paraSoftpattern[1][1] = sparaSoftSquare;
		paraSoftpattern[1][2] = sparaSoftSquare;
		paraSoftpattern[2][0] = sparaSoftSquare;
		paraSoftpattern[2][1] = sparaSoftSquare;
		paraSoftreDraw();
	}

	@Override
	public void reset(Context c) {
		super.reset(c);
		paraSoftpattern[1][1] = sparaSoftSquare;
		paraSoftpattern[1][2] = sparaSoftSquare;
		paraSoftpattern[2][0] = sparaSoftSquare;
		paraSoftpattern[2][1] = sparaSoftSquare;
		paraSoftreDraw();
	}

}
