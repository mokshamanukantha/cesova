
package com.android.cesova.obd.commands.protocol;

public class OdbRawCommand extends ObdProtocolCommand {

    public OdbRawCommand(String command) {
        super(command);
    }

    @Override
    public String getFormattedResult() {
        return getResult();
    }

    @Override
    public String getName() {
        return "Custom command " + getName();
    }

}
