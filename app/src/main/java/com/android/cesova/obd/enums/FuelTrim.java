
package com.android.cesova.obd.enums;

import java.util.HashMap;
import java.util.Map;

public enum FuelTrim {

    SHORT_TERM_BANK_1(0x06, "Short Term Fuel Trim Bank 1"),
    LONG_TERM_BANK_1(0x07, "Long Term Fuel Trim Bank 1"),
    SHORT_TERM_BANK_2(0x08, "Short Term Fuel Trim Bank 2"),
    LONG_TERM_BANK_2(0x09, "Long Term Fuel Trim Bank 2");

    private static Map<Integer, FuelTrim> map = new HashMap<Integer, FuelTrim>();

    static {
        for (FuelTrim error : FuelTrim.values())
            map.put(error.getValue(), error);
    }

    private final int value;
    private final String bank;

    FuelTrim(final int value, final String bank) {
        this.value = value;
        this.bank = bank;
    }

    public static FuelTrim fromValue(final int value) {
        return map.get(value);
    }

    public int getValue() {
        return value;
    }

    public String getBank() {
        return bank;
    }

    public final String buildObdCommand() {
        return new String("01 " + value);
    }

}
