package com.app.chronos;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.bumptech.glide.ListPreloader;

import java.util.List;


public class Preference extends PreferenceActivity {
    @Override
    protected void onCreate( Bundle savedInstanceStete ) {
        super.onCreate(savedInstanceStete);
        addPreferencesFromResource(R.xml.settings);
        Load_setting();
    }
    private void Load_setting(){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean chk_night = sp.getBoolean("Night",false);
        if(chk_night){
            getListView().setBackgroundColor(Color.parseColor("#222222"));
        }else {
            getListView().setBackgroundColor(Color.parseColor("#ffffff"));
        }

        CheckBoxPreference chk_night_instant = (CheckBoxPreference)findPreference("Night");
        chk_night_instant.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange ( android.preference.Preference settings, Object obj ) {

                boolean yes = (boolean)obj;

                if(yes){
                    getListView().setBackgroundColor(Color.parseColor("#222222"));
                }else {
                    getListView().setBackgroundColor(Color.parseColor("#ffffff"));
                }

                return true;
            }
        });

        ListPreference LP = (ListPreference)findPreference("ORIENTATION");
        String orien = sp.getString("ORIENTATION","false");
        if("1".equals(orien)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
            LP.setSummary(LP.getEntry());
        }else if("2".equals(orien)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            LP.setSummary(LP.getEntry());
        }else if("3".equals(orien)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            LP.setSummary(LP.getEntry());
        }

        LP.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange ( android.preference.Preference settings, Object obj ) {

                String items = (String)obj;
                if(settings.getKey().equals("ORIENTATION")){

                    switch (items){
                        case "1":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
                            break;
                        case "2":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            break;
                        case "3":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            break;
                    }
                    ListPreference LPP = (ListPreference)settings;
                    LPP.setSummary(LPP.getEntries()[LPP.findIndexOfValue(items)]);
                }
                return true;
            }
        });


    }
    protected void OnResume(){
        Load_setting();
        super.onResume();
    }
}
