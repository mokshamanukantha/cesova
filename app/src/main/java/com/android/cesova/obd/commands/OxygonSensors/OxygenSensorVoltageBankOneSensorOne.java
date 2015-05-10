package com.android.cesova.obd.commands.OxygonSensors;

import com.android.cesova.obd.commands.ObdCommand;

/**
 * Created by mokshaDev on 5/10/2015.
 */
public class OxygenSensorVoltageBankOneSensorOne extends ObdCommand {

    private int voltage = -1;

    public OxygenSensorVoltageBankOneSensorOne()
    {
        super("01 15");
    }

    @Override
    protected void performCalculations() {
        voltage = (buffer.get(2) /200);
    }

    @Override
    public String getFormattedResult() {
        return String.format("%d%s", voltage, "Voltage");
    }

    @Override
    public String getName() {
        return null;
    }
}
