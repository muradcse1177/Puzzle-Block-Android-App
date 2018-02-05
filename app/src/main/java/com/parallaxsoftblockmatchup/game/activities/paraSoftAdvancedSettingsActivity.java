package com.parallaxsoftblockmatchup.game.activities;
import com.parallaxsoftblockmatchup.game.R;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.MenuItem;

public class paraSoftAdvancedSettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	@SuppressLint("NewApi")
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.advanced_preferences);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	        ActionBar actionBar = getActionBar();
	        actionBar.setDisplayHomeAsUpEnabled(true);
	    }

		setVolumeControlStream(AudioManager.STREAM_MUSIC);

        Preference pref = findPreference("parallaxsoftpref_rng");
        if(PreferenceManager.getDefaultSharedPreferences(this).getString("parallaxsoftpref_rng", "").equals("sevenbag"))
        	pref.setSummary(getResources().getStringArray(R.array.randomizer_preference_array)[0]);
        else
        	pref.setSummary(getResources().getStringArray(R.array.randomizer_preference_array)[1]);
        
        pref = findPreference("parallaxsoftpref_paraSoftfpslimittext");
        pref.setSummary(PreferenceManager.getDefaultSharedPreferences(this).getString("parallaxsoftpref_paraSoftfpslimittext", ""));
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key) {

		if (key.equals("parallaxsoftpref_rng")) {
            Preference connectionPref = findPreference(key);
            if(sharedPreferences.getString(key, "").equals("sevenbag"))
            	connectionPref.setSummary(getResources().getStringArray(R.array.randomizer_preference_array)[0]);
            else
            	connectionPref.setSummary(getResources().getStringArray(R.array.randomizer_preference_array)[1]);
        }
		if (key.equals("parallaxsoftpref_paraSoftfpslimittext")) {
            Preference connectionPref = findPreference(key);
            connectionPref.setSummary(sharedPreferences.getString(key, ""));
        }
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		finish();
		return true;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
	    super.onResume();
	    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onPause() {
	    super.onPause();
	    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}
}
