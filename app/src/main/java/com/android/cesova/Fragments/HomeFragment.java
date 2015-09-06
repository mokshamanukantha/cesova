package com.android.cesova.Fragments;

import android.app.Fragment;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cesova.Activities.MainActivity;
import com.android.cesova.Gauges.CustomGauge;
import com.android.cesova.GlobalClass;
import com.android.cesova.R;
import com.android.cesova.obd.commands.engine.EngineRPMObdCommand;
import com.android.cesova.obd.commands.protocol.EchoOffObdCommand;
import com.android.cesova.obd.commands.protocol.LineFeedOffObdCommand;
import com.android.cesova.obd.commands.protocol.SelectProtocolObdCommand;
import com.android.cesova.obd.commands.protocol.TimeoutObdCommand;
import com.android.cesova.obd.enums.ObdProtocols;
import com.android.cesova.obd.exceptions.NoDataException;
import com.android.cesova.obd.exceptions.NonNumericResponseException;

import java.io.IOException;


public class HomeFragment extends Fragment {

    GlobalClass globalClass;
    BluetoothSocket socket;
    private CustomGauge gauge1;
    private TextView text1;
    ImageView imageView;
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
        imageView  = (ImageView) view.findViewById(R.id.homemeter);

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
            //new ChartTask().execute();
        } else {
            Toast toast = Toast.makeText(getActivity(), "socket is null", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private class ChartTask extends AsyncTask<Void, String, Void> {
        EngineRPMObdCommand rpm = new EngineRPMObdCommand();
        @Override
        protected Void doInBackground(Void... params){
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
                do {
                    try {
                        rpm.run(socket.getInputStream(), socket.getOutputStream());
                    }
                    catch (NoDataException e)
                    {
                        e.printStackTrace();
                    }
                    catch (NonNumericResponseException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gauge1.setValue(rpm.getRPM());
                            text1.setText("" + rpm.getFormattedResult());
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while(!Thread.currentThread().isInterrupted());
            return null;
        }
    }
}
