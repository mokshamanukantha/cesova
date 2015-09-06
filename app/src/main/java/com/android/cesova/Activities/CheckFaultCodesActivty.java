package com.android.cesova.Activities;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cesova.Adapters.CustomListAdapter;
import com.android.cesova.GlobalClass;
import com.android.cesova.R;
import com.android.cesova.obd.ErrorCodes.DatabaseRecords;
import com.android.cesova.obd.commands.control.TroubleCodesObdCommand;
import com.android.cesova.obd.commands.protocol.ObdResetCommand;
import com.android.cesova.obd.exceptions.NoDataException;

import java.io.IOException;

/**
 * Created by mokshaDev on 3/21/2015.
 */
public class CheckFaultCodesActivty extends ActionBarActivity implements AdapterView.OnItemClickListener {
    private static final String TAG_PID="pid";
    GlobalClass globalClass;
    BluetoothSocket socket;
    private Button btnClearFaultCodes;
    private ListView listView;
    private ProgressDialog pDialog;
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_check_fault_codes);
        globalClass = (GlobalClass) this.getApplication();

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Check Fault Codes");
        setSupportActionBar(toolbar);
        this.setSupportActionBar(toolbar);

        btnClearFaultCodes = (Button) findViewById(R.id.btnClearFaultsCode);
        listView = (ListView) findViewById(R.id.list);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Checking...");
        pDialog.show();
        //DatabaseRecords databaseRecords = new DatabaseRecords(this);
        //databaseRecords.insert();
        socket = globalClass.getSocket();

        if (socket != null) {
            Toast toast = Toast.makeText(this, "connection successfull", Toast.LENGTH_SHORT);
            toast.show();
            getDataForListView();
            hidePDialog();
        } else {
            Toast toast = Toast.makeText(this, "You are not connected to any device", Toast.LENGTH_SHORT);
            toast.show();
        }
        btnClearFaultCodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clearCode = "04" + '\r';
                byte[] c = clearCode.getBytes();
                try {
                    socket.getOutputStream().write(c);
                    socket.getInputStream();
                    adapter.notifyDataSetChanged();
                } catch (IOException e) {
                }
            }
        });
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    public void getDataForListView() {
        ObdResetCommand obdResetCommand = new ObdResetCommand();
        TroubleCodesObdCommand tr = new TroubleCodesObdCommand(0);
        String[] arr;
        try {
            obdResetCommand.run(socket.getInputStream(), socket.getOutputStream());
            tr.run(socket.getInputStream(), socket.getOutputStream());
            arr = tr.getFormattedResult().split("\n");
            adapter = new CustomListAdapter(this,arr);
            listView.setAdapter(adapter);
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
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, FaultCodesDetailActivty.class);
        TextView textView = (TextView) view.findViewById(R.id.pid);
        intent.putExtra(TAG_PID,textView.getText().toString());
        startActivity(intent);
    }
}