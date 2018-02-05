package com.parallaxsoftblockmatchup.game.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class paraSoftScoreDataSource {

	  private SQLiteDatabase database;
	  private paraSoftHighscoreOpenHelper dbHelper;
	  private String[] paraSoftallColumns = { paraSoftHighscoreOpenHelper.paraSoftCOLUMN_ID,
			  paraSoftHighscoreOpenHelper.paraSoftCOLUMN_SCORE,
			  paraSoftHighscoreOpenHelper.paraSoftCOLUMN_PLAYERNAME};

	  public paraSoftScoreDataSource(Context context) {
	    dbHelper = new paraSoftHighscoreOpenHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public paraSoftScore paraSoftcreateScore(long score, String paraSoftplayerName) {
	    ContentValues values = new ContentValues();
	    values.put(paraSoftHighscoreOpenHelper.paraSoftCOLUMN_SCORE, score);
	    values.put(paraSoftHighscoreOpenHelper.paraSoftCOLUMN_PLAYERNAME, paraSoftplayerName);
	    long paraSoftinsertId = database.insert(paraSoftHighscoreOpenHelper.paraSoftTABLE_HIGHSCORES, null, values);
	    Cursor cursor = database.query(paraSoftHighscoreOpenHelper.paraSoftTABLE_HIGHSCORES,
	        paraSoftallColumns, paraSoftHighscoreOpenHelper.paraSoftCOLUMN_ID + " = " + paraSoftinsertId, null,
	        null, null, paraSoftHighscoreOpenHelper.paraSoftCOLUMN_SCORE + " DESC");
	    cursor.moveToFirst();
	    paraSoftScore paraSoftnewScore = paraSoftcursorToScore(cursor);
	    cursor.close();
	    return paraSoftnewScore;
	  }

	  public void deleteScore(paraSoftScore score) {
	    long id = score.getId();
	    database.delete(paraSoftHighscoreOpenHelper.paraSoftTABLE_HIGHSCORES, paraSoftHighscoreOpenHelper.paraSoftCOLUMN_ID
	        + " = " + id, null);
	  }

	  private paraSoftScore paraSoftcursorToScore(Cursor cursor) {
		  paraSoftScore score = new paraSoftScore();
		  score.setId(cursor.getLong(0));
		  score.setScore(cursor.getLong(1));
		  score.setName(cursor.getString(2));
	    return score;
	  }

	public Cursor paraSoftgetCursor() {
		return database.query(paraSoftHighscoreOpenHelper.paraSoftTABLE_HIGHSCORES,
		        paraSoftallColumns, null, null, null, null, paraSoftHighscoreOpenHelper.paraSoftCOLUMN_SCORE + " DESC");
	}
	  
}
