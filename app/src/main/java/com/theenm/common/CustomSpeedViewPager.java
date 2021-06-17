package com.theenm.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class CustomSpeedViewPager extends ViewPager {
    private CustomSpeedScroller mCustomSpeedScroller;

    public CustomSpeedViewPager(@NonNull Context context) {
        this(context, null);
    }

    public CustomSpeedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        try {
            Field scroller = ViewPager.class.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            mCustomSpeedScroller = new CustomSpeedScroller(getContext(), new DecelerateInterpolator());
            scroller.set(this, mCustomSpeedScroller);
        } catch (Exception ignored) {
        }
    }

    public void setScrollDuration(int duration) {
        mCustomSpeedScroller.setScrollDuration(duration);
    }

    private class CustomSpeedScroller extends Scroller {
        private int mScrollDuration = 300;

        public CustomSpeedScroller(Context context) {
            this(context, null);
        }

        public CustomSpeedScroller(Context context, Interpolator interpolator) {
            this(context, interpolator, false);
        }

        public CustomSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mScrollDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mScrollDuration);
        }

        public void setScrollDuration(int scrollDuration) {
            mScrollDuration = scrollDuration;
        }
    }
}
