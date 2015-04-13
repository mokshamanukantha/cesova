
package com.android.cesova.obd.commands.protocol;

public class ObdResetCommand extends ObdProtocolCommand {

    public ObdResetCommand() {
        super("AT Z");
    }

    public ObdResetCommand(ObdResetCommand other) {
        super(other);
    }

    @Override
    public String getFormattedResult() {
        return getResult();
    }

    @Override
    public String getName() {
        return "Reset OBD";
    }

}