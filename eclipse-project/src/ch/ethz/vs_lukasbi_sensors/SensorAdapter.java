package ch.ethz.vs_lukasbi_sensors;

import java.util.ArrayList;

import android.content.Context;
import android.hardware.Sensor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SensorAdapter extends BaseAdapter {
	
	protected Sensor sensor;
	private float[] items;
    private LayoutInflater mInflater;
	
	public SensorAdapter(Context context, Sensor s) {
		//Cache a reference to avoid looking it up on every getView() call
		this.mInflater = LayoutInflater.from(context); 
		this.sensor = s;
		this.items = null;
	}
	
	public void setValues(float[] val) {
		this.items = val;
	}
	
	@Override
	public View getView (int position, View convertView, ViewGroup parent) {
		//If there's no recycled view, inflate one and tag each of the views
        //you'll want to modify later
        if (convertView == null) {
            convertView = mInflater.inflate (R.layout.raw_data_item, parent, false);

            //This assumes layout/row_left.xml includes a TextView with an id of "textview"
            convertView.setTag (R.id.raw_data_label, convertView.findViewById(R.id.raw_data_label));
            convertView.setTag (R.id.raw_data_value, convertView.findViewById(R.id.raw_data_value));
        }

        //Retrieve the tagged view, get the item for that position, and
        //update the text
        TextView labelView = (TextView) convertView.getTag(R.id.raw_data_label);
        String textItem = getLabelForItem(position);
        labelView.setText(textItem);
        
        TextView valueView = (TextView) convertView.getTag(R.id.raw_data_value);
        String textValue =  Float.toString(this.items[position]);
        valueView.setText(textValue);
        
        return convertView;
	}
	
	private String getLabelForItem(int position) {
		String label = "LABEL";
		
		if (sensor.getType() == Sensor.TYPE_GRAVITY || sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION || sensor.getType() == Sensor.TYPE_ACCELEROMETER || sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD || sensor.getType() == Sensor.TYPE_GYROSCOPE || sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
			switch (position) {
			case 0:
				label = "X-Axis";
				break;
			case 1:
				label = "Y-Axis";
				break;
			case 2:
				label = "Z-Axis";
				break;
			}
			
		} else if (sensor.getType() == Sensor.TYPE_ORIENTATION) {
			switch (position) {
			case 0:
				label = "Z-Axis";
				break;
			case 1:
				label = "X-Axis";
				break;
			case 2:
				label = "Y-Axis";
				break;
			}
		}else if (sensor.getType() == Sensor.TYPE_PROXIMITY)
			label = "Distance";
		else if (sensor.getType() == Sensor.TYPE_LIGHT)
			label = "Light-intensity";
		else if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE)
			label = "Temperature";
		else if (sensor.getType() == Sensor.TYPE_PRESSURE)
			label = "Pressure";
		else if (sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY)
			label = "Humidity (relative)";
		else if (sensor.getType() == Sensor.TYPE_TEMPERATURE)
			label = "Temperature";
		
		return label;
	}

	@Override
	public int getCount() {
		if (this.items == null)
			return 0;
	
		if (sensor.getType() == Sensor.TYPE_ROTATION_VECTOR || sensor.getType() == Sensor.TYPE_ACCELEROMETER || sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD || sensor.getType() == Sensor.TYPE_GYROSCOPE || sensor.getType() == Sensor.TYPE_GRAVITY || sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION || sensor.getType() == Sensor.TYPE_ORIENTATION)
			return 3;
		else
			return 1;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
