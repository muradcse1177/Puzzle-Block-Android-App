package com.parallaxsoftblockmatchup.game;
import com.parallaxsoftblockmatchup.game.activities.paraSoftGameActivity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;


public class paraSoftBlockBoardView extends SurfaceView implements Callback {

	private paraSoftGameActivity host;
	public paraSoftBlockBoardView(Context context) {
		super(context);
	}
	public paraSoftBlockBoardView(Context context, AttributeSet attrs) {
		super(context,attrs);
	}
	public paraSoftBlockBoardView(Context context, AttributeSet attrs, int defStyle) {
		super(context,attrs,defStyle);
	}
	
	public void setHost(paraSoftGameActivity ga) {
		host = ga;
	}
	
	public void init() {
		setZOrderOnTop(true);
		getHolder().addCallback(this);
		getHolder().setFormat(PixelFormat.TRANSPARENT);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		host.paraSoftstartGame(this);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		host.paraSoftdestroyparaSoftWorkThread();
	}
}

