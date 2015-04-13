package com.android.cesova.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.cesova.Activities.UserSettingActivity;
import com.android.cesova.R;

/**
 * Created by mokshaDev on 3/22/2015.
 */
public class SettingsFragment extends Fragment {
    private static final int RESULT_SETTINGS = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        Intent i = new Intent(getActivity(), UserSettingActivity.class);
        startActivityForResult(i, RESULT_SETTINGS);
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SETTINGS:
                showUserSettings();
                break;

        }

    }

    private void showUserSettings() {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());

        StringBuilder builder = new StringBuilder();

        builder.append("\n Username: "
                + sharedPrefs.getString("prefUsername", "NULL"));

        builder.append("\n Enable Data Upload:"
                + sharedPrefs.getBoolean("prefEnbUpload", false));

        builder.append("\n URL: "
                + sharedPrefs.getString("prefUploadURL", "NULL"));

        builder.append("\n Vehicle ID: "
                + sharedPrefs.getString("prefVehicleID", "NULL"));

        builder.append("\n Bluetooth Device: "
                + sharedPrefs.getString("prefBluetoothDevice", "NULL"));

        TextView settingsTextView = (TextView) getActivity().findViewById(R.id.textUserSettings);

        settingsTextView.setText(builder.toString());
    }
}
