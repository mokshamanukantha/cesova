
package com.android.cesova.obd.commands.pressure;

import com.android.cesova.obd.commands.ObdCommand;
import com.android.cesova.obd.commands.SystemOfUnits;

public abstract class PressureObdCommand extends ObdCommand implements
        SystemOfUnits {

    protected int tempValue = 0;
    protected int pressure = 0;

    public PressureObdCommand(String cmd) {
        super(cmd);
    }


    public PressureObdCommand(PressureObdCommand other) {
        super(other);
    }

    protected int preparePressureValue() {
        return buffer.get(2);
    }

    protected void performCalculations() {
        // ignore first two bytes [hh hh] of the response
        pressure = preparePressureValue();
    }

    @Override
    public String getFormattedResult() {
        return useImperialUnits ? String.format("%.1f%s", getImperialUnit(), "psi")
                : String.format("%d%s", pressure, "kPa");
    }

    public int getMetricUnit() {
        return pressure;
    }

    public float getImperialUnit() {
        return new Double(pressure * 0.145037738).floatValue();
    }

}
