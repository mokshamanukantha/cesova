
package com.android.cesova.obd.commands.protocol;

public class TimeoutObdCommand extends ObdProtocolCommand {

    public TimeoutObdCommand(int timeout) {
        super("AT ST " + Integer.toHexString(0xFF & timeout));
    }

    public TimeoutObdCommand(TimeoutObdCommand other) {
        super(other);
    }

    @Override
    public String getFormattedResult() {
        return getResult();
    }

    @Override
    public String getName() {
        return "Timeout";
    }

}