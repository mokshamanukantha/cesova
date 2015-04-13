
package com.android.cesova.obd.commands.temperature;

import com.android.cesova.obd.enums.AvailableCommandNames;

public class AirIntakeTemperatureObdCommand extends TemperatureObdCommand {

    public AirIntakeTemperatureObdCommand() {
        super("01 0F");
    }

    public AirIntakeTemperatureObdCommand(AirIntakeTemperatureObdCommand other) {
        super(other);
    }

    @Override
    public String getName() {
        return AvailableCommandNames.AIR_INTAKE_TEMP.getValue();
    }

}