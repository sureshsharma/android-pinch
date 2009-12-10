package com.nikkoaiello.mobile.android;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class PinchImageView extends ImageView  {
	
	public static final int GROW = 0;
	public static final int SHRINK = 1;
	public static final int DURATION = 150;
	
	public static final float MIN_SCALE = 0.5f;
	public static final float MAX_SCALE = 2.5f;
	public static final float ZOOM = 0.1f;
	
	protected static float x1, 
		x2, 
		y1, 
		y2, 
		x1_pre,
		y1_pre,
		dist_delta = 0,
		dist_curr = -1, 
		dist_pre = -1,
		x_scale = 1.0f,
		y_scale = 1.0f;
	private long mLastGestureTime;
	private Paint mPaint;
	
	public PinchImageView(Context context,AttributeSet attr) {
         super(context,attr);
         mPaint = new Paint();
         mPaint.setAntiAlias(true);
    }
	
	public PinchImageView(Context context) {
        super(context);        
   }

	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction() & MotionEvent.ACTION_MASK, 
			p_count = event.getPointerCount();
	
	    switch (action) {
	    case MotionEvent.ACTION_MOVE:
	    	int interpolator = android.R.anim.accelerate_interpolator;
	    	
	    	// point 1 coords
    		x1 = event.getX(0);
    		y1 = event.getY(0);
    		
	    	if (p_count > 1) {
	    		// point 2 coords
	    		x2 = event.getX(1);
	    		y2 = event.getY(1);
	    		
	    		// distance between
	    		dist_curr = (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
				dist_delta = dist_curr - dist_pre;
	    		
		    	long now = android.os.SystemClock.uptimeMillis();
		    	if (now - mLastGestureTime > 100/* && Math.abs(dist_delta) > 10*/) {
		    		mLastGestureTime = 0;
		    		
		    		ScaleAnimation scale = null;
	    			int mode = dist_delta > 0 ? GROW : (dist_curr == dist_pre ? 2 : SHRINK);
		    		switch (mode) {
		    		case GROW: // grow
		    			if (x_scale < MAX_SCALE) {
		    				scale = new ScaleAnimation(x_scale, 
		    						x_scale += ZOOM, 
		    						y_scale, 
		    						y_scale += ZOOM, 
		    						ScaleAnimation.RELATIVE_TO_SELF, 
		    						0.5f, 
		    						ScaleAnimation.RELATIVE_TO_SELF, 
		    						0.5f);
		    			}
		    		break;
		    		case SHRINK: // shrink
		    			if (x_scale > MIN_SCALE) {
		    				scale = new ScaleAnimation(x_scale, 
		    						x_scale -= ZOOM, 
		    						y_scale, 
		    						y_scale -= ZOOM, 
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
			            scale.setInterpolator(getContext(), interpolator);
			            startAnimation(scale);
		    		}
		    		
		    		mLastGestureTime = now;
	    		}
		    	
		    	x1_pre = x1;
		    	y1_pre = y1;
				dist_pre = dist_curr;
	    	}
	    	// drag
	    	else {
	    		/*
	    		int dist_x = Math.round(x1_pre - x1),
	    			dist_y = Math.round(y1_pre - y1);
	    		layout(getLeft() - dist_x, getTop() - dist_y, getRight() - dist_x, getBottom() - dist_y);
	    		
	    		x1_pre = x1;
	    		y1_pre = y2;
	    		*/
	    	}
	    break;
	    case MotionEvent.ACTION_POINTER_1_DOWN:
	    	// point 1 coords
    		x1_pre = event.getX(0);
    		y1_pre = event.getY(0);
    		mLastGestureTime = android.os.SystemClock.uptimeMillis();
	    break;
	    }
	    return true;
	}

}
