package com.theenm.android.adapter;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.theenm.android.callback.OnRecylerViewListener;
import com.theenm.android.R;
import com.theenm.common.MyApplication;
import com.theenm.common.http.schemas.CeluvVODListObject;
import com.theenm.common.util.PopkonUtils;

import java.util.ArrayList;

public class HomeTypeVODAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private MyApplication myApp;
    private Context mContext;
    private final ArrayList<CeluvVODListObject> mArrayList;
    private OnRecylerViewListener mOnRecylerViewListener;
    private DisplayImageOptions mOptions = null;

    public HomeTypeVODAdapter(Context context, ArrayList<CeluvVODListObject> mArrayList, OnRecylerViewListener onRecylerViewListener) {
        this.mContext = context;
        this.mArrayList = mArrayList;
        mOnRecylerViewListener = onRecylerViewListener;
        myApp = MyApplication.getInstance();
        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_hometab_vod_row, parent, false);
        LiveVODHolder holder = new LiveVODHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final LiveVODHolder VODHolder = (LiveVODHolder) holder;
        final CeluvVODListObject celuvVODListObject = mArrayList.get(position);

        String pFileName = celuvVODListObject.thumbFileName;
        String castTitle = celuvVODListObject.castTitle;
        String nickName = celuvVODListObject.nickName;
        String watchCnt = celuvVODListObject.watchCnt;

        Drawable drawable = mContext.getResources().getDrawable(R.drawable.play_ic);
        ImageLoader.getInstance().displayImage(pFileName, VODHolder.mIvIcon, mOptions);

        VODHolder.mTvTitle.setText(castTitle);
        VODHolder.mTvSubTitle.setText(nickName);
        VODHolder.mTvViewCount.setText(PopkonUtils.getNumberFormat(watchCnt));
        VODHolder.mTvViewCount.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        if((mArrayList.size() - 1) == position){
            VODHolder.mViewDivider.setVisibility(View.GONE);
        }else{
            VODHolder.mViewDivider.setVisibility(View.VISIBLE);
        }
        VODHolder.mLlHomeVODBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecylerViewListener.onItemClicked(celuvVODListObject);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class LiveVODHolder extends RecyclerView.ViewHolder {

        public TextView mTvSubTitle = null;
        public TextView mTvTitle = null;
        public TextView mTvViewCount = null;
        public ImageView mIvIcon = null;
        public LinearLayout mLlHomeVODBody = null;
        public View mViewDivider = null;

        public LiveVODHolder(View itemView) {
            super(itemView);

            mIvIcon = (ImageView) itemView.findViewById(R.id.ivVod);
            mTvSubTitle = (TextView) itemView.findViewById(R.id.tvSubTitle);
            mLlHomeVODBody = (LinearLayout) itemView.findViewById(R.id.ll_home_vod_body);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTvViewCount = (TextView) itemView.findViewById(R.id.tvViewCount);
            mViewDivider = (View) itemView.findViewById(R.id.view_divider);
        }
    }

}

