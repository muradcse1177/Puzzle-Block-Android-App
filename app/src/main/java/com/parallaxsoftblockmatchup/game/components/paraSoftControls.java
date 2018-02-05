package com.parallaxsoftblockmatchup.game.components;

import com.parallaxsoftblockmatchup.game.R;
import com.parallaxsoftblockmatchup.game.activities.paraSoftGameActivity;
import com.parallaxsoftblockmatchup.game.pieces.*;


import android.content.Context;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Vibrator;
import android.preference.PreferenceManager;

public class paraSoftControls extends paraSoftComponent {

	private paraSoftBoard board;
	private Vibrator v;
	private int paraSoftvibrationOffset;
	private long paraSoftshortVibeTime;
	private int[] paraSoftlineThresholds;
	private boolean paraSoftplayerSoftDrop;
	private boolean paraSoftclearPlayerSoftDrop;
	private boolean paraSoftplayerHardDrop;
	private boolean paraSoftleftMove;
	private boolean paraSoftrightMove;
	private boolean paraSoftcontinuousSoftDrop;
	private boolean paraSoftcontinuousLeftMove;
	private boolean paraSoftcontinuousRightMove;
	private boolean paraSoftclearLeftMove;
	private boolean paraSoftclearRightMove;
	private boolean paraSoftleftRotation;
	private boolean paraSoftrightRotation;
	private boolean paraSoftbuttonVibrationEnabled;
	private boolean paraSofteventVibrationEnabled;
	private int paraSoftinitialHIntervalFactor;
	private int paraSoftinitialVIntervalFactor;
	private Rect paraSoftpreviewBox;
	private boolean paraSoftboardTouched;
	
	public paraSoftControls(paraSoftGameActivity ga) {
		super(ga);
		
		paraSoftlineThresholds = host.getResources().getIntArray(R.array.line_thresholds);
		paraSoftshortVibeTime = 0;
		
		v = (Vibrator) host.getSystemService(Context.VIBRATOR_SERVICE);
		
		paraSoftbuttonVibrationEnabled = PreferenceManager.getDefaultSharedPreferences(host).getBoolean("parallaxsoftpref_vibration_button", false);
		paraSofteventVibrationEnabled = PreferenceManager.getDefaultSharedPreferences(host).getBoolean("parallaxsoftpref_vibration_events", false);
		try {
			paraSoftvibrationOffset = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(host).getString("parallaxsoftpref_vibDurOffset", "0"));
		} catch(NumberFormatException e) {
			paraSoftvibrationOffset = 0;
		}
		if(PreferenceManager.getDefaultSharedPreferences(host).getBoolean("parallaxsoftpref_accelerationH", true))
			paraSoftinitialHIntervalFactor = 2;
		else
			paraSoftinitialHIntervalFactor = 1;
		if(PreferenceManager.getDefaultSharedPreferences(host).getBoolean("parallaxsoftpref_accelerationV", true))
			paraSoftinitialVIntervalFactor = 2;
		else
			paraSoftinitialVIntervalFactor = 1;
		paraSoftplayerSoftDrop = false;
		paraSoftleftMove = false;
		paraSoftrightMove = false;
		paraSoftleftRotation = false;
		paraSoftrightRotation = false;
		paraSoftclearLeftMove = false;
		paraSoftclearRightMove = false;
		paraSoftclearPlayerSoftDrop = false;
		paraSoftcontinuousSoftDrop = false;
		paraSoftcontinuousLeftMove = false;
		paraSoftcontinuousRightMove = false;
		paraSoftpreviewBox = null;
		paraSoftboardTouched = false;
	}
	
	public void paraSoftvibrateWall() {
		if (v == null)
			return;
		if (!paraSofteventVibrationEnabled)
			return;
		if(((AudioManager)host.getSystemService(Context.AUDIO_SERVICE)).getRingerMode() == AudioManager.RINGER_MODE_SILENT)
			return;
		v.vibrate(host.game.paraSoftgetMoveInterval() + paraSoftvibrationOffset);
	}
	
	public void paraSoftcancelVibration() {
		v.cancel();
	}
	
	public void paraSoftvibrateBottom() {
		if (v == null)
			return;
		if (!paraSofteventVibrationEnabled)
			return;
		if(((AudioManager)host.getSystemService(Context.AUDIO_SERVICE)).getRingerMode() == AudioManager.RINGER_MODE_SILENT)
			return;
		v.cancel();
		v.vibrate(new long[] {0, 5 + paraSoftvibrationOffset, 30 + paraSoftvibrationOffset, 20 + paraSoftvibrationOffset}, -1);
	}
	
	public void paraSoftvibrateShort() {
		if (v == null)
			return;
		if (!paraSoftbuttonVibrationEnabled)
			return;
		if(((AudioManager)host.getSystemService(Context.AUDIO_SERVICE)).getRingerMode() == AudioManager.RINGER_MODE_SILENT)
			return;
		if((host.game.getTime() - paraSoftshortVibeTime) > (host.getResources().getInteger(R.integer.shortVibeInterval) + paraSoftvibrationOffset)) {
			paraSoftshortVibeTime = host.game.getTime();
			v.vibrate(paraSoftvibrationOffset);
		}
	}


	public void paraSoftrotateLeftPressed() {
		paraSoftleftRotation = true;
		host.game.action();
		paraSoftvibrateShort();
		host.sound.paraSoftbuttonSound();
	}

	public void paraSoftrotateLeftReleased() {
	}

	public void rotateRightPressed() {
		paraSoftrightRotation = true;
		host.game.action();
		paraSoftvibrateShort();
		host.sound.paraSoftbuttonSound();
	}

	public void paraSoftrotateRightReleased() {
	}

	public void paraSoftdownButtonReleased() {
		paraSoftclearPlayerSoftDrop = true;
		paraSoftvibrateShort();
	}

	public void paraSoftdownButtonPressed() {
		host.game.action();
		paraSoftplayerSoftDrop = true;
		paraSoftclearPlayerSoftDrop = false;
		paraSoftvibrateShort();
		host.game.paraSoftsetNextPlayerDropTime(host.game.getTime());
		host.sound.paraSoftbuttonSound();
	}

	public void paraSoftdropButtonReleased() {
		
	}

	public void paraSoftdropButtonPressed() {
		if(!host.game.paraSoftgetActivePiece().paraSoftisActive())
			return;
		host.game.action();
		paraSoftplayerHardDrop = true;
		if(paraSoftbuttonVibrationEnabled & !paraSofteventVibrationEnabled)
			paraSoftvibrateShort();
	}

	public void paraSoftleftButtonReleased() {
		paraSoftclearLeftMove = true;
		paraSoftcancelVibration();
	}

	public void paraSoftleftButtonPressed() {
		host.game.action();
		paraSoftclearLeftMove = false;
		paraSoftleftMove = true;
		paraSoftrightMove = false;
		host.game.paraSoftsetNextPlayerMoveTime(host.game.getTime());
		host.sound.paraSoftbuttonSound();
	}

	public void paraSoftrightButtonReleased() {
		paraSoftclearRightMove = true;
		paraSoftcancelVibration();
	}

	public void paraSoftrightButtonPressed() {
		host.game.action();
		paraSoftclearRightMove = false;
		paraSoftrightMove = true;
		paraSoftleftMove = false;
		host.game.paraSoftsetNextPlayerMoveTime(host.game.getTime());
		host.sound.paraSoftbuttonSound();
	}

	public void cycle(long tempTime) {
		long paraSoftgameTime = host.game.getTime();
		paraSoftPiece active = host.game.paraSoftgetActivePiece();
		paraSoftBoard board = host.game.getBoard();
		int paraSoftmaxLevel = host.game.paraSoftgetMaxLevel();
		if(paraSoftleftRotation) {
			paraSoftleftRotation = false;
			active.paraSoftturnLeft(board);
			host.paradisplay.paraSoftparaSoftinvalidatePhantom();
		}
		if(paraSoftrightRotation) {
			paraSoftrightRotation = false;
			active.turnRight(board);
			host.paradisplay.paraSoftparaSoftinvalidatePhantom();
		}
		if((!paraSoftleftMove && !paraSoftrightMove) && (!paraSoftcontinuousLeftMove && !paraSoftcontinuousRightMove))
			host.game.paraSoftsetNextPlayerMoveTime(paraSoftgameTime);
		if(paraSoftleftMove) {
			paraSoftcontinuousLeftMove = true;
			paraSoftleftMove = false;
			if(active.moveLeft(board)) {
				paraSoftvibrateShort();
				host.paradisplay.paraSoftparaSoftinvalidatePhantom();
				host.game.paraSoftsetNextPlayerMoveTime(host.game.paraSoftgetNextPlayerMoveTime() + paraSoftinitialHIntervalFactor*host.game.paraSoftgetMoveInterval());
			} else {
				paraSoftvibrateWall();
				host.game.paraSoftsetNextPlayerMoveTime(paraSoftgameTime);
			}
			
		} else if(paraSoftcontinuousLeftMove) {
			if(paraSoftgameTime >= host.game.paraSoftgetNextPlayerMoveTime()) {
				if(active.moveLeft(board)) {
					paraSoftvibrateShort();
					host.paradisplay.paraSoftparaSoftinvalidatePhantom();
					host.game.paraSoftsetNextPlayerMoveTime(host.game.paraSoftgetNextPlayerMoveTime() + host.game.paraSoftgetMoveInterval());
				} else {
					paraSoftvibrateWall();
					host.game.paraSoftsetNextPlayerMoveTime(paraSoftgameTime);
				}
			}

			if(paraSoftclearLeftMove) {
				paraSoftcontinuousLeftMove = false;
				paraSoftclearLeftMove = false;
			}
		}
		if(paraSoftrightMove) {
			paraSoftcontinuousRightMove = true;
			paraSoftrightMove = false;
			if(active.moveRight(board)) {
				paraSoftvibrateShort();
				host.paradisplay.paraSoftparaSoftinvalidatePhantom();
				host.game.paraSoftsetNextPlayerMoveTime(host.game.paraSoftgetNextPlayerMoveTime() + paraSoftinitialHIntervalFactor*host.game.paraSoftgetMoveInterval());
			} else {
				paraSoftvibrateWall();
				host.game.paraSoftsetNextPlayerMoveTime(paraSoftgameTime);
			}
			
		} else if(paraSoftcontinuousRightMove) {
			if(paraSoftgameTime >= host.game.paraSoftgetNextPlayerMoveTime()) {
				if(active.moveRight(board)) {
					paraSoftvibrateShort();
					host.paradisplay.paraSoftparaSoftinvalidatePhantom();
					host.game.paraSoftsetNextPlayerMoveTime(host.game.paraSoftgetNextPlayerMoveTime() + host.game.paraSoftgetMoveInterval());
				} else {
					paraSoftvibrateWall();
					host.game.paraSoftsetNextPlayerMoveTime(paraSoftgameTime);
				}
			}

			if(paraSoftclearRightMove) {
				paraSoftcontinuousRightMove = false;
				paraSoftclearRightMove = false;
			}
		}
		if(paraSoftplayerHardDrop) {
			board.paraSoftinterruptClearAnimation();
			int paraSofthardDropDistance = active.paraSofthardDrop(false, board);
			paraSoftvibrateBottom();
			host.game.paraSoftclearLines(true, paraSofthardDropDistance);
			host.game.paraSoftpieceTransition(paraSofteventVibrationEnabled);
			board.paraSoftinvalidate();
			paraSoftplayerHardDrop = false;
			
			if((host.game.paraSoftgetMaxLevel() < paraSoftmaxLevel) && (host.game.paraSoftgetClearedLines() > paraSoftlineThresholds[Math.min(host.game.paraSoftgetMaxLevel(),paraSoftmaxLevel - 1)]))
				host.game.nextLevel();
			host.game.paraSoftsetNextDropTime(paraSoftgameTime + host.game.paraSoftgetAutoDropInterval());
			host.game.paraSoftsetNextPlayerDropTime(paraSoftgameTime);
		} else if(paraSoftplayerSoftDrop) {
			paraSoftplayerSoftDrop = false;
			paraSoftcontinuousSoftDrop = true;
			if(!active.drop(board)) {
				paraSoftvibrateBottom();
				host.game.paraSoftclearLines(false, 0);
				host.game.paraSoftpieceTransition(paraSofteventVibrationEnabled);
				board.paraSoftinvalidate();
			} else {
				host.game.paraSoftincSoftDropCounter();
			}
			if((host.game.paraSoftgetMaxLevel() < paraSoftmaxLevel) && (host.game.paraSoftgetClearedLines() > paraSoftlineThresholds[Math.min(host.game.paraSoftgetMaxLevel(),paraSoftmaxLevel - 1)]))
				host.game.nextLevel();
			host.game.paraSoftsetNextDropTime(host.game.paraSoftgetNextPlayerDropTime() + host.game.paraSoftgetAutoDropInterval());
			host.game.paraSoftsetNextPlayerDropTime(host.game.paraSoftgetNextPlayerDropTime() + paraSoftinitialVIntervalFactor*host.game.paraSoftgetSoftDropInterval());

		} else if(paraSoftcontinuousSoftDrop) {
			if(paraSoftgameTime >= host.game.paraSoftgetNextPlayerDropTime()) {
				if(!active.drop(board)) {
					paraSoftvibrateBottom();
					host.game.paraSoftclearLines(false, 0);
					host.game.paraSoftpieceTransition(paraSofteventVibrationEnabled);
					board.paraSoftinvalidate();
				} else {
					host.game.paraSoftincSoftDropCounter();
				}
				if((host.game.paraSoftgetMaxLevel() < paraSoftmaxLevel) && (host.game.paraSoftgetClearedLines() > paraSoftlineThresholds[Math.min(host.game.paraSoftgetMaxLevel(),paraSoftmaxLevel - 1)]))
					host.game.nextLevel();
				host.game.paraSoftsetNextDropTime(host.game.paraSoftgetNextPlayerDropTime() + host.game.paraSoftgetAutoDropInterval());
				host.game.paraSoftsetNextPlayerDropTime(host.game.paraSoftgetNextPlayerDropTime() + host.game.paraSoftgetSoftDropInterval());
			} else if(paraSoftgameTime >= host.game.paraSoftgetNextDropTime()) {
				if(!active.drop(board)) {
					paraSoftvibrateBottom();
					host.game.paraSoftclearLines(false, 0);
					host.game.paraSoftpieceTransition(paraSofteventVibrationEnabled);
					board.paraSoftinvalidate();
				}
				if((host.game.paraSoftgetMaxLevel() < paraSoftmaxLevel) && (host.game.paraSoftgetClearedLines() > paraSoftlineThresholds[Math.min(host.game.paraSoftgetMaxLevel(),paraSoftmaxLevel - 1)]))
					host.game.nextLevel();
				host.game.paraSoftsetNextDropTime(host.game.paraSoftgetNextDropTime() + host.game.paraSoftgetAutoDropInterval());
				host.game.paraSoftsetNextPlayerDropTime(host.game.paraSoftgetNextDropTime() + host.game.paraSoftgetSoftDropInterval());
			}
			if(paraSoftclearPlayerSoftDrop) {
				paraSoftcontinuousSoftDrop = false;
				paraSoftclearPlayerSoftDrop = false;
			}
		} else if(paraSoftgameTime >= host.game.paraSoftgetNextDropTime()) {
			if(!active.drop(board)) {
				paraSoftvibrateBottom();
				host.game.paraSoftclearLines(false, 0);
				host.game.paraSoftpieceTransition(paraSofteventVibrationEnabled);
				board.paraSoftinvalidate();
			}
			if((host.game.paraSoftgetMaxLevel() < paraSoftmaxLevel) && (host.game.paraSoftgetClearedLines() > paraSoftlineThresholds[Math.min(host.game.paraSoftgetMaxLevel(),paraSoftmaxLevel - 1)]))
				host.game.nextLevel();
			host.game.paraSoftsetNextDropTime(host.game.paraSoftgetNextDropTime() + host.game.paraSoftgetAutoDropInterval());
			host.game.paraSoftsetNextPlayerDropTime(host.game.paraSoftgetNextDropTime());
			
		} else
			host.game.paraSoftsetNextPlayerDropTime(paraSoftgameTime);
	}

	public void setBoard(paraSoftBoard instance2) {
		this.board = instance2;
	}

	public paraSoftBoard getBoard() {
		return this.board;
	}
	@Override
	public void reconnect(paraSoftGameActivity cont) {
		super.reconnect(cont);
		v = (Vibrator) cont.getSystemService(Context.VIBRATOR_SERVICE);
		
		paraSoftbuttonVibrationEnabled = PreferenceManager.getDefaultSharedPreferences(cont).getBoolean("parallaxsoftpref_vibration_button", false);
		paraSofteventVibrationEnabled = PreferenceManager.getDefaultSharedPreferences(cont).getBoolean("parallaxsoftpref_vibration_events", false);
		try {
			paraSoftvibrationOffset = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(cont).getString("parallaxsoftpref_vibDurOffset", "0"));
		} catch(NumberFormatException e) {
			paraSoftvibrationOffset = 0;
		}
	}
	@Override
	public void disconnect() {
		super.disconnect();
		v = null;
	}

	public void paraSoftboardPressed(float x, float y) {
		if(paraSoftpreviewBox == null)
			return;
		
		paraSoftboardTouched = true;
		
		if(paraSoftpreviewBox.contains((int)x, (int)y))
			host.game.hold();
	}

	public void paraSoftboardReleased() {
		paraSoftboardTouched = false;
	}

	public void paraSoftsetPreviewRect(Rect rect) {
		paraSoftpreviewBox = rect;
	}

	public boolean paraSoftisBoardTouched() {
		return paraSoftboardTouched;
	}
	
}
