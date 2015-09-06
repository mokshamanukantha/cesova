package com.android.cesova.Activities;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.cesova.GlobalClass;
import com.android.cesova.R;
import com.android.cesova.obd.commands.ObdCommand;
import com.android.cesova.obd.commands.SpeedObdCommand;
import com.android.cesova.obd.commands.engine.EngineLoadObdCommand;
import com.android.cesova.obd.commands.engine.EngineRPMObdCommand;
import com.android.cesova.obd.commands.engine.EngineRuntimeObdCommand;
import com.android.cesova.obd.commands.engine.MassAirFlowObdCommand;
import com.android.cesova.obd.commands.engine.ThrottlePositionObdCommand;
import com.android.cesova.obd.commands.temperature.AirIntakeTemperatureObdCommand;
import com.android.cesova.obd.commands.temperature.AmbientAirTemperatureObdCommand;
import com.android.cesova.obd.commands.temperature.EngineCoolantTemperatureObdCommand;
import com.android.cesova.obd.enums.AvailableCommandNames;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;


public class ActivitySelectedChart extends Activity {

    private static final String TAG_ID = "id";
    private static final String TAG_INTERVAL = "time_interval";
    private static final String TAG_SRC = "sourceX";
    private static final String TAG_TARGET = "targetY";


    private GraphicalView mChart;

    private XYSeries sensorValueSeries;
    private XYMultipleSeriesDataset dataset;

    private XYSeriesRenderer valueRenderer;
    private XYMultipleSeriesRenderer multiRenderer;
    GlobalClass globalClass;
    BluetoothSocket socket;
    String selectedChartTarg;
    String selectedChartSrc;
    ObdCommand[] obdCommands = new ObdCommand[8];

    MassAirFlowObdCommand air = new MassAirFlowObdCommand(); //data for mass air flow cannot be retrieved due to technical issues
    EngineLoadObdCommand engLoad = new EngineLoadObdCommand(); //engine
    EngineRPMObdCommand baro = new EngineRPMObdCommand(); //engine
    EngineRuntimeObdCommand engRuntime = new EngineRuntimeObdCommand();//engine
    ThrottlePositionObdCommand throttle = new ThrottlePositionObdCommand();//engine
    AirIntakeTemperatureObdCommand airIntake = new AirIntakeTemperatureObdCommand();
    AmbientAirTemperatureObdCommand ambientAir = new AmbientAirTemperatureObdCommand();
    EngineCoolantTemperatureObdCommand coolant = new EngineCoolantTemperatureObdCommand();
    SpeedObdCommand speed = new SpeedObdCommand();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_chart_layout);
        globalClass = (GlobalClass) getApplication();
        socket = globalClass.getSocket();
        //2  obdCommands[0] = air;
        if (socket != null) {
            Toast toast = Toast.makeText(this, "connection successfull", Toast.LENGTH_SHORT);
            toast.show();
            Intent launch2 = getIntent();
            selectedChartTarg = launch2.getStringExtra(TAG_TARGET);
            selectedChartSrc = launch2.getStringExtra(TAG_SRC);
            setupChart();
            new ChartTask().execute();
        } else {
            Toast toast = Toast.makeText(this, "You are not connected to any Device", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void setupChart() {

        // Creating an  XYSeries for Visits
        sensorValueSeries = new XYSeries("Realtime Graph");

        // Creating a dataset to hold each series
        dataset = new XYMultipleSeriesDataset();
        // Adding Visits Series to the dataset
        dataset.addSeries(sensorValueSeries);

        // Creating XYSeriesRenderer to customize sensorValueSeries
        valueRenderer = new XYSeriesRenderer();
        valueRenderer.setColor(Color.RED);
        valueRenderer.setPointStyle(PointStyle.CIRCLE);
        valueRenderer.setFillPoints(true);
        valueRenderer.setLineWidth(2);
        valueRenderer.setDisplayChartValues(true);

        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setChartTitle("Visits Chart");
        multiRenderer.setXTitle("Seconds");
        multiRenderer.setYTitle("Count");
        multiRenderer.setZoomButtonsVisible(true);
        if (selectedChartSrc.equals(AvailableCommandNames.ENGINE_COOLANT_TEMP) && selectedChartTarg.equals(AvailableCommandNames.THROTTLE_POS)) {
            multiRenderer.setXAxisMin(0);
            multiRenderer.setXAxisMax(215);
            multiRenderer.setYAxisMin(0);
            multiRenderer.setYAxisMax(100);
        }
        if (selectedChartSrc.equals(AvailableCommandNames.ENGINE_LOAD) && selectedChartTarg.equals(AvailableCommandNames.SPEED)) {
            multiRenderer.setXAxisMin(0);
            multiRenderer.setXAxisMax(1000);
            multiRenderer.setYAxisMin(0);
            multiRenderer.setYAxisMax(300);
        }
        if (selectedChartSrc.equals("Engine RPM") && selectedChartTarg.equals(AvailableCommandNames.THROTTLE_POS)) {
            multiRenderer.setXAxisMin(0);
            multiRenderer.setXAxisMax(100);
            multiRenderer.setYAxisMin(0);
            multiRenderer.setYAxisMax(17000);
        }
        multiRenderer.setXAxisMin(0);
        multiRenderer.setXAxisMax(100);
        multiRenderer.setYAxisMin(0);
        multiRenderer.setYAxisMax(17000);
        Intent launch = getIntent();
        String selectedChartInt = launch.getStringExtra(TAG_INTERVAL);
        Log.d("chart Interval", selectedChartInt);
        /*if (selectedChartInt.equals("100")) {
            multiRenderer.setBarSpacing(100);
        }
        if (selectedChartInt.equals("500")) {
            multiRenderer.setBarSpacing(500);
        }

        if (selectedChartInt.equals("1000")) {

            multiRenderer.setBarSpacing(1000);
        }

        if (selectedChartInt.equals("2500")) {

            multiRenderer.setBarSpacing(2500);
        }

        if (selectedChartInt.equals("5000")) {

            multiRenderer.setBarSpacing(5000);
        }*/
        // Adding valueRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.setBarSpacing(5);
        multiRenderer.addSeriesRenderer(valueRenderer);
        // Getting a reference to LinearLayout of the MainActivity Layout
        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart_container);
        // Intent launch = getIntent();
        String selectedChart = launch.getStringExtra(TAG_ID);
        Log.d("chart", selectedChart);
        if (selectedChart.equals("Line Chart")) {
            mChart = (GraphicalView) ChartFactory.getLineChartView(getBaseContext(), dataset, multiRenderer);

        }
        if (selectedChart.equals("Scatter Chart")) {
            mChart = (GraphicalView) ChartFactory.getScatterChartView(getBaseContext(), dataset, multiRenderer);

        }
        // Adding the Line Chart to the LinearLayout
        chartContainer.addView(mChart);
    }

    private class ChartTask extends AsyncTask<Void, String, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Log.d("selectedChartTarg", selectedChartTarg);
            Log.d("selectedChartSrc", selectedChartSrc);
            try {
                do {
                    String[] values = new String[2];
                    /*if (selectedChartSrc.equals(AvailableCommandNames.ENGINE_COOLANT_TEMP) && selectedChartTarg.equals(AvailableCommandNames.THROTTLE_POS)) {
                        coolant.run(socket.getInputStream(), socket.getOutputStream());
                        throttle.run(socket.getInputStream(), socket.getOutputStream());
                        values[0] = Integer.toString((int) coolant.getTemperature());
                        values[1] = Integer.toString((int) throttle.getPercentage());
                    }
                    if (selectedChartSrc.equals(AvailableCommandNames.ENGINE_LOAD) && selectedChartTarg.equals(AvailableCommandNames.SPEED)) {
                        engLoad.run(socket.getInputStream(), socket.getOutputStream());
                        speed.run(socket.getInputStream(), socket.getOutputStream());

                        values[0] = Integer.toString((int) engLoad.getPercentage());
                        values[1] = Integer.toString(speed.getMetricSpeed());
                    }
                    if (selectedChartSrc.trim().equals(AvailableCommandNames.ENGINE_RPM) && selectedChartTarg.trim().equals(AvailableCommandNames.THROTTLE_POS)) {
                        baro.run(socket.getInputStream(), socket.getOutputStream());
                        throttle.run(socket.getInputStream(), socket.getOutputStream());
                        Log.d("value1", "rpm");
                        values[0] = Integer.toString(baro.getRPM());
                        values[1] = Integer.toString((int) throttle.getPercentage());
                    }*/
                    baro.run(socket.getInputStream(), socket.getOutputStream());
                    throttle.run(socket.getInputStream(), socket.getOutputStream());
                    Log.d("value1", "rpm");
                    values[0] = Integer.toString(baro.getRPM());
                    values[1] = Integer.toString((int) throttle.getPercentage());
                    publishProgress(values);
                    Thread.sleep(100);
                } while (!Thread.currentThread().isInterrupted());
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Log.d("value1", values[0]);
            Log.d("value2", values[1]);
            sensorValueSeries.add(Integer.parseInt(values[1]), Integer.parseInt(values[0]));
            mChart.repaint();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }
}
