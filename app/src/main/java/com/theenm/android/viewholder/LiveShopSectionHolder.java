package com.theenm.android.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.theenm.android.LiveShopAllFragment;
import com.theenm.android.R;
import com.theenm.android.adapter.RecyclerAdapter;
import com.theenm.android.callback.OnRecylerViewListener;
import com.theenm.common.Constant;
import com.theenm.common.ListRecyclerDivider;
import com.theenm.common.util.ObjectUtils;
import com.theenm.common.util.PopkonUtils;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class LiveShopSectionHolder extends BaseViewHolder<ArrayList<Object>> implements View.OnClickListener{
    private final RecyclerAdapter mAdapter;

    private final TextView mTvTitle;
    private final RelativeLayout mRlAllView;

    private OnRecylerViewListener mOnRecylerViewListener;

    private final HashMap<Constant.ReCyclerType, Object> mArrayList;

    public static LiveShopSectionHolder newInstance(ViewGroup parent, OnRecylerViewListener onRecylerViewListener) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_liveshop_section, parent, false);
        return new LiveShopSectionHolder(itemView, onRecylerViewListener);
    }

    public LiveShopSectionHolder(View itemView, OnRecylerViewListener onRecylerViewListener) {
        super(itemView);

        mOnRecylerViewListener = onRecylerViewListener;

        mTvTitle = itemView.findViewById(R.id.tvTitle);
        mRlAllView = itemView.findViewById(R.id.rlAllView);
        mRlAllView.setOnClickListener(this);

        mAdapter = new RecyclerAdapter(itemView.getContext());
        mAdapter.setOnRecylerViewListener(onRecylerViewListener);

        RecyclerView mRecyclerView = itemView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(mAdapter);

        mArrayList = new HashMap<>();
    }

    @Override
    public void onBindView(ArrayList<Object> arrayList, Context context, int position) {
        mArrayList.clear();

        if(getItemViewType() == Constant.ReCyclerType.TYPE_LIVE_SHOP_LIVE_SECTION_ROW.ordinal()){
            mTvTitle.setText(context.getString(R.string.live));
            mRlAllView.setVisibility(View.VISIBLE);
            mArrayList.put(Constant.ReCyclerType.TYPE_LIVE_SHOP_LIVE_ROW, arrayList);
            if (ObjectUtils.isEmpty(arrayList)){
                mRlAllView.setVisibility(View.GONE);
                mArrayList.put(Constant.ReCyclerType.TYPE_LIVE_SHOP_LIVE_EMPTY_LIST, context.getString(R.string.empty_live_text));
            }
        }
        else if(getItemViewType() == Constant.ReCyclerType.TYPE_LIVE_SHOP_REVIEW_SECTION_ROW.ordinal()) {
            mTvTitle.setText(context.getString(R.string.review));
            mRlAllView.setVisibility(View.VISIBLE);
            mArrayList.put(Constant.ReCyclerType.TYPE_LIVE_SHOP_REVIEW_ROW, arrayList);
            if (ObjectUtils.isEmpty(arrayList)){
                mRlAllView.setVisibility(View.GONE);
                mArrayList.put(Constant.ReCyclerType.TYPE_LIVE_SHOP_REVIEW_EMPTY_LIST, context.getString(R.string.empty_liveshop_review_text));
            }
        }
        mAdapter.setRows(PopkonUtils.setViewTypeCreate(mArrayList));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rlAllView:
                if(getItemViewType() == Constant.ReCyclerType.TYPE_LIVE_SHOP_LIVE_SECTION_ROW.ordinal()){
                    LiveShopAllFragment liveShopAllFragment = LiveShopAllFragment.newInstance(LiveShopAllFragment.LIVE_SHOP_TYPE_LIVE);
                    liveShopAllFragment.addFragment(itemView.getContext(), R.id.fragment_container);
                } else if(getItemViewType() == Constant.ReCyclerType.TYPE_LIVE_SHOP_REVIEW_SECTION_ROW.ordinal()) {
                    LiveShopAllFragment liveShopAllFragment = LiveShopAllFragment.newInstance(LiveShopAllFragment.LIVE_SHOP_TYPE_REVIEW);
                    liveShopAllFragment.addFragment(itemView.getContext(), R.id.fragment_container);
                }
                break;
        }
    }
}
