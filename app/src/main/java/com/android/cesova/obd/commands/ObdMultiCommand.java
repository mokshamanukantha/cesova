
package com.android.cesova.obd.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class ObdMultiCommand {

    private ArrayList<ObdCommand> commands;

    public ObdMultiCommand() {
        this.commands = new ArrayList<ObdCommand>();
    }

    public void add(ObdCommand command) {
        this.commands.add(command);
    }

    public void remove(ObdCommand command) {
        this.commands.remove(command);
    }

    public void sendCommands(InputStream in, OutputStream out)
            throws IOException, InterruptedException {
        for (ObdCommand command : commands)
            command.run(in, out);
    }

    public String getFormattedResult() {
        StringBuilder res = new StringBuilder();
        for (ObdCommand command : commands)
            res.append(command.getFormattedResult()).append(",");

        return res.toString();
    }

}
