package com.android.cesova.Activities;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.cesova.GlobalClass;
import com.android.cesova.R;
import com.android.cesova.obd.commands.engine.EngineRPMObdCommand;
import com.android.cesova.obd.commands.engine.MassAirFlowObdCommand;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

/**
 * Created by mokshaDev on 5/8/2015.
 */
public class ActivitySelectedChart extends Activity {

    private GraphicalView mChart;

    private XYSeries sensorValueSeries;
    private XYMultipleSeriesDataset dataset;

    private XYSeriesRenderer valueRenderer;
    private XYMultipleSeriesRenderer multiRenderer;
    GlobalClass globalClass;
    BluetoothSocket socket;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_chart_layout);
        globalClass = (GlobalClass) getApplication();
        socket = globalClass.getSocket();
        if (socket != null) {
            Toast toast = Toast.makeText(this, "connection successfull", Toast.LENGTH_SHORT);
            toast.show();
            setupChart();
            new ChartTask().execute();
        } else {
            Toast toast = Toast.makeText(this, "You are not connected to any Device", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void setupChart(){

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

        multiRenderer.setXAxisMin(0);
        multiRenderer.setXAxisMax(700);

        multiRenderer.setYAxisMin(0);
        multiRenderer.setYAxisMax(17000);

        multiRenderer.setBarSpacing(100);

        // Adding valueRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(valueRenderer);

        // Getting a reference to LinearLayout of the MainActivity Layout
        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart_container);

        mChart = (GraphicalView) ChartFactory.getLineChartView(getBaseContext(), dataset, multiRenderer);

        // Adding the Line Chart to the LinearLayout
        chartContainer.addView(mChart);
    }

    private class ChartTask extends AsyncTask<Void, String, Void>{
        MassAirFlowObdCommand air = new MassAirFlowObdCommand();
        EngineRPMObdCommand baro = new EngineRPMObdCommand();
        @Override
        protected Void doInBackground(Void... params) {
            //int i = 0;
            try{
                do{
                    air.run(socket.getInputStream(), socket.getOutputStream());
                    baro.run(socket.getInputStream(), socket.getOutputStream());
                    String [] values = new String[2];
                    //Random r = new Random();
                    //int visits = r.nextInt(10);

                    values[0] = Integer.toString((int) air.getMAF());
                    values[1] = Integer.toString(baro.getRPM());

                    publishProgress(values);
                    Thread.sleep(100);
                    //i++;
                }while(!Thread.currentThread().isInterrupted());
            }catch(Exception e) {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            sensorValueSeries.add(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
            mChart.repaint();
        }
    }
}
