
package com.android.cesova.obd.commands.pressure;

import com.android.cesova.obd.enums.AvailableCommandNames;

public class BarometricPressureObdCommand extends PressureObdCommand {

    public BarometricPressureObdCommand() {
        super("01 33");
    }

    public BarometricPressureObdCommand(PressureObdCommand other) {
        super(other);
    }

    @Override
    public String getName() {
        return AvailableCommandNames.BAROMETRIC_PRESSURE.getValue();
    }

}
