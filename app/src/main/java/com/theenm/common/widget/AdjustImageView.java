package com.theenm.common.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewParent;
//import android.widget.ImageView;

/**
 * Created by Techno Blogger on 19/12/16.
 * https://www.linkedin.com/pulse/adjustviewbounds-behaviors-below-api-level-17-aman-shekhar
 * https://github.com/nuuneoi/AdjustableImageView
 *
 * ImageView >> android:adjustViewBounds : 이미지의 가로,세로의 비율을 맞추기 위해 이비지뷰의 크기를 조정할 것인가를 지정합니다.
 * 이 속성에는 한가지 문제가 있는데, 바로 SDK 버전 17(4.2) 미만일 경우 이미지가 이미지 뷰보다 클 때에만 작동하는 속성이다.
 * 그렇기 때문에 SDK 17 미만 버전에서 완전히 작동하게 하려면 이미지 영역을 직접 계산해야 한다.
 *
 * [Usage]
 
    <com.theenm.common.widget.AdjustImageView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:adjustViewBounds="true"
        android:src="@mipmap/ic_launcher" />
 *
 */
public class AdjustImageView extends androidx.appcompat.widget.AppCompatImageView
{

    private boolean mAdjustViewBounds;

    public AdjustImageView(Context context) {
        super(context);
    }

    public AdjustImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdjustImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setAdjustViewBounds(boolean adjustViewBounds) {
        mAdjustViewBounds = adjustViewBounds;
        super.setAdjustViewBounds(adjustViewBounds);
    }
	
	@Override
    public boolean getAdjustViewBounds() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return super.getAdjustViewBounds();
        } else {
            return mAdjustViewBounds;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable mDrawable = getDrawable();
        if (mDrawable == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        if (mAdjustViewBounds) {
            int mDrawableWidth = mDrawable.getIntrinsicWidth();
            int mDrawableHeight = mDrawable.getIntrinsicHeight();
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);

            if (heightMode == MeasureSpec.EXACTLY && widthMode != MeasureSpec.EXACTLY) {
                // Fixed Height & Adjustable Width
                int height = heightSize;
                int width = height * mDrawableWidth / mDrawableHeight;
                if (isInScrollingContainer()) {
                    setMeasuredDimension(width, height);
                } else {
                    setMeasuredDimension(Math.min(width, widthSize), Math.min(height, heightSize));
                }
            } else if( widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY ) {
                // Fixed Width & Adjustable Height
                int width = widthSize;
                int height = width * mDrawableHeight / mDrawableWidth;
                if (isInScrollingContainer()) {
                    setMeasuredDimension(width, height);
                } else {
                    setMeasuredDimension(Math.min(width, widthSize), Math.min(height, heightSize));
                }
            } else {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private boolean isInScrollingContainer() {
        ViewParent p = getParent();
        while ((p != null) && (p instanceof ViewGroup)) {
            if (((ViewGroup) p).shouldDelayChildPressedState()) {
                return true;
            }
            p = p.getParent();
        }
        return false;
    }

}
