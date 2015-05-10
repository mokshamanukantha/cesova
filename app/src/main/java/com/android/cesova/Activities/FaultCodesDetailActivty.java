package com.android.cesova.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.android.cesova.R;
import com.android.cesova.obd.ErrorCodes.OBDErrorCode;
import com.android.cesova.obd.ErrorCodes.OBDErrorCodeManager;

/**
 * Created by mokshaDev on 5/9/2015.
 */
public class FaultCodesDetailActivty extends ActionBarActivity {
    private static final String TAG_PID="pid";
    OBDErrorCodeManager obdErrorCodeManager;

    TextView errcode;
    TextView symptom;
    TextView causes;
    TextView solution;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fault_detail_activity);
        errcode = (TextView) findViewById(R.id.errorCode);
        symptom = (TextView) findViewById(R.id.symptoms);
        causes = (TextView) findViewById(R.id.causes);
        solution = (TextView) findViewById(R.id.solutions);
        Intent intent = getIntent();
        String pid = intent.getStringExtra(TAG_PID);
        obdErrorCodeManager = new OBDErrorCodeManager(getApplication());
        OBDErrorCode obdErrorCode = new OBDErrorCode();
        obdErrorCode = obdErrorCodeManager.getOBDErrorCodeById(pid);
        errcode.setText("Error Code "+obdErrorCode.getPid() +"\n"+"Description "+ obdErrorCode.getDescription());
        symptom.setText(""+obdErrorCode.getSymptom());
        causes.setText(""+obdErrorCode.getCause());
        solution.setText(""+obdErrorCode.getSolution());
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Detailed Description About "+pid);
        setSupportActionBar(toolbar);
        this.setSupportActionBar(toolbar);
    }
}
