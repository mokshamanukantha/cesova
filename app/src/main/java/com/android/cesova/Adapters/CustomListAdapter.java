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

import java.util.List;

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<OBDErrorCode> obdItems;

    public CustomListAdapter(Activity activity, List<OBDErrorCode> obdItems) {
        this.activity = activity;
        this.obdItems = obdItems;
    }

    @Override
    public int getCount() {
        return obdItems.size();
    }

    @Override
    public Object getItem(int location) {
        return obdItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        notifyDataSetChanged();
        return position;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);
        TextView pid = (TextView) convertView.findViewById(R.id.pid);
        TextView type = (TextView) convertView.findViewById(R.id.type);
        TextView description = (TextView) convertView.findViewById(R.id.description);
        OBDErrorCode m = obdItems.get(position);
        pid.setText(m.getPid());
        type.setText("Type: " + String.valueOf(m.getType()));
        description.setText(String.valueOf(m.getDescription()));
        return convertView;
    }

}