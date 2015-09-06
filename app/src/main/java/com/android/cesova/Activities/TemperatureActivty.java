package com.android.cesova.Activities;

import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cesova.GlobalClass;
import com.android.cesova.R;
import com.android.cesova.obd.commands.protocol.EchoOffObdCommand;
import com.android.cesova.obd.commands.protocol.LineFeedOffObdCommand;
import com.android.cesova.obd.commands.protocol.SelectProtocolObdCommand;
import com.android.cesova.obd.commands.protocol.TimeoutObdCommand;
import com.android.cesova.obd.commands.temperature.AirIntakeTemperatureObdCommand;
import com.android.cesova.obd.commands.temperature.EngineCoolantTemperatureObdCommand;
import com.android.cesova.obd.enums.ObdProtocols;

import java.io.IOException;

/**
 * Created by mokshaDev on 5/9/2015.
 */
public class TemperatureActivty extends ActionBarActivity {
    GlobalClass globalClass;
    BluetoothSocket socket;
    TextView airIntakeTemp;
    TextView ambientTemp;
    TextView coolantTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperature_fragment);
        globalClass = (GlobalClass) getApplication();
        socket = globalClass.getSocket();

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Check Temperature Values");
        setSupportActionBar(toolbar);
        this.setSupportActionBar(toolbar);

        airIntakeTemp = (TextView) findViewById(R.id.airtIntakeTemp);
        ambientTemp = (TextView) findViewById(R.id.ambientTemp);
        coolantTemp = (TextView) findViewById(R.id.coolantTemp);

        if (socket != null) {
            Toast toast = Toast.makeText(this, "connection successfull", Toast.LENGTH_SHORT);
            toast.show();
            //mTimer.start();
            try {
                new EchoOffObdCommand().run(socket.getInputStream(), socket.getOutputStream());
                new LineFeedOffObdCommand().run(socket.getInputStream(), socket.getOutputStream());
                new TimeoutObdCommand(62).run(socket.getInputStream(), socket.getOutputStream());
                new SelectProtocolObdCommand(ObdProtocols.AUTO).run(socket.getInputStream(), socket.getOutputStream());
                new ChartTask().execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Toast toast = Toast.makeText(this, "You are not connected to any device", Toast.LENGTH_SHORT);
            toast.show();
        }
    }




    /*private final CountDownTimer mTimer = new CountDownTimer(3000000, 100) {
        AmbientAirTemperatureObdCommand ambientAirTemperature = new AmbientAirTemperatureObdCommand();
        AirIntakeTemperatureObdCommand airTemperature = new AirIntakeTemperatureObdCommand();
        EngineCoolantTemperatureObdCommand engineCoolantTemperature = new EngineCoolantTemperatureObdCommand();
        @Override
        public void onTick(final long millisUntilFinished) {
            try {
                try {
                    ambientAirTemperature.run(socket.getInputStream(), socket.getOutputStream());
                    airTemperature.run(socket.getInputStream(), socket.getOutputStream());
                    engineCoolantTemperature.run(socket.getInputStream(), socket.getOutputStream());

                    airIntakeTemp.setText("" + airTemperature.getFormattedResult());
                    ambientTemp.setText("" + ambientAirTemperature.getFormattedResult());
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
    };*/

     private class ChartTask extends AsyncTask<Void, String, Void> {
         //AmbientAirTemperatureObdCommand ambientAirTemperature = new AmbientAirTemperatureObdCommand();
         AirIntakeTemperatureObdCommand airTemperature = new AirIntakeTemperatureObdCommand();
         EngineCoolantTemperatureObdCommand engineCoolantTemperature = new EngineCoolantTemperatureObdCommand();

         @Override
         protected Void doInBackground(Void... params){
                 do {
                     try {
                        // ambientAirTemperature.run(socket.getInputStream(), socket.getOutputStream());
                         airTemperature.run(socket.getInputStream(), socket.getOutputStream());
                         engineCoolantTemperature.run(socket.getInputStream(), socket.getOutputStream());

                     } catch (IOException e) {
                         e.printStackTrace();
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }


                     try {
                         Thread.sleep(100);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                     runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             airIntakeTemp.setText("" + airTemperature.getFormattedResult());
                             //ambientTemp.setText("" + ambientAirTemperature.getFormattedResult());
                             coolantTemp.setText("" + engineCoolantTemperature.getFormattedResult());
                         }
                     });
                 } while (!Thread.currentThread().isInterrupted());
             return null;
         }
     }
}
