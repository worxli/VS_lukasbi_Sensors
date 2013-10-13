package ch.ethz.vs_lukasbi_sensors;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;

public class SensorActivity extends Activity implements SensorEventListener {
	
	protected Sensor sensor;
	protected SensorManager sensorManager;
	protected SensorAdapter sensorAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sensor);
		// Show the Up button in the action bar.
		setupActionBar();
		
		// Get sensor manager
		this.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		
		// Find the sensor that was passed in as argument
		int sensorHash = this.getIntent().getExtras().getInt("sensor");
		SensorManager manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		for (Sensor s : manager.getSensorList(Sensor.TYPE_ALL)) {
			if (s.hashCode() == sensorHash)
				this.sensor = s;
		}
		
		// Set sensor name and type
		TextView nameTextView = (TextView) findViewById(R.id.sensor_name);
		TextView typeTextView = (TextView) findViewById(R.id.sensor_type);
		
		nameTextView.setText(this.sensor.getName());
		typeTextView.setText(typeAsString(this.sensor.getType()));
		
		// Init sensor adapter
		this.sensorAdapter = new SensorAdapter(this, this.sensor);
		
		// Hookup adapter and list view
		ListView raw_data_list = (ListView) findViewById(R.id.raw_data_list);
		raw_data_list.setAdapter(sensorAdapter);
	}
	
	@Override
	protected void onResume () {
		super.onResume();
		// Register for sensor events
		this.sensorManager.registerListener(this, this.sensor, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	@Override
	protected void onPause () {
		super.onPause();
		// Unregister from sensor events
		this.sensorManager.unregisterListener(this);
	}
	
	@Override
	public void onAccuracyChanged(Sensor s, int accuracy) {
		TextView accuracyTextView = (TextView) findViewById(R.id.sensor_accuracy);
		
		String accuracyString = null;
		if (accuracy == SensorManager.SENSOR_STATUS_ACCURACY_HIGH)
			accuracyString = "High";
		else if (accuracy == SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM)
			accuracyString = "Medium";
		else if (accuracy == SensorManager.SENSOR_STATUS_ACCURACY_LOW)
			accuracyString = "Low";
		else if (accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
			accuracyString = "Unreliable!";
			
		accuracyTextView.setText(accuracyString);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// Set new values
		this.sensorAdapter.setValues(event.values);
		this.sensorAdapter.notifyDataSetChanged();
	}
	
	protected String typeAsString(int type) {
		String text = null;
		if (type == Sensor.TYPE_ACCELEROMETER)
			text = "Accelerometer";
		else if (type == Sensor.TYPE_AMBIENT_TEMPERATURE)
			text = "Ambient temperature";
		else if (type == Sensor.TYPE_GAME_ROTATION_VECTOR)
			text = "Game rotation vector";
		else if (type == Sensor.TYPE_GRAVITY)
			text = "Gravity";
		else if (type == Sensor.TYPE_GYROSCOPE || type == Sensor.TYPE_GYROSCOPE_UNCALIBRATED)
			text = "Gyroscope";
		else if (type == Sensor.TYPE_LIGHT)
			text = "Light";
		else if (type == Sensor.TYPE_LINEAR_ACCELERATION)
			text = "Linear acceleration";
		else if (type == Sensor.TYPE_MAGNETIC_FIELD || type == Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED)
			text = "Magnetic field";
		else if (type == Sensor.TYPE_PRESSURE)
			text = "Pressure";
		else if (type == Sensor.TYPE_PROXIMITY)
			text = "Proximity";
		else if (type == Sensor.TYPE_RELATIVE_HUMIDITY)
			text = "Relative humidity";
		else if (type == Sensor.TYPE_ROTATION_VECTOR)
			text = "Rotation";
		else if (type == Sensor.TYPE_SIGNIFICANT_MOTION)
			text = "Significant motion";
		else if (type == Sensor.TYPE_ORIENTATION)
			text = "Orientation [DEPRECATED]";
		else if (type == Sensor.TYPE_TEMPERATURE)
			text = "Temperature [DEPRECATED]";
		
		return text;
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sensor, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
