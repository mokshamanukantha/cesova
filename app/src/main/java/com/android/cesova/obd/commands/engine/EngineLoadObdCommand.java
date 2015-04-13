
package com.android.cesova.obd.commands.engine;

import com.android.cesova.obd.commands.PercentageObdCommand;
import com.android.cesova.obd.enums.AvailableCommandNames;

public class EngineLoadObdCommand extends PercentageObdCommand {

    public EngineLoadObdCommand() {
        super("01 04");
    }


    public EngineLoadObdCommand(EngineLoadObdCommand other) {
        super(other);
    }

    @Override
    public String getName() {
        return AvailableCommandNames.ENGINE_LOAD.getValue();
    }

}