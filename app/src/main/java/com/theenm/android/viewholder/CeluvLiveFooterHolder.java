package com.theenm.android.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theenm.android.R;


public class CeluvLiveFooterHolder extends BaseViewHolder {

    public static CeluvLiveFooterHolder newInstance(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_livetab_footer, parent, false);
        return new CeluvLiveFooterHolder(itemView);
    }

    public CeluvLiveFooterHolder(View itemView) {
        super(itemView);

    }

    @Override
    public void onBindView(Object object, final Context mContext, int position) {
    }
}
