
package com.android.cesova.obd.commands.fuel;

import com.android.cesova.obd.commands.ObdCommand;
import com.android.cesova.obd.enums.AvailableCommandNames;
import com.android.cesova.obd.enums.FuelType;


public class FindFuelTypeObdCommand extends ObdCommand {

    private int fuelType = 0;

    public FindFuelTypeObdCommand() {
        super("01 51");
    }

    public FindFuelTypeObdCommand(FindFuelTypeObdCommand other) {
        super(other);
    }

    @Override
    protected void performCalculations() {
        // ignore first two bytes [hh hh] of the response
        fuelType = buffer.get(2);
    }

    @Override
    public String getFormattedResult() {
        try {
            return FuelType.fromValue(fuelType).getDescription();
        } catch (Exception e) {
            return "-";
        }
    }

    @Override
    public String getName() {
        return AvailableCommandNames.FUEL_TYPE.getValue();
    }

}
