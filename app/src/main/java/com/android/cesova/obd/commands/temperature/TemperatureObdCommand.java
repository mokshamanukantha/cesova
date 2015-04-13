
package com.android.cesova.obd.commands.temperature;

import com.android.cesova.obd.commands.ObdCommand;
import com.android.cesova.obd.commands.SystemOfUnits;

public abstract class TemperatureObdCommand extends ObdCommand implements
        SystemOfUnits {

    private float temperature = 0.0f;

    public TemperatureObdCommand(String cmd) {
        super(cmd);
    }

    public TemperatureObdCommand(TemperatureObdCommand other) {
        super(other);
    }

    @Override
    protected void performCalculations() {
        // ignore first two bytes [hh hh] of the response
        temperature = buffer.get(2) - 40;
    }

    @Override
    public String getFormattedResult() {
        return useImperialUnits ? String.format("%.1f%s", getImperialUnit(), "F")
                : String.format("%.0f%s", temperature, "C");
    }

    public float getTemperature() {
        return temperature;
    }


    public float getImperialUnit() {
        return temperature * 1.8f + 32;
    }


    public abstract String getName();

}
