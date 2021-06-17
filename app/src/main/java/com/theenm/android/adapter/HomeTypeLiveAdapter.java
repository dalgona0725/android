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

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.theenm.android.R;
import com.theenm.android.callback.OnRecylerViewListener;
import com.theenm.common.Constant;
import com.theenm.common.MyApplication;
import com.theenm.common.http.schemas.LiveCastListObject;
import com.theenm.common.util.ObjectUtils;
import com.theenm.common.util.PopkonUtils;

import java.util.ArrayList;

public class HomeTypeLiveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private MyApplication myApp;
    private Context mContext;
    private final ArrayList<LiveCastListObject> mArrayList;
    private OnRecylerViewListener mOnRecylerViewListener;
    private DisplayImageOptions mOptions = null;

    public HomeTypeLiveAdapter(Context context, ArrayList<LiveCastListObject> mArrayList, OnRecylerViewListener onRecylerViewListener) {
        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
        this.mContext = context;
        this.mArrayList = mArrayList;
        mOnRecylerViewListener = onRecylerViewListener;
        myApp = MyApplication.getInstance();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_hometab_live_row, parent, false);
        LiveVODHolder holder = new LiveVODHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final LiveVODHolder liveHolder = (LiveVODHolder) holder;
        final LiveCastListObject liveList = mArrayList.get(position);

        ImageLoader.getInstance().displayImage(liveList.pFileName, liveHolder.mIvIcon, mOptions);

        if(liveList.anniversaryImg != null) {
            liveHolder.mImgEvent.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(liveList.anniversaryImg).into(liveHolder.mImgEvent);
        }else{
            liveHolder.mImgEvent.setVisibility(View.GONE);
        }

//        if(liveList.isAdult != null && liveList.isAdult.equals("1")){
//            liveHolder.mIvAdult.setVisibility(View.VISIBLE);
//        }else{
            liveHolder.mIvAdult.setVisibility(View.GONE);
//        }
        if (liveList.isPrivate != null && liveList.isPrivate.equals("0")) { // 0:공개 방송, 1:비공개 방송
            liveHolder.mIvSecure.setVisibility(View.GONE);
        }else {
            liveHolder.mIvSecure.setVisibility(View.VISIBLE);
        }

        int castType = Integer.parseInt(liveList.castType);
        switch (castType) {
            case 5: // 5:팬클럽 방송(1:N)
                liveHolder.mIvFan.setVisibility(View.VISIBLE);
                liveHolder.mIvPay.setVisibility(View.GONE);
                break;

            case 7: // 7:유료 방송(1:N)
                liveHolder.mIvFan.setVisibility(View.GONE);
                liveHolder.mIvPay.setVisibility(View.VISIBLE);
                break;

            default:
                liveHolder.mIvFan.setVisibility(View.GONE);
                liveHolder.mIvPay.setVisibility(View.GONE);
                break;
        }

        String strBrdcrLvl = liveList.brdcrLvl;
        if(ObjectUtils.isNumber(strBrdcrLvl)){
            liveHolder.mLayoutCasterLevel.setVisibility(View.VISIBLE);
            liveHolder.mTxtCasterLevel.setBackground(PopkonUtils.getLevelBackground(mContext, Integer.parseInt(strBrdcrLvl)));
            liveHolder.mTxtCasterLevel.setText(String.format(mContext.getString(R.string.level_text_caster), Integer.parseInt(strBrdcrLvl)));
        } else {
            liveHolder.mLayoutCasterLevel.setVisibility(View.GONE);
        }

        if(liveList.category != null && liveList.category.equals(Constant.Commerce.CATEGORY_TYPE_COMMERCE)) {
            liveHolder.mImgCommerce.setVisibility(View.VISIBLE);
        } else {
            liveHolder.mImgCommerce.setVisibility(View.GONE);
        }

        liveHolder.mTvTitle.setText(liveList.castTitle);
        liveHolder.mTvSubTitle.setText(liveList.nickName);

        int watchCnt = 0;
        if( ObjectUtils.isNumber(liveList.watchCnt) )
            watchCnt = Integer.parseInt(liveList.watchCnt);

        int limitNumber = 10;
        if( ObjectUtils.isNumber(liveList.limitNumber) )
            limitNumber = Integer.parseInt(liveList.limitNumber);

        Drawable drawable = mContext.getResources().getDrawable(R.drawable.countb_ic);

        if( watchCnt < limitNumber ) {
            liveHolder.mTvViewCount.setText(PopkonUtils.getNumberFormat(liveList.watchCnt));
        }else{
            liveHolder.mTvViewCount.setText(mContext.getString(R.string.live_date_count_full));
        }
        liveHolder.mTvViewCount.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

        if((mArrayList.size() - 1) == position){
            liveHolder.mViewDivider.setVisibility(View.GONE);
        }else{
            liveHolder.mViewDivider.setVisibility(View.VISIBLE);
        }
        liveHolder.mLlHomeVODBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecylerViewListener.onItemClicked(liveList);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class LiveVODHolder extends RecyclerView.ViewHolder {

        public TextView mTvSubTitle;
        public TextView mTvTitle;
        public TextView mTvViewCount;
        public ImageView mImgCommerce;
        public ImageView mIvIcon;
        public ImageView mImgEvent;
        public ImageView mIvAdult;
        public ImageView mIvSecure;
        public ImageView mIvFan;
        public ImageView mIvPay;
        public LinearLayout mLlHomeVODBody;
        public View mViewDivider;
        public MaterialCardView mLayoutCasterLevel;
        public TextView mTxtCasterLevel;

        public LiveVODHolder(View itemView) {
            super(itemView);

            mImgCommerce = (ImageView) itemView.findViewById(R.id.iv_commerce);
            mIvIcon = (ImageView) itemView.findViewById(R.id.ivLive);
            mImgEvent = (ImageView) itemView.findViewById(R.id.img_event);
            mIvAdult = (ImageView)itemView.findViewById(R.id.img_adult);
            mIvSecure = (ImageView)itemView.findViewById(R.id.img_secure);
            mIvFan = (ImageView) itemView.findViewById(R.id.img_fan);
            mIvPay = (ImageView) itemView.findViewById(R.id.img_pay);
            mTvSubTitle = (TextView) itemView.findViewById(R.id.tvSubTitle);
            mLlHomeVODBody = (LinearLayout) itemView.findViewById(R.id.ll_home_live_body);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTvViewCount = (TextView) itemView.findViewById(R.id.tvViewCount);
            mViewDivider = (View) itemView.findViewById(R.id.view_divider);
            mLayoutCasterLevel = (MaterialCardView) itemView.findViewById(R.id.layout_caster_level);
            mTxtCasterLevel = (TextView) itemView.findViewById(R.id.text_level);
        }
    }

}

