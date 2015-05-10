package com.android.cesova.obd.commands.control;

import com.android.cesova.obd.commands.ObdCommand;

/**
 * Created by mokshaDev on 5/10/2015.
 */
public class DistanceTraveledWithMILOn extends ObdCommand {

    private int distance = -1;

    public DistanceTraveledWithMILOn()
    {
        super("01 21");
    }
    @Override
    protected void performCalculations() {
        distance = (buffer.get(2) * 256 + buffer.get(3));

    }

    @Override
    public String getFormattedResult() {
        return String.format("%d%s", distance, " Distance");
    }

    @Override
    public String getName() {
        return null;
    }
}
