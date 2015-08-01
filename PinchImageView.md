# Introduction #
Here are a couple techniques for achieving some popular gestures with the newer PinchImageView object.


# Details #

This snippet shows how _double-tap_ zoom, and _scroll_ functionality can be added to your ImageView. The GestureDetector is located in the PinchImageView's parent activity, and touch events are delegated to it via the Activity's dispachTouchEvent method.

```
mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
     // double tap zoom
     public boolean onDoubleTap(MotionEvent e) {
	if (mPinchImageView.getScale() > 1f) {
	    mPinchImageView.zoomTo(1f);
	}
	else {
	    // MAX_SCALE is a float constant defined in the Activity
	    mPinchImageView.zoomTo(MAX_SCALE);
	}
			
	return true;
     }
     // image scrolling
     public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
	if (mPinchImageView.getScale() > 1f) {
            mPinchImageView.postTranslate(-distanceX, -distanceY);
            return true;
        }
        return false;
     }
});
```

Since the parent Activity, or other Views, can sometimes consume all of the touch events, it may be good practice to explicitly call them from the dispachTouchEvent method. Here's a quick example;

```
....
// if the gesture detector didn't handle it, check for another condition
if (!mGestureDetector.onTouchEvent(event)) {
    // have the PinchImageView handle 2 fingers/pointers for pinch gestures
    if (event.getPointerCount() == 2) {
        return mPinchImageView.onTouchEvent(event);
    }
}
....
```