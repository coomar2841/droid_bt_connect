
package com.beanie.bluetooth.threads;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

public class AbsBTServer extends Thread {

    private BluetoothServerSocket serverSocket;

    private final static String NAME = "DROID_BT_CONNECT";

    private final static UUID APP_UUID = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");

    public AbsBTServer() {
        BluetoothServerSocket temp = null;

        try {
            temp = BluetoothAdapter.getDefaultAdapter().listenUsingRfcommWithServiceRecord(NAME,
                    APP_UUID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverSocket = temp;
    }

    @Override
    public void run() {
        BluetoothSocket socket = null;
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            if (socket != null) {
                // Do work to manage the connection (in a separate thread)
                // manageConnectedSocket(socket);
                // mmServerSocket.close();
                break;
            }
        }
    }

    public void cancel() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
