package com.talisman6803.forecaster;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

public class SettingPreferenceFragment extends PreferenceFragment {

    String city_value = null;
    String city = null;
    ListPreference cityPreference = null;
    public SettingPreferenceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_settings);
        cityPreference = (ListPreference)findPreference("cityselect");

        cityPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                city_value = newValue.toString();
                preference.setSummary((CharSequence)city_value);
                Log.d("city_value captured", city_value);
                CityData city = new CityData(city_value);
                EventBus.getDefault().post(city);
                return true;
            }

        });
    }

    public class CityData{
        public String city;

        public CityData(String cityvalue){
            this.city = cityvalue;
        }
    }
}
