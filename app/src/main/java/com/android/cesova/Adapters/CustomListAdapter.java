package com.android.cesova.Adapters;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.cesova.R;
import com.android.cesova.obd.ErrorCodes.OBDErrorCode;
import com.android.cesova.obd.ErrorCodes.OBDErrorCodeManager;

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private String[] obdItems;
    private LayoutInflater obdInf;
    OBDErrorCodeManager obdErrorCodeManager;
    public CustomListAdapter(Context c, String[] obdItems) {
        this.obdItems = obdItems;
        obdInf =LayoutInflater.from(c);
        obdErrorCodeManager = new OBDErrorCodeManager(c);
    }

    @Override
    public int getCount() {
        return obdItems.length;
    }

    @Override
    public Object getItem(int location) {
        return obdItems[location];
    }

    @Override
    public long getItemId(int position) {
        return position;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = obdInf.inflate(R.layout.list_row, null);
        OBDErrorCode obdErrorCode = new OBDErrorCode();
        obdErrorCode = obdErrorCodeManager.getOBDErrorCodeById(obdItems[position]);
        TextView pid = (TextView) convertView.findViewById(R.id.pid);
        TextView type = (TextView) convertView.findViewById(R.id.type);
        TextView description = (TextView) convertView.findViewById(R.id.description);
        //Log.d("position", String.valueOf(position));
        pid.setText(obdErrorCode.getPid());
        type.setText("Type: " + String.valueOf(obdErrorCode.getType()));
        description.setText(String.valueOf(obdErrorCode.getDescription()));
        return convertView;
    }

}