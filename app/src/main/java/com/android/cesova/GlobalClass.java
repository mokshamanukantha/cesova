package com.android.cesova;

import android.app.Application;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by mokshaDev on 3/21/2015.
 */
public class GlobalClass extends Application {
    static BluetoothSocket socket;

    public GlobalClass() {
    }

    public GlobalClass(BluetoothSocket underlyingSocket) {
        socket = underlyingSocket;
    }

    public InputStream getInputStream() {
        try {
            return socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public OutputStream getOutputStream() {
        try {
            return socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public BluetoothSocket getSocket() {
        return socket;
    }
}
