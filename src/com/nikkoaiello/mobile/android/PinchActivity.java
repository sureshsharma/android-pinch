package com.nikkoaiello.mobile.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PinchActivity extends Activity {

	Button imageButton, webButton, mapButton;
	
	/** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        imageButton = (Button) findViewById(R.id.launchImage);
        imageButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(PinchActivity.this, PinchImageActivity.class));
			}
        });
        
        webButton = (Button) findViewById(R.id.launchWeb);
        webButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(PinchActivity.this, PinchWebActivity.class));
			}
        });
        
        mapButton = (Button) findViewById(R.id.launchMap);
        mapButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(PinchActivity.this, PinchMapActivity.class));
			}
        }); 
        
        
        //startActivity(new Intent("android.intent.action.MAIN"));
    }

}
