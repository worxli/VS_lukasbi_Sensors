package ch.ethz.vs_lukasbi_sensors;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ToggleButton;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class ActuatorsActivity extends Activity implements OnSeekBarChangeListener {
	
	private MediaPlayer mp;
	private int durationValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actuators);
		// Show the Up button in the action bar.
		setupActionBar();
		
		this.durationValue = 10;
		SeekBar duration = (SeekBar) findViewById(R.id.seekBar1);
		duration.setOnSeekBarChangeListener(this);
		duration.setProgress(this.durationValue);
	}
	
	public void onVibrateClicked(View v) {
		Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		vibrator.vibrate(this.durationValue * 10);
	}
	
	public void onSoundClicked(View v) {
		// Is the toggle on?
	    boolean on = ((ToggleButton) v).isChecked();
	    
	    if (on) {
	        // Play sound
	    	this.playSound();
	    } else {
	        // Stop sound
	    	this.stopSound();
	    }
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
		getMenuInflater().inflate(R.menu.actuators, menu);
		return true;
	}
	
	public void playSound () {
		// start playing the alarm file
		this.mp = MediaPlayer.create(this, R.raw.bells);
		mp.setLooping(true);
		mp.setVolume(1.0f, 1.0f);
		mp.start();
	}
	
	public void stopSound () {
		mp.stop();
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

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		this.durationValue = progress;
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

}
