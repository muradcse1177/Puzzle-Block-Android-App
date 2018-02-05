package com.parallaxsoftblockmatchup.game.pieces;

import com.parallaxsoftblockmatchup.game.paraSoftSquare;
import com.parallaxsoftblockmatchup.game.components.paraSoftBoard;

import android.content.Context;

public abstract class parasoftPiece3 extends paraSoftPiece {
	

	protected parasoftPiece3(Context c) {
		super(c,3);
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
		paraSoftrotated[0][0] = paraSoftpattern[0][2];
		paraSoftrotated[0][1] = paraSoftpattern[1][2];
		paraSoftrotated[0][2] = paraSoftpattern[2][2];
		
		paraSoftrotated[1][0] = paraSoftpattern[0][1];
		paraSoftrotated[1][1] = paraSoftpattern[1][1];
		paraSoftrotated[1][2] = paraSoftpattern[2][1];
		
		paraSoftrotated[2][0] = paraSoftpattern[0][0];
		paraSoftrotated[2][1] = paraSoftpattern[1][0];
		paraSoftrotated[2][2] = paraSoftpattern[2][0];
		for(int i = 0; i < dim; i++) {
			for(int j = 0; j < dim; j++) {
				if(paraSoftrotated[i][j] != null) {
					paraSoftleftOffset = - (x+j);
					paraSoftrightOffset = (x+j) - (board.getWidth() - 1);
					paraSoftbottomOffset = (y+i) - (board.getHeight() - 1);
					if(!paraSoftrotated[i][j].isEmpty())
						if (paraSoftmaxLeftOffset < paraSoftleftOffset)
							paraSoftmaxLeftOffset = paraSoftleftOffset;
					if(!paraSoftrotated[i][j].isEmpty())
						if (paraSoftmaxRightOffset < paraSoftrightOffset)
							paraSoftmaxRightOffset = paraSoftrightOffset;
					if(!paraSoftrotated[i][j].isEmpty())
						if (paraSoftmaxBottomOffset < paraSoftbottomOffset)
							paraSoftmaxBottomOffset = paraSoftbottomOffset;
					if(board.get(x+j,y+i) != null)
						if(!paraSoftrotated[i][j].isEmpty() && !board.get(x+j,y+i).isEmpty())
							return false;
				}
			}
		}
		
		backup = paraSoftpattern;
		paraSoftpattern = paraSoftrotated;
		paraSoftrotated = backup;
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

	@Override
	public boolean turnRight(paraSoftBoard board) {
		int paraSoftmaxLeftOffset = -4;
		int paraSoftmaxRightOffset = -4;
		int paraSoftmaxBottomOffset = -4;
		int paraSoftleftOffset = 0;
		int paraSoftrightOffset = 0;
		int paraSoftbottomOffset = 0;
		paraSoftSquare backup[][] = paraSoftpattern;
		paraSoftrotated[0][0] = paraSoftpattern[2][0];
		paraSoftrotated[0][1] = paraSoftpattern[1][0];
		paraSoftrotated[0][2] = paraSoftpattern[0][0];
		
		paraSoftrotated[1][0] = paraSoftpattern[2][1];
		paraSoftrotated[1][1] = paraSoftpattern[1][1];
		paraSoftrotated[1][2] = paraSoftpattern[0][1];
		
		paraSoftrotated[2][0] = paraSoftpattern[2][2];
		paraSoftrotated[2][1] = paraSoftpattern[1][2];
		paraSoftrotated[2][2] = paraSoftpattern[0][2];

		for(int i = 0; i < dim; i++) {
			for(int j = 0; j < dim; j++) {
				if(paraSoftrotated[i][j] != null) {
					paraSoftleftOffset = - (x+j);
					paraSoftrightOffset = (x+j) - (board.getWidth() - 1);
					paraSoftbottomOffset = (y+i) - (board.getHeight() - 1);
					if(!paraSoftrotated[i][j].isEmpty())
						if (paraSoftmaxLeftOffset < paraSoftleftOffset)
							paraSoftmaxLeftOffset = paraSoftleftOffset;
					if(!paraSoftrotated[i][j].isEmpty())
						if (paraSoftmaxRightOffset < paraSoftrightOffset)
							paraSoftmaxRightOffset = paraSoftrightOffset;
					if(!paraSoftrotated[i][j].isEmpty())
						if (paraSoftmaxBottomOffset < paraSoftbottomOffset)
							paraSoftmaxBottomOffset = paraSoftbottomOffset;
					if(board.get(x+j,y+i) != null)
						if(!paraSoftrotated[i][j].isEmpty() && !board.get(x+j,y+i).isEmpty())
							return false;
				}
			}
		}
		
		backup = paraSoftpattern;
		paraSoftpattern = paraSoftrotated;
		paraSoftrotated = backup;

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
				}else {
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
