
package com.android.cesova.obd.exceptions;

public class ObdResponseException extends RuntimeException {

    private String message;

    private String response;

    private String command;

    protected ObdResponseException(String message) {
        this.message = message;
    }

    private static String clean(String s) {
        return s == null ? "" : s.replaceAll("\\s", "").toUpperCase();
    }

    public boolean isError(String response) {
        this.response = response;
        return clean(response).contains(clean(message));
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public String getMessage() {
        return "Error running " + command + ", response: " + response;
    }

}
