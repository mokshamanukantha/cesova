
package com.android.cesova.obd.exceptions;

public class NonNumericResponseException extends RuntimeException {

    public NonNumericResponseException(String message) {
        super("Error reading response: " + message);
    }

}
