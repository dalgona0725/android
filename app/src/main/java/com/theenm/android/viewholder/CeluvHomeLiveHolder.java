package com.theenm.android.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.theenm.android.MainActivity;
import com.theenm.android.R;
import com.theenm.android.adapter.HomeTypeLiveAdapter;
import com.theenm.android.callback.OnRecylerViewListener;
import com.theenm.common.http.schemas.LiveCastListObject;

import java.util.ArrayList;

public class CeluvHomeLiveHolder extends BaseViewHolder<ArrayList<LiveCastListObject>> {
    private RecyclerView mRecyclerView;
    private HomeTypeLiveAdapter mAdapter;
    private TextView mtvTitle;
    private RelativeLayout mRlAllView;
    public View mViewDivider = null;

    private OnRecylerViewListener mOnRecylerViewListener;

    public static CeluvHomeLiveHolder newInstance(ViewGroup parent, OnRecylerViewListener onRecylerViewListener) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hometab_live_vod, parent, false);
        return new CeluvHomeLiveHolder(itemView, onRecylerViewListener);
    }

    public CeluvHomeLiveHolder(View itemView, OnRecylerViewListener onRecylerViewListener) {
        super(itemView);

        mOnRecylerViewListener = onRecylerViewListener;
        mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
        mtvTitle = (TextView)itemView.findViewById(R.id.tvTitle);
        mRlAllView = (RelativeLayout)itemView.findViewById(R.id.rlAllView);
        mViewDivider = (View) itemView.findViewById(R.id.view_divider);
    }

    @Override
    public void onBindView(ArrayList<LiveCastListObject> arrayList, Context context, int position) {
        mtvTitle.setText(context.getString(R.string.live));
        mRlAllView.setVisibility(View.VISIBLE);

        mAdapter = new HomeTypeLiveAdapter(context, arrayList, mOnRecylerViewListener);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setNestedScrollingEnabled(false);

        mRlAllView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecylerViewListener.onAllViewClicked(MainActivity.TAB_PAGE_LIVE);
            }
        });

    }
}
