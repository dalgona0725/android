package com.theenm.android.viewholder;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder<ITEM> extends RecyclerView.ViewHolder
{

    public BaseViewHolder(View itemView)
    {
        super(itemView);
    }
    public abstract void onBindView(ITEM item, Context mContext, final int position);

}
