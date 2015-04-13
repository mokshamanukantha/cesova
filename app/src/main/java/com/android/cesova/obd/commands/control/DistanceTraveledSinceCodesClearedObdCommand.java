
package com.android.cesova.obd.commands.control;

import com.android.cesova.obd.commands.ObdCommand;
import com.android.cesova.obd.commands.SystemOfUnits;
import com.android.cesova.obd.enums.AvailableCommandNames;


public class DistanceTraveledSinceCodesClearedObdCommand extends ObdCommand
        implements SystemOfUnits {

    private int km = 0;

    public DistanceTraveledSinceCodesClearedObdCommand() {
        super("01 31");
    }

    public DistanceTraveledSinceCodesClearedObdCommand(
            DistanceTraveledSinceCodesClearedObdCommand other) {
        super(other);
    }

    @Override
    protected void performCalculations() {
        // ignore first two bytes [01 31] of the response
        km = buffer.get(2) * 256 + buffer.get(3);
    }

    @Override
    public String getFormattedResult() {
        return String.format("%.2f%s", (float) km, "km");
    }

    @Override
    public float getImperialUnit() {
        return new Double(km * 0.621371192).floatValue();
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    @Override
    public String getName() {
        return AvailableCommandNames.DISTANCE_TRAVELED_AFTER_CODES_CLEARED
                .getValue();
    }

}
