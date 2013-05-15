package com.space150.android.glass.verbosesensordisplay;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener, LocationListener {

	private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private float[] mLastValues = new float[] { 0.0f, 0.0f, 0.0f };
    private float mLowPassFilter = 0.09f;
    
    private LocationManager mLocationManager;
    private String mLocationProvider;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(true);
        criteria.setBearingRequired(true);
        mLocationProvider = mLocationManager.getBestProvider(new Criteria(), false);
        Location location = mLocationManager.getLastKnownLocation(mLocationProvider);
        if (location != null)
        	onLocationChanged(location);
	}
	
	protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        mLocationManager.requestLocationUpdates(mLocationProvider, 400, 1, this);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        mLocationManager.removeUpdates(this);
    }
    
    // sensor listener

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSensorChanged(SensorEvent event) 
	{
		TextView aXView = (TextView)findViewById(R.id.accelX);
		aXView.setText("X: " + event.values[0]);
		
		TextView aYView = (TextView)findViewById(R.id.accelY);
		aYView.setText("Y: " + event.values[1]);
		
		TextView aZView = (TextView)findViewById(R.id.accelZ);
		aZView.setText("Z: " + event.values[2]);
		
		float diffX = mLastValues[0]-event.values[0];
		if ( diffX < mLowPassFilter )
			diffX = 0.0f;
		TextView diffXView = (TextView)findViewById(R.id.diffX);
		diffXView.setText("Diff X: " + diffX);
		
		float diffY = mLastValues[1]-event.values[1];
		if ( diffY < mLowPassFilter )
			diffY = 0.0f;
		TextView diffYView = (TextView)findViewById(R.id.diffY);
		diffYView.setText("Diff Y: " + diffY);
		
		float diffZ = mLastValues[2]-event.values[2];
		if ( diffZ < mLowPassFilter )
			diffZ = 0.0f;
		TextView diffZView = (TextView)findViewById(R.id.diffZ);
		diffZView.setText("Diff Z: " + diffZ);
		
		mLastValues[0] = event.values[0];
		mLastValues[1] = event.values[1];
		mLastValues[2] = event.values[2];
	}
	
	// location listener

	@Override
	public void onLocationChanged(Location location) 
	{
		TextView latView = (TextView)findViewById(R.id.geoLat);
		latView.setText("Lat: " + Double.toString(location.getLatitude()));
		
		TextView longView = (TextView)findViewById(R.id.geoLong);
		longView.setText("Long: " + Double.toString(location.getLongitude()));
		
		TextView altView = (TextView)findViewById(R.id.geoAlt);
		altView.setText("Alt: " + Double.toString(location.getAltitude()));
		
		TextView bearView = (TextView)findViewById(R.id.geoBear);
		bearView.setText("Bearing: " + Double.toString(location.getBearing()));
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "Disabled provider " + provider, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "Enabled new provider " + provider, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
