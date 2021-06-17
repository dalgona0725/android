package com.theenm.common.widget;

import com.theenm.android.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class RollingLayout extends RelativeLayout
{
    private Context mContext;
    
    private Animation inAnimation;
    private Animation outAnimation;
    
    private int index;
    
    private RollingCallback callback = null;
    
    public void setCallback(RollingCallback callback)
    {
        this.callback = callback;
    }
    
    public void setIndex(int index)
    {
        this.index = index;
    }
    
    public RollingLayout(Context context)
    {
        super(context);
        this.mContext = context;
        initView(context);
    }
    
    public RollingLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.mContext = context;
        initView(context);
    }
    
    public RollingLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView(context);
    }
    
    private void initView(final Context context)
    {
        inAnimation = (Animation) AnimationUtils.loadAnimation(mContext, R.anim.hot_item_r2l_enter);
        outAnimation = (Animation) AnimationUtils.loadAnimation(mContext, R.anim.hot_item_r2l_exit);
        
        this.setOnTouchListener(new RollingTouchListener(context) {
            @Override
            public void onSwipeRight()
            {
                if (callback != null) callback.onSwipeRightHotItem();
            }
            
            @Override
            public void onSwipeLeft()
            {
                if (callback != null) callback.onSwipeLeftHotItem();
            }
            
            @Override
            public void onSingleTap()
            {
                if (callback != null) callback.onClickHotItem(index);
            }
        });
    }
    
    public void show()
    {
        if (isVisible()) return;
        show(true);
    }
    
    public void show(boolean withAnimation)
    {
        if (withAnimation) this.startAnimation(inAnimation);
        this.setVisibility(View.VISIBLE);
    }
    
    public void hide()
    {
        if (!isVisible()) return;
        hide(true);
    }
    
    public void hide(boolean withAnimation)
    {
        if (withAnimation) this.startAnimation(outAnimation);
        this.setVisibility(View.GONE);
    }
    
    public boolean isVisible()
    {
        return (this.getVisibility() == View.VISIBLE);
    }
    
    public void overrideDefaultInAnimation(Animation inAnimation)
    {
        this.inAnimation = inAnimation;
    }
    
    public void overrideDefaultOutAnimation(Animation outAnimation)
    {
        this.outAnimation = outAnimation;
    }
}
