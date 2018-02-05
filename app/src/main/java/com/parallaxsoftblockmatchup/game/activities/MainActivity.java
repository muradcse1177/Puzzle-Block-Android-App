package com.parallaxsoftblockmatchup.game.activities;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.parallaxsoftblockmatchup.game.R;
import com.parallaxsoftblockmatchup.game.components.paraSoftGameState;
import com.parallaxsoftblockmatchup.game.components.paraSoftSound;
import com.parallaxsoftblockmatchup.game.db.paraSoftHighscoreOpenHelper;
import com.parallaxsoftblockmatchup.game.db.paraSoftScoreDataSource;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Button;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends ListActivity {

	public static final int paraSoftSCORE_REQUEST = 0x0;
	public static final String paraSoftPLAYERNAME_KEY = "com.parallaxsoftblockmatchup.game.activities.playername";
	public static final String paraSoftSCORE_KEY = "com.parallaxsoftblockmatchup.game.activities.score";
	
	public paraSoftScoreDataSource paraSoftdatasource;
	private SimpleCursorAdapter paraSoftadapter;
	private AlertDialog.Builder paraSoftparaSoftstartLevelDialog;
	private AlertDialog.Builder paraSoftdonateDialog;
	private int paraSoftstartLevel;
	private View paraSoftdialogView;
	private SeekBar paraSoftleveldialogBar;
	private TextView paraSoftleveldialogtext;
	private paraSoftSound sound;
	private InterstitialAd mInterstitialAd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		prepareAd();

		ScheduledExecutorService scheduler =
				Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new Runnable() {

			Handler handler = new Handler();
			public void run() {
				Log.i("hello", "world");
				runOnUiThread(new Runnable() {
					public void run() {
						if (mInterstitialAd.isLoaded()) {
							Log.d("TAG"," Loaded another ad");
							mInterstitialAd.show();
						}

						else {
							Log.d("TAG"," Interstitial not loaded");
						}
						prepareAd();
					}
				});

			}
		}, 30, 60, TimeUnit.SECONDS);


		PreferenceManager.setDefaultValues(this, R.xml.simple_preferences, true);
		PreferenceManager.setDefaultValues(this, R.xml.advanced_preferences, true);
		sound = new paraSoftSound(this);
		sound.paraSoftstartMusic(paraSoftSound.paraSoftMENU_MUSIC, 0);
		Cursor mc;
	    paraSoftdatasource = new paraSoftScoreDataSource(this);
	    paraSoftdatasource.open();
	    mc = paraSoftdatasource.paraSoftgetCursor();
	    paraSoftadapter = new SimpleCursorAdapter(
	    	(Context)this,
	        R.layout.bloertyuckinger_list_item,
	        mc,
	        new String[] {paraSoftHighscoreOpenHelper.paraSoftCOLUMN_SCORE, paraSoftHighscoreOpenHelper.paraSoftCOLUMN_PLAYERNAME},
	        new int[] {R.id.text1, R.id.text2},
	        SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
	    setListAdapter(paraSoftadapter);
	    paraSoftstartLevel = 0;
	    paraSoftparaSoftstartLevelDialog = new AlertDialog.Builder(this);
		paraSoftparaSoftstartLevelDialog.setTitle(R.string.paraSoftparaSoftstartLevelDialogTitle);
		paraSoftparaSoftstartLevelDialog.setCancelable(false);
		paraSoftparaSoftstartLevelDialog.setNegativeButton(R.string.paraSoftparaSoftstartLevelDialogCancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		paraSoftparaSoftstartLevelDialog.setPositiveButton(R.string.paraSoftparaSoftstartLevelDialogStart, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				MainActivity.this.start();
			}
		});

	    paraSoftdonateDialog = new AlertDialog.Builder(this);
	    paraSoftdonateDialog.setTitle(R.string.parallaxsoftpref_donate_title);
	    paraSoftdonateDialog.setMessage(R.string.parallaxsoftpref_donate_summary);
	    paraSoftdonateDialog.setNegativeButton(R.string.paraSoftparaSoftstartLevelDialogCancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
	    paraSoftdonateDialog.setPositiveButton(R.string.donate_button, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String url = getResources().getString(R.string.donation_url);
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		});
	}
	public void  prepareAd(){
		mInterstitialAd = new InterstitialAd(this);
		mInterstitialAd.setAdUnitId("ca-app-pub-6861884208088827/1553458703");
		mInterstitialAd.loadAd(new AdRequest.Builder().build());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.parallaxsoftaction_settings:
				Intent intent = new Intent(this, paraSoftSettingsActivity.class);
				startActivity(intent);
				return true;
			case R.id.parallaxsoftaction_about:
				Intent intent1 = new Intent(this, paraSoftAboutActivity.class);
				startActivity(intent1);
				return true;
			case R.id.parallaxsoftaction_donate:
				paraSoftdonateDialog.show();
				return true;
			case R.id.parallaxsoftaction_help:
				Intent intent2 = new Intent(this, paraSoftHelpActivity.class);
				startActivity(intent2);
				return true;
			case R.id.parallaxsoftaction_exit:
			    paraSoftGameState.destroy();
			    MainActivity.this.finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	public void start() {
		Intent intent = new Intent(this, paraSoftGameActivity.class);
		Bundle b = new Bundle();
		b.putInt("mode", paraSoftGameActivity.paraSoftNEW_GAME);
		b.putInt("level", paraSoftstartLevel);
		b.putString("playername", ((TextView)findViewById(R.id.nicknameEditView)).getText().toString()); //Your id
		intent.putExtras(b);
		startActivityForResult(intent,paraSoftSCORE_REQUEST);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode != paraSoftSCORE_REQUEST)
			return;
		if(resultCode != RESULT_OK)
			return;

		String paraSoftplayerName = data.getStringExtra(paraSoftPLAYERNAME_KEY);
		long score = data.getLongExtra(paraSoftSCORE_KEY,0);

	    paraSoftdatasource.open();
	    paraSoftdatasource.paraSoftcreateScore(score, paraSoftplayerName);
	}


    public void onClickStart(View view) {
		paraSoftdialogView = getLayoutInflater().inflate(R.layout.seeertrytye5k_bar_dialog, null);
		paraSoftleveldialogtext = ((TextView)paraSoftdialogView.findViewById(R.id.leveldialoglevelparadisplay));
		paraSoftleveldialogBar = ((SeekBar)paraSoftdialogView.findViewById(R.id.levelseekbar));
		paraSoftleveldialogBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				paraSoftleveldialogtext.setText("" + arg1);
				paraSoftstartLevel = arg1;
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
			}
			
		});
		paraSoftleveldialogBar.setProgress(paraSoftstartLevel);
		paraSoftleveldialogtext.setText("" + paraSoftstartLevel);
		paraSoftparaSoftstartLevelDialog.setView(paraSoftdialogView);
		paraSoftparaSoftstartLevelDialog.show();
    }

    public void onClickResume(View view) {
		Intent intent = new Intent(this, paraSoftGameActivity.class);
		Bundle b = new Bundle();
		b.putInt("mode", paraSoftGameActivity.paraSoftRESUME_GAME);
		b.putString("playername", ((TextView)findViewById(R.id.nicknameEditView)).getText().toString());
		intent.putExtras(b);
		startActivityForResult(intent,paraSoftSCORE_REQUEST);
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	sound.pause();
    	sound.setInactive(true);
    };
    
    @Override
    protected void onStop() {
    	super.onStop();
    	sound.pause();
    	sound.setInactive(true);
    	paraSoftdatasource.close();
    };
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	sound.release();
    	sound = null;
    	paraSoftdatasource.close();
    };
    
    @Override
    protected void onResume() {
    	super.onResume();
    	sound.setInactive(false);
    	sound.resume();
    	paraSoftdatasource.open();
	    Cursor cursor = paraSoftdatasource.paraSoftgetCursor();
	    paraSoftadapter.changeCursor(cursor);
	    
	    if(!paraSoftGameState.paraSoftisFinished()) {
	    	((Button)findViewById(R.id.resumeButton)).setEnabled(true);
	    	((Button)findViewById(R.id.resumeButton)).setTextColor(getResources().getColor(R.color.parallaxsoftserror));
	    } else {
	    	((Button)findViewById(R.id.resumeButton)).setEnabled(false);
	    	((Button)findViewById(R.id.resumeButton)).setTextColor(getResources().getColor(R.color.holo_grey));
	    }
    };

}
