package com.theenm.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.theenm.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khj0704 on 2018-01-22.
 *
 * @author khj0704
 */
public final class TranslateAnimationTextLayout extends RelativeLayout implements Animation.AnimationListener {

    public static final int REPEAT_COUNT_ONCE = 0;
    public static final int REPEAT_COUNT_INFINITE = Animation.INFINITE;
    public static final int REPEAT_MODE_RESTART = Animation.RESTART;
    public static final int REPEAT_MODE_REVERSE = Animation.REVERSE;

    private final String TAG = "TAnimationTextLayout";
    private final int ANIMATION_DEFAULT_DURATION = 10000;
    // Speed = Distance / Time (적을수록 느린 Offset Time Constants)
    private final int DEFAULT_SPEED_TIME = 300;
    private final int DEFAULT_FONT_SIZE_DP = 14;
    private final int DEFAULT_OFFSET_LENGTH_DP = 20;
    private String mId;
    private String mNickName;
    private String mMessage;


    public interface IAnimationCallBack {
        public abstract void onAnimationStart();

        public abstract void onAnimationEnd();

        public abstract void onAnimationRepeat();

        public abstract void onAnimationError(Exception e);
    }

    public interface IOnPreparedAnimation {
        public abstract void onPreparedAnimation();
    }

    private IAnimationCallBack mCallback;

    private Animation mAnimation;

    private RelativeLayout mParentViewGroup;
    private RelativeLayout mTxtBgViewGroup;
    private HorizontalScrollView mHorizontalScrollView;
    private TextView mTxtView;
    private List<String> mDataList;

    // 현재 출력할 List 의 인덱스
    private int mCurrentIdx = 0;
    private boolean mIsShowingAnimation = false;
    // List 를 원형(circular)으로 순차 조회해서 계속 출력할지에 대한 여부
    private boolean mIsCanceled = false;

    private int mDuration = ANIMATION_DEFAULT_DURATION;
    // REPEAT COUNT: infinite (-1, 0xffffffff), Default = 0 (Only Once);
    private int mRepeatCount = REPEAT_COUNT_ONCE;
    // REPEAT MODE: RESTART (1, 0x00000001) | REVERSE (2, 0x00000002)
    private int mRepeatMode = REPEAT_MODE_RESTART;
    private Interpolator mInterpolator = null;
    private int mAnimationTime = DEFAULT_SPEED_TIME;

    private int mTxtBgColor = Color.TRANSPARENT;
    private int mTxtColor = Color.BLACK;
    private int mTxtSize = 0;

    /**
     * @param context
     */
    public TranslateAnimationTextLayout(Context context) {
        super(context);
        initUI();
    }

    /**
     * @param context
     * @param attrs
     */
    public TranslateAnimationTextLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        setAttributeSet(context, attrs);
        initUI();
    }

    public TranslateAnimationTextLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributeSet(context, attrs);
        initUI();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TranslateAnimationTextLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setAttributeSet(context, attrs);
        initUI();
    }

    public void setIsCanceled(boolean pCanceled) {
        mIsCanceled = pCanceled;
    }

    public boolean isCanceled() {
        return mIsCanceled;
    }

    public int getAnimationTime() {
        return mAnimationTime;
    }

    public void setAnimationTime(int pAnimationTime) {
        mAnimationTime = pAnimationTime;
    }

    /**
     * Text 리스트 등록
     *
     * @param pDataList
     */
    public void setMessageDataList(List<String> pDataList) {
        if (mDataList == null) {
            mDataList = new ArrayList<String>();
        } else {
            mDataList.clear();
        }
        mDataList.addAll(pDataList);
        mCurrentIdx = (mDataList.size() - 1);
    }

    /**
     * Text 추가
     *
     * @param pMsg
     */
    public void addMessageData(String pMsg) {
        if (mDataList == null) {
            mDataList = new ArrayList<String>();
        }
        mDataList.add(0, pMsg);
        manageCurrentIndex();
    }

    public void addMessageData(String pMsg, String id, String nickName, String message) {
        if (mDataList == null) {
            mDataList = new ArrayList<String>();
        }
        mDataList.add(0, pMsg);
        manageCurrentIndex();

        mId = id;
        mNickName = nickName;
        mMessage = message;
    }

    /**
     * 데이터 리스트 전부 삭제
     */
    public void clearDataList() {
        if (mDataList != null) {
            mDataList.clear();
            mCurrentIdx = 0;
        }

        if (mTxtView != null) {
            mTxtView.clearAnimation();
        }
    }

    /**
     * 텍스트 색상 설정
     *
     * @param pTxtColor
     */
    public void setAnimTextColor(String pTxtColor) {
        try {
            mTxtColor = Color.parseColor(pTxtColor);
        } catch (IllegalArgumentException iae) {
            //iae.printStackTrace();
            mTxtColor = Color.BLACK;
        } finally {
            if (mTxtView != null) {
                mTxtView.setTextColor(mTxtColor);
            }
        }
    }

    /**
     * 텍스트 사이즈 설정
     *
     * @param pTxtSizeDP
     */
    public void setAnimTextSize(int pTxtSizeDP) {
        mTxtSize = convertDpToPixels(pTxtSizeDP);
        if (mTxtView != null) {
            mTxtView.setTextSize(mTxtSize);
        }
    }

    /**
     * 배경색 설정
     *
     * @param resBgColor
     */
    public void setAnimBgColor(String resBgColor) {
        try {
            mTxtBgColor = Color.parseColor(resBgColor);
        } catch (IllegalArgumentException iae) {
            //iae.printStackTrace();
            mTxtBgColor = Color.TRANSPARENT;
        } finally {
            if (mHorizontalScrollView != null) {
                //mTxtBgViewGroup.setBackgroundColor(mTxtBgColor);
                setRoundDrawable(mHorizontalScrollView);
            }
        }
    }

    /**
     * 애니메이션 콜백 리스너 등록
     *
     * @param pCallback
     */
    public void setAnimationCallback(IAnimationCallBack pCallback) {
        mCallback = pCallback;
    }

    /**
     * 애니메이션 콜백 리스너 해제
     */
    public void removeAnimationCallback() {
        mCallback = null;
    }

    /**
     * 애니메이션 객체 등록
     *
     * @param animation
     */
    public void setAnimation(Animation animation) {
        mAnimation = animation;
    }

    /**
     * 애니메이션 duration 등록
     *
     * @param duration
     */
    public void setDuration(int duration) {
        mDuration = duration;
    }

    /**
     * 애니메이션 repeatCount 등록
     *
     * @param repeatCount
     */
    public void setRepeatCount(int repeatCount) {
        mRepeatCount = repeatCount;
    }

    /**
     * 애니메이션 interpolator 등록
     *
     * @param interpolator
     */
    public void setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
    }

    /**
     * 애니메이션 설정 적용 메소드
     * - View 의 사이즈를 구하기 위해 비동기적으로 처리되는 부분이 있기 때문에
     * 반드시 onCompleteAnimationSettings() 콜백 메소드에서 애니메이션을 컨트롤 해야 한다.
     */
    public void applyAnimationSettings(final IOnPreparedAnimation pOnPreparedCallback) {

        if (mParentViewGroup != null) {
            // View 가 그려지지 않았을 때 View 의 width, height 를 가져오는 방법.
            mParentViewGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if ((mDataList != null) && (!mDataList.isEmpty()) && (checkIndex(mCurrentIdx))) {
                        int layoutWidth = getWidth();
                        int offset = convertDpToPixels(DEFAULT_OFFSET_LENGTH_DP);
                        int textLength = ((int) Math.ceil(getTxtWidth(mDataList.get(mCurrentIdx).toString())));
                        //Log.d(TAG, "applyAnimationSettings() :: layoutWidth - " + layoutWidth);
                        //Log.d(TAG, "applyAnimationSettings() :: textLength - " + textLength);
                        //Log.d(TAG, "applyAnimationSettings() :: offset - " + offset);

                        // Speed = Distance / Time
                        int maxDistance = (Math.max(layoutWidth, textLength) + offset);
                        int animationSpeed = ((maxDistance * 1000) / mAnimationTime);
                        //Log.d(TAG, "applyAnimationSettings() :: animationSpeed - " + animationSpeed);
                        mAnimation = new TranslateAnimation(layoutWidth, -textLength, 0, 0);
                        mAnimation.setDuration(10000);
                    } else {
                        mAnimation = getDefaultAnimation();
                    }
                    mAnimation.setRepeatMode(mRepeatMode);
                    mAnimation.setRepeatCount(mRepeatCount);
                    if (mInterpolator != null) {
                        mAnimation.setInterpolator(mInterpolator);
                    } else {
                        // Default - LinearInterpolator
                        mInterpolator = new LinearInterpolator();
                        mAnimation.setInterpolator(mInterpolator);
                    }
                    //mAnimation.setFillEnabled(true);
                    mAnimation.setFillAfter(true);
                    mAnimation.setAnimationListener(TranslateAnimationTextLayout.this);

                    if ((pOnPreparedCallback != null) && (!mIsCanceled)) {
                        pOnPreparedCallback.onPreparedAnimation();
                    }

                    // 리스너 해제
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        mParentViewGroup.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        mParentViewGroup.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            });
        } else {
            // 예외상황 - Default 로 설정한다.
            mAnimation = getDefaultAnimation();
            mAnimation.setRepeatMode(mRepeatMode);
            mAnimation.setRepeatCount(mRepeatCount);
            if (mInterpolator != null) {
                mAnimation.setInterpolator(mInterpolator);
            } else {
                // Default - LinearInterpolator
                mInterpolator = new LinearInterpolator();
                mAnimation.setInterpolator(mInterpolator);
            }
            //mAnimation.setFillEnabled(true);
            mAnimation.setFillAfter(true);
            mAnimation.setAnimationListener(this);

            if ((pOnPreparedCallback != null) && (!mIsCanceled)) {
                pOnPreparedCallback.onPreparedAnimation();
            }
        }
    }

    /**
     * 애니메이션 시작 메소드
     */
    public void startAnimation() {
        if (mAnimation == null) {
            //Log.e(TAG, "startAnimation() :: mAnimation is Null!");
            return;
        }
        startAnimation(mCurrentIdx);
    }

    /**
     * 애니메이션 정지
     */
    public void stopAnimation() {
        if (mTxtView != null) {
            mTxtView.clearAnimation();
        }
        if (mAnimation != null) {
            mAnimation.cancel();
            mAnimation = null;
        }
        setIsShowingAnimation(false);
        mIsCanceled = true;
    }

    /**
     * 애니메이션 시작
     *
     * @param index
     */
    private void startAnimation(int index) {
        try {
            if (!mIsShowingAnimation) {
                // 유효한 인덱스 범위인지 확인하고 애니메이션을 실행한다. (Rolling 설정을 한 경우를 고려한 체크)
                if (checkIndex(index)) {
                    String text = mDataList.remove(index).toString();
                    if ((mTxtView != null) && (mAnimation != null)) {
                        mTxtView.setAnimation(mAnimation);

                        if (!mId.equals("")) {
                            String msgFormat = getResources().getString(R.string.shout_msg_txt_format);
                            String msg = String.format(msgFormat, mNickName, mId, mMessage);
                            mTxtView.setText(Html.fromHtml(msg));
                        } else {
                            mTxtView.setText(Html.fromHtml(text));
                        }

                        mTxtView.startAnimation(mAnimation);
                    }
                    setIsShowingAnimation(true);
                    // 다음 인덱스를 조절한다.
                    manageCurrentIndex();
                } else {
                    // 유효한 인덱스 범위를 넘어 애니메이션이 실행되지 않음.
                    setIsShowingAnimation(false);
                    if (mCallback != null) {
                        mCallback.onAnimationError(new IndexOutOfBoundsException("startAnimation() :: Out of Index!!"));
                    }
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
            // 예외상황이 발생하여 애니메이션이 실행되지 않음.
            setIsShowingAnimation(false);
            if (mCallback != null) {
                mCallback.onAnimationError(e);
            }
        }
    }

    /**
     * 애니메이션 취소 및 자원 해제
     */
    public void dispose() {
        if (mDataList != null) {
            mDataList.clear();
            mDataList = null;
        }
        removeAnimationCallback();
        if (mTxtView != null) {
            mTxtView.clearAnimation();
        }
        if (mAnimation != null) {
            mAnimation.cancel();
            mAnimation.setInterpolator(null);
            mAnimation.setAnimationListener(null);
            mAnimation = null;
        }
        removeAllViews();
    }

    /* ============= AnimationListener 콜백 전달 ============= */
    @Override
    public void onAnimationStart(Animation animation) {
        if (mCallback != null) {
            mCallback.onAnimationStart();
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        setIsShowingAnimation(false);
        if (mDataList == null || mDataList.isEmpty()) {
            stopAnimation();
            if (mCallback != null) {
                mCallback.onAnimationEnd();
            }
        } else {
            startAnimation();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        if (mCallback != null) {
            mCallback.onAnimationRepeat();
        }
    }
    /* ====================================================== */

    /**
     * 커스텀 어트리뷰트 설정 (XML 에 정의된 커스텀 속성을 받아서 설정한다.)
     *
     * @param context
     * @param attrs
     */
    private void setAttributeSet(Context context, AttributeSet attrs) {
        if (context != null) {
            TypedArray typeArray = null;
            try {
                typeArray = context.obtainStyledAttributes(attrs, R.styleable.AnimationTextLayout, 0, 0);
                if (typeArray.hasValue(R.styleable.AnimationTextLayout_bgColor)) {
                    mTxtBgColor = typeArray.getColor(R.styleable.AnimationTextLayout_bgColor, Color.TRANSPARENT);
                }
                if (typeArray.hasValue(R.styleable.AnimationTextLayout_textColor)) {
                    mTxtColor = typeArray.getColor(R.styleable.AnimationTextLayout_textColor, Color.BLACK);
                }
                if (typeArray.hasValue(R.styleable.AnimationTextLayout_textSize)) {
                    mTxtSize = typeArray.getDimensionPixelSize(R.styleable.AnimationTextLayout_textSize,
                            getResources().getDimensionPixelSize(R.dimen.shout_text_size));
                }
            } catch (Exception e) {
                //e.printStackTrace();
                mTxtBgColor = Color.TRANSPARENT;
                mTxtColor = Color.BLACK;
                mTxtSize = convertDpToPixels(DEFAULT_FONT_SIZE_DP);
            } finally {
                if (typeArray != null) {
                    typeArray.recycle();
                }
            }
        }
    }

    /**
     * 뷰 초기화
     */
    private void initUI() {
        // 커스텀 레이아웃 초기화
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mParentViewGroup = (RelativeLayout) inflater.inflate(R.layout.translate_anim_layout, null);
        //mParentViewGroup.setBackgroundColor(mTxtBgColor);
        mHorizontalScrollView = (HorizontalScrollView) mParentViewGroup.findViewById(R.id.horizontal_animation_scrollview);
        setRoundDrawable(mHorizontalScrollView);
        mTxtBgViewGroup = (RelativeLayout) mParentViewGroup.findViewById(R.id.txt_bg_viewgroup);
        //mTxtBgViewGroup.setBackgroundColor(mTxtBgColor);
        mTxtView = (TextView) mParentViewGroup.findViewById(R.id.txtAnimView);
        mTxtView.setBackgroundColor(mTxtBgColor);
        mTxtView.setTextColor(mTxtColor);
        mTxtView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTxtSize);
        addView(mParentViewGroup);
    }

    /**
     * Drawable (Round Drawable) 을 가져와서 색상만 교체한다.
     *
     * @param pView
     */
    private void setRoundDrawable(View pView) {
        if (pView == null) {
            return;
        }
        Drawable roundDrawable = getResources().getDrawable(R.drawable.round_corner_bg);
        if (roundDrawable != null) {
            //roundDrawable.setColorFilter(0xff99cc00, PorterDuff.Mode.SRC_ATOP);
            roundDrawable.setColorFilter(mTxtBgColor, PorterDuff.Mode.SRC_OVER);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                pView.setBackgroundDrawable(roundDrawable);
            } else {
                pView.setBackground(roundDrawable);
            }
        }
    }

    private void setIsShowingAnimation(boolean pIsShowingAnimation) {
        this.mIsShowingAnimation = pIsShowingAnimation;
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
     * Default TranslateAnimation 객체를 반환한다.
     *
     * @return
     */
    private Animation getDefaultAnimation() {
        Animation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, +1f,
                Animation.RELATIVE_TO_SELF, -1f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f
        );
        animation.setDuration(mDuration);
        return animation;
    }

    /**
     * DP 값을 해상도와 맞는 Pixel 로 변환한다.
     *
     * @param dp
     * @return
     */
    private int convertDpToPixels(float dp) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
        return px;
    }

    /**
     * SP 값을 해상도와 맞는 Pixel 로 변환한다.
     *
     * @param sp
     * @return
     */
    private int convertSpToPixels(float sp) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp
                , getContext().getResources().getDisplayMetrics());
        return px;
    }

    /**
     * TextView 의 폰트 사이즈를 적용한 너비를 반환한다.
     *
     * @param text
     * @return
     */
    private float getTxtWidth(String text) {
        if (mTxtView == null) {
            return 0.0f;
        }
        Paint textPaint = mTxtView.getPaint();
        if (textPaint == null) {
            return 0.0f;
        }
        float width = textPaint.measureText(text);
        return width;
    }


}
