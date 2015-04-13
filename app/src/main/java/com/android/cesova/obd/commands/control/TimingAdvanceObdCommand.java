
package com.android.cesova.obd.commands.control;

import com.android.cesova.obd.commands.PercentageObdCommand;
import com.android.cesova.obd.enums.AvailableCommandNames;


public class TimingAdvanceObdCommand extends PercentageObdCommand {

    public TimingAdvanceObdCommand() {
        super("01 0E");
    }

    public TimingAdvanceObdCommand(TimingAdvanceObdCommand other) {
        super(other);
    }

    @Override
    public String getName() {
        return AvailableCommandNames.TIMING_ADVANCE.getValue();
    }

}