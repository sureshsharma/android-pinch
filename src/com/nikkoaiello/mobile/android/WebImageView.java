package com.nikkoaiello.mobile.android;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

public class WebImageView extends ImageView {
	
	/**
	 * Caches web images. This solution is only appropriate for a minimal amount of images.
	 * For larger images, the files should be cached locally.
	 */
	static Map<String,Drawable> imageCache = new HashMap<String,Drawable>();
	
	Handler mHandler = null;
	ImageListener listener = null;
	boolean cache = false;
	
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
	
	public void setCache(boolean cache) {
		this.cache = cache;
	}
	
	public void setImageListener(ImageListener listener) {
		this.listener = listener;
	}
	
	public void setImageFromURL(String imageUrl) {
		
		if (cache) {
			Drawable d = imageCache.get(imageUrl);
			if (d != null) {
				// use our cached bitmap
				setImageDrawable(d);
				return;
			}
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
					final Drawable d = Drawable.createFromStream(imgStream, "src");
					WebImageView.this.mHandler.post(new Runnable() {
						public void run() {
							// cache the image
							if (WebImageView.this.cache) {
								WebImageView.imageCache.put(url.toExternalForm(), d);
							}
							// set the image within the UI thread
							WebImageView.this.setImageDrawable(d);
							// call our listener
							if (WebImageView.this.listener != null) {
								WebImageView.this.listener.onImageLoaded(WebImageView.this);
							}
						}
					});
				}
			}.start();
		} 
		catch (MalformedURLException e) {}
	}
	
	private void _init() {
		mHandler = new Handler();
	}
	
	// ---------- Image Load Listener ---------- //
	public static interface ImageListener {
		public void onImageLoaded(WebImageView im);
	}
}
