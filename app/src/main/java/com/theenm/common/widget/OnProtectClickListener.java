package com.theenm.common.widget;

import android.view.View;

public abstract class OnProtectClickListener implements View.OnClickListener {
    // 중복 클릭 방지 시간 설정
    private static final long MIN_CLICK_INTERVAL=600;

    private long mLastClickTime;

    public abstract void OnProtectClick(View v);

    @Override
    public final void onClick(View v) {
        long currentClickTime = System.currentTimeMillis();
        long elapsedTime = currentClickTime - mLastClickTime;
        mLastClickTime = currentClickTime;

        // 중복 클릭인 경우
        if (elapsedTime <= MIN_CLICK_INTERVAL) {
            return;
        }

        // 중복 클릭이 아니라면 추상함수 호출
        OnProtectClick(v);
    }
}
