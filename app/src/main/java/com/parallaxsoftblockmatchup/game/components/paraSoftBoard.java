package com.parallaxsoftblockmatchup.game.components;

import com.parallaxsoftblockmatchup.game.R;
import com.parallaxsoftblockmatchup.game.paraSoftRow;
import com.parallaxsoftblockmatchup.game.paraSoftSquare;
import com.parallaxsoftblockmatchup.game.activities.paraSoftGameActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class paraSoftBoard extends paraSoftComponent {
	
	private int height;
	private int width;
	private paraSoftRow paraSofttopRow;
	private paraSoftRow paraSoftcurrentRow;
	private int paraSoftcurrentIndex;
	private paraSoftRow paraSofttempRow;
	private boolean valid;
	private Bitmap paraSoftblockMap;
	private Canvas paraSoftblockVas;
	
	public paraSoftBoard(paraSoftGameActivity ga) {
		super(ga);
		width = host.getResources().getInteger(R.integer.spalten);
		height = host.getResources().getInteger(R.integer.zeilen);
		valid = false;
		paraSofttopRow = new paraSoftRow(width,host);
		paraSoftcurrentIndex = 0;
		paraSofttempRow = paraSofttopRow;
		paraSoftcurrentRow = paraSofttopRow;
		for(int i = 1; i < height; i++) {
			paraSoftcurrentRow = new paraSoftRow(width,host);
			paraSoftcurrentIndex = i;
			paraSoftcurrentRow.setAbove(paraSofttempRow);
			paraSofttempRow.setBelow(paraSoftcurrentRow);
			paraSofttempRow = paraSoftcurrentRow;
		}
		paraSofttopRow.setAbove(paraSoftcurrentRow);
		paraSoftcurrentRow.setBelow(paraSofttopRow);
	}
	
	public void draw(int x, int y, int paraSoftsquareSize, Canvas c){
		if(paraSofttopRow == null)
			throw new RuntimeException("BlockBoard was not initialized!");
		
		if(valid) {
			c.drawBitmap(paraSoftblockMap, x, y, null);
			return;
		}
		try {
			paraSoftblockMap = Bitmap.createBitmap(width*paraSoftsquareSize, height*paraSoftsquareSize, Bitmap.Config.ARGB_8888);
		} catch(Exception e) {
			valid = false;
			paraSofttempRow = paraSofttopRow;
			for(int i = 0; i < height; i++) {
				if(paraSofttempRow != null) {
					c.drawBitmap(paraSofttempRow.drawBitmap(paraSoftsquareSize), x, y+i*paraSoftsquareSize, null);
					paraSofttempRow = paraSofttempRow.below();
				}
			}
			return;
		}
		
		paraSoftblockVas = new Canvas(paraSoftblockMap);
		valid = true;
		paraSofttempRow = paraSofttopRow;
		for(int i = 0; i < height; i++) {
			if(paraSofttempRow != null) {
				paraSofttempRow.draw(0,0+i*paraSoftsquareSize,paraSoftsquareSize,paraSoftblockVas);
				paraSofttempRow = paraSofttempRow.below();
			}
		}
		c.drawBitmap(paraSoftblockMap, x, y, null);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public paraSoftSquare get(int x, int y) {
		if(x < 0)
			return null;
		if(x > (width - 1))
			return null;
		if(y < 0)
			return null;
		if(y > (height - 1))
			return null;
		if(paraSoftcurrentIndex == y){
			return paraSoftcurrentRow.get(x);
		} else if(paraSoftcurrentIndex < y) {
			if(paraSoftcurrentRow.below() == null)
				return null;
			else {
				paraSoftcurrentRow = paraSoftcurrentRow.below();
				paraSoftcurrentIndex++;
				return get(x, y);
			}
		} else {
			if(paraSoftcurrentRow.above() == null)
				return null;
			else {
				paraSoftcurrentRow = paraSoftcurrentRow.above();
				paraSoftcurrentIndex--;
				return get(x, y);
			}
		}
	}

	public void set(int x, int y, paraSoftSquare square) {
		if(x < 0)
			return;
		if(x > (width - 1))
			return;
		if(y < 0)
			return;
		if(y > (height - 1))
			return;
		if(square == null)
			return;
		if(square.isEmpty())
			return;
		
		valid = false;
		
		if(paraSoftcurrentIndex == y)
			paraSoftcurrentRow.set(square,x);
		else if(paraSoftcurrentIndex < y) {
			paraSoftcurrentRow = paraSoftcurrentRow.below();
			paraSoftcurrentIndex++;
			set(x, y, square);
		} else {
			paraSoftcurrentRow = paraSoftcurrentRow.above();
			paraSoftcurrentIndex--;
			set(x, y, square);
		}
	}

	public void cycle(long time) {
		if(paraSofttopRow == null)
			throw new RuntimeException("BlockBoard was not initialized!");
		
		paraSofttempRow = paraSofttopRow.above();
		for(int i = 0; i < height; i++) {
			paraSofttempRow.cycle(time, this);
			paraSofttempRow = paraSofttempRow.above();
			if(paraSofttempRow == null)
				return;
		}
	}

	public int paraSoftclearLines(int dim) {
		valid = false;
		paraSoftRow clearPointer = paraSoftcurrentRow;
		int clearCounter = 0;
		for(int i = 0; i < dim; i++) {
			if(clearPointer.isFull()) {
				clearCounter++;
				clearPointer.clear(this, host.game.paraSoftgetAutoDropInterval());
			}
			clearPointer = clearPointer.above();
		}
		paraSoftcurrentRow = paraSofttopRow;
		paraSoftcurrentIndex = 0;
		return clearCounter;
	}

	public paraSoftRow paraSoftgetTopRow() {
		return paraSofttopRow;
	}

	public void finishClear(paraSoftRow row) {
		valid = false;
		paraSofttopRow = row;
		paraSoftcurrentIndex++;
		host.paradisplay.paraSoftparaSoftinvalidatePhantom();
	}

	public void paraSoftinterruptClearAnimation() {
		if(paraSofttopRow == null)
			throw new RuntimeException("BlockBoard was not initialized!");
		
		paraSoftRow interator = paraSofttopRow.above();
		for(int i = 0; i < height; i++) {
			if(interator.paraSoftinterrupt(this)) {
				interator = paraSofttopRow.above();
				i = 0;
				valid = false;
			} else
				interator = interator.above();
			if(interator == null)
				return;
		}
		host.paradisplay.paraSoftparaSoftinvalidatePhantom();
	}

	public void paraSoftinvalidate() {
		valid = false;
	}

	public void popupScore(long addScore) {
	}

	public int paraSoftparaSoftgetCurrentRowIndex() {
		return paraSoftcurrentIndex;
	}

	public paraSoftRow paraSoftgetCurrentRow() {
		return paraSoftcurrentRow;
	}

	public void paraSoftparaSoftsetCurrentRowIndex(int index) {
		paraSoftcurrentIndex = index;
	}

	public void paraSoftsetCurrentRow(paraSoftRow row) {
		paraSoftcurrentRow = row;
	}

}
