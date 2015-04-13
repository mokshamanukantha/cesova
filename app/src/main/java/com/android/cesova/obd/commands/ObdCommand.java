
package com.android.cesova.obd.commands;

import com.android.cesova.obd.exceptions.BusInitException;
import com.android.cesova.obd.exceptions.MisunderstoodCommandException;
import com.android.cesova.obd.exceptions.NoDataException;
import com.android.cesova.obd.exceptions.NonNumericResponseException;
import com.android.cesova.obd.exceptions.ObdResponseException;
import com.android.cesova.obd.exceptions.StoppedException;
import com.android.cesova.obd.exceptions.UnableToConnectException;
import com.android.cesova.obd.exceptions.UnknownObdErrorException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public abstract class ObdCommand {

    protected ArrayList<Integer> buffer = null;
    protected String cmd = null;
    protected boolean useImperialUnits = false;
    protected String rawData = null;

    private Class[] ERROR_CLASSES = {
            UnableToConnectException.class,
            BusInitException.class,
            MisunderstoodCommandException.class,
            NoDataException.class,
            StoppedException.class,
            UnknownObdErrorException.class
    };

    public ObdCommand(String command) {
        this.cmd = command;
        this.buffer = new ArrayList<Integer>();
    }

    private ObdCommand() {
    }

    public ObdCommand(ObdCommand other) {
        this(other.cmd);
    }

    public void run(InputStream in, OutputStream out) throws IOException,
            InterruptedException {
        sendCommand(out);
        readResult(in);
    }

    protected void sendCommand(OutputStream out) throws IOException,
            InterruptedException {
        out.write((cmd + "\r").getBytes());
        out.flush();
        Thread.sleep(200);
    }

    protected void resendCommand(OutputStream out) throws IOException,
            InterruptedException {
        out.write("\r".getBytes());
        out.flush();
    }

    protected void readResult(InputStream in) throws IOException {
        readRawData(in);
        checkForErrors();
        fillBuffer();
        performCalculations();
    }

    protected abstract void performCalculations();

    protected void fillBuffer() {
        rawData = rawData.replaceAll("\\s", "");

        if (!rawData.matches("([0-9A-F]{2})+")) {
            throw new NonNumericResponseException(rawData);
        }

        // read string each two chars
        buffer.clear();
        int begin = 0;
        int end = 2;
        while (end <= rawData.length()) {
            buffer.add(Integer.decode("0x" + rawData.substring(begin, end)));
            begin = end;
            end += 2;
        }
    }

    protected void readRawData(InputStream in) throws IOException {
        byte b = 0;
        StringBuilder res = new StringBuilder();

        while ((char) (b = (byte) in.read()) != '>')
            res.append((char) b);
        rawData = res.toString().trim();
        rawData = rawData.substring(rawData.lastIndexOf(13) + 1);
    }

    void checkForErrors() {
        for (Class<? extends ObdResponseException> errorClass : ERROR_CLASSES) {
            ObdResponseException messageError;

            try {
                messageError = errorClass.newInstance();
                messageError.setCommand(this.cmd);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            if (messageError.isError(rawData)) {
                throw messageError;
            }
        }
    }

    public String getResult() {
        return rawData;
    }

    public abstract String getFormattedResult();

    protected ArrayList<Integer> getBuffer() {
        return buffer;
    }

    public boolean useImperialUnits() {
        return useImperialUnits;
    }

    public void useImperialUnits(boolean isImperial) {
        this.useImperialUnits = isImperial;
    }

    public abstract String getName();

}
