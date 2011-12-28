package com.beanie.bluetooth;

import java.util.ArrayList;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DevicesAdapter extends BaseAdapter {

	private ArrayList<BluetoothDevice> devices;
	private Context context;

	public DevicesAdapter(Context context) {
		this.context = context;
		this.devices = new ArrayList<BluetoothDevice>();
	}

	@Override
	public int getCount() {
		return devices.size();
	}

	@Override
	public Object getItem(int position) {
		return devices.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BluetoothDevice device = (BluetoothDevice) getItem(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.device_adapter, null);
		}

		TextView textViewDeviceName = (TextView) convertView
				.findViewById(R.id.textViewDeviceName);

		textViewDeviceName.setText(device.getName());
		return convertView;
	}

	public void addDevice(BluetoothDevice device) {
		devices.add(device);
	}

}
