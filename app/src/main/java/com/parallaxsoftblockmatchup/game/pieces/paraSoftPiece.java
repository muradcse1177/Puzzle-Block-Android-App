package com.parallaxsoftblockmatchup.game.pieces;

import com.parallaxsoftblockmatchup.game.R;
import com.parallaxsoftblockmatchup.game.paraSoftSquare;
import com.parallaxsoftblockmatchup.game.components.paraSoftBoard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class paraSoftPiece {

	public static final int paraSofttype_J = 1;
	public static final int paraSofttype_L = 2;
	public static final int paraSofttype_O = 3;
	public static final int paraSofttype_Z = 4;
	public static final int paraSofttype_S = 5;
	public static final int paraSofttype_T = 6;
	public static final int paraSofttype_I = 7;

	protected boolean active;
	protected int x;
	protected int y;
	protected int dim;
	protected int paraSoftsquareSize;
	protected paraSoftSquare paraSoftpattern[][];
	protected paraSoftSquare paraSoftrotated[][];
	private paraSoftSquare emptyparaSoftSquare;
	private Canvas cv;
	private Bitmap bm;
	private Canvas paraSoftcvPhantom;
	private Bitmap paraSoftbmPhantom;
	private boolean paraSoftisPhantom;
	

	protected paraSoftPiece(Context c, int dimension) {
		this.dim = dimension;
		paraSoftsquareSize = 1;
		x = c.getResources().getInteger(R.integer.paraSoftpiece_start_x);
		y = 0;
		active = false;
		paraSoftisPhantom = false;
		
		emptyparaSoftSquare =  new paraSoftSquare(paraSoftSquare.paraSofttype_empty,c);
		
		paraSoftpattern = new paraSoftSquare[dim][dim];
		paraSoftrotated = new paraSoftSquare[dim][dim];
		for(int i = 0; i < dim; i++) {
			for(int j = 0; j < dim; j++) {
				paraSoftpattern[i][j] = emptyparaSoftSquare;
				paraSoftrotated[i][j] = emptyparaSoftSquare;
			}
		}
	}
	
	public void reset(Context c) {
		x = c.getResources().getInteger(R.integer.paraSoftpiece_start_x);
		y = 0;
		active = false;
		for(int i = 0; i < dim; i++) {
			for(int j = 0; j < dim; j++) {
				paraSoftpattern[i][j] = emptyparaSoftSquare;
			}
		}
	}
	
	public void paraSoftsetActive(boolean b) {
		active = b;
		paraSoftreDraw();
	}
	
	public boolean paraSoftisActive() {
		return active;
	}
	
	public void place(paraSoftBoard board) {
		active = false;
		for(int i = 0; i < dim; i++) {
			for(int j = 0; j < dim; j++) {
				if(paraSoftpattern[i][j] != null)
					board.set(x+j,y+i,paraSoftpattern[i][j]);
			}
		}
	}

	public boolean paraSoftsetPosition(int x_new, int y_new, boolean noInterrupt, paraSoftBoard board) {
		boolean collision = false;
		int paraSoftleftOffset = 0;
		int paraSoftrightOffset = 0;
		int paraSoftbottomOffset = 0;
		for(int i = 0; i < dim; i++) {
			for(int j = 0; j < dim; j++) {
				if(paraSoftpattern[i][j] != null) {
					paraSoftleftOffset = - (x_new+j);
					paraSoftrightOffset = (x_new+j) - (board.getWidth() - 1);
					paraSoftbottomOffset = (y_new+i) - (board.getHeight() - 1);
					if(!paraSoftpattern[i][j].isEmpty() && (paraSoftleftOffset > 0)) // left border violation
						return false;
					if(!paraSoftpattern[i][j].isEmpty() && (paraSoftrightOffset > 0)) // right border violation
						return false;
					if(!paraSoftpattern[i][j].isEmpty() && (paraSoftbottomOffset > 0)) // bottom border violation
						return false;
					if(board.get(x_new+j,y_new+i) != null) {
						collision = (!paraSoftpattern[i][j].isEmpty() && !board.get(x_new+j,y_new+i).isEmpty()); // collision
						if(collision) {
							if(noInterrupt)
								return false;
							board.paraSoftinterruptClearAnimation();
							collision = !board.get(x_new+j,y_new+i).isEmpty();
							if(collision)
								return false;
						}
					}
				}
			}
		}
		x = x_new;
		y = y_new;
		return true;
	}

	public abstract boolean paraSoftturnLeft(paraSoftBoard board);
	public abstract boolean turnRight(paraSoftBoard board);
	public boolean moveLeft(paraSoftBoard board) {
		if(!active)
			return true;
		return paraSoftsetPosition(x - 1, y, false, board);
	}

	public boolean moveRight(paraSoftBoard board) {
		if(!active)
			return true;
		return paraSoftsetPosition(x + 1, y, false, board);
	}

	public boolean drop(paraSoftBoard board) {
		if(!active)
			return true;
		return paraSoftsetPosition(x, y + 1, false, board);
	}

	public int paraSofthardDrop(boolean noInterrupt, paraSoftBoard board) {
		int i=0;
		while(paraSoftsetPosition(x, y + 1, noInterrupt, board)){
			if(i >= board.getHeight())
				throw new RuntimeException("Hard Drop Error: dropped too far.");
			i++;
		}
		return i;
	}
	
	protected void paraSoftreDraw() {
		
		bm = Bitmap.createBitmap(paraSoftsquareSize*dim, paraSoftsquareSize*dim, Bitmap.Config.ARGB_8888);
		cv = new Canvas(bm);
		for(int i = 0; i < dim; i++) {
			for(int j = 0; j < dim; j++) {
				if(paraSoftpattern[i][j] == null) {} else
					if(!paraSoftpattern[i][j].isEmpty())
						paraSoftpattern[i][j].draw(j*paraSoftsquareSize, i*paraSoftsquareSize, paraSoftsquareSize, cv, false);
			}
		}

		paraSoftbmPhantom = Bitmap.createBitmap(paraSoftsquareSize*dim, paraSoftsquareSize*dim, Bitmap.Config.ARGB_8888);
		paraSoftcvPhantom = new Canvas(paraSoftbmPhantom);
		for(int i = 0; i < dim; i++) {
			for(int j = 0; j < dim; j++) {
				if(paraSoftpattern[i][j] == null) {} else
					if(!paraSoftpattern[i][j].isEmpty())
						paraSoftpattern[i][j].draw(j*paraSoftsquareSize, i*paraSoftsquareSize, paraSoftsquareSize, paraSoftcvPhantom, true);
			}
		}
	}

	public void paraSoftdrawOnBoard(int xOffset, int yOffset, int ss, Canvas c) {
		if(!active)
			return;
		if(ss != paraSoftsquareSize) {
			paraSoftsquareSize = ss;
			paraSoftreDraw();
		}
		if(paraSoftisPhantom)
			c.drawBitmap(paraSoftbmPhantom, x*paraSoftsquareSize + xOffset, y*paraSoftsquareSize + yOffset, null);
		else
			c.drawBitmap(bm, x*paraSoftsquareSize + xOffset, y*paraSoftsquareSize + yOffset, null);
	}
	public void drawOnPreview(int xpos, int ypos, int ss, Canvas c) {
		if(ss != paraSoftsquareSize) {
			paraSoftsquareSize = ss;
			paraSoftreDraw();
		}
		c.drawBitmap(bm, xpos, ypos, null);
	}

	public int getDim() {
		return dim;
	}

	public void paraSoftsetPhantom(boolean b) {
		paraSoftisPhantom = b;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void paraSoftsetPositionSimple(int x_new, int y_new) {
		x = x_new;
		y = y_new;
	}

	public boolean paraSoftsetPositionSimpleCollision(int x_new, int y_new, paraSoftBoard board) {
		for(int i = 0; i < dim; i++) {
			for(int j = 0; j < dim; j++) {
				if(paraSoftpattern[i][j] != null) {
					if(board.get(x_new+j,y_new+i) == null) {
						if(!paraSoftpattern[i][j].isEmpty())
							return false;
					} else {
						if(!paraSoftpattern[i][j].isEmpty() && !board.get(x_new+j,y_new+i).isEmpty())
							return false;
					}
					
				}
			}
		}
		x = x_new;
		y = y_new;
		return true;
	}
}
