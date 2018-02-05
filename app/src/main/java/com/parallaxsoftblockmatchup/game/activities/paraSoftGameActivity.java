package com.parallaxsoftblockmatchup.game.activities;

import com.parallaxsoftblockmatchup.game.paraSoftBlockBoardView;
import com.parallaxsoftblockmatchup.game.R;
import com.parallaxsoftblockmatchup.game.paraSoftWorkThread;
import com.parallaxsoftblockmatchup.game.components.paraSoftControls;
import com.parallaxsoftblockmatchup.game.components.paraSoftDisplay;
import com.parallaxsoftblockmatchup.game.components.paraSoftGameState;
import com.parallaxsoftblockmatchup.game.components.paraSoftSound;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Button;
import android.view.View.OnTouchListener;


public class paraSoftGameActivity extends FragmentActivity {

	public paraSoftDisplay paradisplay;
	public paraSoftGameState game;
	public paraSoftSound sound;
	public paraSoftControls controls;
	private paraSoftWorkThread paraSoftmainThread;
	private paraSoftDefeatDialogFragment dialog;
	private boolean layoutSwap;

	public static final int paraSoftNEW_GAME = 0;
	public static final int paraSoftRESUME_GAME = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("parallaxsoftpref_layoutswap", false)) {
			setContentView(R.layout.activity_game_alt);
			layoutSwap = true;
		} else {
			setContentView(R.layout.activity_game);
			layoutSwap = false;
		}
		Bundle b = getIntent().getExtras();
		int value = paraSoftNEW_GAME;
		game = (paraSoftGameState)getLastCustomNonConfigurationInstance();
		if(game == null) {
			if(b!=null)
				value = b.getInt("mode");
				
			if((value == paraSoftNEW_GAME)) {
				game = paraSoftGameState.getNewInstance(this);
				game.setLevel(b.getInt("level"));
			} else
				game = paraSoftGameState.getInstance(this);
		}
		game.reconnect(this);
		dialog = new paraSoftDefeatDialogFragment();
		controls = new paraSoftControls(this);
		paradisplay = new paraSoftDisplay(this);
		sound = new paraSoftSound(this);
		if(game.paraSoftisResumable())
			sound.paraSoftstartMusic(paraSoftSound.paraSoftGAME_MUSIC, game.paraSoftgetSongtime());
		sound.paraSoftloadEffects();
		if(b!=null){
			value = b.getInt("mode");
			if(b.getString("playername") != null)
				game.paraSoftsetPlayerName(b.getString("playername"));
		} else 
			game.paraSoftsetPlayerName(getResources().getString(R.string.anonymous));
		dialog.setCancelable(false);
		if(!game.paraSoftisResumable())
			paraSoftgameOver(game.getScore(), game.paraSoftgetTimeString(), game.getAPM());
		((Button)findViewById(R.id.pausebutton_1)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				paraSoftGameActivity.this.finish();
			}
		});
		((paraSoftBlockBoardView)findViewById(R.id.boardView)).setOnTouchListener(new OnTouchListener() {
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		        if(event.getAction() == MotionEvent.ACTION_DOWN) {
		        	controls.paraSoftboardPressed(event.getX(), event.getY());
		        } else if (event.getAction() == MotionEvent.ACTION_UP) {
		        	controls.paraSoftboardReleased();
		        }
		        return true;
		    }
		});
		((ImageButton)findViewById(R.id.rightButton)).setOnTouchListener(new OnTouchListener() {
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		        if(event.getAction() == MotionEvent.ACTION_DOWN) {
		        	controls.paraSoftrightButtonPressed();
		        	((ImageButton)findViewById(R.id.rightButton)).setPressed(true);
		        } else if (event.getAction() == MotionEvent.ACTION_UP) {
		        	controls.paraSoftrightButtonReleased();
		        	((ImageButton)findViewById(R.id.rightButton)).setPressed(false);
		        }
		        return true;
		    }
		});
		((ImageButton)findViewById(R.id.leftButton)).setOnTouchListener(new OnTouchListener() {
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		        if(event.getAction() == MotionEvent.ACTION_DOWN) {
		        	controls.paraSoftleftButtonPressed();
		        	((ImageButton)findViewById(R.id.leftButton)).setPressed(true);
		        } else if (event.getAction() == MotionEvent.ACTION_UP) {
		        	controls.paraSoftleftButtonReleased();
		        	((ImageButton)findViewById(R.id.leftButton)).setPressed(false);
		        }
		        return true;
		    }
		});
		((ImageButton)findViewById(R.id.softDropButton)).setOnTouchListener(new OnTouchListener() {
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		        if(event.getAction() == MotionEvent.ACTION_DOWN) {
		        	controls.paraSoftdownButtonPressed();
		        	((ImageButton)findViewById(R.id.softDropButton)).setPressed(true);
		        } else if (event.getAction() == MotionEvent.ACTION_UP) {
		        	controls.paraSoftdownButtonReleased();
		        	((ImageButton)findViewById(R.id.softDropButton)).setPressed(false);
		        }
		        return true;
		    }
		});
		((ImageButton)findViewById(R.id.paraSofthardDropButton)).setOnTouchListener(new OnTouchListener() {
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		        if(event.getAction() == MotionEvent.ACTION_DOWN) {
		        	controls.paraSoftdropButtonPressed();
		        	((ImageButton)findViewById(R.id.paraSofthardDropButton)).setPressed(true);
		        } else if (event.getAction() == MotionEvent.ACTION_UP) {
		        	controls.paraSoftdropButtonReleased();
		        	((ImageButton)findViewById(R.id.paraSofthardDropButton)).setPressed(false);
		        }
		        return true;
		    }
		});
		((ImageButton)findViewById(R.id.rotateRightButton)).setOnTouchListener(new OnTouchListener() {
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		        if(event.getAction() == MotionEvent.ACTION_DOWN) {
		        	controls.rotateRightPressed();
		        	((ImageButton)findViewById(R.id.rotateRightButton)).setPressed(true);
		        } else if (event.getAction() == MotionEvent.ACTION_UP) {
		        	controls.paraSoftrotateRightReleased();
		        	((ImageButton)findViewById(R.id.rotateRightButton)).setPressed(false);
		        }
		        return true;
		    }
		});
		((ImageButton)findViewById(R.id.rotateLeftButton)).setOnTouchListener(new OnTouchListener() {
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		        if(event.getAction() == MotionEvent.ACTION_DOWN) {
		        	controls.paraSoftrotateLeftPressed();
		        	((ImageButton)findViewById(R.id.rotateLeftButton)).setPressed(true);
		        } else if (event.getAction() == MotionEvent.ACTION_UP) {
		        	controls.paraSoftrotateLeftReleased();
		        	((ImageButton)findViewById(R.id.rotateLeftButton)).setPressed(false);
		        }
		        return true;
		    }
		});

		((paraSoftBlockBoardView)findViewById(R.id.boardView)).init();
		((paraSoftBlockBoardView)findViewById(R.id.boardView)).setHost(this);
	}
	public void paraSoftstartGame(paraSoftBlockBoardView caller){
		paraSoftmainThread = new paraSoftWorkThread(this, caller.getHolder());
		paraSoftmainThread.paraSoftsetFirstTime(false);
		game.paraSoftsetRunning(true);
		paraSoftmainThread.paraSoftsetRunning(true);
		paraSoftmainThread.start();
	}
	public void paraSoftdestroyparaSoftWorkThread() {
        boolean retry = true;
        paraSoftmainThread.paraSoftsetRunning(false);
        while (retry) {
            try {
            	paraSoftmainThread.join();
                retry = false;
            } catch (InterruptedException e) {
                
            }
        }
	}
	public void paraSoftputScore(long score) {
		String paraSoftplayerName = game.paraSoftgetPlayerName();
		if(paraSoftplayerName == null || paraSoftplayerName.equals(""))
			paraSoftplayerName = getResources().getString(R.string.anonymous);
		
		Intent data = new Intent();
		data.putExtra(MainActivity.paraSoftPLAYERNAME_KEY, paraSoftplayerName);
		data.putExtra(MainActivity.paraSoftSCORE_KEY, score);
		setResult(MainActivity.RESULT_OK, data);
		
		finish();
	}
	
	@Override
	protected void onPause() {
    	super.onPause();
    	sound.pause();
    	sound.setInactive(true);
    	game.paraSoftsetRunning(false);
	};
    
    @Override
    protected void onStop() {
    	super.onStop();
    	sound.pause();
    	sound.setInactive(true);
    };
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	game.paraSoftsetSongtime(sound.paraSoftgetSongtime());
    	sound.release();
    	sound = null;
    	game.disconnect();
    };
    
    @Override
    protected void onResume() {
    	super.onResume();
    	sound.resume();
    	sound.setInactive(false);
    	boolean tempswap = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("parallaxsoftpref_layoutswap", false);
		if(layoutSwap != tempswap) {
			layoutSwap = tempswap;
			if(layoutSwap) {
				setContentView(R.layout.activity_game_alt);
			} else {
				setContentView(R.layout.activity_game);
			}
		}
    	game.paraSoftsetRunning(true);
    };
    
    @Override
    public Object onRetainCustomNonConfigurationInstance () {
        return game;
    }
	
	public void paraSoftgameOver(long score, String paraSoftgameTime, int apm) {
		dialog.setData(score, paraSoftgameTime, apm);
		dialog.show(getSupportFragmentManager(), "hamster");
	}

}
