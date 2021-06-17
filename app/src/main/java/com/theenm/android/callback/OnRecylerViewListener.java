package com.theenm.android.callback;

public abstract class OnRecylerViewListener {
    // 중복 클릭 방지 시간 설정
    private static final long MIN_CLICK_INTERVAL=600;

    private long mLastClickTime;
    protected boolean mIsClicked = false;

    public void onItemClicked(Object itemObject){
        long currentClickTime = System.currentTimeMillis();
        long elapsedTime = currentClickTime - mLastClickTime;
        mLastClickTime = currentClickTime;

        // 중복 클릭인 경우
        if (elapsedTime <= MIN_CLICK_INTERVAL) {
            mIsClicked = false;
        } else {
            mIsClicked = true;
        }
    };
    public void onPreviewClicked(Object itemObject){
        long currentClickTime = System.currentTimeMillis();
        long elapsedTime = currentClickTime - mLastClickTime;
        mLastClickTime = currentClickTime;

        // 중복 클릭인 경우
        if (elapsedTime <= MIN_CLICK_INTERVAL) {
            mIsClicked = false;
        } else {
            mIsClicked = true;
        }
    }
    public void onAllViewClicked(int position){};
    public void onVODMenuClicked(Object itemObject){};
    public void onVODReplyClicked(Object itemObject){};
    public void onVODRecommend(Object itemObject, int type){};
    public void onVODSortList(int sortType){};
    public void onNetworkRetry(){};
    public void onSortList(int sortType){};
    public void onSendMessage(Object itemObject){}
    public void onBookmarkAdd(Object itemObject){}
    public void onBookmarkDeleted(Object itemObject, int position){}
    public void onPushSwichChecked(Object itemObject, boolean isChecked){};
}