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
import com.theenm.android.adapter.HomeTypeVODAdapter;
import com.theenm.android.callback.OnRecylerViewListener;
import com.theenm.common.Constant;
import com.theenm.common.http.schemas.CeluvVODListObject;

import java.util.ArrayList;

public class CeluvHomeVODHolder extends BaseViewHolder<ArrayList<CeluvVODListObject>> {
    private RecyclerView mRecyclerView;
    private HomeTypeVODAdapter mAdapter;
    private TextView mtvTitle;
    private RelativeLayout mRlAllView;
    public View mViewDivider = null;

    private OnRecylerViewListener mOnRecylerViewListener;

    public static CeluvHomeVODHolder newInstance(ViewGroup parent, OnRecylerViewListener onRecylerViewListener) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hometab_live_vod, parent, false);
        return new CeluvHomeVODHolder(itemView, onRecylerViewListener);
    }

    public CeluvHomeVODHolder(View itemView, OnRecylerViewListener onRecylerViewListener) {
        super(itemView);

        mOnRecylerViewListener = onRecylerViewListener;
        mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
        mtvTitle = (TextView)itemView.findViewById(R.id.tvTitle);
        mRlAllView = (RelativeLayout)itemView.findViewById(R.id.rlAllView);
        mViewDivider = (View) itemView.findViewById(R.id.view_divider);
    }

    @Override
    public void onBindView(ArrayList<CeluvVODListObject> arrayList, Context context, int position) {
        if(getItemViewType() == Constant.ReCyclerType.TYPE_CELUV_HOME_VOD_ROW.ordinal()){
            mtvTitle.setText(context.getString(R.string.vod));
        }else{
            mtvTitle.setText(context.getString(R.string.hotclip));
        }

        mRlAllView.setVisibility(View.VISIBLE);

        mAdapter = new HomeTypeVODAdapter(context, arrayList, mOnRecylerViewListener);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setNestedScrollingEnabled(false);

        mRlAllView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecylerViewListener.onAllViewClicked(MainActivity.TAB_PAGE_VOD);
            }
        });

    }
}
