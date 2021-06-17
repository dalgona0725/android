package com.theenm.android.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.theenm.android.R;
import com.theenm.android.adapter.RecyclerAdapter;
import com.theenm.android.callback.OnRecylerViewListener;
import com.theenm.common.Constant;
import com.theenm.common.GridRecyclerDivider;
import com.theenm.common.util.PopkonUtils;

import java.util.ArrayList;
import java.util.HashMap;


public class PortalSectionHolder extends BaseViewHolder<ArrayList<Object>> {
    private final RecyclerAdapter mAdapter;

    private final  TextView mTvTitle;

    private final HashMap<Constant.ReCyclerType, Object> mArrayList;

    public static PortalSectionHolder newInstance(ViewGroup parent, OnRecylerViewListener onRecylerViewListener) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_portal_section, parent, false);
        return new PortalSectionHolder(itemView, onRecylerViewListener);
    }

    public PortalSectionHolder(View itemView, OnRecylerViewListener onRecylerViewListener) {
        super(itemView);

        mTvTitle = itemView.findViewById(R.id.text_top_title);

        mAdapter = new RecyclerAdapter(itemView.getContext());
        mAdapter.setOnRecylerViewListener(onRecylerViewListener);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(itemView.getContext(), 2);

        int inPadding = PopkonUtils.convertDpToPixels(itemView.getContext(), 4);
        int outPadding = PopkonUtils.convertDpToPixels(itemView.getContext(), 8);
        RecyclerView mRecyclerView = itemView.findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new GridRecyclerDivider(inPadding, outPadding));
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(mAdapter);

        mArrayList = new HashMap<>();
    }

    @Override
    public void onBindView(ArrayList<Object> arrayList, Context context, int position) {
        mArrayList.clear();

        if(getItemViewType() == Constant.ReCyclerType.TYPE_POPKON_PORTAL_LIVE_SECTION_BEST_ROW.ordinal()){
            mTvTitle.setText(context.getString(R.string.now_best));
            mArrayList.put(Constant.ReCyclerType.TYPE_POPKON_PORTAL_LIVE_BEST_ROW, arrayList);
        }
        else if(getItemViewType() == Constant.ReCyclerType.TYPE_POPKON_PORTAL_LIVE_SECTION_BOOKMARK_ROW.ordinal()) {
            mTvTitle.setText(context.getString(R.string.bookmark));
            mArrayList.put(Constant.ReCyclerType.TYPE_POPKON_PORTAL_LIVE_BOOKMARK_ROW, arrayList);
        }
        else if(getItemViewType() == Constant.ReCyclerType.TYPE_POPKON_PORTAL_VOD_SECTION_BEST_ROW.ordinal()) {
            mTvTitle.setText(context.getString(R.string.now_best));
            mArrayList.put(Constant.ReCyclerType.TYPE_POPKON_PORTAL_VOD_BEST_ROW, arrayList);
        }
        else if(getItemViewType() == Constant.ReCyclerType.TYPE_POPKON_PORTAL_ADULT_SECTION_BEST_ROW.ordinal()){
            mTvTitle.setText(context.getString(R.string.now_best));
            mArrayList.put(Constant.ReCyclerType.TYPE_POPKON_PORTAL_ADULT_BEST_ROW, arrayList);
        }
        else if(getItemViewType() == Constant.ReCyclerType.TYPE_POPKON_PORTAL_ADULT_SECTION_BOOKMARK_ROW.ordinal()) {
            mTvTitle.setText(context.getString(R.string.bookmark));
            mArrayList.put(Constant.ReCyclerType.TYPE_POPKON_PORTAL_ADULT_BOOKMARK_ROW, arrayList);
        }
        mAdapter.setRows(PopkonUtils.setViewTypeCreate(mArrayList));
        mAdapter.notifyDataSetChanged();
    }
}
