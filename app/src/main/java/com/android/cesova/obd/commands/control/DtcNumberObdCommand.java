
package com.android.cesova.obd.commands.control;

import com.android.cesova.obd.commands.ObdCommand;
import com.android.cesova.obd.enums.AvailableCommandNames;


public class DtcNumberObdCommand extends ObdCommand {

    private int codeCount = 0;
    private boolean milOn = false;


    public DtcNumberObdCommand() {
        super("01 01");
    }


    public DtcNumberObdCommand(DtcNumberObdCommand other) {
        super(other);
    }

    @Override
    protected void performCalculations() {
        // ignore first two bytes [hh hh] of the response
        final int mil = buffer.get(2);
        milOn = (mil & 0x80) == 128;
        codeCount = mil & 0x7F;
    }


    public String getFormattedResult() {
        final String res = milOn ? "MIL is ON" : "MIL is OFF";
        return new StringBuilder().append(res).append(codeCount).append(" codes")
                .toString();
    }

    public int getTotalAvailableCodes() {
        return codeCount;
    }


    public boolean getMilOn() {
        return milOn;
    }

    @Override
    public String getName() {
        return AvailableCommandNames.DTC_NUMBER.getValue();
    }

}
