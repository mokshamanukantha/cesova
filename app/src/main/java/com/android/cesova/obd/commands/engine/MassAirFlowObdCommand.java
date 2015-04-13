
package com.android.cesova.obd.commands.engine;

import com.android.cesova.obd.commands.ObdCommand;
import com.android.cesova.obd.enums.AvailableCommandNames;

public class MassAirFlowObdCommand extends ObdCommand {

    private float maf = -1.0f;

    public MassAirFlowObdCommand() {
        super("01 10");
    }

    public MassAirFlowObdCommand(MassAirFlowObdCommand other) {
        super(other);
    }

    @Override
    protected void performCalculations() {
        // ignore first two bytes [hh hh] of the response
        maf = (buffer.get(2) * 256 + buffer.get(3)) / 100.0f;
    }

    @Override
    public String getFormattedResult() {
        return String.format("%.2f%s", maf, "g/s");
    }

    public double getMAF() {
        return maf;
    }

    @Override
    public String getName() {
        return AvailableCommandNames.MAF.getValue();
    }

}
