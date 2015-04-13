
package com.android.cesova.obd.commands.fuel;

import com.android.cesova.obd.commands.ObdCommand;
import com.android.cesova.obd.enums.AvailableCommandNames;


public class FuelLevelObdCommand extends ObdCommand {

    private float fuelLevel = 0f;

    public FuelLevelObdCommand() {
        super("01 2F");
    }

    @Override
    protected void performCalculations() {
        fuelLevel = 100.0f * buffer.get(2) / 255.0f;
    }

    @Override
    public String getFormattedResult() {
        return String.format("%.1f%s", fuelLevel, "%");
    }

    @Override
    public String getName() {
        return AvailableCommandNames.FUEL_LEVEL.getValue();
    }

    public float getFuelLevel() {
        return fuelLevel;
    }

}
