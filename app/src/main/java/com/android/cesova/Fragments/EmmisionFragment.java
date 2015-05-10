package com.android.cesova.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.cesova.R;

/**
 * Created by mokshaDev on 5/10/2015.
 */
public class EmmisionFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.emmission_fragment, container, false);
        return rootView;

    }
}
