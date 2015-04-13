
package com.android.cesova.obd.commands.temperature;

import com.android.cesova.obd.enums.AvailableCommandNames;

public class AmbientAirTemperatureObdCommand extends TemperatureObdCommand {

    public AmbientAirTemperatureObdCommand() {
        super("01 46");
    }

    public AmbientAirTemperatureObdCommand(TemperatureObdCommand other) {
        super(other);
    }

    @Override
    public String getName() {
        return AvailableCommandNames.AMBIENT_AIR_TEMP.getValue();
    }

}