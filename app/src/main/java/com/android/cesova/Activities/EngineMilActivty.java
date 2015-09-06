package com.android.cesova.Activities;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cesova.GlobalClass;
import com.android.cesova.R;
import com.android.cesova.obd.commands.control.DistanceTraveledWithMILOn;
import com.android.cesova.obd.commands.control.DtcNumberObdCommand;
import com.android.cesova.obd.commands.control.TimeRunWithMILObdCommand;
import com.android.cesova.obd.commands.control.TimeSinceTroubleCodesCleardObdCommand;
import com.android.cesova.obd.commands.protocol.EchoOffObdCommand;
import com.android.cesova.obd.commands.protocol.LineFeedOffObdCommand;
import com.android.cesova.obd.commands.protocol.SelectProtocolObdCommand;
import com.android.cesova.obd.commands.protocol.TimeoutObdCommand;
import com.android.cesova.obd.enums.ObdProtocols;
import com.android.cesova.obd.exceptions.NoDataException;
import com.android.cesova.obd.exceptions.NonNumericResponseException;

import java.io.IOException;

/**
 * Created by mokshaDev on 5/9/2015.
 */
public class EngineMilActivty extends ActionBarActivity implements View.OnClickListener {

    TextView distanceMIL;
    TextView timeMIL;
    TextView dtcMIL;
    TextView engineMIL;
    Button checkMIL;
    GlobalClass globalClass;
    BluetoothSocket socket;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.engine_mil_fragment);
        globalClass = (GlobalClass) getApplication();
        distanceMIL = (TextView) findViewById(R.id.distanceMIL);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Check Engine MIL");
        setSupportActionBar(toolbar);
        this.setSupportActionBar(toolbar);
        timeMIL = (TextView) findViewById(R.id.timeMIL);
        dtcMIL = (TextView) findViewById(R.id.dtcMIL);
        engineMIL = (TextView) findViewById(R.id.engineMIL);
        checkMIL = (Button) findViewById(R.id.checkMIL);
        checkMIL.setOnClickListener(this);
        pDialog = new ProgressDialog(this);
        socket = globalClass.getSocket();
        if (socket != null) {
            Toast toast = Toast.makeText(this, "connection successfull", Toast.LENGTH_SHORT);
            toast.show();

            DistanceTraveledWithMILOn distanceTraveledWithMILOn = new DistanceTraveledWithMILOn();
            TimeRunWithMILObdCommand timeRunWithMILObdCommand = new TimeRunWithMILObdCommand();
            TimeSinceTroubleCodesCleardObdCommand timeSinceTroubleCodesCleardObdCommand = new TimeSinceTroubleCodesCleardObdCommand();
            try {
                distanceTraveledWithMILOn.run(socket.getInputStream(), socket.getOutputStream());
                timeRunWithMILObdCommand.run(socket.getInputStream(), socket.getOutputStream());
                timeSinceTroubleCodesCleardObdCommand.run(socket.getInputStream(), socket.getOutputStream());
            }catch(NonNumericResponseException e)
            {
                e.printStackTrace();
            }
            catch (NoDataException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String time = timeSinceTroubleCodesCleardObdCommand.getFormattedResult();
            String timedt = timeRunWithMILObdCommand.getFormattedResult();
            String distance = distanceTraveledWithMILOn.getFormattedResult();
            distanceMIL.setText("" + distance);
            timeMIL.setText("" + time);
            dtcMIL.setText("" + timedt);
        }else {
            Toast toast = Toast.makeText(this, "You are not connected to any device", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    @Override
    public void onClick(View view) {
        if (socket != null) {
            Toast toast = Toast.makeText(this, "connection successfull", Toast.LENGTH_SHORT);
            toast.show();
            pDialog.setMessage("Checking Engine MIL...");
            pDialog.show();
            DtcNumberObdCommand dtcNumberObdCommand = new DtcNumberObdCommand();
            try {
                new EchoOffObdCommand().run(socket.getInputStream(), socket.getOutputStream());
                new LineFeedOffObdCommand().run(socket.getInputStream(), socket.getOutputStream());
                new TimeoutObdCommand(62).run(socket.getInputStream(), socket.getOutputStream());
                new SelectProtocolObdCommand(ObdProtocols.AUTO).run(socket.getInputStream(), socket.getOutputStream());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                dtcNumberObdCommand.run(socket.getInputStream(), socket.getOutputStream());
                String arr = dtcNumberObdCommand.getFormattedResult();
                engineMIL.setText("" + arr);
                hidePDialog();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Toast toast = Toast.makeText(this, "You are not connected to any device", Toast.LENGTH_SHORT);
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
