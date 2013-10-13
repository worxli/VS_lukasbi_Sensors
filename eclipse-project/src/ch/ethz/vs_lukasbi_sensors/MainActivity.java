package ch.ethz.vs_lukasbi_sensors;

import java.util.ArrayList;
import java.util.List;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	final String MAIN_TAG = "### MAIN ###";
	
	private SensorManager sensorManager;
	private List<Sensor> deviceSensors;
	private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Get a list of all device sensors and display them
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        this.listView = (ListView) findViewById(R.id.listView1);
        
        List<String> sensorNames = new ArrayList<String>();
        for (Sensor sens : deviceSensors) {
        	sensorNames.add(sens.getName());
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.sensor_list_item, sensorNames);
        this.listView.setAdapter(adapter);
        
        this.listView.setClickable(true);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	
        	@Override
            public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
                                    long id) {
        		showDetails(deviceSensors.get(position));
            }
       });
    }
    
    private void showDetails(Sensor s) {
    	Intent intent = new Intent(this, SensorActivity.class);
    	intent.putExtra("sensor", s.hashCode());
    	this.startActivity(intent);
    }
    
    public void showActuators(View v) {
    	Intent intent = new Intent(this, ActuatorsActivity.class); 
    	this.startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
