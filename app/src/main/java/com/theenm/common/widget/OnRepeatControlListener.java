package com.theenm.common.widget;

public interface OnRepeatControlListener {
    public void onSendPopkon(int type, int popkon);
    public void onRepeatStart(boolean isDoosan, int startCount, int endCount, int totalCount, int period);
    public void onRepeatTotal(int haveCount);
}
