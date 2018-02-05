package com.parallaxsoftblockmatchup.game;
import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class paraSoftSeekBarPreference extends Preference implements OnSeekBarChangeListener {
   
    private final String TAG = getClass().getName();
    private static final String paraSoftANDROIDNS="http://schemas.android.com/apk/res/android";
    private static final String paraSoftROBOBUNNYNS="http://robobunny.com";
    private static final int paraSoftDEFAULT_VALUE = 50;
    private int paraSoftmMaxValue      = 100;
    private int paraSoftmMinValue      = 0;
    private int paraSoftmInterval      = 1;
    private int paraSoftmCurrentValue;
    private String paraSoftmUnitsLeft  = "";
    private String paraSofmUnitsRight = "";
    private SeekBar paraSofmSeekBar;
   
    private TextView paraSofmStatusText;

    public paraSoftSeekBarPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        paraSoftinitPreference(context, attrs);
    }

    public paraSoftSeekBarPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paraSoftinitPreference(context, attrs);
    }

    private void paraSoftinitPreference(Context context, AttributeSet attrs) {
        paraSoftsetValuesFromXml(attrs);
        paraSofmSeekBar = new SeekBar(context, attrs);
        paraSofmSeekBar.setMax(paraSoftmMaxValue - paraSoftmMinValue);
        paraSofmSeekBar.setOnSeekBarChangeListener(this);
    }
   
    private void paraSoftsetValuesFromXml(AttributeSet attrs) {
        paraSoftmMaxValue = attrs.getAttributeIntValue(paraSoftANDROIDNS, "max", 100);
        paraSoftmMinValue = attrs.getAttributeIntValue(paraSoftROBOBUNNYNS, "min", 0);
       
        paraSoftmUnitsLeft = paraSoftgetAttributeStringValue(attrs, paraSoftROBOBUNNYNS, "unitsLeft", "");
        String units = paraSoftgetAttributeStringValue(attrs, paraSoftROBOBUNNYNS, "units", "");
        paraSofmUnitsRight = paraSoftgetAttributeStringValue(attrs, paraSoftROBOBUNNYNS, "paraSoftunitsRight", units);
       
        try {
            String paraSoftnewInterval = attrs.getAttributeValue(paraSoftROBOBUNNYNS, "interval");
            if(paraSoftnewInterval != null)
                paraSoftmInterval = Integer.parseInt(paraSoftnewInterval);
        }
        catch(Exception e) {
            Log.e(TAG, "Invalid interval value", e);
        }
       
    }
   
    private String paraSoftgetAttributeStringValue(AttributeSet attrs, String namespace, String name, String defaultValue) {
        String value = attrs.getAttributeValue(namespace, name);
        if(value == null)
            value = defaultValue;
       
        return value;
    }
   
    @Override
    protected View onCreateView(ViewGroup parent){
       
        RelativeLayout layout =  null;
       
        try {
            LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            layout = (RelativeLayout)mInflater.inflate(R.layout.szxcdcfhtrheek_bar_preference, parent, false);
        }
        catch(Exception e)
        {
            Log.e(TAG, "Error creating seek bar preference", e);
        }

        return layout;
       
    }
   
    @SuppressWarnings("deprecation")
	@Override
    public void onBindView(View view) {
        super.onBindView(view);

        try
        {
            ViewParent paraSoftoldContainer = paraSofmSeekBar.getParent();
            ViewGroup paraSoftnewContainer = (ViewGroup) view.findViewById(R.id.seekBarPrefBarContainer);
           
            if (paraSoftoldContainer != paraSoftnewContainer) {
                if (paraSoftoldContainer != null) {
                    ((ViewGroup) paraSoftoldContainer).removeView(paraSofmSeekBar);
                }
                paraSoftnewContainer.removeAllViews();
                paraSoftnewContainer.addView(paraSofmSeekBar, ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        }
        catch(Exception ex) {
            Log.e(TAG, "Error binding view: " + ex.toString());
        }

        paraSoftupdateView(view);
    }

    protected void paraSoftupdateView(View view) {

        try {
            RelativeLayout layout = (RelativeLayout)view;

            paraSofmStatusText = (TextView)layout.findViewById(R.id.seekBarPrefValue);
            paraSofmStatusText.setText(String.valueOf(paraSoftmCurrentValue));
            paraSofmStatusText.setMinimumWidth(30);
           
            paraSofmSeekBar.setProgress(paraSoftmCurrentValue - paraSoftmMinValue);

            TextView paraSoftunitsRight = (TextView)layout.findViewById(R.id.seekBarPrefUnitsRight);
            paraSoftunitsRight.setText(paraSofmUnitsRight);
           
            TextView unitsLeft = (TextView)layout.findViewById(R.id.seekBarPrefUnitsLeft);
            unitsLeft.setText(paraSoftmUnitsLeft);
           
        }
        catch(Exception e) {
            Log.e(TAG, "Error updating seek bar preference", e);
        }
       
    }
   
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int newValue = progress + paraSoftmMinValue;
       
        if(newValue > paraSoftmMaxValue)
            newValue = paraSoftmMaxValue;
        else if(newValue < paraSoftmMinValue)
            newValue = paraSoftmMinValue;
        else if(paraSoftmInterval != 1 && newValue % paraSoftmInterval != 0)
            newValue = Math.round(((float)newValue)/paraSoftmInterval)*paraSoftmInterval;
        if(!callChangeListener(newValue)){
            seekBar.setProgress(paraSoftmCurrentValue - paraSoftmMinValue);
            return;
        }
        paraSoftmCurrentValue = newValue;
        paraSofmStatusText.setText(String.valueOf(newValue));
        persistInt(newValue);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        notifyChanged();
    }


    @Override
    protected Object onGetDefaultValue(TypedArray ta, int index){
       
        int defaultValue = ta.getInt(index, paraSoftDEFAULT_VALUE);
        return defaultValue;
       
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {

        if(restoreValue) {
            paraSoftmCurrentValue = getPersistedInt(paraSoftmCurrentValue);
        }
        else {
            int temp = 0;
            try {
                temp = (Integer)defaultValue;
            }
            catch(Exception ex) {
                Log.e(TAG, "Invalid default value: " + defaultValue.toString());
            }
           
            persistInt(temp);
            paraSoftmCurrentValue = temp;
        }
       
    }
   
}