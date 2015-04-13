package com.android.cesova.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.cesova.Adapters.CustomListAdapter;
import com.android.cesova.GlobalClass;
import com.android.cesova.R;
import com.android.cesova.obd.ErrorCodes.OBDErrorCode;
import com.android.cesova.obd.ErrorCodes.OBDErrorCodeManager;
import com.android.cesova.obd.commands.control.TroubleCodesObdCommand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mokshaDev on 3/21/2015.
 */
public class CheckFaultCodesFragment extends Fragment {
    GlobalClass globalClass;
    BluetoothSocket socket;
    // private Button btnAdd;
    private List<OBDErrorCode> obdList = new ArrayList<OBDErrorCode>();
    private Button btnClearFaultCodes;
    private ListView listView;
    private ProgressDialog pDialog;
    private CustomListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_check_fault_codes, container, false);
        globalClass = (GlobalClass) getActivity().getApplication();
        //btnAdd = (Button) rootView.findViewById(R.id.btnAdd);
        btnClearFaultCodes = (Button) rootView.findViewById(R.id.btnClearFaultsCode);
        listView = (ListView) rootView.findViewById(R.id.list);
        adapter = new CustomListAdapter(getActivity(), obdList);
        listView.setAdapter(adapter);
        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        socket = globalClass.getSocket();


        if (socket != null) {
            Toast toast = Toast.makeText(getActivity(), "connection successfull", Toast.LENGTH_SHORT);
            toast.show();
            OBDErrorCodeManager obdErrorCodeManager = new OBDErrorCodeManager(getActivity());
            TroubleCodesObdCommand tr = new TroubleCodesObdCommand();
            try {
                tr.run(socket.getInputStream(), socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String[] arr = tr.getFormattedResult().split("\n");
            OBDErrorCode obd = new OBDErrorCode();

            for (int i = 0; i < arr.length; i++) {
                Log.d("Error Code", arr[i]);
                OBDErrorCode res = obdErrorCodeManager.getOBDErrorCodeById(arr[i]);

                obd.setPid(res.getPid());
                Log.d("PID", res.getPid());
                obd.setType(res.getType());
                obd.setDescription(res.getDescription());
                Log.d("NPID", obd.getPid());
                obdList.add(obd);
                adapter.notifyDataSetChanged();
            }
            hidePDialog();
        } else {
            Toast toast = Toast.makeText(getActivity(), "socket is null", Toast.LENGTH_SHORT);
            toast.show();
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
       /* btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OBDErrorCodeManager obdErrorCodeManager = new OBDErrorCodeManager(getActivity());
                OBDErrorCode obd = new OBDErrorCode();

                OBDErrorCode res = obdErrorCodeManager.getOBDErrorCodeById("P0020");
                Toast toast = Toast.makeText(getActivity(), " results " + res.getPid() + " " + res.getType() + " " + res.getDescription(), Toast.LENGTH_SHORT);
                toast.show();


            }
        });*/
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

    public List<OBDErrorCode> getDataForListView() {
        OBDErrorCodeManager obdErrorCodeManager = new OBDErrorCodeManager(getActivity());
        TroubleCodesObdCommand tr = new TroubleCodesObdCommand();
        try {
            tr.run(socket.getInputStream(), socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String[] arr = tr.getFormattedResult().split("\n");
        OBDErrorCode obd = new OBDErrorCode();

        for (int i = 0; i < arr.length; i++) {
            Log.d("Error Code", arr[i]);
            OBDErrorCode res = obdErrorCodeManager.getOBDErrorCodeById(arr[i]);

            obd.setPid(res.getPid());
            Log.d("PID", res.getPid());
            obd.setType(res.getType());
            obd.setDescription(res.getDescription());
            Log.d("NPID", obd.getPid());
            obdList.add(obd);
            adapter.notifyDataSetChanged();
        }
        return obdList;
    }              /*obd.pid = "P0001";
                obd.type = "Generic";
                obd.description = "Fuel volume regulator control -circuit open";
                obdErrorCodeManager.insert(obd);

                obd.pid = "P0002";
                obd.type = "Generic";
                obd.description = "Fuel volume regulator control -circuit range/ performance";
                obdErrorCodeManager.insert(obd);

                obd.pid = "P0003";
                obd.type = "Generic";
                obd.description = "Fuel volume regulator control -circuit low";
                obdErrorCodeManager.insert(obd);

                obd.pid = "P0004";
                obd.type = "Generic";
                obd.description = "Fuel volume regulator control -circuit high";
                obdErrorCodeManager.insert(obd);

                obd.pid = "P0005";
                obd.type = "Generic";
                obd.description = "Fuel shut-off valve control -circuit open";
                obdErrorCodeManager.insert(obd);

                obd.pid = "P0006";
                obd.type = "Generic";
                obd.description = "Fuel shut-off valve control -circuit low";
                obdErrorCodeManager.insert(obd);

                obd.pid = "P0007";
                obd.type = "Generic";
                obd.description = "Fuel shut-off valve control -circuit high";
                obdErrorCodeManager.insert(obd);

                obd.pid = "P0008";
                obd.type = "Generic";
                obd.description = "Engine position system, bank 1 -performance";
                obdErrorCodeManager.insert(obd);

                obd.pid = "P0009";
                obd.type = "Generic";
                obd.description = "Engine position system, bank 2 -performance";
                obdErrorCodeManager.insert(obd);

                obd.pid = "P0010";
                obd.type = "Generic";
                obd.description = "Camshaft position (CMP)·actuator, intake/left/front, bank 1 -circuit malfunction";
                obdErrorCodeManager.insert(obd);

                obd.pid = "P0011";
                obd.type = "Generic";
                obd.description = "Camshaft position (CMP), intake/left/front, bank 1 -timing over-advanced/system performance";
                obdErrorCodeManager.insert(obd);

                obd.pid = "P0012";
                obd.type = "Generic";
                obd.description = "Camshaft position (CMP) actuator, intake/left/front, bank 1 -circuit malfunction";
                obdErrorCodeManager.insert(obd);

                obd.pid = "P0013";
                obd.type = "Generic";
                obd.description = "Camshaft position (CMP) actuator, exhaust!right! rear, bank 1 -timing over-advanced/system performance";
                obdErrorCodeManager.insert(obd);

                obd.pid = "P0014";
                obd.type = "Generic";
                obd.description = "Crankshaft position/camshaft position, bank 1 sensor A -correlation";
                obdErrorCodeManager.insert(obd);

                obd.pid = "P0015";
                obd.type = "Generic";
                obd.description = "Crankshaft position/camshaft position, bank 1 sensor B -correlation";
                obdErrorCodeManager.insert(obd);

                obd.pid = "P0016";
                obd.type = "Generic";
                obd.description = "Crankshaft position/camshaft position, bank 2 sensor A -correlation";
                obdErrorCodeManager.insert(obd);

                obd.pid = "P0017";
                obd.type = "Generic";
                obd.description = "Crankshaft position/camshaft position, bank 2 sensor B -correlation";
                obdErrorCodeManager.insert(obd);


                obd.pid = "P0018";
                obd.type = "Generic";
                obd.description = "Camshaft position (CMP) actuator, intake/left/front, bank 2 -circuit malfunction";
                obdErrorCodeManager.insert(obd);


                obd.pid = "P0019";
                obd.type = "Generic";
                obd.description = "Camshaft position (CMP), intake/left/front, bank 2 -timing over-advanced/system performance";
                obdErrorCodeManager.insert(obd);

                obd.pid = "P0020";
                obd.type = "Generic";
                obd.description = "Camshaft position (CMP), intake/left/front, bank 2 -timing over-retarded";
                obdErrorCodeManager.insert(obd);*/
}