
package com.android.cesova.obd.commands.protocol;

public class LineFeedOffObdCommand extends ObdProtocolCommand {

    public LineFeedOffObdCommand() {
        super("AT L0");
    }

    public LineFeedOffObdCommand(LineFeedOffObdCommand other) {
        super(other);
    }

    @Override
    public String getFormattedResult() {
        return getResult();
    }

    @Override
    public String getName() {
        return "Line Feed Off";
    }

}
