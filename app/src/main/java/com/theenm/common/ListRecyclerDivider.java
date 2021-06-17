package com.theenm.common;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListRecyclerDivider extends RecyclerView.ItemDecoration{
    private int insideSpacing;
    private int outsideSpacing;

    public ListRecyclerDivider() {
        this(0);
    }

    public ListRecyclerDivider(int insideSpacing) {
        this(insideSpacing, 0);
    }

    public ListRecyclerDivider(int insideSpacing, int outsideSpacing) {
        this.insideSpacing = insideSpacing;
        this.outsideSpacing = outsideSpacing;
    }

    public void setSpacing(int insideSpacing, int outsideSpacing){
        this.insideSpacing = insideSpacing;
        this.outsideSpacing = outsideSpacing;
    }

    public void setInsideSpacing(int insideSpacing) {
        this.insideSpacing = insideSpacing;
    }

    public void setOutsideSpacing(int outsideSpacing) {
        this.outsideSpacing = outsideSpacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();

        int orientation = ((LinearLayoutManager)parent.getLayoutManager()).getOrientation();
        if(orientation == LinearLayoutManager.HORIZONTAL) {
            if (position == 0) { // left edge
                outRect.left = outsideSpacing;
            } else {
                outRect.left = insideSpacing / 2;
            }

            if (position == itemCount - 1) { // right edge
                outRect.right = outsideSpacing;
            } else {
                outRect.right = insideSpacing / 2;
            }

            outRect.top = outsideSpacing;
            outRect.bottom = outsideSpacing; // item bottom
        } else {
            if (position == 0) { // left edge
                outRect.top = outsideSpacing;
            } else {
                outRect.top = insideSpacing / 2;
            }

            if (position == itemCount - 1) { // right edge
                outRect.bottom = outsideSpacing;
            } else {
                outRect.bottom = insideSpacing / 2;
            }

            outRect.left = outsideSpacing;
            outRect.right = outsideSpacing; // item bottom
        }
    }
}
