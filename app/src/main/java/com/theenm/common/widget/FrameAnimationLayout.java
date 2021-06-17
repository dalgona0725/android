package com.theenm.common.widget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.theenm.android.R;
import com.theenm.common.node.Message;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by khj0704 on 2018-01-30.
 * 입장 효과 커스텀 레이아웃. 반드시 RelativeLayout 안에 있어야 한다.
 */
public final class FrameAnimationLayout extends RelativeLayout implements View.OnClickListener {

    private static final String TAG = "FrameAnimationLayout";

    private List<Message> mDataList;
    private RelativeLayout mRootViewGroup;
    private RelativeLayout mParentViewGroup;
    private ImageView mItemAnimaionImgView;
    private LinearLayout mLabelLayout;
    private TextView mTxtEventItemNickName;
    private TextView mTxtEventItemLabel;

    private AnimationDrawable mAnimationEventDrawable;
    private Handler mHandler;

    private int mCurrentIdx;
    private boolean mIsShowingAnimation;

    public FrameAnimationLayout(Context context) {
        super(context);
        initUI();
    }

    public FrameAnimationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI();
    }

    public FrameAnimationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FrameAnimationLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initUI();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void onChangeOrientation(Configuration pConfig) {
        if (mParentViewGroup == null || pConfig == null) {
            return;
        }

        ViewGroup.LayoutParams parentParams = mParentViewGroup.getLayoutParams();
        if ((parentParams == null) || (parentParams instanceof RelativeLayout.LayoutParams == false)) {
            return;
        }

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mParentViewGroup.getLayoutParams();
        if (params == null) {
            return;
        }

        Resources res = getResources();
        if (res == null) {
            return;
        }
        if (pConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            params.leftMargin = res.getDimensionPixelSize(R.dimen.enterevt_pc_portrait_margin_left_right);
            params.rightMargin = res.getDimensionPixelSize(R.dimen.enterevt_pc_portrait_margin_left_right);
            params.bottomMargin = res.getDimensionPixelSize(R.dimen.enterevt_pc_portrait_margin_bottom);
            params.topMargin = res.getDimensionPixelSize(R.dimen.enterevt_pc_portrait_margin_top);
        } else if (pConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            params.leftMargin = res.getDimensionPixelSize(R.dimen.enterevt_pc_landscape_margin_left_right);
            params.rightMargin = res.getDimensionPixelSize(R.dimen.enterevt_pc_landscape_margin_left_right);
            params.topMargin = 0;
            params.bottomMargin = res.getDimensionPixelSize(R.dimen.enterevt_pc_portrait_margin_bottom);
        }
        mParentViewGroup.setLayoutParams(params);
    }

    public void setForceBottomMargin(int px) {
        if (mParentViewGroup == null) {
            return;
        }

        ViewGroup.LayoutParams parentParams = mParentViewGroup.getLayoutParams();
        if ((parentParams == null) || (parentParams instanceof RelativeLayout.LayoutParams == false)) {
            return;
        }

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mParentViewGroup.getLayoutParams();
        if (params == null) {
            return;
        }

        params.topMargin = px;
        mParentViewGroup.setLayoutParams(params);
    }

    public int getCurrentBottomMarginPx() {
        if (mParentViewGroup == null) {
            return 0;
        }

        ViewGroup.LayoutParams parentParams = mParentViewGroup.getLayoutParams();
        if ((parentParams == null) || (parentParams instanceof RelativeLayout.LayoutParams == false)) {
            return 0;
        }

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mParentViewGroup.getLayoutParams();
        if (params == null) {
            return 0;
        }

        return params.topMargin;
    }

    public void dispose() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        if (mDataList != null) {
            Message msg = null;
            AnimationDrawable ad = null;
            Iterator<Message> iterator = mDataList.iterator();
            while (iterator.hasNext()) {
                msg = iterator.next();
                if (msg != null) {
                    ad = msg.mAnimationDrawable;
                    releaseAnimationDrawable(ad);
                }
            }
            mDataList.clear();
            mDataList = null;
        }
    }

    /**
     * Message 리스트 등록
     *
     * @param pDataList
     */
    public void setMessageDataList(List<Message> pDataList) {
        if (mDataList == null) {
            mDataList = new ArrayList<Message>();
        } else {
            mDataList.clear();
        }
        mDataList.addAll(pDataList);
        mCurrentIdx = (mDataList.size() - 1);
    }

    /**
     * Message 추가
     *
     * @param pMsg
     */
    public void addMessageData(Message pMsg) {
        if (mDataList == null) {
            mDataList = new ArrayList<Message>();
        }
        mDataList.add(0, pMsg);
        manageCurrentIndex();
    }

    public void showAnimation() {
        //Log.d(TAG, "showAnimation() :: mIsShowingAnimation - " + mIsShowingAnimation);
        if (!mIsShowingAnimation) {
            if (mDataList == null || mDataList.isEmpty()) {
                //Log.d(TAG, "showAnimation() :: mDataList is Empty!!");
                setIsShowingAnimation(false);
                if (mRootViewGroup != null) {
                    mRootViewGroup.setVisibility(View.GONE);
                }
                return;
            }
            try
            {
                if (checkIndex(mCurrentIdx)) {
                    //Log.d(TAG, "showAnimation() :: valid index!!");
                    final Message message = mDataList.remove(mCurrentIdx);
                    if (message == null) {
                        //Log.d(TAG, "showAnimation() :: message is Null!!");
                        setIsShowingAnimation(false);
                        return;
                    }
                    if (mRootViewGroup != null) {
                        mRootViewGroup.setVisibility(View.VISIBLE);
                    }
                    mAnimationEventDrawable = message.mAnimationDrawable;
                    if ((mAnimationEventDrawable != null) && (mAnimationEventDrawable.getNumberOfFrames() > 0)) {
                        //Log.d(TAG, "showAnimation() :: mAnimationEventDrawable is NOT Null!!");
                        if (mItemAnimaionImgView != null) {
                            mItemAnimaionImgView.setImageDrawable(mAnimationEventDrawable);
                        }
                        mAnimationEventDrawable.setOneShot(true);
                        mAnimationEventDrawable.setFilterBitmap(true);

                        int duration = getTotalFrameDuration(mAnimationEventDrawable);
                        //Log.d(TAG, "showAnimation() :: duration - " + duration);
                        mAnimationEventDrawable.start();

                        if (mHandler != null) {
                            mHandler.removeCallbacksAndMessages(null);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    onNotifiedFinishedAnimation();
                                }
                            }, duration);
                        }

                        //Log.d(TAG, "showAnimation() :: Animation is Started!!");

                        if ((mTxtEventItemNickName != null) && (!TextUtils.isEmpty(message.mNickName))) {
                            if (mLabelLayout != null) {
                                mLabelLayout.setVisibility(View.VISIBLE);
                            }
                            mTxtEventItemNickName.setText(message.mNickName);
                        }

                        manageCurrentIndex();
                        setIsShowingAnimation(true);

                    } else {
                        // AnimationDrawable 이 없는 경우
                        setIsShowingAnimation(false);
                        if (mRootViewGroup != null) {
                            mRootViewGroup.setVisibility(View.GONE);
                        }
                    }
                } else {
                    //Log.d(TAG, "showAnimation() :: invalid index!!");
                    setIsShowingAnimation(false);
                    if (mRootViewGroup != null) {
                        mRootViewGroup.setVisibility(View.GONE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (mRootViewGroup != null) {
                    mRootViewGroup.setVisibility(View.GONE);
                }
                setIsShowingAnimation(false);
                releaseAnimationDrawable(mAnimationEventDrawable);
            }
        }
    }

    private void onNotifiedFinishedAnimation() {
        //Log.d(TAG, "onNotifiedFinishedAnimation()");
        setIsShowingAnimation(false);
        releaseAnimationDrawable(mAnimationEventDrawable);
        if (mDataList == null || mDataList.isEmpty()) {
            //Log.d(TAG, "onNotifiedFinishedAnimation() :: mDataList is Empty! >> closeAnimationUI()");
            closeAnimationUI();
        } else {
            //Log.d(TAG, "onNotifiedFinishedAnimation() :: mDataList is NOT Empty! >> showAnimation()");
            showAnimation();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == null) {
            return;
        }

        int id = v.getId();
        switch (id) {
            case R.id.event_item_parent_layout:
            case R.id.event_item_animaion_view:
                //Log.d(TAG, "onClick()");
                setIsShowingAnimation(false);
                releaseAnimationDrawable(mAnimationEventDrawable);
                if (mDataList == null || mDataList.isEmpty()) {
                    closeAnimationUI();
                } else {
                    showAnimation();
                }
                //closeAnimationUI();
                break;
        }
    }

    /**
     * View 초기화
     */
    private void initUI() {
        mCurrentIdx = 0;
        setIsShowingAnimation(false);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootViewGroup = (RelativeLayout) inflater.inflate(R.layout.event_item_layout, null);
        mParentViewGroup = (RelativeLayout) mRootViewGroup.findViewById(R.id.event_item_parent_layout);
        mParentViewGroup.setOnClickListener(this);
        mItemAnimaionImgView = (ImageView) mRootViewGroup.findViewById(R.id.event_item_animaion_view);
        mItemAnimaionImgView.setOnClickListener(this);
        mLabelLayout = (LinearLayout) mRootViewGroup.findViewById(R.id.enterevt_label_layout);
        mTxtEventItemNickName = (TextView) mRootViewGroup.findViewById(R.id.enterevt_nickname_txt_view);
        mTxtEventItemLabel = (TextView) mRootViewGroup.findViewById(R.id.enterevt_label_txt_view);
        addView(mRootViewGroup);

        mHandler = new Handler(Looper.getMainLooper());
    }

    private void setIsShowingAnimation(boolean pIsShowingAnimation) {
        this.mIsShowingAnimation = pIsShowingAnimation;
    }

    public boolean isShowingAnimation() {
        return mIsShowingAnimation;
    }

    private void releaseAnimationDrawable(AnimationDrawable ad) {
        //Log.d(TAG, "releaseAnimationDrawable()");
        if (ad != null) {
            ad.stop();
            ad.selectDrawable(0);
        }
    }

    private void closeAnimationUI() {
        //Log.d(TAG, "closeAnimationUI()");
        if (mRootViewGroup != null) {
            mRootViewGroup.setVisibility(View.GONE);
        }
        if (mLabelLayout != null) {
            mLabelLayout.setVisibility(View.GONE);
        }
        if (mItemAnimaionImgView != null) {
            mItemAnimaionImgView.clearAnimation();
        }
        releaseAnimationDrawable(mAnimationEventDrawable);
        setIsShowingAnimation(false);
    }

    /**
     * 인덱스 유효 범위 확인
     *
     * @param index
     * @return
     */
    private boolean checkIndex(int index) {
        // 인덱스 범위 관리
        int listSize = (mDataList == null || mDataList.isEmpty()) ? 0 : mDataList.size();
        if ((index >= 0) && (index < listSize)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 인덱스 범위 관리
     */
    private void manageCurrentIndex() {
        int listSize = (((mDataList != null) && (!mDataList.isEmpty())) ? mDataList.size() : 0);
        if ((mCurrentIdx > 0) && (mCurrentIdx < listSize)) {
            mCurrentIdx = listSize - 1;
        } else {
            mCurrentIdx = 0;
        }
    }

    /**
     * AnimationDrawable 이 가지고 있는 모든 프레임 Duration 값을 합해서 리턴한다.
     *
     * @param pAnimationDrawable
     * @return
     */
    private int getTotalFrameDuration(AnimationDrawable pAnimationDrawable) {
        if (pAnimationDrawable == null) {
            return 0;
        }

        int duration = 0;
        int frameSize = pAnimationDrawable.getNumberOfFrames();
        for (int i=0; i<frameSize; i++) {
            duration += pAnimationDrawable.getDuration(i);
        }
        return duration;
    }

}
