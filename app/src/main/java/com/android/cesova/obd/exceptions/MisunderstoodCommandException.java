
package com.android.cesova.obd.exceptions;

public class MisunderstoodCommandException extends ObdResponseException {

    public MisunderstoodCommandException() {
        super("?");
    }

}
