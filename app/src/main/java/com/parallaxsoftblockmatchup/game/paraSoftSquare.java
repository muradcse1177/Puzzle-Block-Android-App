package com.parallaxsoftblockmatchup.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class paraSoftSquare {

	public static final int paraSofttype_empty = 0;
	public static final int paraSofttype_blue = 1;
	public static final int paraSofttype_orange = 2;
	public static final int paraSofttype_yellow = 3;
	public static final int paraSofttype_red = 4;
	public static final int paraSofttype_green = 5;
	public static final int paraSofttype_magenta = 6;
	public static final int paraSofttype_cyan = 7;
	
	private int type;
	private Paint paint;
	private Bitmap bm;
	private Bitmap paraSoftphantomBM;
	private Canvas canv;
	private Canvas paraSoftphantomCanv;
	private int paraSoftsquaresize;
	private int paraSoftphantomAlpha;
	
	public paraSoftSquare(int type, Context c) {
		this.type = type;
		paint = new Paint();
		paraSoftphantomAlpha = c.getResources().getInteger(R.integer.phantom_alpha);
		paraSoftsquaresize = 0;
		switch(type){
			case paraSofttype_blue:
				paint.setColor(c.getResources().getColor(R.color.parallaxsoftparallaxsoftsblue));
				break;
			case paraSofttype_orange:
				paint.setColor(c.getResources().getColor(R.color.parallaxsoftparallaxsoftsorange));
				break;
			case paraSofttype_yellow:
				paint.setColor(c.getResources().getColor(R.color.parallaxsoftparallaxsoftsyellow));
				break;
			case paraSofttype_red:
				paint.setColor(c.getResources().getColor(R.color.parallaxsoftsparallaxsoftsred));
				break;
			case paraSofttype_green:
				paint.setColor(c.getResources().getColor(R.color.parallaxsoftparallaxsoftsgreen));
				break;
			case paraSofttype_magenta:
				paint.setColor(c.getResources().getColor(R.color.parallaxsoftsmagenta));
				break;
			case paraSofttype_cyan:
				paint.setColor(c.getResources().getColor(R.color.parallaxsoftscyan));
				break;
			case paraSofttype_empty:
				return;
			default:
				paint.setColor(c.getResources().getColor(R.color.parallaxsoftserror));
				break;
		}
	}
	
	public void paraSoftreDraw(int ss) {
		if(type == paraSofttype_empty)
			return;
		
		paraSoftsquaresize = ss;
		bm = Bitmap.createBitmap(ss, ss, Bitmap.Config.ARGB_8888);
		paraSoftphantomBM = Bitmap.createBitmap(ss, ss, Bitmap.Config.ARGB_8888);
		canv = new Canvas(bm);
		paraSoftphantomCanv = new Canvas(paraSoftphantomBM);

		paint.setAlpha(255);
		canv.drawRect(0, 0, paraSoftsquaresize, paraSoftsquaresize, paint);
		paint.setAlpha(paraSoftphantomAlpha);
		paraSoftphantomCanv.drawRect(0, 0, paraSoftsquaresize, paraSoftsquaresize, paint);
	}
	
	public paraSoftSquare clone(Context c) {
		return new paraSoftSquare(type, c);
	}
	
	public boolean isEmpty() {
		if(type == paraSofttype_empty)
			return true;
		else 
			return false;
	}

	public void draw(int x, int y, int paraSoftsquareSize, Canvas c, boolean paraSoftisPhantom) {
		if(type == paraSofttype_empty)
			return;
		
		if(paraSoftsquareSize != paraSoftsquaresize)
			paraSoftreDraw(paraSoftsquareSize);
		
		if(paraSoftisPhantom) {
			c.drawBitmap(paraSoftphantomBM, x, y, null);
		} else {
			c.drawBitmap(bm, x, y, null);
		}
	}
}
