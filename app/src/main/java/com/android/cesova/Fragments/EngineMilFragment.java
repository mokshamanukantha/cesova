package com.android.cesova.Fragments;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cesova.GlobalClass;
import com.android.cesova.R;
import com.android.cesova.obd.commands.control.DistanceTraveledWithMILOn;
import com.android.cesova.obd.commands.control.DtcNumberObdCommand;
import com.android.cesova.obd.commands.control.TimeRunWithMILObdCommand;
import com.android.cesova.obd.commands.control.TimeSinceTroubleCodesCleardObdCommand;
import com.android.cesova.obd.exceptions.NoDataException;

import java.io.IOException;

/**
 * Created by mokshaDev on 5/9/2015.
 */
public class EngineMilFragment extends Fragment implements View.OnClickListener {
    TextView distanceMIL;
    TextView timeMIL;
    TextView dtcMIL;
    TextView engineMIL;
    Button checkMIL;
    GlobalClass globalClass;
    BluetoothSocket socket;
    private ProgressDialog pDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.engine_mil_fragment, container, false);
        globalClass = (GlobalClass) getActivity().getApplication();
        distanceMIL = (TextView) rootView.findViewById(R.id.distanceMIL);
        timeMIL = (TextView) rootView.findViewById(R.id.timeMIL);
        dtcMIL = (TextView) rootView.findViewById(R.id.dtcMIL);
        engineMIL = (TextView) rootView.findViewById(R.id.engineMIL);
        checkMIL = (Button) rootView.findViewById(R.id.checkMIL);
        checkMIL.setOnClickListener(this);
        pDialog = new ProgressDialog(getActivity());
        socket = globalClass.getSocket();
        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (socket != null) {
            Toast toast = Toast.makeText(getActivity(), "connection successfull", Toast.LENGTH_SHORT);
            toast.show();
            pDialog.setMessage("Checking Engine MIL...");
            pDialog.show();
            DtcNumberObdCommand dtcNumberObdCommand = new DtcNumberObdCommand();
            DistanceTraveledWithMILOn distanceTraveledWithMILOn = new DistanceTraveledWithMILOn();
            TimeRunWithMILObdCommand timeRunWithMILObdCommand = new TimeRunWithMILObdCommand();
            TimeSinceTroubleCodesCleardObdCommand timeSinceTroubleCodesCleardObdCommand = new TimeSinceTroubleCodesCleardObdCommand();
            try {
                dtcNumberObdCommand.run(socket.getInputStream(), socket.getOutputStream());

                distanceTraveledWithMILOn.run(socket.getInputStream(), socket.getOutputStream());
                timeRunWithMILObdCommand.run(socket.getInputStream(), socket.getOutputStream());
                timeSinceTroubleCodesCleardObdCommand.run(socket.getInputStream(), socket.getOutputStream());

            }
            catch (NoDataException e)
            {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String arr = dtcNumberObdCommand.getFormattedResult();

            String time = dtcNumberObdCommand.getFormattedResult();
            String timedt = dtcNumberObdCommand.getFormattedResult();
            String distance = dtcNumberObdCommand.getFormattedResult();

            engineMIL.setText("" + arr);
            distanceMIL.setText("" + distance);
            timeMIL.setText("" + time);
            dtcMIL.setText("" + timedt);
            hidePDialog();
        } else {
            Toast toast = Toast.makeText(getActivity(), "You are not connected to any device", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
