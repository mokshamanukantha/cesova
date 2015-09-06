package com.android.cesova.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cesova.Gauges.CustomGauge;
import com.android.cesova.GlobalClass;
import com.android.cesova.R;
import com.android.cesova.obd.commands.engine.EngineLoadObdCommand;
import com.android.cesova.obd.commands.engine.EngineRPMObdCommand;
import com.android.cesova.obd.commands.engine.MassAirFlowObdCommand;
import com.android.cesova.obd.commands.engine.ThrottlePositionObdCommand;
import com.android.cesova.obd.commands.fuel.FuelLevelObdCommand;
import com.android.cesova.obd.commands.temperature.EngineCoolantTemperatureObdCommand;

import java.io.IOException;

import static com.android.cesova.R.layout.fragment_realtime_information;


public class RealTimeInformationFragment extends ActionBarActivity {

    GlobalClass globalClass;
    BluetoothSocket socket;
    private CustomGauge gaugeThrottle;
    private CustomGauge gaugeEnginetemp;
    private CustomGauge gaugeMani;
    private CustomGauge gaugeEngLoad;
    private CustomGauge gaugeBaro;
    private CustomGauge gaugeFuelLevel;
    private TextView txtThrottle;
    private TextView txtEngineTemp;
    private TextView txtMani;
    private TextView txtEngineLoad;
    private TextView txtBaro;
    private TextView txtFuelLevel;
    private Toolbar toolbar;
    private final CountDownTimer mTimer = new CountDownTimer(3000000, 100) {
        ThrottlePositionObdCommand throttle = new ThrottlePositionObdCommand();
        MassAirFlowObdCommand air = new MassAirFlowObdCommand();
        EngineCoolantTemperatureObdCommand mani = new EngineCoolantTemperatureObdCommand();
        EngineLoadObdCommand engload=new EngineLoadObdCommand();
        EngineRPMObdCommand baro = new EngineRPMObdCommand();

        FuelLevelObdCommand fuellevel = new FuelLevelObdCommand();

        @Override
        public void onTick(final long millisUntilFinished) {
            try {
                try {
                    throttle.run(socket.getInputStream(), socket.getOutputStream());
                    //air.run(socket.getInputStream(), socket.getOutputStream());
                    mani.run(socket.getInputStream(), socket.getOutputStream());
                    engload.run(socket.getInputStream(), socket.getOutputStream());
                    baro.run(socket.getInputStream(), socket.getOutputStream());
                    // fuellevel.run(socket.getInputStream(), socket.getOutputStream());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gaugeThrottle.setValue((int) throttle.getPercentage());
                txtThrottle.setText("" + throttle.getFormattedResult());

                gaugeEnginetemp.setValue((int) air.getMAF());
                txtEngineTemp.setText(air.getFormattedResult());

                //gaugeMani.setValue((int) mani.getTemperature());
                //txtMani.setText(air.getFormattedResult());

                 gaugeEngLoad.setValue((int) engload.getPercentage());
                 txtEngineLoad.setText(engload.getFormattedResult());

                gaugeBaro.setValue(baro.getRPM());
                txtBaro.setText(baro.getFormattedResult());

                gaugeFuelLevel.setValue((int) fuellevel.getFuelLevel());
                txtFuelLevel.setText(fuellevel.getFormattedResult());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFinish() {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_realtime_information);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("RealTime Information");
        setSupportActionBar(toolbar);
        this.setSupportActionBar(toolbar);

        globalClass = (GlobalClass) getApplication();

        gaugeThrottle = (CustomGauge) findViewById(R.id.gaugeThrottle);
        txtThrottle = (TextView) findViewById(R.id.txtThrottle);

        gaugeEnginetemp = (CustomGauge) findViewById(R.id.gaugeEngineTemp);
        txtEngineTemp = (TextView) findViewById(R.id.txtengineTemp);

        gaugeMani = (CustomGauge) findViewById(R.id.gaugeMani);
        txtMani = (TextView) findViewById(R.id.txtMani);

        gaugeEngLoad = (CustomGauge) findViewById(R.id.gaugeEngLoad);
        txtEngineLoad = (TextView) findViewById(R.id.txtEngineLoad);

        gaugeBaro = (CustomGauge) findViewById(R.id.gaugeBaro);
        txtBaro = (TextView) findViewById(R.id.txtBaro);

        gaugeFuelLevel = (CustomGauge) findViewById(R.id.gaugeFuelLevel);
        txtFuelLevel = (TextView) findViewById(R.id.txtFuelLevel);


        socket = globalClass.getSocket();
        if (socket != null) {
            Toast toast = Toast.makeText(this, "connection successful", Toast.LENGTH_SHORT);
            toast.show();
            mTimer.start();
        } else {
            Toast toast = Toast.makeText(this, "You are not connected to any device", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
