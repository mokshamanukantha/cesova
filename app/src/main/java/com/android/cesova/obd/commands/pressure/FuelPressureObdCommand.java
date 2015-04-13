
package com.android.cesova.obd.commands.pressure;

import com.android.cesova.obd.enums.AvailableCommandNames;

public class FuelPressureObdCommand extends PressureObdCommand {

    public FuelPressureObdCommand() {
        super("010A");
    }

    public FuelPressureObdCommand(FuelPressureObdCommand other) {
        super(other);
    }

    @Override
    protected final int preparePressureValue() {
        return buffer.get(2) * 3;
    }

    @Override
    public String getName() {
        return AvailableCommandNames.FUEL_PRESSURE.getValue();
    }

}