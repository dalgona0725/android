package com.theenm.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class CircleAnimIndicator extends LinearLayout {

    private Context mContext;

    //원 사이의 간격
    private int itemMargin = 10;

    //애니메이션 시간
    private int animDuration = 500;

    private int mDefaultCircle;
    private int mSelectCircle;
    private ArrayList<Integer> mSelectCircleList;
    private ImageView[] imageDot;

    public void setAnimDuration(int animDuration) {
        this.animDuration = animDuration;
    }

    public void setItemMargin(int itemMargin) {
        this.itemMargin = itemMargin;
    }

    public CircleAnimIndicator(Context context) {
        super(context);

        mContext = context;
    }

    public CircleAnimIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
    }


    /**
     * 기본 점 생성
     *  @param count         점의 갯수
     * @param defaultCircle 점의 이미지
     * @param selectCircle
     */
    public void createAppInfoPanel(int count, int defaultCircle, ArrayList<Integer> selectCircle) {

        mDefaultCircle = defaultCircle;
        mSelectCircleList = selectCircle;

        imageDot = new ImageView[count];
        this.removeAllViews();
        for (int i = 0; i < count; i++) {

            imageDot[i] = new ImageView(mContext);
            LayoutParams params = new LayoutParams(20, 20);
            params.topMargin = itemMargin;
            params.bottomMargin = itemMargin;
            params.leftMargin = itemMargin;
            params.rightMargin = itemMargin;
            params.gravity = Gravity.CENTER;

            imageDot[i].setLayoutParams(params);
            imageDot[i].setImageResource(defaultCircle);
            imageDot[i].setScaleType(ImageView.ScaleType.FIT_XY);
            imageDot[i].setTag(imageDot[i].getId(), false);
            this.addView(imageDot[i]);
        }
        selectAppInfoDot(0);
    }
    public void createDotPanel(int count, int defaultCircle, int selectCircle) {

        mDefaultCircle = defaultCircle;
        mSelectCircle = selectCircle;

        imageDot = new ImageView[count];
        this.removeAllViews();
        for (int i = 0; i < count; i++) {

            imageDot[i] = new ImageView(mContext);
            LayoutParams params = new LayoutParams(60, 10);
            //(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.topMargin = itemMargin;
            params.bottomMargin = itemMargin;
            params.leftMargin = itemMargin;
            params.rightMargin = itemMargin;
            params.gravity = Gravity.CENTER;

            imageDot[i].setLayoutParams(params);
            imageDot[i].setImageResource(defaultCircle);
            imageDot[i].setScaleType(ImageView.ScaleType.FIT_XY);
            imageDot[i].setTag(imageDot[i].getId(), false);
            this.addView(imageDot[i]);
        }


        //첫인덱스 선택
        selectDot(0);
    }
    public void createContentDotPanel(int count, int defaultCircle, int selectCircle) {

        mDefaultCircle = defaultCircle;
        mSelectCircle = selectCircle;

        imageDot = new ImageView[count];
        this.removeAllViews();
        for (int i = 0; i < count; i++) {

            imageDot[i] = new ImageView(mContext);
            LayoutParams params = new LayoutParams(20, 20);
            params.topMargin = itemMargin;
            params.bottomMargin = itemMargin;
            params.leftMargin = itemMargin;
            params.rightMargin = itemMargin;
            params.gravity = Gravity.CENTER;

            imageDot[i].setLayoutParams(params);
            imageDot[i].setImageResource(defaultCircle);
            imageDot[i].setScaleType(ImageView.ScaleType.FIT_XY);
            imageDot[i].setTag(imageDot[i].getId(), false);
            this.addView(imageDot[i]);
        }
        //첫인덱스 선택
        selectDot2(0);
    }

    public void selectDot2(int position) {
        if (imageDot != null && imageDot.length > 0) {
            for (int i = 0; i < imageDot.length; i++) {
                if (i == position) {
                    imageDot[i].setImageResource(mSelectCircle);
                    selectScaleAnim(imageDot[i], 1f, 1.0f);
                } else {

                    if ((boolean) imageDot[i].getTag(imageDot[i].getId()) == true) {
                        imageDot[i].setImageResource(mDefaultCircle);
                        defaultScaleAnim(imageDot[i], 1.0f, 1f);
                    }
                }
            }
        }
    }

    public void selectDot(int position) {
        if (imageDot != null && imageDot.length > 0) {
            for (int i = 0; i < imageDot.length; i++) {
                if (i == position) {
                    imageDot[i].setImageResource(mSelectCircle);
                    selectScaleAnim(imageDot[i], 1f, 1.5f);
                } else {

                    if ((boolean) imageDot[i].getTag(imageDot[i].getId()) == true) {
                        imageDot[i].setImageResource(mDefaultCircle);
                        defaultScaleAnim(imageDot[i], 1.5f, 1f);
                    }
                }
            }
        }
    }
    public void selectAppInfoDot(int position) {
        if (imageDot != null && imageDot.length > 0) {
            for (int i = 0; i < imageDot.length; i++) {
                if (i == position) {
                    imageDot[i].setImageResource(mSelectCircleList.get(position));
                    selectScaleAnim(imageDot[i], 1f, 1.0f);
                } else {

                    if ((boolean) imageDot[i].getTag(imageDot[i].getId()) == true) {
                        imageDot[i].setImageResource(mDefaultCircle);
                        defaultScaleAnim(imageDot[i], 1.0f, 1f);
                    }
                }
            }
        }
    }

    /**
     * 선택된 점의 애니메이션
     *
     * @param view
     * @param startScale
     * @param endScale
     */
    public void selectScaleAnim(View view, float startScale, float endScale) {
        Animation anim = new ScaleAnimation(
                startScale, endScale,
                startScale, endScale,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setFillAfter(true);
        anim.setDuration(animDuration);
        view.startAnimation(anim);
        view.setTag(view.getId(), true);
    }


    /**
     * 선택되지 않은 점의 애니메이션
     *
     * @param view
     * @param startScale
     * @param endScale
     */
    public void defaultScaleAnim(View view, float startScale, float endScale) {
        Animation anim = new ScaleAnimation(
                startScale, endScale,
                startScale, endScale,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setFillAfter(true);
        anim.setDuration(animDuration);
        view.startAnimation(anim);
        view.setTag(view.getId(), false);
    }
}