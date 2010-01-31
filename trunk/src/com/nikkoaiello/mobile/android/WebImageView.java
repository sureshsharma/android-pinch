package com.nikkoaiello.mobile.android;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

public class WebImageView extends ImageView {

	Handler mHandler = null;
	
	public WebImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		_init();
	}

	public WebImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		_init();
	}
	
	public WebImageView(Context context) {
		super(context);
		_init();
	}
	
	public void setImageFromURL(String imageUrl) {
		Bitmap bmp = getDrawingCache();
		if (bmp != null) {
			// use our cached bitmap
			setImageBitmap(bmp);
			return;
		}
		
		try {
			// create the URL object
			final URL url = new URL(imageUrl);
			new Thread() {
				public void run() {
					// obtain an input stream of the image
					InputStream is = null;
					try {
						is = (InputStream) url.getContent();
					} 
					catch (IOException e) {}
					
					final InputStream imgStream = is;
					WebImageView.this.mHandler.post(new Runnable() {
						public void run() {
							// set the image within the UI thread
							setImageDrawable(Drawable.createFromStream(imgStream, "src"));
							setDrawingCacheEnabled(true);
						}
					});
				}
			}.start();
		} 
		catch (MalformedURLException e) {}
	}
	
	private void _init() {
		mHandler = new Handler();
		// set an image as a place holder for the view
		//setImageResource();
	}
}
