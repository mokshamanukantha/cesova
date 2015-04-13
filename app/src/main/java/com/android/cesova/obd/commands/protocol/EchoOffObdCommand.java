
package com.android.cesova.obd.commands.protocol;

public class EchoOffObdCommand extends ObdProtocolCommand {

    public EchoOffObdCommand() {
        super("AT E0");
    }

    public EchoOffObdCommand(EchoOffObdCommand other) {
        super(other);
    }

    @Override
    public String getFormattedResult() {
        return getResult();
    }

    @Override
    public String getName() {
        return "Echo Off";
    }

}