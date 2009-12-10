package com.nikkoaiello.mobile.android;

import com.google.android.maps.MapActivity;

import android.os.Bundle;

public class PinchMapActivity extends MapActivity {
	
	PinchMapView view;
	
	/** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapview);
        
        view = (PinchMapView) findViewById(R.id.map);
    }

	protected boolean isRouteDisplayed() {
		return false;
	}
}
