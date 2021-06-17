package com.theenm.android.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.theenm.android.R;
import com.theenm.android.callback.OnRecylerViewListener;

public class NetworkNotTypeHolder extends BaseViewHolder<String> {
    private TextView mTvRetry;

    private OnRecylerViewListener mOnRecylerViewListener;

    public static NetworkNotTypeHolder newInstance(ViewGroup parent, OnRecylerViewListener onRecylerViewListener) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_check_network, parent, false);

        return new NetworkNotTypeHolder(itemView, onRecylerViewListener);
    }

    public NetworkNotTypeHolder(View itemView, OnRecylerViewListener onRecylerViewListener) {
        super(itemView);

        mOnRecylerViewListener = onRecylerViewListener;
        mTvRetry = (TextView)itemView.findViewById(R.id.tvRetry);
    }

    @Override
    public void onBindView(String s, final Context mContext, int position) {

        mTvRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecylerViewListener.onNetworkRetry();
            }
        });

    }

}
