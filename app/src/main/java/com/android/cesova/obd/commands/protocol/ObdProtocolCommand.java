
package com.android.cesova.obd.commands.protocol;

import com.android.cesova.obd.commands.ObdCommand;

public abstract class ObdProtocolCommand extends ObdCommand {

    public ObdProtocolCommand(String command) {
        super(command);
    }

    public ObdProtocolCommand(ObdProtocolCommand other) {
        this(other.cmd);
    }

    protected void performCalculations() {

    }

    protected void fillBuffer() {
    }
}
