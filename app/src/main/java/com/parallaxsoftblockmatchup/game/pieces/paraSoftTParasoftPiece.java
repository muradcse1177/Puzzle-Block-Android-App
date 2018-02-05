package com.parallaxsoftblockmatchup.game.pieces;
import com.parallaxsoftblockmatchup.game.paraSoftSquare;
import android.content.Context;

public class paraSoftTParasoftPiece extends parasoftPiece3 {

	private paraSoftSquare tparaSoftSquare;

	public paraSoftTParasoftPiece(Context c) {
		super(c);
		tparaSoftSquare = new paraSoftSquare(paraSoftPiece.paraSofttype_T,c);
		paraSoftpattern[1][0] = tparaSoftSquare;
		paraSoftpattern[1][1] = tparaSoftSquare;
		paraSoftpattern[1][2] = tparaSoftSquare;
		paraSoftpattern[2][1] = tparaSoftSquare;
		paraSoftreDraw();
	}

	@Override
	public void reset(Context c) {
		super.reset(c);
		paraSoftpattern[1][0] = tparaSoftSquare;
		paraSoftpattern[1][1] = tparaSoftSquare;
		paraSoftpattern[1][2] = tparaSoftSquare;
		paraSoftpattern[2][1] = tparaSoftSquare;
		paraSoftreDraw();
	}

}
