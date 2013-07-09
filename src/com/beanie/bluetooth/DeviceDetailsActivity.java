
package com.beanie.bluetooth;

import com.beanie.bluetooth.threads.AbsBTServer;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DeviceDetailsActivity extends Activity {

    private BluetoothDevice device;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_details_activity);

        if (getIntent().getExtras() != null) {
            device = (BluetoothDevice) getIntent().getExtras().get("device");
        }

        initializeUIElements();
    }

    private void initializeUIElements() {
        TextView textViewDeviceName = (TextView) findViewById(R.id.textViewDeviceName);
        textViewDeviceName.setText(device.getName());

        ImageView imageViewPairStatus = (ImageView) findViewById(R.id.imageViewPairStatus);
        switch (device.getBondState()) {
            case BluetoothDevice.BOND_NONE:
                imageViewPairStatus.setImageResource(R.drawable.bluetooth_notpaired);
                break;
            case BluetoothDevice.BOND_BONDED:
                imageViewPairStatus.setImageResource(R.drawable.bluetooth_paired);
                break;
            case BluetoothDevice.BOND_BONDING:
                imageViewPairStatus.setImageResource(R.drawable.bluetooth_bonding);
                break;
        }

        TextView textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        switch (device.getBondState()) {
            case BluetoothDevice.BOND_BONDED:
                textViewStatus.setText(R.string.label_status_bonded);
                break;
            case BluetoothDevice.BOND_BONDING:
                textViewStatus.setText(R.string.label_status_bonding);
                break;
            case BluetoothDevice.BOND_NONE:
                textViewStatus.setText(R.string.label_status_not_bonded);
                break;
        }

        TextView textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        textViewAddress.setText(getResources().getString(R.string.label_device_address) + " "
                + device.getAddress());

        TextView textViewClass = (TextView) findViewById(R.id.textViewClass);
        String deviceClass = getResources().getString(R.string.label_device_class);
        textViewClass.setText(deviceClass + " : "
                + Integer.toString(device.getBluetoothClass().getMajorDeviceClass()));

        Button buttonPair = (Button) findViewById(R.id.buttonPair);
        buttonPair.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                pair();
            }
        });
    }

    private void pair() {
        AbsBTServer server = new AbsBTServer();
        server.start();
    }
}
