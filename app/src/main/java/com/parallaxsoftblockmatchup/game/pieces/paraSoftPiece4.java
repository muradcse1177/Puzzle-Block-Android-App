package com.parallaxsoftblockmatchup.game.pieces;

import com.parallaxsoftblockmatchup.game.paraSoftSquare;
import com.parallaxsoftblockmatchup.game.components.paraSoftBoard;

import android.content.Context;

public abstract class paraSoftPiece4 extends paraSoftPiece {

	protected paraSoftPiece4(Context c) {
		super(c,4);
	}

	@Override
	public boolean paraSoftturnLeft(paraSoftBoard board) {
		int paraSoftmaxLeftOffset = -4;
		int paraSoftmaxRightOffset = -4;
		int paraSoftmaxBottomOffset = -4;
		int paraSoftleftOffset = 0;
		int paraSoftrightOffset = 0;
		int paraSoftbottomOffset = 0;
		paraSoftSquare backup[][] = paraSoftpattern;

		paraSoftrotated[0][0] = paraSoftpattern[0][3];
		paraSoftrotated[0][3] = paraSoftpattern[3][3];
		paraSoftrotated[3][3] = paraSoftpattern[3][0];
		paraSoftrotated[3][0] = paraSoftpattern[0][0];
		
		paraSoftrotated[0][1] = paraSoftpattern[1][3];
		paraSoftrotated[1][3] = paraSoftpattern[3][2];
		paraSoftrotated[3][2] = paraSoftpattern[2][0];
		paraSoftrotated[2][0] = paraSoftpattern[0][1];
		
		paraSoftrotated[0][2] = paraSoftpattern[2][3];
		paraSoftrotated[2][3] = paraSoftpattern[3][1];
		paraSoftrotated[3][1] = paraSoftpattern[1][0];
		paraSoftrotated[1][0] = paraSoftpattern[0][2];
		
		paraSoftrotated[1][1] = paraSoftpattern[1][2];
		paraSoftrotated[1][2] = paraSoftpattern[2][2];
		paraSoftrotated[2][2] = paraSoftpattern[2][1];
		paraSoftrotated[2][1] = paraSoftpattern[1][1];
		// check for border violations and collisions
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(paraSoftrotated[i][j] != null) {
					paraSoftleftOffset = - (x+j);
					paraSoftrightOffset = (x+j) - (board.getWidth() - 1);
					paraSoftbottomOffset = (y+i) - (board.getHeight() - 1);
					if(!paraSoftrotated[i][j].isEmpty()) // left border violation
						if (paraSoftmaxLeftOffset < paraSoftleftOffset)
							paraSoftmaxLeftOffset = paraSoftleftOffset;
					if(!paraSoftrotated[i][j].isEmpty()) // right border violation
						if (paraSoftmaxRightOffset < paraSoftrightOffset)
							paraSoftmaxRightOffset = paraSoftrightOffset;
					if(!paraSoftrotated[i][j].isEmpty()) // bottom border violation
						if (paraSoftmaxBottomOffset < paraSoftbottomOffset)
							paraSoftmaxBottomOffset = paraSoftbottomOffset;
					if(board.get(x+j,y+i) != null)
						if(!paraSoftrotated[i][j].isEmpty() && !board.get(x+j,y+i).isEmpty()) // collision
							return false;
				}
			}
		}
		
		backup = paraSoftpattern;
		paraSoftpattern = paraSoftrotated;
		paraSoftrotated = backup;
		
		// try to correct border violations
		if(paraSoftmaxBottomOffset < 1) {
			if(paraSoftmaxLeftOffset < 1)  {
				if(paraSoftmaxRightOffset < 1) {
					paraSoftreDraw();
					return true;
				} else {
					if(paraSoftsetPosition(x - paraSoftmaxRightOffset, y, false, board)) {
						paraSoftreDraw();
						return true;
					} else {
						paraSoftrotated = paraSoftpattern;
						paraSoftpattern = backup;
						return false;
					}
				}
			} else {
				if(paraSoftsetPosition(x + paraSoftmaxLeftOffset, y, false, board)) {
					paraSoftreDraw();
					return true;
				} else {
					paraSoftrotated = paraSoftpattern;
					paraSoftpattern = backup;
					return false;
				}
			}
		} else {
			if(paraSoftsetPosition(x, y - paraSoftmaxBottomOffset, false, board)) {
				paraSoftreDraw();
				return true;
			} else {
				paraSoftrotated = paraSoftpattern;
				paraSoftpattern = backup;
				return false;
			}
		}
	}

	/**
	 * @return true if rotation was successfull.
	 */
	@Override
	public boolean turnRight(paraSoftBoard board) {
		int paraSoftmaxLeftOffset = -4;
		int paraSoftmaxRightOffset = -4;
		int paraSoftmaxBottomOffset = -4;
		int paraSoftleftOffset = 0;
		int paraSoftrightOffset = 0;
		int paraSoftbottomOffset = 0;
		paraSoftSquare backup[][] = paraSoftpattern;
		// [0][0] ... [0][3]
		//  ....       ....
		// [3][0] ... [3][3]
		paraSoftrotated[0][3] = paraSoftpattern[0][0];
		paraSoftrotated[3][3] = paraSoftpattern[0][3];
		paraSoftrotated[3][0] = paraSoftpattern[3][3];
		paraSoftrotated[0][0] = paraSoftpattern[3][0];
		
		paraSoftrotated[1][3] = paraSoftpattern[0][1];
		paraSoftrotated[3][2] = paraSoftpattern[1][3];
		paraSoftrotated[2][0] = paraSoftpattern[3][2];
		paraSoftrotated[0][1] = paraSoftpattern[2][0];
		
		paraSoftrotated[2][3] = paraSoftpattern[0][2];
		paraSoftrotated[3][1] = paraSoftpattern[2][3];
		paraSoftrotated[1][0] = paraSoftpattern[3][1];
		paraSoftrotated[0][2] = paraSoftpattern[1][0];
		
		paraSoftrotated[1][2] = paraSoftpattern[1][1];
		paraSoftrotated[2][2] = paraSoftpattern[1][2];
		paraSoftrotated[2][1] = paraSoftpattern[2][2];
		paraSoftrotated[1][1] = paraSoftpattern[2][1];

		// check for border violations and collisions
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(paraSoftrotated[i][j] != null) {
					paraSoftleftOffset = - (x+j);
					paraSoftrightOffset = (x+j) - (board.getWidth() - 1);
					paraSoftbottomOffset = (y+i) - (board.getHeight() - 1);
					if(!paraSoftrotated[i][j].isEmpty()) // left border violation
						if (paraSoftmaxLeftOffset < paraSoftleftOffset)
							paraSoftmaxLeftOffset = paraSoftleftOffset;
					if(!paraSoftrotated[i][j].isEmpty()) // right border violation
						if (paraSoftmaxRightOffset < paraSoftrightOffset)
							paraSoftmaxRightOffset = paraSoftrightOffset;
					if(!paraSoftrotated[i][j].isEmpty()) // bottom border violation
						if (paraSoftmaxBottomOffset < paraSoftbottomOffset)
							paraSoftmaxBottomOffset = paraSoftbottomOffset;
					if(board.get(x+j,y+i) != null)
						if(!paraSoftrotated[i][j].isEmpty() && !board.get(x+j,y+i).isEmpty()) // collision
							return false;
				}
			}
		}
		
		backup = paraSoftpattern;
		paraSoftpattern = paraSoftrotated;
		paraSoftrotated = backup;
		
		// try to correct border violations
		if(paraSoftmaxBottomOffset < 1) {
			if(paraSoftmaxLeftOffset < 1)  {
				if(paraSoftmaxRightOffset < 1) {
					paraSoftreDraw();
					return true;
				} else {
					if(paraSoftsetPosition(x - paraSoftmaxRightOffset, y, false, board)) {
						paraSoftreDraw();
						return true;
					} else {
						paraSoftrotated = paraSoftpattern;
						paraSoftpattern = backup;
						return false;
					}
				}
			} else {
				if(paraSoftsetPosition(x + paraSoftmaxLeftOffset, y, false, board)) {
					paraSoftreDraw();
					return true;
				} else {
					paraSoftrotated = paraSoftpattern;
					paraSoftpattern = backup;
					return false;
				}
			}
		} else {
			if(paraSoftsetPosition(x, y - paraSoftmaxBottomOffset, false, board)) {
				paraSoftreDraw();
				return true;
			} else {
				paraSoftrotated = paraSoftpattern;
				paraSoftpattern = backup;
				return false;
			}
		}
	}
}
