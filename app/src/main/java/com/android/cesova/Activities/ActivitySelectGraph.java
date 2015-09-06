package com.android.cesova.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.cesova.R;


public class ActivitySelectGraph extends ActionBarActivity {

    private Button btnSelectAccGraph;
    private Button btnSelectVoltGraph;
    private Button btnGraphing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_graph);

        btnSelectAccGraph = (Button) findViewById(R.id.accGraph);
        btnSelectVoltGraph = (Button) findViewById(R.id.button);
        btnGraphing = (Button) findViewById(R.id.graphing);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Real time Graphing");
        setSupportActionBar(toolbar);
        this.setSupportActionBar(toolbar);

        btnSelectAccGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(ActivitySelectGraph.this, ActivityGraphing.class);
                startActivity(intent1);
            }


        });


        btnSelectVoltGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(ActivitySelectGraph.this, ActivityDynamicGraphingVoltage.class);
                startActivity(intent2);
            }
        });

        btnGraphing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(ActivitySelectGraph.this, GraphActivity.class);
                startActivity(intent3);
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
}
