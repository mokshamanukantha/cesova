package com.android.cesova.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.cesova.R;
import com.android.cesova.obd.enums.AvailableCommandNames;

import java.util.ArrayList;
import java.util.List;


public class GraphActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG_ID="id";
    private static final String TAG_INTERVAL="time_interval";
    private static final String TAG_SRC="sourceX";
    private static final String TAG_TARGET="targetY";


    Spinner source; //X Axis
    Spinner target; //Y Axis
    Spinner chartType;
    Spinner interval;
    Button btnStartGraph;

    String selectedChartItem;
    String selectedChartInterval;
    String selectedChartSource;
    String selectedChartTarget;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_layout);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Graphing");
        setSupportActionBar(toolbar);

        source = (Spinner) findViewById(R.id.source);
        target = (Spinner) findViewById(R.id.target);
        chartType = (Spinner) findViewById(R.id.chartType);
        interval = (Spinner) findViewById(R.id.interval);
        btnStartGraph = (Button) findViewById(R.id.startGraph);

        List<String> categories = new ArrayList<String>();
        List<String> chartCategories = new ArrayList<String>();
        List<String> chartIntervals = new ArrayList<String>();

        String[] sensors = {AvailableCommandNames.AIR_INTAKE_TEMP.getValue(),
                AvailableCommandNames.AMBIENT_AIR_TEMP.getValue(),
                AvailableCommandNames.BAROMETRIC_PRESSURE.getValue(),
                AvailableCommandNames.ENGINE_COOLANT_TEMP.getValue(),
                AvailableCommandNames.ENGINE_LOAD.getValue(),
                AvailableCommandNames.ENGINE_RPM.getValue(),
                AvailableCommandNames.EQUIV_RATIO.getValue(),
                AvailableCommandNames.ENGINE_RUNTIME.getValue(),
                AvailableCommandNames.FUEL_CONSUMPTION.getValue(),
                AvailableCommandNames.FUEL_PRESSURE.getValue(),
                AvailableCommandNames.FUEL_LEVEL.getValue(),
                AvailableCommandNames.INTAKE_MANIFOLD_PRESSURE.getValue(),
                AvailableCommandNames.SPEED.getValue(),
                AvailableCommandNames.THROTTLE_POS.getValue(),};
        for(int i=0;i< sensors.length;i++) {
            categories.add(sensors[i].toString());
        }
        chartCategories.add("Line Chart");
        chartCategories.add("Scatter Chart");

        chartIntervals.add("100");
        chartIntervals.add("500");
        chartIntervals.add("1000");
        chartIntervals.add("2500");
        chartIntervals.add("5000");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, categories);
        ArrayAdapter<String> chartAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, chartCategories);
        ArrayAdapter<String> intervalAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, chartIntervals);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chartAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        source.setAdapter(dataAdapter);
        target.setAdapter(dataAdapter);
        chartType.setAdapter(chartAdapter);
        interval.setAdapter(intervalAdapter);
        btnStartGraph.setOnClickListener(this);



        chartType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedChartItem = chartType.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        interval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedChartInterval = interval.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        source.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedChartSource = source.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        target.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedChartTarget = target.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,ActivitySelectedChart.class);
        intent.putExtra(TAG_ID,selectedChartItem);
        intent.putExtra(TAG_INTERVAL,selectedChartInterval);
        intent.putExtra(TAG_SRC,selectedChartSource);
        intent.putExtra(TAG_TARGET,selectedChartTarget);
        startActivity(intent);
    }
}

