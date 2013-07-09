package com.beanie.bluetooth.adapters;

import java.util.ArrayList;

import com.beanie.bluetooth.R;
import com.beanie.bluetooth.R.drawable;
import com.beanie.bluetooth.R.id;
import com.beanie.bluetooth.R.layout;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

		ImageView imageViewPairStatus = (ImageView) convertView
				.findViewById(R.id.imageViewPairStatus);
		switch (device.getBondState()) {
		case BluetoothDevice.BOND_NONE:
			imageViewPairStatus
					.setImageResource(R.drawable.bluetooth_notpaired);
			break;
		case BluetoothDevice.BOND_BONDED:
			imageViewPairStatus.setImageResource(R.drawable.bluetooth_paired);
			break;
		case BluetoothDevice.BOND_BONDING:
			imageViewPairStatus.setImageResource(R.drawable.bluetooth_bonding);
			break;
		}

		textViewDeviceName.setText(device.getName());
		
		convertView.setTag(device);
		return convertView;
	}

	public void addDevice(BluetoothDevice device) {
		int index = -1;
		int count = 0;
		for (BluetoothDevice temp : devices) {
			if (temp.getAddress().equals(device.getAddress())) {
				index = count;
			}
			count++;
		}
		if (index != -1) {
			devices.remove(index);
		}
		devices.add(device);
	}

}
