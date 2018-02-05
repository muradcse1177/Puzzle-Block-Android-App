package com.parallaxsoftblockmatchup.game;
import com.parallaxsoftblockmatchup.game.components.paraSoftBoard;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class paraSoftRow {

	private paraSoftRow below;
	private paraSoftRow above;
	private paraSoftSquare[] elements;
	private paraSoftSquare emptyparaSoftSquare;
	private int width;
	private paraSoftAnimator animator;
	private int fillStatus;
	
	public paraSoftRow(int width, Context c) {
		emptyparaSoftSquare = new paraSoftSquare(paraSoftSquare.paraSofttype_empty, c);
		animator = new paraSoftAnimator(c,this);
		this.width = width;
		below = null;
		above = null;
		fillStatus = 0;
		elements = new paraSoftSquare[width];
		for(int i = 0; i < width; i++) {
			elements[i] = emptyparaSoftSquare;
		}
	}
	
	public void set(paraSoftSquare s, int i) {
		if(s.isEmpty())
			return;
		if((i >= 0) && (i < width)) {
			fillStatus++;
			elements[i] = s;
		}
	}
	
	public paraSoftSquare get(int i) {
		if((i >= 0) && (i < width))
			return elements[i];
		return null;
	}

	public void set(paraSoftSquare[] squares) {
		elements = squares;
		fillStatus = 0;
		
		if(elements!=null)
			for(int i = 0; i < width; i++) {
				if(elements[i]!=null)
					if(!elements[i].isEmpty())
						fillStatus++;
			}
	}

	public void setAbove(paraSoftRow row) {
		this.above = row;
	}

	public void setBelow(paraSoftRow row) {
		this.below = row;
	}
	
	public paraSoftRow below() {
		return this.below;
	}
	
	public paraSoftRow above() {
		return this.above;
	}
	
	public paraSoftRow delete() {
		paraSoftRow result = this.below;
		
		if(above!=null)
			above.setBelow(below);
		if(below!=null)
			below.setAbove(above);
		
		above = null;
		below = null;
		
		return result;
	}

	public void draw(int x, int y, int paraSoftsquareSize, Canvas c) { // top left corner of Row
		animator.draw(x, y, paraSoftsquareSize, c);
	}

	public Bitmap drawBitmap(int paraSoftsquareSize) {
		Bitmap bm = Bitmap.createBitmap(width*paraSoftsquareSize, paraSoftsquareSize, Bitmap.Config.ARGB_8888);
		Canvas tamp = new Canvas(bm);
		for(int i = 0; i < width; i++) {
			if(elements[i] != null)
				elements[i].draw(i*paraSoftsquareSize,0,paraSoftsquareSize,tamp,false);
		}
		return bm;
	}
	
	public boolean isFull() {
		if(fillStatus >= width)
			return true;
		else
			return false;
	}
	
	public void cycle(long time, paraSoftBoard board) {
		animator.cycle(time, board);
	}
	
	public void clear(paraSoftBoard board, int currentDropInterval) {
		animator.start(board, currentDropInterval);
	}
	
	public void finishClear(paraSoftBoard board) {
		fillStatus = 0;
		for(int i = 0; i < width; i++) {
			elements[i] = emptyparaSoftSquare;
		}
		paraSoftRow paraSofttopRow = board.paraSoftgetTopRow();
		above().setBelow(below());
		below().setAbove(above());
		setBelow(paraSofttopRow);
		setAbove(paraSofttopRow.above());
		paraSofttopRow.above().setBelow(this);
		paraSofttopRow.setAbove(this);
		
		board.finishClear(this);
	}

	public boolean paraSoftinterrupt(paraSoftBoard board) {
		return animator.finish(board);
	}
}
