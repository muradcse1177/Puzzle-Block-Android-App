package com.parallaxsoftblockmatchup.game.components;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import com.parallaxsoftblockmatchup.game.paraSoftPieceGenerator;
import com.parallaxsoftblockmatchup.game.R;
import com.parallaxsoftblockmatchup.game.activities.paraSoftGameActivity;
import com.parallaxsoftblockmatchup.game.pieces.*;
import android.R.color;
import android.preference.PreferenceManager;


public class paraSoftGameState extends paraSoftComponent {

	public final static int paraSoftstate_startable = 0;
	public final static int paraSoftstate_running = 1;
	public final static int paraSoftstate_paused = 2;
	public final static int paraSoftstate_finished = 3;
	private static paraSoftGameState instance;
	private paraSoftPieceGenerator rng;
	public paraSoftBoard board;
	private GregorianCalendar date;
	private SimpleDateFormat paraSoftformatter;
	public int paraSofthourOffset;
	private String paraSoftplayerName;
	private int paraSoftactiveIndex, paraSoftpreviewIndex;
	private paraSoftPiece[] paraSoftactivePieces;
	private paraSoftPiece[] paraSoftpreviewPieces;
	private boolean paraSoftscheduleSpawn;
	private long paraSoftspawnTime;
	private int paraSoftstateOfTheGame;
	private long score;
	private int paraSoftclearedLines;
	private int level;
	private int paraSoftmaxLevel;
	private long paraSoftgameTime;
	private long currentTime;
	private long nextDropTime;
	private long paraSoftnextPlayerDropTime;
	private long paraSoftnextPlayerMoveTime;
	private int[] paraSoftdropIntervals;
	private long paraSoftplayerDropInterval;
	private long paraSoftplayerMoveInterval;
	private int paraSoftsingleLineScore;
	private int paraSoftdoubleLineScore;
	private int paraSofttrippleLineScore;
	private int paraSofttmultiTetrisScore;
	private boolean paraSoftmultitetris;
	private int paraSoftquadLineScore;
	private int paraSofthardDropBonus;
	private int paraSoftsoftDropBonus;
	private int paraSoftspawn_delay;
	private int paraSoftpiece_start_x;
	private long actions;
	private int paraSoftsongtime;
	private long paraSoftpopupTime;
	private String paraSoftpopupString;
	private int paraSoftpopupAttack;
	private int paraSoftpopupSustain;
	private int paraSoftpopupDecay;
	private int paraSoftsoftDropDistance;
	
	private paraSoftGameState(paraSoftGameActivity ga) {
		super(ga);
		actions = 0;
		board = new paraSoftBoard(host);
		date = new GregorianCalendar();
		paraSoftformatter = new SimpleDateFormat("HH:mm:ss",Locale.US);
		date.setTimeInMillis(60000);
		if(paraSoftformatter.format(date.getTime()).startsWith("23"))
			paraSofthourOffset = 1;
		else if(paraSoftformatter.format(date.getTime()).startsWith("01"))
			paraSofthourOffset = -1;
		else
			paraSofthourOffset = 0;

		paraSoftdropIntervals = host.getResources().getIntArray(R.array.intervals);
		paraSoftsingleLineScore = host.getResources().getInteger(R.integer.paraSoftsingleLineScore);
		paraSoftdoubleLineScore = host.getResources().getInteger(R.integer.paraSoftdoubleLineScore);
		paraSofttrippleLineScore = host.getResources().getInteger(R.integer.paraSofttrippleLineScore);
		paraSofttmultiTetrisScore = host.getResources().getInteger(R.integer.paraSofttmultiTetrisScore);
		paraSoftquadLineScore = host.getResources().getInteger(R.integer.paraSoftquadLineScore);
		paraSofthardDropBonus = host.getResources().getInteger(R.integer.paraSofthardDropBonus);
		paraSoftsoftDropBonus = host.getResources().getInteger(R.integer.paraSoftsoftDropBonus);
		paraSoftsoftDropDistance = 0;
		paraSoftspawn_delay = host.getResources().getInteger(R.integer.paraSoftspawn_delay);
		paraSoftpiece_start_x = host.getResources().getInteger(R.integer.paraSoftpiece_start_x);
		paraSoftpopupAttack = host.getResources().getInteger(R.integer.popup_attack);
		paraSoftpopupSustain = host.getResources().getInteger(R.integer.popup_sustain);
		paraSoftpopupDecay = host.getResources().getInteger(R.integer.popup_decay);
		paraSoftpopupString = "";
		paraSoftpopupTime = -(paraSoftpopupAttack + paraSoftpopupSustain + paraSoftpopupDecay);
		paraSoftclearedLines = 0;
		level = 0;
		score = 0;
		paraSoftsongtime = 0;
		paraSoftmaxLevel = host.getResources().getInteger(R.integer.levels);
		nextDropTime = host.getResources().getIntArray(R.array.intervals)[0];
		paraSoftplayerDropInterval = (int)(1000.0f / PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_softdropspeed", 60));
		paraSoftplayerMoveInterval = (int)(1000.0f / PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_movespeed", 60));
		paraSoftnextPlayerDropTime = (int)(1000.0f / PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_softdropspeed", 60));
		paraSoftnextPlayerMoveTime = (int)(1000.0f / PreferenceManager.getDefaultSharedPreferences(host).getInt("parallaxsoftpref_movespeed", 60));
		
		paraSoftgameTime = 0;
		if(PreferenceManager.getDefaultSharedPreferences(host).getString("parallaxsoftpref_rng", "sevenbag").equals("sevenbag") ||
				PreferenceManager.getDefaultSharedPreferences(host).getString("parallaxsoftpref_rng", "7-Bag-Randomization (default)").equals("7-Bag-Randomization (default)"))
			rng = new paraSoftPieceGenerator(paraSoftPieceGenerator.paraSoftSTRAT_7BAG);
		else
			rng = new paraSoftPieceGenerator(paraSoftPieceGenerator.paraSoftSTRAT_RANDOM);
		paraSoftactivePieces  = new paraSoftPiece[7];
		paraSoftpreviewPieces = new paraSoftPiece[7];
		
		paraSoftactivePieces[0] = new IParaSoftPiece(host);
		paraSoftactivePieces[1] = new JParasoftPiece(host);
		paraSoftactivePieces[2] = new LParasoftPiece(host);
		paraSoftactivePieces[3] = new OParaSoftPiece(host);
		paraSoftactivePieces[4] = new paraSoftSParasoftPiece(host);
		paraSoftactivePieces[5] = new paraSoftTParasoftPiece(host);
		paraSoftactivePieces[6] = new paraSoftZParasoftPiece(host);
		
		paraSoftpreviewPieces[0] = new IParaSoftPiece(host);
		paraSoftpreviewPieces[1] = new JParasoftPiece(host);
		paraSoftpreviewPieces[2] = new LParasoftPiece(host);
		paraSoftpreviewPieces[3] = new OParaSoftPiece(host);
		paraSoftpreviewPieces[4] = new paraSoftSParasoftPiece(host);
		paraSoftpreviewPieces[5] = new paraSoftTParasoftPiece(host);
		paraSoftpreviewPieces[6] = new paraSoftZParasoftPiece(host);
		paraSoftactiveIndex  = rng.next();
		paraSoftpreviewIndex = rng.next();
		paraSoftactivePieces[paraSoftactiveIndex].paraSoftsetActive(true);
		paraSoftstateOfTheGame = paraSoftstate_startable;
		paraSoftscheduleSpawn = false;
		paraSoftspawnTime = 0;
	}

	public void paraSoftsetPlayerName(String string) {
		paraSoftplayerName = string;
	}
	
	public paraSoftBoard getBoard() {
		return board;
	}

	public String paraSoftgetPlayerName() {
		return paraSoftplayerName;
	}
	
	public int paraSoftgetAutoDropInterval() {
		return paraSoftdropIntervals[Math.min(level,paraSoftmaxLevel)];
	}
	
	public long paraSoftgetMoveInterval() {
		return paraSoftplayerMoveInterval;
	}
	
	public long paraSoftgetSoftDropInterval() {
		return paraSoftplayerDropInterval;
	}
	
	public void paraSoftsetRunning(boolean b) {
		if(b) {
			currentTime = System.currentTimeMillis();
			if(paraSoftstateOfTheGame != paraSoftstate_finished)
				paraSoftstateOfTheGame = paraSoftstate_running;
		} else {
			if(paraSoftstateOfTheGame == paraSoftstate_running)
				paraSoftstateOfTheGame = paraSoftstate_paused;
		}
	}
	
	public void paraSoftclearLines(boolean paraSoftplayerHardDrop, int paraSofthardDropDistance) {
		if(host == null)
			return;

		paraSoftactivePieces[paraSoftactiveIndex].place(board);
		int cleared = board.paraSoftclearLines(paraSoftactivePieces[paraSoftactiveIndex].getDim());
		paraSoftclearedLines += cleared;
		long addScore;
		
		switch(cleared) {
			case 1:
				addScore = paraSoftsingleLineScore;
				paraSoftmultitetris = false;
				host.sound.paraSoftclearSound();
				paraSoftpopupTime = paraSoftgameTime;
				break;
			case 2:
				addScore = paraSoftdoubleLineScore;
				paraSoftmultitetris = false;
				host.sound.paraSoftclearSound();
				paraSoftpopupTime = paraSoftgameTime;
				break;
			case 3:
				addScore = paraSofttrippleLineScore;
				paraSoftmultitetris = false;
				host.sound.paraSoftclearSound();
				paraSoftpopupTime = paraSoftgameTime;
				break;
			case 4:
				if(paraSoftmultitetris)
					addScore = paraSofttmultiTetrisScore;
				else
					addScore = paraSoftquadLineScore;
				paraSoftmultitetris = true;
				host.sound.paraSofttetrisSound();
				paraSoftpopupTime = paraSoftgameTime;
				break;
			default:
				addScore = 0;
				host.sound.paraSoftdropSound();
				if((paraSoftgameTime - paraSoftpopupTime) < (paraSoftpopupAttack + paraSoftpopupSustain))
					paraSoftpopupTime = paraSoftgameTime - (paraSoftpopupAttack + paraSoftpopupSustain);
				break;
		}
		if(cleared > 0) {
			if(paraSoftplayerHardDrop) {
				addScore += paraSofthardDropDistance*paraSofthardDropBonus;
			} else {
				addScore += paraSoftsoftDropDistance*paraSoftsoftDropBonus;
			}
		}
		score += addScore;
		if(addScore != 0)
			paraSoftpopupString = "+"+addScore;
	}

	public void paraSoftpieceTransition(boolean paraSofteventVibrationEnabled) {
		if(host == null)
			return;
		
		paraSoftscheduleSpawn = true;
		if(paraSofteventVibrationEnabled)
			paraSoftspawnTime = paraSoftgameTime + paraSoftspawn_delay;
		else
			paraSoftspawnTime = paraSoftgameTime;
		
		paraSoftactivePieces[paraSoftactiveIndex].reset(host);
		paraSoftactiveIndex  = paraSoftpreviewIndex;
		paraSoftpreviewIndex = rng.next();
		paraSoftactivePieces[paraSoftactiveIndex].reset(host);
	}
	
	public void hold() {
		if(host == null)
			return;
		
		
	}
	
	public void paraSoftfinishTransition() {
		if(host == null)
			return;
		
		paraSoftscheduleSpawn = false;
		host.paradisplay.paraSoftparaSoftinvalidatePhantom();
		paraSoftactivePieces[paraSoftactiveIndex].paraSoftsetActive(true);
		paraSoftsetNextDropTime(paraSoftgameTime + paraSoftdropIntervals[Math.min(level,paraSoftmaxLevel)]);
		paraSoftsetNextPlayerDropTime(paraSoftgameTime);
		paraSoftsetNextPlayerMoveTime(paraSoftgameTime);
		paraSoftsoftDropDistance = 0;

		if(!paraSoftactivePieces[paraSoftactiveIndex].paraSoftsetPosition(paraSoftpiece_start_x, 0, false, board)) {
			paraSoftstateOfTheGame = paraSoftstate_finished;
			host.sound.paraSoftparaSoftgameOverSound();
			host.paraSoftgameOver(score, paraSoftgetTimeString(), (int)((float)actions*(60000.0f / paraSoftgameTime)));
		}
	}
	
	public boolean paraSoftisResumable() {
		return (paraSoftstateOfTheGame != paraSoftstate_finished);
	}

	public String paraSoftgetScoreString() {
		return "" + score;
	}

	public paraSoftPiece paraSoftgetActivePiece() {
		return paraSoftactivePieces[paraSoftactiveIndex];
	}

	public boolean cycle(long tempTime) {
		if(paraSoftstateOfTheGame != paraSoftstate_running)
			return false;
		
		paraSoftgameTime += (tempTime - currentTime);
		currentTime = tempTime;
		if(paraSoftscheduleSpawn) {
			if(paraSoftgameTime >= paraSoftspawnTime)
				paraSoftfinishTransition();
			return false;
		}
		return true;
	}

	public String paraSoftgetLevelString() {
		return "" + level;
	}

	public String paraSoftgetTimeString() {
		date.setTimeInMillis(paraSoftgameTime + paraSofthourOffset*(3600000));
		return paraSoftformatter.format(date.getTime());
	}

	public String paraSoftgetAPMString() {
		if(host == null)
			return "";
		return String.valueOf((int)((float)actions*(60000.0f / paraSoftgameTime)));
	}
	
	@Override
	public void reconnect(paraSoftGameActivity ga) {
		super.reconnect(ga);
		
		paraSoftplayerDropInterval = (int)(1000.0f / PreferenceManager.getDefaultSharedPreferences(ga).getInt("parallaxsoftpref_softdropspeed", 60));
		paraSoftplayerMoveInterval = (int)(1000.0f / PreferenceManager.getDefaultSharedPreferences(ga).getInt("parallaxsoftpref_movespeed", 60));
		
		if(PreferenceManager.getDefaultSharedPreferences(ga).getString("parallaxsoftpref_rng", "sevenbag").equals("sevenbag") ||
				PreferenceManager.getDefaultSharedPreferences(ga).getString("parallaxsoftpref_rng", "7-Bag-Randomization (default)").equals("7-Bag-Randomization (default)"))
			rng = new paraSoftPieceGenerator(paraSoftPieceGenerator.paraSoftSTRAT_7BAG);
		else
			rng = new paraSoftPieceGenerator(paraSoftPieceGenerator.paraSoftSTRAT_RANDOM);

		board.reconnect(ga);
		paraSoftsetRunning(true);
	}

	public void disconnect() {
		paraSoftsetRunning(false);
		board.disconnect();
		super.disconnect();
	}

	public paraSoftPiece paraSoftgetPreviewPiece() {
		return paraSoftpreviewPieces[paraSoftpreviewIndex];
	}

	public long getTime() {
		return paraSoftgameTime;
	}

	public void nextLevel() {
		level++;
	}

	public int getLevel() {
		return level;
	}

	public int paraSoftgetMaxLevel() {
		return paraSoftmaxLevel;
	}

	public int paraSoftgetClearedLines() {
		return paraSoftclearedLines;
	}

	public void action() {
		actions++;
	}

	public void paraSoftsetNextPlayerDropTime(long time) {
		paraSoftnextPlayerDropTime = time;
	}

	public void paraSoftsetNextPlayerMoveTime(long time) {
		paraSoftnextPlayerMoveTime = time;
	}

	public void paraSoftsetNextDropTime(long l) {
		nextDropTime = l;
	}

	public long paraSoftgetNextPlayerDropTime() {
		return paraSoftnextPlayerDropTime;
	}

	public long paraSoftgetNextDropTime() {
		return nextDropTime;
	}

	public long paraSoftgetNextPlayerMoveTime() {
		return paraSoftnextPlayerMoveTime;
	}

	public static void destroy() {
		if(instance != null)
			instance.disconnect();
		instance = null;
	}

	public static paraSoftGameState getInstance(paraSoftGameActivity ga) {
		if(instance == null)
			instance = new paraSoftGameState(ga);
		return instance;
	}

	public static paraSoftGameState getNewInstance(paraSoftGameActivity ga) {
		instance = new paraSoftGameState(ga);
		return instance;
	}

	public static boolean hasInstance() {
		return (instance != null);
	}

	public long getScore() {
		return score;
	}

	public int getAPM() {
		return (int)((float)actions*(60000.0f / paraSoftgameTime));
	}

	public int paraSoftgetSongtime() {
		return paraSoftsongtime;
	}
	
	public static boolean paraSoftisFinished() {
		if(instance == null)
			return true;
		return !instance.paraSoftisResumable();
	}

	public void paraSoftsetSongtime(int paraSoftsongtime) {
		this.paraSoftsongtime = paraSoftsongtime;
	}

	public void setLevel(int int1) {
		level = int1;
		nextDropTime = host.getResources().getIntArray(R.array.intervals)[int1];
		paraSoftclearedLines = 10*int1;
	}

	public String paraSoftgetPopupString() {
		return paraSoftpopupString;
	}

	public int paraSoftgetPopupAlpha() {
		long x = paraSoftgameTime - paraSoftpopupTime;
		
		if(x < (paraSoftpopupAttack+paraSoftpopupSustain))
			return 255;
		
		if(x < (paraSoftpopupAttack+paraSoftpopupSustain+paraSoftpopupDecay))
			return (int)(255.0f*(1.0f + (((float)(paraSoftpopupAttack + paraSoftpopupSustain - x))/((float)paraSoftpopupDecay))));
		
		return 0;
	}

	public float paraSoftgetPopupSize() {
		long x = paraSoftgameTime - paraSoftpopupTime;
		
		if(x < paraSoftpopupAttack)
			return (int)(60.0f*(1.0f + (((float)x)/((float)paraSoftpopupAttack))));
		
		return 120;
	}

	public int getPopupColor() {
		if(host == null)
			return 0;
		
		if(paraSoftmultitetris)
			return host.getResources().getColor(R.color.yellow);
		return host.getResources().getColor(color.white);
	}

	public void paraSoftincSoftDropCounter() {
		paraSoftsoftDropDistance++;
	}
	
}