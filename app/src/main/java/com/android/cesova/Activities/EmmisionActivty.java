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
import com.android.cesova.obd.commands.OxygonSensors.OxygenSensorVoltageBankOneSensorFour;
import com.android.cesova.obd.commands.OxygonSensors.OxygenSensorVoltageBankOneSensorOne;
import com.android.cesova.obd.commands.OxygonSensors.OxygenSensorVoltageBankOneSensorThree;
import com.android.cesova.obd.commands.OxygonSensors.OxygenSensorVoltageBankOneSensorTwo;
import com.android.cesova.obd.commands.OxygonSensors.OxygenSensorVoltageBankTwoSensorFour;
import com.android.cesova.obd.commands.OxygonSensors.OxygenSensorVoltageBankTwoSensorOne;
import com.android.cesova.obd.commands.OxygonSensors.OxygenSensorVoltageBankTwoSensorThree;
import com.android.cesova.obd.commands.OxygonSensors.OxygenSensorVoltageBankTwoSensorTwo;
import com.android.cesova.obd.commands.protocol.EchoOffObdCommand;
import com.android.cesova.obd.commands.protocol.LineFeedOffObdCommand;
import com.android.cesova.obd.commands.protocol.SelectProtocolObdCommand;
import com.android.cesova.obd.commands.protocol.TimeoutObdCommand;
import com.android.cesova.obd.enums.ObdProtocols;
import com.android.cesova.obd.exceptions.NoDataException;

import java.io.IOException;

/**
 * Created by mokshaDev on 5/10/2015.
 */
public class EmmisionActivty extends ActionBarActivity {

    TextView OxygenSensor1;
    TextView OxygenSensor2;
    TextView OxygenSensor3;
    TextView OxygenSensor4;
    TextView OxygenSensor5;
    TextView OxygenSensor6;
    TextView OxygenSensor7;
    TextView OxygenSensor8;
    GlobalClass globalClass;
    BluetoothSocket socket;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emmission_fragment);

        globalClass = (GlobalClass) getApplication();
        socket = globalClass.getSocket();

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Check Emission(Oxygen Sensor) Values");
        setSupportActionBar(toolbar);
        this.setSupportActionBar(toolbar);

        OxygenSensor1 = (TextView) findViewById(R.id.oxysensor1);
        OxygenSensor2 = (TextView) findViewById(R.id.oxysensor2);
        OxygenSensor3 = (TextView) findViewById(R.id.oxysensor3);
        OxygenSensor4 = (TextView) findViewById(R.id.oxysensor4);
        OxygenSensor5 = (TextView) findViewById(R.id.oxysensor5);
        OxygenSensor6 = (TextView) findViewById(R.id.oxysensor6);
        OxygenSensor7 = (TextView) findViewById(R.id.oxysensor7);
        OxygenSensor8 = (TextView) findViewById(R.id.oxysensor8);

        if (socket != null) {
            Toast toast = Toast.makeText(this, "connection successfull", Toast.LENGTH_SHORT);
            toast.show();
            try {
                new EchoOffObdCommand().run(socket.getInputStream(), socket.getOutputStream());
                new LineFeedOffObdCommand().run(socket.getInputStream(), socket.getOutputStream());
                new TimeoutObdCommand(62).run(socket.getInputStream(), socket.getOutputStream());
                new SelectProtocolObdCommand(ObdProtocols.AUTO).run(socket.getInputStream(), socket.getOutputStream());
                //new ChartTask().execute();
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

    private class ChartTask extends AsyncTask<Void, String, Void> {
        OxygenSensorVoltageBankOneSensorOne oxygenSensorVoltageBankOneSensorOne = new OxygenSensorVoltageBankOneSensorOne();
        OxygenSensorVoltageBankOneSensorTwo oxygenSensorVoltageBankOneSensorTwo = new OxygenSensorVoltageBankOneSensorTwo();
        OxygenSensorVoltageBankOneSensorThree oxygenSensorVoltageBankOneSensorThree = new OxygenSensorVoltageBankOneSensorThree();
        OxygenSensorVoltageBankOneSensorFour oxygenSensorVoltageBankOneSensorFour = new OxygenSensorVoltageBankOneSensorFour();
        OxygenSensorVoltageBankTwoSensorOne oxygenSensorVoltageBankTwoSensorOne = new OxygenSensorVoltageBankTwoSensorOne();
        OxygenSensorVoltageBankTwoSensorTwo oxygenSensorVoltageBankTwoSensorTwo = new OxygenSensorVoltageBankTwoSensorTwo();
        OxygenSensorVoltageBankTwoSensorThree oxygenSensorVoltageBankTwoSensorThree = new OxygenSensorVoltageBankTwoSensorThree();
        OxygenSensorVoltageBankTwoSensorFour oxygenSensorVoltageBankTwoSensorFour = new OxygenSensorVoltageBankTwoSensorFour();

        @Override
        protected Void doInBackground(Void... params) {

                do {
                    try {
                        oxygenSensorVoltageBankOneSensorOne.run(socket.getInputStream(), socket.getOutputStream());
                        oxygenSensorVoltageBankOneSensorTwo.run(socket.getInputStream(), socket.getOutputStream());
                        oxygenSensorVoltageBankOneSensorThree.run(socket.getInputStream(), socket.getOutputStream());
                        oxygenSensorVoltageBankOneSensorFour.run(socket.getInputStream(), socket.getOutputStream());
                        oxygenSensorVoltageBankTwoSensorOne.run(socket.getInputStream(), socket.getOutputStream());
                        oxygenSensorVoltageBankTwoSensorTwo.run(socket.getInputStream(), socket.getOutputStream());
                        oxygenSensorVoltageBankTwoSensorThree.run(socket.getInputStream(), socket.getOutputStream());
                        oxygenSensorVoltageBankTwoSensorFour.run(socket.getInputStream(), socket.getOutputStream());
                    }catch (NoDataException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            OxygenSensor1.setText("N/A" + oxygenSensorVoltageBankOneSensorOne.getFormattedResult());
                            OxygenSensor2.setText("N/A" + oxygenSensorVoltageBankOneSensorTwo.getFormattedResult());
                            OxygenSensor3.setText("N/A" + oxygenSensorVoltageBankOneSensorThree.getFormattedResult());
                            OxygenSensor4.setText("N/A" + oxygenSensorVoltageBankOneSensorFour.getFormattedResult());
                            OxygenSensor5.setText("N/A" + oxygenSensorVoltageBankTwoSensorOne.getFormattedResult());
                            OxygenSensor6.setText("N/A" + oxygenSensorVoltageBankTwoSensorTwo.getFormattedResult());
                            OxygenSensor7.setText("N/A" + oxygenSensorVoltageBankTwoSensorThree.getFormattedResult());
                            OxygenSensor8.setText("N/A" + oxygenSensorVoltageBankTwoSensorFour.getFormattedResult());

                        }
                    });


                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (!Thread.currentThread().isInterrupted());

            return null;
        }
    }
}
