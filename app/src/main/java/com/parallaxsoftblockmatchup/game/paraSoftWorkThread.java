package com.parallaxsoftblockmatchup.game;
import com.parallaxsoftblockmatchup.game.activities.paraSoftGameActivity;
import android.graphics.Canvas;
import android.preference.PreferenceManager;
import android.view.SurfaceHolder;

public class paraSoftWorkThread extends Thread {

	private SurfaceHolder paraSoftsurfaceHolder;
    private boolean paraSoftrunFlag = false;
    boolean paraSoftfirstTime = true;
	public long paraSoftlastFrameDuration = 0;
	private long paraSoftlastFrameStartingTime = 0;
	int paraSoftfpslimit;
	long paraSoftlastDelay;
	private paraSoftGameActivity host;

    public paraSoftWorkThread(paraSoftGameActivity ga, SurfaceHolder sh) {
    	host = ga;
		this.paraSoftsurfaceHolder = sh;
        try {
        	paraSoftfpslimit = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(host).getString("parallaxsoftpref_paraSoftfpslimittext", "35"));
        } catch(NumberFormatException e) {
        	paraSoftfpslimit = 25;
        }
        if(paraSoftfpslimit < 5)
        	paraSoftfpslimit = 5;
        
		paraSoftlastDelay = 100;
    }

    public void paraSoftsetRunning(boolean run) {
        this.paraSoftrunFlag = run;
    }
    
    @Override
    public void run() {
        Canvas c;
        long tempTime = System.currentTimeMillis();

		long fpsUpdateTime = tempTime + 200;
		int frames = 0;
		int frameCounter[] = {0, 0, 0, 0, 0};
		int i = 0;
        
        while (this.paraSoftrunFlag) {
	            if(paraSoftfirstTime){
	            	paraSoftfirstTime = false;
	            	continue;
	            }
	            tempTime = System.currentTimeMillis();
	            
	            try {
	            	paraSoftfpslimit = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(host).getString("parallaxsoftpref_paraSoftfpslimittext", "35"));
	            } catch(NumberFormatException e) {
	            	paraSoftfpslimit = 35;
	            }
	            if(paraSoftfpslimit < 5)
	            	paraSoftfpslimit = 5;
	            
	            if(PreferenceManager.getDefaultSharedPreferences(host).getBoolean("parallaxsoftpref_paraSoftfpslimit", false)) {
		            paraSoftlastFrameDuration = tempTime - paraSoftlastFrameStartingTime;
		            if(paraSoftlastFrameDuration > (1000.0f/paraSoftfpslimit))
		            	paraSoftlastDelay = Math.max(0, paraSoftlastDelay - 25);
		            else
		            	paraSoftlastDelay+= 25;
		            
		            if(paraSoftlastDelay == 0) {}
		            else {
			            try {
							Thread.sleep(paraSoftlastDelay);
						} catch (InterruptedException e) {
						}
		            }
		            paraSoftlastFrameStartingTime = tempTime;
	            }
	            
	            if(tempTime >= fpsUpdateTime) {
	            	i = (i + 1) % 5;
		    		fpsUpdateTime += 200;
		    		frames = frameCounter[0] + frameCounter[1] + frameCounter[2] + frameCounter[3] + frameCounter[4];
		            frameCounter[i] = 0;
	            }
	            frameCounter[i]++;
	            if(host.game.cycle(tempTime))
	            	host.controls.cycle(tempTime);
	            host.game.getBoard().cycle(tempTime);
	            
	            c = null;
	            try {
	               
	                c = this.paraSoftsurfaceHolder.lockCanvas(null);
	                synchronized (this.paraSoftsurfaceHolder) {
	                    host.paradisplay.paraSoftdoDraw(c, frames);
	                }
	            } finally {
	               
	                if (c != null) {
	                    this.paraSoftsurfaceHolder.unlockCanvasAndPost(c);
	                    
	                }
	            }
        }
    }

	public void paraSoftsetFirstTime(boolean b) {
		paraSoftfirstTime = b;
	}

}