package com.beanie.bluetooth;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class DiscoverDevicesActivity extends Activity implements
		OnItemClickListener {

	private final static String TAG = "DiscoverDevicesActivity";

	private BluetoothAdapter bluetoothAdapter;

	private Button buttonScan;

	private DevicesAdapter adapter;

	private final static int REQUEST_ENABLE_BT = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.discover_devices_activity);

		initializeUIComponents();
		startDiscovery();
	}

	private void initializeUIComponents() {
		buttonScan = (Button) findViewById(R.id.buttonScan);
		buttonScan.setEnabled(false);

		buttonScan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				scanForDevices();
			}
		});

		ListView listViewDevices = (ListView) findViewById(R.id.listViewDevices);
		adapter = new DevicesAdapter(this);
		listViewDevices.setAdapter(adapter);
		listViewDevices.setOnItemClickListener(this);
	}

	private void startDiscovery() {
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		if (bluetoothAdapter == null) {
			// Check if bluetooth is available
			showNoBluetoothAvailableMessage();
		} else {
			// Check if bluetooth is not enabled
			if (!bluetoothAdapter.isEnabled()) {
				showBluetoothDisabledMessage();
				startEnableBluetoothActivity();
			} else {
				showBluetoothEnabledMessage();
			}
		}
	}

	private void startEnableBluetoothActivity() {
		Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		startActivityForResult(intent, REQUEST_ENABLE_BT);
	}

	private void showNoBluetoothAvailableMessage() {
		Log.i(TAG, "Your device doesn't support bluetooth");
	}

	private void showBluetoothDisabledMessage() {
		Log.i(TAG, "Bluetooth is disabled");
	}

	private void showBluetoothEnabledMessage() {
		Log.i(TAG, "Bluetooth is enabled");
		buttonScan.setEnabled(true);
	}

	private void scanForDevices() {
		// Check already discovered devices
		Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
		for (BluetoothDevice device : devices) {
			adapter.addDevice(device);
		}
		adapter.notifyDataSetChanged();

		// Scan for new devices
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(devicesReceiver, filter);

		bluetoothAdapter.startDiscovery();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_ENABLE_BT) {
			if (resultCode == RESULT_OK) {
				showBluetoothEnabledMessage();
			} else {
				showBluetoothDisabledMessage();
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (bluetoothAdapter != null) {
			bluetoothAdapter.cancelDiscovery();
		}
		try {
			unregisterReceiver(devicesReceiver);
		} catch (Exception e) {
			// Do nothing
		}
	}

	private final BroadcastReceiver devicesReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Log.i(TAG, "BC Recieved action: " + action);
			// When discovery finds a device
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				Log.i(TAG, "Device found");

				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// Add the name and address to an array adapter to show in a
				// ListView
				adapter.addDevice(device);
				adapter.notifyDataSetChanged();
			}

		}
	};

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int postion,
			long index) {
		BluetoothDevice device = (BluetoothDevice) view.getTag();
		showDeviceDetailsActivity(device);
	}

	private void showDeviceDetailsActivity(BluetoothDevice device) {
		Intent intent = new Intent(this, DeviceDetailsActivity.class);
		intent.putExtra("device", device);
		startActivity(intent);
	}

}
