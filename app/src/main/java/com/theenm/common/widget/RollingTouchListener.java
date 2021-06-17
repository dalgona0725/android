package com.theenm.common.widget;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class RollingTouchListener extends SimpleOnGestureListener
        implements OnTouchListener
{
    private final String TAG = RollingTouchListener.class.getName();
    
    private static final int SWIPE_DISTANCE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    
    private final GestureDetector gestureDetector;
    
    public RollingTouchListener(Context context)
    {
        gestureDetector = new GestureDetector(context, this);
    }
    
    public void onSwipeLeft()
    {
    }
    
    public void onSwipeRight()
    {
    }
    
    public void onSingleTap()
    {
    }
    
    public boolean onTouch(View v, MotionEvent event)
    {
        v.getParent().requestDisallowInterceptTouchEvent(true);
       
        return gestureDetector.onTouchEvent(event);
    }
    
    @Override
    public boolean onDown(MotionEvent e)
    {
        return true;
    }
    
    @Override
    public boolean onSingleTapUp(MotionEvent e)
    {
        onSingleTap();
        return super.onSingleTapUp(e);
    }
    
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
        float distanceX = e2.getX() - e1.getX();
        float distanceY = e2.getY() - e1.getY();
        if (Math.abs(distanceX) > Math.abs(distanceY)
                && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD
                && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD)
        {
            if (distanceX > 0)
                onSwipeRight();
            else
                onSwipeLeft();
            return true;
        }
        return false;
    }
}