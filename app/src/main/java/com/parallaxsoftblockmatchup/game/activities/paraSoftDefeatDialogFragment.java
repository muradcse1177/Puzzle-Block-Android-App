package com.parallaxsoftblockmatchup.game.activities;

import com.parallaxsoftblockmatchup.game.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class paraSoftDefeatDialogFragment extends DialogFragment {

	private CharSequence paraSoftscoreString;
	private CharSequence paraSofttimeString;
	private CharSequence paraSoftapmString;
	private long score;
	
	public paraSoftDefeatDialogFragment() {
		super();
		paraSoftscoreString = "unknown";
		paraSofttimeString = "unknown";
		paraSoftapmString = "unknown";
	}
	
	public void setData(long scoreArg, String time, int apm) {
		paraSoftscoreString = String.valueOf(scoreArg);
		paraSofttimeString = time;
		paraSoftapmString = String.valueOf(apm);
		score = scoreArg;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstance) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.defeatDialogTitle);
		builder.setMessage(
				getResources().getString(R.string.scoreLabel) +
				"\n    " + paraSoftscoreString + "\n\n" +
				getResources().getString(R.string.timeLabel) +
				"\n    " + paraSofttimeString + "\n\n" +
				getResources().getString(R.string.apmLabel) +
				"\n    " + paraSoftapmString + "\n\n" +
				getResources().getString(R.string.hint)
				);
		builder.setNeutralButton(R.string.defeatDialogReturn, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				((paraSoftGameActivity)getActivity()).paraSoftputScore(score);
			}
		});
		return builder.create();
	}
}
