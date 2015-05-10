package com.android.cesova.Fragments;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cesova.GlobalClass;
import com.android.cesova.R;
import com.android.cesova.obd.commands.temperature.AirIntakeTemperatureObdCommand;
import com.android.cesova.obd.commands.temperature.AmbientAirTemperatureObdCommand;
import com.android.cesova.obd.commands.temperature.EngineCoolantTemperatureObdCommand;
import com.android.cesova.obd.exceptions.NoDataException;

import java.io.IOException;

/**
 * Created by mokshaDev on 5/9/2015.
 */
public class TemperatureFragment extends Fragment {
    GlobalClass globalClass;
    BluetoothSocket socket;
    TextView airIntakeTemp;
    TextView ambientTemp;
    TextView coolantTemp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.temperature_fragment, container, false);
        globalClass = (GlobalClass) getActivity().getApplication();
        socket = globalClass.getSocket();

        airIntakeTemp = (TextView) rootView.findViewById(R.id.airtIntakeTemp);
        ambientTemp = (TextView) rootView.findViewById(R.id.ambientTemp);
        coolantTemp = (TextView) rootView.findViewById(R.id.coolantTemp);

        return rootView;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (socket != null) {
            Toast toast = Toast.makeText(getActivity(), "connection successfull", Toast.LENGTH_SHORT);
            toast.show();
           mTimer.start();
        } else {
            Toast toast = Toast.makeText(getActivity(), "You are not connected to any device", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private final CountDownTimer mTimer = new CountDownTimer(3000000, 100) {
        AmbientAirTemperatureObdCommand ambientAirTemperature = new AmbientAirTemperatureObdCommand();
        AirIntakeTemperatureObdCommand airTemperature = new AirIntakeTemperatureObdCommand();
        EngineCoolantTemperatureObdCommand engineCoolantTemperature = new EngineCoolantTemperatureObdCommand();
        @Override
        public void onTick(final long millisUntilFinished) {
            try {
                try {
                    //ambientAirTemperature.run(socket.getInputStream(), socket.getOutputStream());
                    //airTemperature.run(socket.getInputStream(), socket.getOutputStream());
                    engineCoolantTemperature.run(socket.getInputStream(),socket.getOutputStream());

                    //airIntakeTemp.setText("" + airTemperature.getFormattedResult());
                    //ambientTemp.setText("" + ambientAirTemperature.getFormattedResult());
                    coolantTemp.setText("" + engineCoolantTemperature.getFormattedResult());
                } catch (NoDataException e) {
                    e.getMessage();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFinish() {
        }
    };
}
