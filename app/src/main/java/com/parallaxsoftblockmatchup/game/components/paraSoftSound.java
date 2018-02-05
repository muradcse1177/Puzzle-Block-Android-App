package com.parallaxsoftblockmatchup.game.components;
import com.parallaxsoftblockmatchup.game.R;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.preference.PreferenceManager;

public class paraSoftSound implements OnAudioFocusChangeListener {

	private Activity host;
	private AudioManager paraSoftaudioCEO;
	private int paraSoftsoundID_paraSofttetrisSoundPlayer;
	private int paraSoftsoundID_paraSoftdropSoundPlayer;
	private int paraSoftparaSoftsoundID_paraSoftclearSoundPlayer;
	private int paraSoftsoundID_paraSoftgameOverPlayer;
	private int paraSoftsoundID_paraSoftbuttonSoundPlayer;
	private MediaPlayer musicPlayer;
	private boolean noFocus;
	private boolean paraSoftisMusicReady;
	private BroadcastReceiver paraSoftnoisyAudioStreamReceiver;
	private BroadcastReceiver paraSoftringerModeReceiver;
	private BroadcastReceiver paraSoftheadsetPlugReceiver;
	private SoundPool paraSoftsoundPool;
	private int paraSoftsongtime;
	private int musicType;
	private boolean paraSoftisInactive;

	public static final int paraSoftNO_MUSIC = 0x0;
	public static final int paraSoftMENU_MUSIC = 0x1;
	public static final int paraSoftGAME_MUSIC = 0x2;
	
	public paraSoftSound(Activity c) {
		host = c;
		
		paraSoftaudioCEO = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
		c.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		paraSoftrequestFocus();

		IntentFilter intentFilter;
		intentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
		paraSoftnoisyAudioStreamReceiver = new BroadcastReceiver() {
				public void onReceive(Context context, android.content.Intent intent) {
					paraSoftSound.this.pauseMusic();
				}
			};
		c.registerReceiver(paraSoftnoisyAudioStreamReceiver, intentFilter);
		intentFilter = new IntentFilter(android.content.Intent.ACTION_HEADSET_PLUG );
		paraSoftheadsetPlugReceiver = new BroadcastReceiver() {
			
				public void onReceive(Context context, android.content.Intent intent) {
					if (intent.getAction().equals(android.content.Intent.ACTION_HEADSET_PLUG)) {
			            int state = intent.getIntExtra("state", -1);
			            switch (state) {
			            case 0:
			                break;
			            case 1:
			            	paraSoftSound.this.paraSoftstartMusic(musicType,paraSoftsongtime);
			                break;
			            default:
			            }
			        }
				}
				
			};
		c.registerReceiver(paraSoftheadsetPlugReceiver, intentFilter);
		intentFilter = new IntentFilter(AudioManager.RINGER_MODE_CHANGED_ACTION);
		paraSoftringerModeReceiver = new BroadcastReceiver() {
			
			public void onReceive(Context context, android.content.Intent intent) {
				paraSoftsongtime = paraSoftgetSongtime();
            	paraSoftSound.this.pauseMusic();
				paraSoftSound.this.paraSoftstartMusic(musicType,paraSoftsongtime);
			}
			
		};
		c.registerReceiver(paraSoftringerModeReceiver,intentFilter);
		
		paraSoftsoundPool = new SoundPool(c.getResources().getInteger(R.integer.audio_streams),AudioManager.STREAM_MUSIC,0);

		paraSoftsoundID_paraSofttetrisSoundPlayer = -1;
		paraSoftsoundID_paraSoftdropSoundPlayer = -1;
		paraSoftparaSoftsoundID_paraSoftclearSoundPlayer = -1;
		paraSoftsoundID_paraSoftgameOverPlayer = -1;
		paraSoftsoundID_paraSoftbuttonSoundPlayer = -1;
		
		paraSoftsongtime = 0;
		musicType = 0;
		paraSoftisMusicReady = false;
		paraSoftisInactive = false;
	}
	
	private void paraSoftrequestFocus() {
		SharedPreferences prefs;
		try{
			prefs = PreferenceManager.getDefaultSharedPreferences(host);
				
		} catch(Exception e) {
			noFocus = true;
			return;
		}
		if(prefs == null) {
			noFocus = true;
			return;
		}
		if(prefs.getInt("parallaxsoftpref_musicvolume", 60) > 0) {
			int result = paraSoftaudioCEO.requestAudioFocus(this,
		                AudioManager.STREAM_MUSIC,
		                AudioManager.AUDIOFOCUS_GAIN);
			if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
				noFocus = false;
			} else
				noFocus = true;
		}
	}

	public void setInactive(boolean b) {
		paraSoftisInactive = b;
	}
	
	public void paraSoftloadEffects() {
		paraSoftsoundID_paraSofttetrisSoundPlayer = paraSoftsoundPool.load(host, R.raw.tetris_free, 1);
		paraSoftsoundID_paraSoftdropSoundPlayer = paraSoftsoundPool.load(host, R.raw.drop_free, 1);
		paraSoftsoundID_paraSoftbuttonSoundPlayer = paraSoftsoundPool.load(host, R.raw.key_free, 1);
		paraSoftparaSoftsoundID_paraSoftclearSoundPlayer = paraSoftsoundPool.load(host, R.raw.clear2_free, 1);
		paraSoftsoundID_paraSoftgameOverPlayer = paraSoftsoundPool.load(host, R.raw.gameover2_free, 1);
	}
	
	public void paraSoftloadMusic(int type, int startTime) {
		paraSoftisMusicReady = false;
		if(musicPlayer != null)
			musicPlayer.release();
		musicPlayer = null;
		paraSoftrequestFocus();
		if(noFocus)
			return;
		if(paraSoftisInactive)
			return;
		if(paraSoftaudioCEO.getRingerMode() != AudioManager.RINGER_MODE_NORMAL)
			return;
		paraSoftsongtime = startTime;
		musicType = type;
		switch(type) {
			case paraSoftMENU_MUSIC :
				musicPlayer = MediaPlayer.create(host, R.raw.lemmings03);
				break;
			case paraSoftGAME_MUSIC :
				musicPlayer = MediaPlayer.create(host, R.raw.sadrobot01);
				break;
			default :
				musicPlayer = new MediaPlayer();
				break;
		}
		musicPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		musicPlayer.setLooping(true);
		musicPlayer.setVolume(0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_musicvolume", 60), 0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_musicvolume", 60));
		musicPlayer.seekTo(paraSoftsongtime);
		paraSoftisMusicReady = true;
	}
	
	public void paraSoftstartMusic(int type, int startTime) {
		paraSoftrequestFocus();
		if(noFocus)
			return;
		if(paraSoftisInactive)
			return;
		
		if(paraSoftisMusicReady) {
		} else {
			paraSoftloadMusic(type,startTime);
		}
		if(paraSoftisMusicReady) {
			if(paraSoftaudioCEO.getRingerMode() != AudioManager.RINGER_MODE_NORMAL)
				return;
			
			musicPlayer.setVolume(0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_musicvolume", 60), 0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_musicvolume", 60));
			musicPlayer.start();
		}
	}
	
	public void paraSoftclearSound() {
		if(noFocus)
			return;
		if(paraSoftaudioCEO.getRingerMode() != AudioManager.RINGER_MODE_NORMAL)
			return;
		paraSoftsoundPool.play(
			paraSoftparaSoftsoundID_paraSoftclearSoundPlayer,
			0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60),
			0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60),
			1, 
			0, 
			1.0f
		);
	}
	
	public void paraSoftbuttonSound() {
		if(noFocus)
			return;
		if(paraSoftaudioCEO.getRingerMode() != AudioManager.RINGER_MODE_NORMAL)
			return;
		if(!PreferenceManager.getDefaultSharedPreferences(host).getBoolean("parallaxsoftpref_button_sound", true))
			return;
		paraSoftsoundPool.play(
			paraSoftsoundID_paraSoftbuttonSoundPlayer,
			0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60),
			0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60),
			1, 
			0, 
			1.0f
		);
	}
	
	public void paraSoftdropSound() {
		if(noFocus)
			return;
		if(paraSoftaudioCEO.getRingerMode() != AudioManager.RINGER_MODE_NORMAL)
			return;
		paraSoftsoundPool.play(
			paraSoftsoundID_paraSoftdropSoundPlayer,
			0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60),
			0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60),
			1, 
			0, 
			1.0f
		);
	}

	public void paraSofttetrisSound() {
		if(noFocus)
			return;
		if(paraSoftaudioCEO.getRingerMode() != AudioManager.RINGER_MODE_NORMAL)
			return;
		paraSoftsoundPool.play(
			paraSoftsoundID_paraSofttetrisSoundPlayer,
			0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60),
			0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60),
			1, 
			0, 
			1.0f
		);
	}

	public void paraSoftparaSoftgameOverSound() {
		if(noFocus)
			return;
		if(paraSoftaudioCEO.getRingerMode() != AudioManager.RINGER_MODE_NORMAL)
			return;
		pause();
		paraSoftsoundPool.play(
			paraSoftsoundID_paraSoftgameOverPlayer,
			0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60),
			0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60),
			1, 
			0, 
			1.0f
		);
	}

	public void resume() {
		if(paraSoftisInactive)
			return;
		
		paraSoftsoundPool.autoResume();
		paraSoftstartMusic(musicType,paraSoftsongtime);
	}
	
	public void pauseMusic() {
		paraSoftisMusicReady = false;
		if(musicPlayer != null) {
			try{
				musicPlayer.pause();
				paraSoftisMusicReady = true;
			} catch(IllegalStateException e) {
				paraSoftisMusicReady = false;
			}
		}
	}

	public void pause() {
		paraSoftsoundPool.autoPause();
		pauseMusic();
	}
	
	public void release() {
		paraSoftsoundPool.autoPause();
		paraSoftsoundPool.release();
		paraSoftsoundPool = null;
		paraSoftisMusicReady = false;
		if(musicPlayer != null)
			musicPlayer.release();
		musicPlayer = null;

		host.unregisterReceiver(paraSoftnoisyAudioStreamReceiver);
		host.unregisterReceiver(paraSoftringerModeReceiver);
		host.unregisterReceiver(paraSoftheadsetPlugReceiver);
		paraSoftaudioCEO.abandonAudioFocus(this);
		paraSoftaudioCEO = null;
		host = null;
		noFocus = true;
	}

	@Override
	public void onAudioFocusChange(int focusChange) {
        if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
        	noFocus = true;
    		if(musicPlayer != null) {
    			try{
    				musicPlayer.setVolume(0.0025f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_musicvolume", 60), 0.0025f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_musicvolume", 60));
    	        } catch(IllegalStateException e) {
    				
    			}
    		}
        	paraSoftsoundPool.setVolume(paraSoftsoundID_paraSofttetrisSoundPlayer, 0.0025f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60), 0.0025f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60));
        	paraSoftsoundPool.setVolume(paraSoftsoundID_paraSoftdropSoundPlayer, 0.0025f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60), 0.0025f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60));
        	paraSoftsoundPool.setVolume(paraSoftparaSoftsoundID_paraSoftclearSoundPlayer, 0.0025f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60), 0.0025f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60));
        	paraSoftsoundPool.setVolume(paraSoftsoundID_paraSoftgameOverPlayer, 0.0025f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60), 0.0025f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60));
        	paraSoftsoundPool.setVolume(paraSoftsoundID_paraSoftbuttonSoundPlayer, 0.0025f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60), 0.0025f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60));
        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
        	noFocus = true;
            pause();
        } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
        	noFocus = false;
    		if(musicPlayer != null) {
    			try{
    				musicPlayer.setVolume(0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_musicvolume", 60), 0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_musicvolume", 60));
    	        } catch(IllegalStateException e) {
    				
    			}
    		}
    		paraSoftsoundPool.setVolume(paraSoftsoundID_paraSofttetrisSoundPlayer, 0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60), 0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60));
        	paraSoftsoundPool.setVolume(paraSoftsoundID_paraSoftdropSoundPlayer, 0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60), 0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60));
        	paraSoftsoundPool.setVolume(paraSoftparaSoftsoundID_paraSoftclearSoundPlayer, 0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60), 0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60));
        	paraSoftsoundPool.setVolume(paraSoftsoundID_paraSoftgameOverPlayer, 0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60), 0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60));
        	paraSoftsoundPool.setVolume(paraSoftsoundID_paraSoftbuttonSoundPlayer, 0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60), 0.01f * PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_soundvolume", 60));
        	resume();
        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
        	noFocus = true;
        	pause();
        }
	}

	public int paraSoftgetSongtime() {
		if(musicPlayer != null) {
			try{
				return musicPlayer.getCurrentPosition();
			} catch(IllegalStateException e) {
				
			}
		}
		return 0;
	}
}
