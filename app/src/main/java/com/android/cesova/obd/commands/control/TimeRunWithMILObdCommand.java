package com.android.cesova.obd.commands.control;

import com.android.cesova.obd.commands.ObdCommand;

/**
 * Created by mokshaDev on 5/10/2015.
 */
public class TimeRunWithMILObdCommand extends ObdCommand {

    private int time = -1;

    public TimeRunWithMILObdCommand()
    {
        super("01 4D");
    }
    @Override
    protected void performCalculations() {
        time = (buffer.get(2) * 256 + buffer.get(3));

    }

    @Override
    public String getFormattedResult() {
        return String.format("%d%s", time, " Distance");
    }

    @Override
    public String getName() {
        return null;
    }
}
