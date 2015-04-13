
package com.android.cesova.obd.commands.pressure;

import com.android.cesova.obd.enums.AvailableCommandNames;

public class IntakeManifoldPressureObdCommand extends PressureObdCommand {

    public IntakeManifoldPressureObdCommand() {
        super("01 0B");
    }

    public IntakeManifoldPressureObdCommand(IntakeManifoldPressureObdCommand other) {
        super(other);
    }

    @Override
    public String getName() {
        return AvailableCommandNames.INTAKE_MANIFOLD_PRESSURE.getValue();
    }

}