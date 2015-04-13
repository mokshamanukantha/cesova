package com.android.cesova.Activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.android.cesova.R;

public class UserSettingActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

    }
}
