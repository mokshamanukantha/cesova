package com.android.cesova.Activities;

/**
 * Created by mokshaDev on 5/8/2015.
 */

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.android.cesova.R;

public class CheckFaultCodeMain extends ActionBarActivity implements View.OnClickListener {

    private Resources res;
    //ImageView img;
    Button dtc;
    Button mil;
    Button temp;
    Button oxyg;
    public CheckFaultCodeMain() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_fault_code_main);
        res = this.getResources();

        dtc= (Button) findViewById(R.id.btnDTC);
        mil= (Button) findViewById(R.id.btnEngineMIL);
        temp= (Button) findViewById(R.id.btnTemp);
        oxyg= (Button) findViewById(R.id.btnOxygen);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("ECU Diagnosis");
        setSupportActionBar(toolbar);
        this.setSupportActionBar(toolbar);

        dtc.setOnClickListener(this);
        mil.setOnClickListener(this);
        temp.setOnClickListener(this);
        oxyg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if(view.getId()==R.id.btnDTC)
        {
            intent = new Intent(this, CheckFaultCodesActivty.class);
            startActivity(intent);
        }
        if(view.getId()==R.id.btnEngineMIL)
        {
            intent = new Intent(this, EngineMilActivty.class);
            startActivity(intent);
        }
        if(view.getId()==R.id.btnTemp)
        {
            intent = new Intent(this, TemperatureActivty.class);
            startActivity(intent);
        }
        if(view.getId()==R.id.btnOxygen)
        {
            intent = new Intent(this, EmmisionActivty.class);
            startActivity(intent);
        }

    }
}
