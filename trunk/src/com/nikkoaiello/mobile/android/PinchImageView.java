package com.nikkoaiello.mobile.android;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Scroller;

public class PinchImageView extends ImageView  {
	
	public static final int GROW = 0;
	public static final int SHRINK = 1;
	
	public static final int DURATION = 100;
	public static final int TOUCH_INTERVAL = 100;
	
	public static final float MIN_SCALE = 0.5f;
	public static final float MAX_SCALE = 2.75f;
	public static final float ZOOM = 0.25f;
	
	private static int _interpolator = android.R.anim.accelerate_interpolator;
	
	float xCur, yCur, 
		xPre, yPre,
		xSec, ySec,
		distDelta,
		distCur, distPre,
		xScale = 1.0f, yScale = 1.0f;
	int mTouchSlop;
	long mLastGestureTime;
	Paint mPaint;
	//Scroller mScroller;
	
	public PinchImageView(Context context,AttributeSet attr) {
         super(context,attr);
         _init();
    }
	
	public PinchImageView(Context context) {
        super(context);        
        _init();
   }

	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction() & MotionEvent.ACTION_MASK, 
			p_count = event.getPointerCount();
	
	    switch (action) {
	    case MotionEvent.ACTION_MOVE:
	    	
	    	// point 1 coords
    		xCur = event.getX(0);
    		yCur = event.getY(0);
    		
	    	if (p_count > 1) {
	    		// point 2 coords
	    		xSec = event.getX(1);
	    		ySec = event.getY(1);
	    		
	    		// distance between
	    		distCur = (float) Math.sqrt(Math.pow(xSec - xCur, 2) + Math.pow(ySec - yCur, 2));
				distDelta = distCur - distPre;
	    		
				//float rate = ZOOM;
				float rate = ZOOM * (Math.abs(distDelta) > 100 ? 2 : 1);
		    	long now = android.os.SystemClock.uptimeMillis();
		    	if (now - mLastGestureTime > TOUCH_INTERVAL && Math.abs(distDelta) > mTouchSlop) {
		    		mLastGestureTime = 0;
		    		
		    		ScaleAnimation scale = null;
	    			int mode = distDelta > 0 ? GROW : (distCur == distPre ? 2 : SHRINK);
		    		switch (mode) {
		    		case GROW: // grow
		    			if (xScale < MAX_SCALE) {
		    				scale = new ScaleAnimation(xScale, 
		    						xScale += rate, 
		    						yScale, 
		    						yScale += rate, 
		    						ScaleAnimation.RELATIVE_TO_SELF, 
		    						0.5f, 
		    						ScaleAnimation.RELATIVE_TO_SELF, 
		    						0.5f);
		    			}
		    		break;
		    		case SHRINK: // shrink
		    			if (xScale > MIN_SCALE) {
		    				scale = new ScaleAnimation(xScale, 
		    						xScale -= rate, 
		    						yScale, 
		    						yScale -= rate, 
		    						ScaleAnimation.RELATIVE_TO_SELF, 
		    						0.5f, 
		    						ScaleAnimation.RELATIVE_TO_SELF, 
		    						0.5f);
		    			}
		    		break;
		    		}
		    		
		    		if (scale != null) {
			            scale.setDuration(DURATION);
			            scale.setFillAfter(true);
			            scale.setInterpolator(getContext(), _interpolator);
			            startAnimation(scale);
		    		}
		    		
		    		mLastGestureTime = now;
	    		}
		    	
		    	xPre = xCur;
		    	yPre = yCur;
				distPre = distCur;
	    	}
	    break;
	    case MotionEvent.ACTION_POINTER_1_DOWN:
	    	// point 1 coords
    		xCur = xPre = event.getX(0);
    		yCur = yPre = event.getY(0);
    		mLastGestureTime = android.os.SystemClock.uptimeMillis();
	    break;
	    }
	    return true;
	}
	
	private void _init() {
		mTouchSlop = ViewConfiguration.getTouchSlop();
		mPaint = new Paint();
        mPaint.setAntiAlias(true);
        //mScroller = new Scroller(getContext());
	}

}
