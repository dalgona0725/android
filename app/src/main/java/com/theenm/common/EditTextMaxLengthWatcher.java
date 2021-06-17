package com.theenm.common;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by khj0704 on 2018-05-29.
 */

public final class EditTextMaxLengthWatcher implements TextWatcher {

    private int mMaxTextSize = 10;

    public EditTextMaxLengthWatcher() {

    }

    public EditTextMaxLengthWatcher(ICallbackTextState pIOnCompletedMaxText) {
        this.mIOnCompletedMaxText = pIOnCompletedMaxText;
    }

    public EditTextMaxLengthWatcher(ICallbackTextState pIOnCompletedMaxText, int pMaxTextSize) {
        this(pIOnCompletedMaxText);
        this.mMaxTextSize = pMaxTextSize;
    }

    private ICallbackTextState mIOnCompletedMaxText;

    public interface ICallbackTextState {
        public abstract void onCompleteMaxText(String pText); // 입력 최대값에 도달한 경우
        public abstract void onEnableInputText(String pText); // 입력 최대값에 도달하지 않은 경우
    }

    public void setICallbackTextState(ICallbackTextState pIOnCompletedMaxText) {
        this.mIOnCompletedMaxText = pIOnCompletedMaxText;
    }

    public void removeICallbackTextState() {
        this.mIOnCompletedMaxText = null;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s == null)
            return;

        if (mIOnCompletedMaxText == null)
            return;

        if( s.length() >= mMaxTextSize ) {
            mIOnCompletedMaxText.onCompleteMaxText(s.toString());
        } else {
            mIOnCompletedMaxText.onEnableInputText(s.toString());
        }
    }

}
