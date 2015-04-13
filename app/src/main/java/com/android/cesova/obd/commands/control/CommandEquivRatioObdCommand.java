
package com.android.cesova.obd.commands.control;

import com.android.cesova.obd.commands.ObdCommand;
import com.android.cesova.obd.enums.AvailableCommandNames;

public class CommandEquivRatioObdCommand extends ObdCommand {


    private double ratio = 0.00;


    public CommandEquivRatioObdCommand() {
        super("01 44");
    }

    public CommandEquivRatioObdCommand(CommandEquivRatioObdCommand other) {
        super(other);
    }

    @Override
    protected void performCalculations() {
        // ignore first two bytes [hh hh] of the response
        int a = buffer.get(2);
        int b = buffer.get(3);
        ratio = (a * 256 + b) / 32768;
    }

    @Override
    public String getFormattedResult() {
        return String.format("%.1f%s", ratio, "%");
    }

    public double getRatio() {
        return ratio;
    }

    @Override
    public String getName() {
        return AvailableCommandNames.EQUIV_RATIO.getValue();
    }

}
