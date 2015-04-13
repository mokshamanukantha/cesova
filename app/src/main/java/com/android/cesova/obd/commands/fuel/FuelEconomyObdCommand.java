
package com.android.cesova.obd.commands.fuel;

import com.android.cesova.obd.commands.ObdCommand;
import com.android.cesova.obd.commands.SpeedObdCommand;
import com.android.cesova.obd.enums.AvailableCommandNames;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FuelEconomyObdCommand extends ObdCommand {

    protected float kml = -1.0f;

    public FuelEconomyObdCommand() {
        super("");
    }

    @Override
    protected void performCalculations() {
    }

    @Override
    public void run(InputStream in, OutputStream out) throws IOException,
            InterruptedException {
        final FuelConsumptionRateObdCommand fuelConsumptionCommand = new FuelConsumptionRateObdCommand();
        fuelConsumptionCommand.run(in, out);

        final SpeedObdCommand speedCommand = new SpeedObdCommand();
        speedCommand.run(in, out);

        kml = (100 / speedCommand.getMetricSpeed())
                * fuelConsumptionCommand.getLitersPerHour();
    }

    @Override
    public String getFormattedResult() {
        return useImperialUnits ? String.format("%.1f %s", getMilesPerUKGallon(),
                "mpg") : String.format("%.1f %s", kml, "l/100km");
    }

    public float getLitersPer100Km() {
        return kml;
    }

    public float getMilesPerUSGallon() {
        return 235.2f / kml;
    }

    public float getMilesPerUKGallon() {
        return 282.5f / kml;
    }

    @Override
    public String getName() {
        return AvailableCommandNames.FUEL_ECONOMY.getValue();
    }

}