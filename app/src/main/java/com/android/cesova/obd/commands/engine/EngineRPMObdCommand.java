
package com.android.cesova.obd.commands.engine;

import com.android.cesova.obd.commands.ObdCommand;
import com.android.cesova.obd.enums.AvailableCommandNames;


public class EngineRPMObdCommand extends ObdCommand {

    private int rpm = -1;

    public EngineRPMObdCommand() {
        super("01 0C");
    }

    public EngineRPMObdCommand(EngineRPMObdCommand other) {
        super(other);
    }

    @Override
    protected void performCalculations() {
        // ignore first two bytes [41 0C] of the response
        rpm = (buffer.get(2) * 256 + buffer.get(3)) / 4;
    }

    @Override
    public String getFormattedResult() {
        return String.format("%d%s", rpm, " RPM");
    }

    @Override
    public String getName() {
        return AvailableCommandNames.ENGINE_RPM.getValue();
    }

    public int getRPM() {
        return rpm;
    }

}
