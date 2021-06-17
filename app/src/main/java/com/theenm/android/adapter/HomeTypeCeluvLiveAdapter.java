package com.theenm.android.adapter;


import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.theenm.android.R;
import com.theenm.android.callback.OnRecylerViewListener;
import com.theenm.common.Constant;
import com.theenm.common.http.schemas.LiveCastListObject;
import com.theenm.common.util.ObjectUtils;
import com.theenm.common.util.PopkonUtils;

import java.util.ArrayList;

public class HomeTypeCeluvLiveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext = null;
    private final ArrayList<LiveCastListObject> mArrayLiveList;
    private LiveHolder lvHolder;
    private DisplayImageOptions mOptions = null;
    private OnRecylerViewListener mOnRecylerViewListener;
    public HomeTypeCeluvLiveAdapter(Context context, ArrayList<LiveCastListObject> mArrayList, OnRecylerViewListener onRecylerViewListener) {
        mContext = context;
        this.mArrayLiveList = mArrayList;
        mOnRecylerViewListener = onRecylerViewListener;
        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_hometab_celuv_live_row, parent, false);
        LiveHolder holder = new LiveHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        lvHolder = (LiveHolder) holder;

        final LiveCastListObject liveList = mArrayLiveList.get(position);

        int watchCnt = 0;
        if( ObjectUtils.isNumber(liveList.watchCnt) )
            watchCnt = Integer.parseInt(liveList.watchCnt);

        int limitNumber = 10;
        if( ObjectUtils.isNumber(liveList.limitNumber) )
            limitNumber = Integer.parseInt(liveList.limitNumber);

        if( watchCnt < limitNumber ) {
            lvHolder.mTvCount.setText(PopkonUtils.getNumberFormat(liveList.watchCnt));
        }else{
            lvHolder.mTvCount.setText(mContext.getString(R.string.live_date_count_full));
        }

        if(liveList.category != null && liveList.category.equals(Constant.Commerce.CATEGORY_TYPE_COMMERCE)) {
            lvHolder.mImgCommerce.setVisibility(View.VISIBLE);
        } else {
            lvHolder.mImgCommerce.setVisibility(View.GONE);
        }

        lvHolder.mTvName.setText(liveList.nickName);
        lvHolder.mTvTitle.setText(liveList.castTitle);
        lvHolder.mIvThumbnail.setVisibility(View.VISIBLE);
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = width *  9 / 16;
        lvHolder.mIvThumbnail.getLayoutParams().height = height;

        if (liveList.isPrivate != null && liveList.isPrivate.equals("0")) { // 0:공개 방송, 1:비공개 방송
            lvHolder.mIvSecure.setVisibility(View.GONE);
        }else {
            lvHolder.mIvSecure.setVisibility(View.VISIBLE);
        }

        int castType = Integer.parseInt(liveList.castType);
        switch (castType) {
            case 5: // 5:팬클럽 방송(1:N)
                lvHolder.mIvFan.setVisibility(View.VISIBLE);
                lvHolder.mIvPay.setVisibility(View.GONE);
                break;

            case 7: // 7:유료 방송(1:N)
                lvHolder.mIvFan.setVisibility(View.GONE);
                lvHolder.mIvPay.setVisibility(View.VISIBLE);
                break;

            default:
                lvHolder.mIvFan.setVisibility(View.GONE);
                lvHolder.mIvPay.setVisibility(View.GONE);
                break;
        }

        String strBrdcrLvl = liveList.brdcrLvl;
        if(ObjectUtils.isNumber(strBrdcrLvl)){
            lvHolder.mLayoutCasterLevel.setVisibility(View.VISIBLE);
            lvHolder.mTxtCasterLevel.setBackground(PopkonUtils.getLevelBackground(mContext, Integer.parseInt(strBrdcrLvl)));
            lvHolder.mTxtCasterLevel.setText(String.format(mContext.getString(R.string.level_text_caster), Integer.parseInt(strBrdcrLvl)));
        } else {
            lvHolder.mLayoutCasterLevel.setVisibility(View.GONE);
        }

        lvHolder.mLlHomeLiveBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecylerViewListener.onItemClicked(liveList);
            }
        });
        ImageLoader.getInstance().displayImage(liveList.pFileName, lvHolder.mIvThumbnail, mOptions);
    }

    @Override
    public int getItemCount() {
        return mArrayLiveList.size();
    }

    public class LiveHolder extends RecyclerView.ViewHolder {
        public LinearLayout mLlHomeLiveBody;
        public ImageView mIvThumbnail;
        public ImageView mIvAdult;
        public ImageView mIvSecure;
        public ImageView mIvFan;
        public ImageView mIvPay;
        public ImageView mImgCommerce;
        public TextView mTvCount;
        public TextView mTvTitle;
        public TextView mTvName;
        public MaterialCardView mLayoutCasterLevel;
        public TextView mTxtCasterLevel;

        public LiveHolder(View itemView) {
            super(itemView);
            mLlHomeLiveBody = (LinearLayout)itemView.findViewById(R.id.ll_home_live_body);
            mIvAdult = (ImageView)itemView.findViewById(R.id.img_adult);
            mIvSecure = (ImageView)itemView.findViewById(R.id.img_secure);
            mIvFan = (ImageView) itemView.findViewById(R.id.img_fan);
            mIvPay = (ImageView) itemView.findViewById(R.id.img_pay);
            mIvThumbnail = (ImageView)itemView.findViewById(R.id.iv_thumbnail);
            mImgCommerce = (ImageView) itemView.findViewById(R.id.iv_commerce);
            mTvCount = (TextView) itemView.findViewById(R.id.tvCount);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTvName = (TextView) itemView.findViewById(R.id.text_nickname);
            mLayoutCasterLevel = (MaterialCardView) itemView.findViewById(R.id.layout_caster_level);
            mTxtCasterLevel = (TextView) itemView.findViewById(R.id.text_level);
        }
    }}

