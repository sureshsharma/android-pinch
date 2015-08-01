# About #

Adds multi-touch zoom to; ImageViews, WebViews, and MapViews. Simply use the corresponding object in place of the default, and the "pinch" functionality will be usable. Also includes misc. Views which can be useful for development.

# News #

**June 9, 2010 - Project Update**

Hello everyone. I've been neglecting this project, as I'm putting all of my spare time into my _live_ video streaming application, **Mestream**. Once I finish the next release, sometime soon, I'll be able to focus on the bugs. Thank you for your patience.

**April 6, 2010 - New 0.8 Download!**

A new project jar is available for download. The updates in this jar include;

  * new PinchImageView w/ updated code (inspired by code for Android gallery)
  * updated bug in WebImageView, decoder no longer returns false for larger images


Check out the Wiki to see some examples of how the PinchImageView can be used.



**March 22, 2010 - Updated PinchImageView in project source**

I've committed a new version of _PinchImageView_ that is much better than the former. This version uses Matrices instead of animations to accomplish the resizing. This version also supports zooming, and can be translated (or scrolled). I've been using this version with a _GestureDetector_ in the main container, to give me; scroll, double-tap, and pinch-zoom capabilities. It takes a bit of logic to detect and delegate which _View_ receives the touch action, so I will post some code samples on how I accomplished this in the coming days. If you'd like to see this in action, check out an app of mine in the Android Market named _BackStep_.


**March 3, 2010 - New 0.6 Download!**

The image caching has been improved for the WebImageView, and a ConcurrentHashMap is in place with SoftReferences for Bitmaps. The image loader has also seen a bit of a change. Here is an example;

```
WebImageView img = (WebImageView) findViewById(R.id.main_pic);
img.setImageListener(new WebImageView.ImageListener() {
  public void onImageLoaded(WebImageView im, Bitmap bm, String url) {
       // do stuff with the loaded image
  }
});
```

The Bitmap object and url are now passed into the callback.

Also, the PinchImageView has been modified to work as a decorator as well! So, the PinchImageView can be used with the WebImageView. Here is an example of how that could work;

```
...
// create the WebImageView object from xml
WebImageView img = (WebImageView) findViewById(R.id.main_pic);
// fetches the image in a background thread
img.setImageFromURL("http://www.mysite.com/mypicture.jpg");
// enable pinch-zoom abilities on the image
new PinchImageView(img);
...
```

There is no default scrolling enabled for the PinchImageView, and that may be coming in a future release.

Thanks for looking!

-nick-


**Feb. 13 2010 - New Jar Download!**

I realized that the previous release was breaking compilations. I've updated the jar, as well as added new features to the WebImageView. I added a load listener, and caching for downloaded images. These are all optional, and disabled by default. The caching is done with a HasMap, which is good for small file sizes, and a minimal amount of files. For something more substantial, saving the files locally would be ideal.

example;

```
WebImageView img = (WebImageView) findViewById(R.id.main_pic);
img.setImageListener(new WebImageView.ImageListener() {
  public void onImageLoaded(WebImageView im) {
       // do stuff with the loaded image
  }
});
```


**Jan. 21 2010 - New Jar Download!**

An updated jar is in the Downloads section. This jar includes the PinchImageView, which is still unpolished, and also a WebImageView. The WebImageView loads an image from a url into the ImageView, all in the background without interfering with the UI thread. Here's an example;

```
...
WebImageView img = (WebImageView) findViewById(R.id.main_pic);
// fetches the image in a background thread
img.setImageFromURL("http://www.mysite.com/mypicture.jpg");
...
```

I have posted a jar containing the PinchMapView class, which extends a MapView. The ImageView and WebView are still in the works, and will be included in a forthcoming release. Check it out now at the [Downloads](http://code.google.com/p/android-pinch/downloads/list) page.

[Watch a preview of the PinchImageView!](http://www.youtube.com/watch?v=RImQm41djmI)

# Getting Started #

For more information on how to implement he pinch views, check out the [Wiki](Wiki.md).

## Quick Example ##

**XML Layout**
```
 <?xml version="1.0" encoding="utf-8"?>
 <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:isScrollContainer="true"
    >
    <com.nikkoaiello.mobile.android.PinchMapView
        android:id="@+id/map"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent"
        android:clickable="true"
        android:apiKey="YOUR_API_KEY"
    />
    
 </RelativeLayout>
```

**Activity Class**
```
  ...  

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
  
    ...
  }
```

The only thing left to do is run your activity.

### Questions? ###

send me an e-mail @ nikko.aiello@gmail.om