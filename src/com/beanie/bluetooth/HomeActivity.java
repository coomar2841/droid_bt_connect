package com.beanie.bluetooth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);

		initializeUIComponents();
	}

	private void initializeUIComponents() {
		Button buttonDiscoverDevices = (Button) findViewById(R.id.buttonDiscoverDevices);
		buttonDiscoverDevices.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startDiscoverDevicesActivity();
			}
		});
	}

	private void startDiscoverDevicesActivity() {
		Intent intent = new Intent(this, DiscoverDevicesActivity.class);
		startActivity(intent);
	}
}