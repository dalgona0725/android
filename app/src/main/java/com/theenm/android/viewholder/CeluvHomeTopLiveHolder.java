package com.theenm.android.viewholder;

import android.content.Context;
import android.os.Build;
import android.text.Html;
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
import com.theenm.android.adapter.HomeTypeCeluvLiveAdapter;
import com.theenm.android.callback.OnRecylerViewListener;
import com.theenm.common.http.schemas.LiveCastListObject;

import java.util.ArrayList;


public class CeluvHomeTopLiveHolder extends BaseViewHolder<ArrayList<LiveCastListObject>> {
    private RecyclerView mRecyclerView;
    private HomeTypeCeluvLiveAdapter mAdapter;
    private TextView mtvTitle;
    private RelativeLayout mRlAllView;

    private OnRecylerViewListener mOnRecylerViewListener;

    public static CeluvHomeTopLiveHolder newInstance(ViewGroup parent, OnRecylerViewListener onRecylerViewListener) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hometab_celuv_live, parent, false);
        return new CeluvHomeTopLiveHolder(itemView, onRecylerViewListener);
    }

    public CeluvHomeTopLiveHolder(View itemView, OnRecylerViewListener onRecylerViewListener) {
        super(itemView);

        mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
        mtvTitle = (TextView)itemView.findViewById(R.id.tvTitle);
        mRlAllView = (RelativeLayout)itemView.findViewById(R.id.rlAllView);

        mOnRecylerViewListener = onRecylerViewListener;
    }

    @Override
    public void onBindView(ArrayList<LiveCastListObject> mArrayList, final Context mContext, int position) {
        String source = "<font color='#494949'>" + mContext.getString(R.string.row_im_celuv)  + "</font>" + "<font color='#6545E6'>" + mContext.getString(R.string.row_im_celuv_live) + "</font>";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mtvTitle.setText(Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY));
        }else{
            mtvTitle.setText(Html.fromHtml(source));
        }

        mAdapter = new HomeTypeCeluvLiveAdapter(mContext, mArrayList, mOnRecylerViewListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
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
