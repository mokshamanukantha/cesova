package com.android.cesova.Fragments;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.cesova.Activities.FaultCodesDetailActivty;
import com.android.cesova.Adapters.CustomListAdapter;
import com.android.cesova.GlobalClass;
import com.android.cesova.R;
import com.android.cesova.obd.commands.control.TroubleCodesObdCommand;
import com.android.cesova.obd.exceptions.NoDataException;

import java.io.IOException;

/**
 * Created by mokshaDev on 3/21/2015.
 */
public class CheckFaultCodesFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String TAG_PID="pid";
    GlobalClass globalClass;
    BluetoothSocket socket;
    private Button btnClearFaultCodes;
    private ListView listView;
    private ProgressDialog pDialog;
    private CustomListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_check_fault_codes, container, false);
        globalClass = (GlobalClass) getActivity().getApplication();
        btnClearFaultCodes = (Button) rootView.findViewById(R.id.btnClearFaultsCode);
        listView = (ListView) rootView.findViewById(R.id.list);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Checking...");
        pDialog.show();
        //OBDErrorCodeManager obdErrorCodeManager = new OBDErrorCodeManager(getActivity());
        //DatabaseRecords databaseRecords = new DatabaseRecords(getActivity());
        //databaseRecords.insert();
        socket = globalClass.getSocket();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (socket != null) {
            Toast toast = Toast.makeText(getActivity(), "connection successfull", Toast.LENGTH_SHORT);
            toast.show();
            adapter = new CustomListAdapter(getActivity(),getDataForListView());
            listView.setAdapter(adapter);
            hidePDialog();
        } else {
            Toast toast = Toast.makeText(getActivity(), "You are not connected to any device", Toast.LENGTH_SHORT);
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

    public String[] getDataForListView() {
        TroubleCodesObdCommand tr = new TroubleCodesObdCommand();
        try {
            tr.run(socket.getInputStream(), socket.getOutputStream());
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
        String[] arr = tr.getFormattedResult().split("\n");
        return arr;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String[] obdCodes = getDataForListView();
        Intent intent = new Intent(getActivity(), FaultCodesDetailActivty.class);
        intent.putExtra(TAG_PID,obdCodes[i]);
        startActivity(intent);
    }
}