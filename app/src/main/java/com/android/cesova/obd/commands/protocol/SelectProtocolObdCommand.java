
package com.android.cesova.obd.commands.protocol;

import com.android.cesova.obd.enums.ObdProtocols;

public class SelectProtocolObdCommand extends ObdProtocolCommand {

    private final ObdProtocols protocol;

    public SelectProtocolObdCommand(final ObdProtocols protocol) {
        super("AT SP " + protocol.getValue());
        this.protocol = protocol;
    }

    @Override
    public String getFormattedResult() {
        return getResult();
    }

    @Override
    public String getName() {
        return "Select Protocol " + protocol.name();
    }

}