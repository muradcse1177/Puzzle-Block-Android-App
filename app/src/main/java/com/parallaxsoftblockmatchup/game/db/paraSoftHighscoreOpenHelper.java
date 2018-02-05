package com.parallaxsoftblockmatchup.game.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class paraSoftHighscoreOpenHelper extends SQLiteOpenHelper {

	public static final String paraSoftTABLE_HIGHSCORES = "highscores";
	public static final String paraSoftCOLUMN_ID = "_id";
	public static final String paraSoftCOLUMN_SCORE = "score";
	public static final String paraSoftCOLUMN_PLAYERNAME = "playername";
	private static final String DATABASE_NAME = "highscores.db";
	private static final int DATABASE_VERSION = 1;

	private static final String paraSoftDATABASE_CREATE = "create table "
	      + paraSoftTABLE_HIGHSCORES + "(" + paraSoftCOLUMN_ID
	      + " integer primary key autoincrement, " + paraSoftCOLUMN_SCORE
	      + " integer, " + paraSoftCOLUMN_PLAYERNAME
	      + " text);";
	  
    public paraSoftHighscoreOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(paraSoftDATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(paraSoftHighscoreOpenHelper.class.getName(),
			"Upgrading database from version " + oldVersion + " to "
			+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + paraSoftTABLE_HIGHSCORES);
		onCreate(db);
	}

}
