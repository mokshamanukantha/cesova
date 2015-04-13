
package com.android.cesova.obd.commands.fuel;

import com.android.cesova.obd.commands.ObdCommand;
import com.android.cesova.obd.enums.AvailableCommandNames;

public class FuelConsumptionRateObdCommand extends ObdCommand {

    private float fuelRate = -1.0f;

    public FuelConsumptionRateObdCommand() {
        super("01 5E");
    }

    public FuelConsumptionRateObdCommand(FuelConsumptionRateObdCommand other) {
        super(other);
    }

    @Override
    protected void performCalculations() {
        // ignore first two bytes [hh hh] of the response
        fuelRate = (buffer.get(2) * 256 + buffer.get(3)) * 0.05f;
    }

    @Override
    public String getFormattedResult() {
        return String.format("%.1f%s", fuelRate, "");
    }

    public float getLitersPerHour() {
        return fuelRate;
    }

    @Override
    public String getName() {
        return AvailableCommandNames.FUEL_CONSUMPTION.getValue();
    }

}
