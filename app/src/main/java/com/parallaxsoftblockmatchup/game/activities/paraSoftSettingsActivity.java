package com.parallaxsoftblockmatchup.game.activities;
import com.parallaxsoftblockmatchup.game.R;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

public class paraSoftSettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener, OnPreferenceClickListener {

	@SuppressLint("NewApi")
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.simple_preferences);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	        ActionBar actionBar = getActionBar();
	        actionBar.setDisplayHomeAsUpEnabled(true);
	    }

		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		Preference pref = findPreference("parallaxsoftpref_advanced");
		pref.setOnPreferenceClickListener(this);

        pref = findPreference("parallaxsoftpref_vibDurOffset");
        String paraSofttimeString = PreferenceManager.getDefaultSharedPreferences(this).getString("parallaxsoftpref_vibDurOffset", "");
        if(paraSofttimeString.equals(""))
        	paraSofttimeString = "0";
        paraSofttimeString = "" + paraSofttimeString + " ms";
        pref.setSummary(paraSofttimeString);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key) {

		if (key.equals("parallaxsoftpref_vibDurOffset")) {
            Preference connectionPref = findPreference(key);
            String paraSofttimeString = sharedPreferences.getString(key, "");
            if(paraSofttimeString.equals(""))
            	paraSofttimeString = "0";
            paraSofttimeString = "" + paraSofttimeString + " ms";
            connectionPref.setSummary(paraSofttimeString);
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

	@Override
	public boolean onPreferenceClick(Preference preference) {
		Intent intent = new Intent(this, paraSoftAdvancedSettingsActivity.class);
		startActivity(intent);
		return true;
	}


}
