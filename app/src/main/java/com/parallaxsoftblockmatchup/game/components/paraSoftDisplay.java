package com.parallaxsoftblockmatchup.game.components;
import com.parallaxsoftblockmatchup.game.R;
import com.parallaxsoftblockmatchup.game.paraSoftRow;
import com.parallaxsoftblockmatchup.game.activities.paraSoftGameActivity;
import com.parallaxsoftblockmatchup.game.pieces.paraSoftPiece;
import android.R.color;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceManager;

public class paraSoftDisplay extends paraSoftComponent {

	private int paraSoftprevPhantomY;
	private boolean paraSoftdropPhantom;
	private Paint paint;
	private int paraSoftgridRowBorder;
	private int paraSoftgridColumnBorder;
	private int paraSoftsquaresize;
	private int paraSoftrowOffset;
	private int rows;
	private int paraSoftcolumnOffset;
	private int columns;
	private boolean paraSoftlandscapeInitialized;
	private int paraSoftprev_top;
	private int paraSoftprev_bottom;
	private int paraSoftprev_left;
	private int paraSoftprev_right;
	private int paraSofttextLeft;
	private int paraSofttextTop;
	private int paraSofttextRight;
	private int paraSofttextBottom;
	private int paraSoftttextLines;
	private int paraSofttextSizeH;
	private int paraSofttextEmptySpacing;
	private Paint paraSofttextPaint;
	private Rect paraSofttextRect;
	private int paraSofttextHeight;
	private Paint popUpparaSofttextPaint;
	
	public paraSoftDisplay(paraSoftGameActivity ga) {
		super(ga);
		paraSoftparaSoftinvalidatePhantom();
		paraSoftsetPhantomY(0);
		paraSoftlandscapeInitialized = false;
	    paint = new Paint();
		rows = host.getResources().getInteger(R.integer.zeilen);
		columns = host.getResources().getInteger(R.integer.spalten);
		paraSoftsquaresize = 1;
		paraSoftprev_top = 1;
		paraSoftprev_bottom = 1;
		paraSoftprev_left = 1;
		paraSoftprev_right = 1;
		paraSoftrowOffset = host.getResources().getInteger(R.integer.zeilenoffset);
		paraSoftcolumnOffset = host.getResources().getInteger(R.integer.spaltenoffset);
		paraSofttextPaint = new Paint();
		paraSofttextRect = new Rect();
		paraSofttextPaint.setColor(host.getResources().getColor(color.white));
		paraSofttextPaint.setAlpha(host.getResources().getInteger(R.integer.textalpha));
		paraSofttextPaint.setAntiAlias(PreferenceManager.getDefaultSharedPreferences(host).getBoolean("parallaxsoftpref_antialiasing", true));
		popUpparaSofttextPaint = new Paint();
		popUpparaSofttextPaint.setColor(host.getResources().getColor(color.white));
		popUpparaSofttextPaint.setAntiAlias(PreferenceManager.getDefaultSharedPreferences(host).getBoolean("parallaxsoftpref_antialiasing", true));
		popUpparaSofttextPaint.setTextSize(120);
		paraSofttextSizeH = 1;
		paraSofttextHeight = 2;
		if(PreferenceManager.getDefaultSharedPreferences(host).getBoolean("parallaxsoftpref_fps", false))
			paraSoftttextLines = 10;
		else
			paraSoftttextLines = 8;
	}
	
	public void paraSoftdoDraw(Canvas c, int fps) {
		if(c==null)
			return;

		if (!paraSoftlandscapeInitialized){
			int fpsenabled = 0;
			if(PreferenceManager.getDefaultSharedPreferences(host).getBoolean("parallaxsoftpref_fps", false))
				fpsenabled = 1;
			
			host.game.getBoard().paraSoftinvalidate();
			paraSoftlandscapeInitialized = true;
			paraSoftsquaresize   = (int)(((c.getHeight()-1) - 2*paraSoftrowOffset)/rows);
			int size2 = (int)(((c.getHeight()-1) - 2*paraSoftcolumnOffset)/(columns + 4 + host.getResources().getInteger(R.integer.padding_columns)));
			if(size2 < paraSoftsquaresize) {
				paraSoftsquaresize = size2;
				paraSoftrowOffset = (int)(((c.getHeight()-1) - paraSoftsquaresize*rows)/2);
			} else
				paraSoftcolumnOffset = (int)(((c.getWidth()-1) - paraSoftsquaresize*(host.getResources().getInteger(R.integer.padding_columns)+4+columns))/2);
			paraSoftgridRowBorder = paraSoftrowOffset + paraSoftsquaresize*rows;
			paraSoftgridColumnBorder = paraSoftcolumnOffset + paraSoftsquaresize*columns;
			paraSoftprev_top = paraSoftrowOffset;
			paraSoftprev_bottom = paraSoftrowOffset + 4*paraSoftsquaresize;
			paraSoftprev_left = paraSoftgridColumnBorder + host.getResources().getInteger(R.integer.padding_columns)*paraSoftsquaresize;
			paraSoftprev_right = paraSoftprev_left + 4*paraSoftsquaresize;
			paraSofttextLeft = paraSoftprev_left;
			paraSofttextTop = paraSoftprev_bottom + 2*paraSoftsquaresize;
			paraSofttextRight = (c.getWidth()-1) - paraSoftcolumnOffset;
			paraSofttextBottom = (c.getHeight()-1) - paraSoftrowOffset - paraSoftsquaresize;
			paraSofttextSizeH = 1;
			paraSofttextPaint.setTextSize(paraSofttextSizeH + 1);
			while(paraSofttextPaint.measureText("00:00:00") < (paraSofttextRight - paraSofttextLeft)) {
				paraSofttextPaint.getTextBounds((String)"Level:32", 0, 6, paraSofttextRect);
				paraSofttextHeight = paraSofttextRect.height();
				paraSofttextEmptySpacing = ((paraSofttextBottom - paraSofttextTop) - (paraSoftttextLines*(paraSofttextHeight + 3))) / (3 + fpsenabled);
				if(paraSofttextEmptySpacing < 10)
					break;
				
				paraSofttextSizeH++;
				paraSofttextPaint.setTextSize(paraSofttextSizeH + 1);
			}
			paraSofttextPaint.setTextSize(paraSofttextSizeH);
			paraSofttextPaint.getTextBounds((String)"Level:32", 0, 6, paraSofttextRect);
			paraSofttextHeight = paraSofttextRect.height() + 3;
			paraSofttextEmptySpacing = ((paraSofttextBottom - paraSofttextTop) - (paraSoftttextLines*(paraSofttextHeight))) / (3 + fpsenabled);
			
			host.controls.paraSoftsetPreviewRect(new Rect(paraSoftprev_left,paraSoftprev_top,paraSoftprev_right,paraSoftprev_bottom));
		}
		c.drawColor(Color.argb(0, 0, 0, 0), android.graphics.PorterDuff.Mode.CLEAR);
		
		host.game.getBoard().draw(paraSoftcolumnOffset, paraSoftrowOffset, paraSoftsquaresize, c);
		
		paraSoftdrawActive(paraSoftcolumnOffset, paraSoftrowOffset, paraSoftsquaresize, c);

		if(PreferenceManager.getDefaultSharedPreferences(host).getBoolean("parallaxsoftpref_phantom", false))
			paraSoftdrawPhantom(paraSoftcolumnOffset, paraSoftrowOffset, paraSoftsquaresize, c);
		
	    drawGrid(paraSoftcolumnOffset, paraSoftrowOffset, paraSoftgridColumnBorder, paraSoftgridRowBorder, c);
		
	    if(host.controls.paraSoftisBoardTouched())
	    	paraSoftdrawTouchIndicator();
	    
	    paraSoftdrawPreview(paraSoftprev_left, paraSoftprev_top, paraSoftprev_right, paraSoftprev_bottom, c);

	    paraSoftdrawTextFillBox(c, fps);

		if(PreferenceManager.getDefaultSharedPreferences(host).getBoolean("parallaxsoftpref_popup", true))
			paraSoftdrawPopupText(c);
	}

	private void paraSoftdrawTouchIndicator() {
	}

	private void drawGrid(int x, int y, int xBorder, int yBorder, Canvas c) {
		
		paint.setColor(host.getResources().getColor(color.holo_blue_dark));
        for (int zeilePixel = 0; zeilePixel <= rows; zeilePixel ++) {
            c.drawLine(x, y + zeilePixel*paraSoftsquaresize, xBorder, y + zeilePixel*paraSoftsquaresize, paint);
        }
        for (int spaltePixel = 0; spaltePixel <= columns; spaltePixel ++) {
            c.drawLine(x + spaltePixel*paraSoftsquaresize, y, x + spaltePixel*paraSoftsquaresize, yBorder, paint);
        }

		paint.setColor(host.getResources().getColor(color.background_light));
		c.drawLine(x, y, x, yBorder, paint);
		c.drawLine(x, y, xBorder, y, paint);
		c.drawLine(xBorder, yBorder, xBorder, y, paint);
		c.drawLine(xBorder, yBorder, x, yBorder, paint);
	}

	private void paraSoftdrawPreview(int left, int top, int right, int bottom, Canvas c) {
		paraSoftdrawPreview(left, top, paraSoftsquaresize, c);
		paint.setColor(host.getResources().getColor(color.holo_blue_dark));
        for (int zeilePixel = 0; zeilePixel <= 4; zeilePixel ++) {
            c.drawLine(left, top + zeilePixel*paraSoftsquaresize, right, top + zeilePixel*paraSoftsquaresize, paint);
        }
        for (int spaltePixel = 0; spaltePixel <= 4; spaltePixel ++) {
            c.drawLine(left + spaltePixel*paraSoftsquaresize, top, left + spaltePixel*paraSoftsquaresize, bottom, paint);
        }
		paint.setColor(host.getResources().getColor(color.background_light));
		c.drawLine(left, top, right, top, paint);
		c.drawLine(left, top, left, bottom, paint);
		c.drawLine(right, bottom, right, top, paint);
		c.drawLine(right, bottom, left, bottom, paint);
	}

	private void paraSoftdrawTextFillBox(Canvas c, int fps) {
		c.drawText(host.getResources().getString(R.string.level_title), paraSofttextLeft, paraSofttextTop + paraSofttextHeight, paraSofttextPaint);
		c.drawText(host.game.paraSoftgetLevelString(), paraSofttextLeft, paraSofttextTop + 2*paraSofttextHeight, paraSofttextPaint);
		c.drawText(host.getResources().getString(R.string.score_title), paraSofttextLeft, paraSofttextTop + 3*paraSofttextHeight + paraSofttextEmptySpacing, paraSofttextPaint);
		c.drawText(host.game.paraSoftgetScoreString(), paraSofttextLeft, paraSofttextTop + 4*paraSofttextHeight + paraSofttextEmptySpacing, paraSofttextPaint);
		c.drawText(host.getResources().getString(R.string.time_title), paraSofttextLeft, paraSofttextTop + 5*paraSofttextHeight + 2*paraSofttextEmptySpacing, paraSofttextPaint);
		c.drawText(host.game.paraSoftgetTimeString(), paraSofttextLeft, paraSofttextTop + 6*paraSofttextHeight + 2*paraSofttextEmptySpacing, paraSofttextPaint);
		c.drawText(host.getResources().getString(R.string.apm_title), paraSofttextLeft, paraSofttextTop + 7*paraSofttextHeight + 3*paraSofttextEmptySpacing, paraSofttextPaint);
		c.drawText(host.game.paraSoftgetAPMString(), paraSofttextLeft, paraSofttextTop + 8*paraSofttextHeight + 3*paraSofttextEmptySpacing, paraSofttextPaint);
		if(!PreferenceManager.getDefaultSharedPreferences(host).getBoolean("parallaxsoftpref_fps", false))
			return;
		c.drawText(host.getResources().getString(R.string.fps_title), paraSofttextLeft, paraSofttextTop + 9*paraSofttextHeight + 4*paraSofttextEmptySpacing, paraSofttextPaint);
		c.drawText("" + fps, paraSofttextLeft, paraSofttextTop + 10*paraSofttextHeight + 4*paraSofttextEmptySpacing, paraSofttextPaint);
	}
	
	private void paraSoftdrawActive(int spaltenOffset, int zeilenOffset, int spaltenAbstand,
			Canvas c) {
		host.game.paraSoftgetActivePiece().paraSoftdrawOnBoard(spaltenOffset, zeilenOffset, spaltenAbstand, c);
	}

	private void paraSoftdrawPhantom(int spaltenOffset, int zeilenOffset, int spaltenAbstand,
			Canvas c) {
		paraSoftPiece active = host.game.paraSoftgetActivePiece();
		int y = active.getY();
		int x = active.getX();
		active.paraSoftsetPhantom(true);
		
		if(paraSoftdropPhantom) {
			int backup__paraSoftcurrentRowIndex = host.game.getBoard().paraSoftparaSoftgetCurrentRowIndex();
			paraSoftRow backup__paraSoftcurrentRow = host.game.getBoard().paraSoftgetCurrentRow();
			int cnt = y+1;
			
			while(active.paraSoftsetPositionSimpleCollision(x, cnt, host.game.getBoard())) {
				cnt++;
			}
			
			 host.game.getBoard().paraSoftparaSoftsetCurrentRowIndex(backup__paraSoftcurrentRowIndex);
			 host.game.getBoard().paraSoftsetCurrentRow(backup__paraSoftcurrentRow);
		} else
			active.paraSoftsetPositionSimple(x, paraSoftprevPhantomY);
		
		paraSoftprevPhantomY = active.getY();
		active.paraSoftdrawOnBoard(spaltenOffset, zeilenOffset, spaltenAbstand, c);
		active.paraSoftsetPositionSimple(x, y);
		active.paraSoftsetPhantom(false);
		paraSoftdropPhantom = false;
	}

	private void paraSoftdrawPreview(int spaltenOffset, int zeilenOffset, int spaltenAbstand,
			Canvas c) {
		host.game.paraSoftgetPreviewPiece().drawOnPreview(spaltenOffset, zeilenOffset, spaltenAbstand, c);
	}

	private void paraSoftdrawPopupText(Canvas c) {
		
		final int offset = 6;
		final int diagonaloffset = 6;
		
		String text = host.game.paraSoftgetPopupString();
		popUpparaSofttextPaint.setTextSize(host.game.paraSoftgetPopupSize());
		popUpparaSofttextPaint.setColor(host.getResources().getColor(color.black));
		popUpparaSofttextPaint.setAlpha(host.game.paraSoftgetPopupAlpha());

		int left = paraSoftcolumnOffset + ((int)columns*paraSoftsquaresize/2) - ((int)popUpparaSofttextPaint.measureText(text)/2); // middle minus half text width
		int top = c.getHeight()/2;
		
		c.drawText(text, offset+left, top, popUpparaSofttextPaint);
		c.drawText(text, diagonaloffset+left, diagonaloffset+top, popUpparaSofttextPaint);
		c.drawText(text, left, offset+top, popUpparaSofttextPaint);
		c.drawText(text, -diagonaloffset+left, diagonaloffset+top, popUpparaSofttextPaint);
		c.drawText(text, -offset+left, top, popUpparaSofttextPaint);
		c.drawText(text, -diagonaloffset+left, -diagonaloffset+top, popUpparaSofttextPaint);
		c.drawText(text, left, -offset+top, popUpparaSofttextPaint);
		c.drawText(text, diagonaloffset+left, -diagonaloffset+top, popUpparaSofttextPaint);

		popUpparaSofttextPaint.setColor(host.game.getPopupColor());
		popUpparaSofttextPaint.setAlpha(host.game.paraSoftgetPopupAlpha());
		c.drawText(text, left, top, popUpparaSofttextPaint);
		
	}

	public void paraSoftparaSoftinvalidatePhantom() {
		paraSoftdropPhantom = true;
	}

	public void paraSoftsetPhantomY(int i) {
		paraSoftprevPhantomY = i;
	}

}
