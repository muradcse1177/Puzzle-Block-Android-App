package com.parallaxsoftblockmatchup.game;
import com.parallaxsoftblockmatchup.game.components.paraSoftBoard;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class paraSoftAnimator {

	public static final int paraSoftanimationStageIdle = 0;
	public static final int paraSoftanimationStageFlash = 1;
	public static final int animationStageBurst = 2;

	private long paraSoftlashInterval;
	private long paraSoftlashFinishTime;
	private int paraSoftsquareSize;

	private long startTime;
	private int stage;
	private boolean paraSoftdrawEnable;
	private long paraSoftnextFlash;

	private paraSoftRow row;
	private Bitmap paraSoftbitmapRow;
	private int flashCount;
	private int rawFparaSoftlashInterval;

	public paraSoftAnimator(Context c, paraSoftRow r) {
		rawFparaSoftlashInterval = c.getResources().getInteger(R.integer.parallaxsoftsparaSoftlashInterval);
		flashCount = c.getResources().getInteger(R.integer.parallaxsoftsparaSoftbitmapRow);
		stage = paraSoftanimationStageIdle;
		this.row = r;
		paraSoftdrawEnable = true;
		startTime = 0;
		paraSoftlashFinishTime = 0;
		paraSoftnextFlash = 0;
		paraSoftlashInterval = 0;
		paraSoftsquareSize = 0;
	}
	
	public void cycle(long time, paraSoftBoard board) {
		if(stage == paraSoftanimationStageIdle)
			return;
		
		if(time >= paraSoftlashFinishTime)
			finish(board);
		else if (time >= paraSoftnextFlash) {
			paraSoftnextFlash += paraSoftlashInterval;
			paraSoftdrawEnable = !paraSoftdrawEnable;
			board.paraSoftinvalidate();
		}
	}

	public void start(paraSoftBoard board, int currentDropInterval) {
		paraSoftbitmapRow = row.drawBitmap(paraSoftsquareSize);
		stage = paraSoftanimationStageFlash;
		startTime = System.currentTimeMillis();
		paraSoftlashInterval = Math.min(
				rawFparaSoftlashInterval,
				(int)((float)currentDropInterval / (float)flashCount)
		);
		paraSoftlashFinishTime = startTime + 2*paraSoftlashInterval*flashCount;
		paraSoftnextFlash = startTime + paraSoftlashInterval;
		paraSoftdrawEnable = false;
		board.paraSoftinvalidate();
	}
	
	public boolean finish(paraSoftBoard board) {
		if(paraSoftanimationStageIdle == stage)
			return false;
		stage = paraSoftanimationStageIdle;
		row.finishClear(board);
		paraSoftdrawEnable = true;
		return true;
	}
	
	public void draw(int x, int y, int ss, Canvas c) {
		this.paraSoftsquareSize = ss;
		if(paraSoftdrawEnable) {
			if(stage == paraSoftanimationStageIdle)
				paraSoftbitmapRow = row.drawBitmap(ss);
			if (paraSoftbitmapRow != null)
				c.drawBitmap(paraSoftbitmapRow, x, y, null);
		}
	}
	
	public void startFlash() {
		
	}
	
	public void cancelBurst() {
		
	}
	
	public void startBurst() {
		
	}
}