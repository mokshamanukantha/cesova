package com.android.cesova.Fragments;

import android.app.Fragment;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cesova.Activities.MainActivity;
import com.android.cesova.Gauges.CustomGauge;
import com.android.cesova.GlobalClass;
import com.android.cesova.R;
import com.android.cesova.obd.commands.engine.EngineRPMObdCommand;

import java.io.IOException;


public class HomeFragment extends Fragment {

    GlobalClass globalClass;
    BluetoothSocket socket;
    private CustomGauge gauge1;
    private TextView text1;
    public final CountDownTimer mTimer = new CountDownTimer(3000000, 100) {
        EngineRPMObdCommand rpm = new EngineRPMObdCommand();

        @Override
        public void onTick(final long millisUntilFinished) {
            try {
                try {
                    rpm.run(socket.getInputStream(), socket.getOutputStream());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gauge1.setValue(rpm.getRPM());
                text1.setText("" + rpm.getFormattedResult());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFinish() {
        }
    };
    private ImageButton btnRealTimeInformation;
    private ImageButton btnCheckFaultCodes;
    private ImageButton btnMapView;
    private ImageButton btnGraphs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        globalClass = (GlobalClass) getActivity().getApplication();
        gauge1 = (CustomGauge) view.findViewById(R.id.gauge1);
        text1 = (TextView) view.findViewById(R.id.textView1);
        btnCheckFaultCodes = (ImageButton) view.findViewById(R.id.btnFaultCodes);
        btnGraphs = (ImageButton) view.findViewById(R.id.btnGraphs);
        btnMapView = (ImageButton) view.findViewById(R.id.btnMapView);
        btnRealTimeInformation = (ImageButton) view.findViewById(R.id.btnRealtimeInfo);

        btnCheckFaultCodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity m = (MainActivity) getActivity();
                m.changeLayout(2);
            }
        });

        btnGraphs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity m = (MainActivity) getActivity();
                m.changeLayout(3);
            }
        });

        btnMapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity m = (MainActivity) getActivity();
                m.changeLayout(4);
            }
        });

        btnRealTimeInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity m = (MainActivity) getActivity();
                m.changeLayout(1);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void startGauge() throws InterruptedException {
        socket = globalClass.getSocket();
        if (socket != null) {
            Toast toast = Toast.makeText(getActivity(), "connection successfull", Toast.LENGTH_SHORT);
            toast.show();
            mTimer.start();
        } else {
            Toast toast = Toast.makeText(getActivity(), "socket is null", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
