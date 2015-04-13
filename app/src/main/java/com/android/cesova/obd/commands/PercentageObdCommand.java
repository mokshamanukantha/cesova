
package com.android.cesova.obd.commands;

public abstract class PercentageObdCommand extends ObdCommand {

    private float percentage = 0f;

    public PercentageObdCommand(String command) {
        super(command);
    }

    public PercentageObdCommand(PercentageObdCommand other) {
        super(other);
    }

    @Override
    protected void performCalculations() {
        // ignore first two bytes [hh hh] of the response
        percentage = (buffer.get(2) * 100.0f) / 255.0f;
    }

    @Override
    public String getFormattedResult() {
        return String.format("%.1f%s", percentage, "%");
    }

    public float getPercentage() {
        return percentage;
    }

}
