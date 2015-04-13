
package com.android.cesova.obd.commands;

import com.android.cesova.obd.enums.AvailableCommandNames;

public class SpeedObdCommand extends ObdCommand implements SystemOfUnits {

    private int metricSpeed = 0;

    public SpeedObdCommand() {
        super("01 0D");
    }

    public SpeedObdCommand(SpeedObdCommand other) {
        super(other);
    }

    @Override
    protected void performCalculations() {
        metricSpeed = buffer.get(2);
    }


    public String getFormattedResult() {
        return useImperialUnits ? String.format("%.2f%s", getImperialUnit(), "mph")
                : String.format("%d%s", getMetricSpeed(), "km/h");
    }

    public int getMetricSpeed() {
        return metricSpeed;
    }

    public float getImperialSpeed() {
        return getImperialUnit();
    }

    public float getImperialUnit() {
        return new Double(metricSpeed * 0.621371192).floatValue();
    }

    @Override
    public String getName() {
        return AvailableCommandNames.SPEED.getValue();
    }

}
