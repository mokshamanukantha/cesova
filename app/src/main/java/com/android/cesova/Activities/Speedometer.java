package com.android.cesova.Activities;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.android.cesova.Gauges.CustomGauge;
import com.android.cesova.R;

import java.util.Random;

/**
 * Created by mokshaDev on 3/23/2015.
 */


public class Speedometer extends Activity implements LocationListener {

    private final Random RAND = new Random();
    int i;
    private CustomGauge gauge1;
    private Handler handler = new Handler();
    private TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speedometer);


        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        this.onLocationChanged(null);


        gauge1 = (CustomGauge) findViewById(R.id.gauge1);
        text1 = (TextView) findViewById(R.id.textView2);

        text1.setText(Integer.toString(gauge1.getValue()));
        // mTimer.start();


    }


  /*  private final CountDownTimer mTimer = new CountDownTimer(300000, 100) {


    };

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private Runnable drawRunner = new Runnable() {
        @Override
        public void run() {
            try {

                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }; */

    @Override
    public void onLocationChanged(Location location) {

        TextView txt = (TextView) this.findViewById(R.id.textView2);
        if (location == null) {
            txt.setText("0.0 km/h");
        } else {
            float nCurrentSpeed = location.getSpeed();
            float speed = (float) (nCurrentSpeed * 3.6);
            txt.setText(speed + " km/h");
            gauge1.setValue(Integer.parseInt((String.valueOf(speed).split("\\."))[0]));

        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

