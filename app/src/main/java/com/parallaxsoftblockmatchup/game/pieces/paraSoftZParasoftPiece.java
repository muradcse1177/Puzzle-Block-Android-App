package com.parallaxsoftblockmatchup.game.pieces;
import com.parallaxsoftblockmatchup.game.paraSoftSquare;
import android.content.Context;

public class paraSoftZParasoftPiece extends parasoftPiece3 {

	private paraSoftSquare zparaSoftSquare;

	public paraSoftZParasoftPiece(Context c) {
		super(c);
		zparaSoftSquare = new paraSoftSquare(paraSoftPiece.paraSofttype_Z,c);
		paraSoftpattern[1][0] = zparaSoftSquare;
		paraSoftpattern[1][1] = zparaSoftSquare;
		paraSoftpattern[2][1] = zparaSoftSquare;
		paraSoftpattern[2][2] = zparaSoftSquare;
		paraSoftreDraw();
	}

	@Override
	public void reset(Context c) {
		super.reset(c);
		paraSoftpattern[1][0] = zparaSoftSquare;
		paraSoftpattern[1][1] = zparaSoftSquare;
		paraSoftpattern[2][1] = zparaSoftSquare;
		paraSoftpattern[2][2] = zparaSoftSquare;
		paraSoftreDraw();
	}

}
