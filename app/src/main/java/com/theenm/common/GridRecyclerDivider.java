package com.theenm.common;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GridRecyclerDivider extends RecyclerView.ItemDecoration{
    private int insideSpacing;
    private int outsideSpacing;

    public GridRecyclerDivider(int insideSpacing, int outsideSpacing) {
        this.insideSpacing = insideSpacing;
        this.outsideSpacing = outsideSpacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        //상하 설정
        outRect.top = insideSpacing;
        outRect.bottom = insideSpacing;
        // spanIndex = 0 -> 왼쪽
        // spanIndex = 1 -> 오른쪽
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        int spanIndex = lp.getSpanIndex();

        if (spanIndex == 0) {
            //왼쪽 아이템
            outRect.left = outsideSpacing;
            outRect.right = insideSpacing;

        } else if (spanIndex == 1) {
            //오른쪽 아이템
            outRect.left = insideSpacing;
            outRect.right = outsideSpacing;
        }

    }
}
