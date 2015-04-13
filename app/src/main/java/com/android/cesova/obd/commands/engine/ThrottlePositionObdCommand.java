
package com.android.cesova.obd.commands.engine;

import com.android.cesova.obd.commands.PercentageObdCommand;
import com.android.cesova.obd.enums.AvailableCommandNames;

public class ThrottlePositionObdCommand extends PercentageObdCommand {

    public ThrottlePositionObdCommand() {
        super("01 11");
    }

    public ThrottlePositionObdCommand(ThrottlePositionObdCommand other) {
        super(other);
    }

    @Override
    public String getName() {
        return AvailableCommandNames.THROTTLE_POS.getValue();
    }

}