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
import com.android.cesova.obd.commands.OxygonSensors.OxygenSensorVoltageBankOneSensorFour;
import com.android.cesova.obd.commands.OxygonSensors.OxygenSensorVoltageBankOneSensorOne;
import com.android.cesova.obd.commands.OxygonSensors.OxygenSensorVoltageBankOneSensorThree;
import com.android.cesova.obd.commands.OxygonSensors.OxygenSensorVoltageBankOneSensorTwo;
import com.android.cesova.obd.commands.OxygonSensors.OxygenSensorVoltageBankTwoSensorFour;
import com.android.cesova.obd.commands.OxygonSensors.OxygenSensorVoltageBankTwoSensorOne;
import com.android.cesova.obd.commands.OxygonSensors.OxygenSensorVoltageBankTwoSensorThree;
import com.android.cesova.obd.commands.OxygonSensors.OxygenSensorVoltageBankTwoSensorTwo;
import com.android.cesova.obd.exceptions.NoDataException;

import java.io.IOException;

/**
 * Created by mokshaDev on 5/10/2015.
 */
public class EmmisionFragment extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.emmission_fragment, container, false);
        globalClass = (GlobalClass) getActivity().getApplication();
        socket = globalClass.getSocket();
        OxygenSensor1 =(TextView) rootView.findViewById(R.id.oxysensor1);
        OxygenSensor2 =(TextView) rootView.findViewById(R.id.oxysensor2);
        OxygenSensor3 =(TextView) rootView.findViewById(R.id.oxysensor3);
        OxygenSensor4 =(TextView) rootView.findViewById(R.id.oxysensor4);
        OxygenSensor5 =(TextView) rootView.findViewById(R.id.oxysensor5);
        OxygenSensor6 =(TextView) rootView.findViewById(R.id.oxysensor6);
        OxygenSensor7 =(TextView) rootView.findViewById(R.id.oxysensor7);
        OxygenSensor8 =(TextView) rootView.findViewById(R.id.oxysensor8);

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
        OxygenSensorVoltageBankOneSensorOne oxygenSensorVoltageBankOneSensorOne = new OxygenSensorVoltageBankOneSensorOne();
        OxygenSensorVoltageBankOneSensorTwo oxygenSensorVoltageBankOneSensorTwo = new OxygenSensorVoltageBankOneSensorTwo();
        OxygenSensorVoltageBankOneSensorThree oxygenSensorVoltageBankOneSensorThree = new OxygenSensorVoltageBankOneSensorThree();
        OxygenSensorVoltageBankOneSensorFour oxygenSensorVoltageBankOneSensorFour = new OxygenSensorVoltageBankOneSensorFour();
        OxygenSensorVoltageBankTwoSensorOne oxygenSensorVoltageBankTwoSensorOne = new OxygenSensorVoltageBankTwoSensorOne();
        OxygenSensorVoltageBankTwoSensorTwo oxygenSensorVoltageBankTwoSensorTwo = new OxygenSensorVoltageBankTwoSensorTwo();
        OxygenSensorVoltageBankTwoSensorThree oxygenSensorVoltageBankTwoSensorThree = new OxygenSensorVoltageBankTwoSensorThree();
        OxygenSensorVoltageBankTwoSensorFour oxygenSensorVoltageBankTwoSensorFour = new OxygenSensorVoltageBankTwoSensorFour();
        @Override
        public void onTick(final long millisUntilFinished) {
            try {
                try {
                    oxygenSensorVoltageBankOneSensorOne.run(socket.getInputStream(), socket.getOutputStream());
                    oxygenSensorVoltageBankOneSensorTwo.run(socket.getInputStream(), socket.getOutputStream());
                    oxygenSensorVoltageBankOneSensorThree.run(socket.getInputStream(), socket.getOutputStream());
                    oxygenSensorVoltageBankOneSensorFour.run(socket.getInputStream(), socket.getOutputStream());
                    oxygenSensorVoltageBankTwoSensorOne.run(socket.getInputStream(), socket.getOutputStream());
                    oxygenSensorVoltageBankTwoSensorTwo.run(socket.getInputStream(), socket.getOutputStream());
                    oxygenSensorVoltageBankTwoSensorThree.run(socket.getInputStream(), socket.getOutputStream());
                    oxygenSensorVoltageBankTwoSensorFour.run(socket.getInputStream(), socket.getOutputStream());

                    OxygenSensor1.setText("" + oxygenSensorVoltageBankOneSensorOne.getFormattedResult());
                    OxygenSensor2.setText(""+oxygenSensorVoltageBankOneSensorTwo.getFormattedResult());
                    OxygenSensor3.setText(""+oxygenSensorVoltageBankOneSensorThree.getFormattedResult());
                    OxygenSensor4.setText(""+oxygenSensorVoltageBankOneSensorFour.getFormattedResult());
                    OxygenSensor5.setText(""+oxygenSensorVoltageBankTwoSensorOne.getFormattedResult());
                    OxygenSensor6.setText(""+oxygenSensorVoltageBankTwoSensorTwo.getFormattedResult());
                    OxygenSensor7.setText(""+oxygenSensorVoltageBankTwoSensorThree.getFormattedResult());
                    OxygenSensor8.setText(""+oxygenSensorVoltageBankTwoSensorFour.getFormattedResult());



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
