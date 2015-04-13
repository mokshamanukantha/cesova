
package com.android.cesova.obd.commands.temperature;

import com.android.cesova.obd.enums.AvailableCommandNames;

public class EngineCoolantTemperatureObdCommand extends TemperatureObdCommand {

    public EngineCoolantTemperatureObdCommand() {
        super("01 05");
    }

    public EngineCoolantTemperatureObdCommand(TemperatureObdCommand other) {
        super(other);
    }

    @Override
    public String getName() {
        return AvailableCommandNames.ENGINE_COOLANT_TEMP.getValue();
    }

}