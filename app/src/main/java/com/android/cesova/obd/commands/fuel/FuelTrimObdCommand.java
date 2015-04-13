
package com.android.cesova.obd.commands.fuel;

import com.android.cesova.obd.commands.ObdCommand;
import com.android.cesova.obd.enums.FuelTrim;

public class FuelTrimObdCommand extends ObdCommand {

    private final FuelTrim bank;
    private float fuelTrimValue = 0.0f;

    public FuelTrimObdCommand(final FuelTrim bank) {
        super(bank.buildObdCommand());
        this.bank = bank;
    }

    private float prepareTempValue(final int value) {
        return new Double((value - 128) * (100.0 / 128)).floatValue();
    }

    protected void performCalculations() {
        fuelTrimValue = prepareTempValue(buffer.get(2));
    }

    @Override
    public String getFormattedResult() {
        return String.format("%.2f%s", fuelTrimValue, "%");
    }

    public final float getValue() {
        return fuelTrimValue;
    }

    public final String getBank() {
        return bank.getBank();
    }

    @Override
    public String getName() {
        return bank.getBank();
    }

}
