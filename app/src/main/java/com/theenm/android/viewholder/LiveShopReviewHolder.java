package com.theenm.android.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.theenm.android.R;
import com.theenm.android.callback.OnRecylerViewListener;
import com.theenm.common.Constant;
import com.theenm.common.DefineCode;
import com.theenm.common.handler.AnimateFirstDisplayListener;
import com.theenm.common.http.schemas.LiveCastListObject;
import com.theenm.common.http.schemas.RecVodListListObject;
import com.theenm.common.util.ObjectUtils;
import com.theenm.common.util.PopkonUtils;

import java.util.Locale;

public class LiveShopReviewHolder extends BaseViewHolder<RecVodListListObject.RecVodList> {
    private final TextView mTvSubTitle;
    private final TextView mTxtTitle;
    private final ImageView mImgCommerce;
    private final ImageView mImgIcon;
    private final ImageView mImgEvent;
    private final ImageView mImgAdult;
    private final ImageView mIvSecure;
    private final ImageView mIvFan;
    private final ImageView mIvPay;
    private final LinearLayout mLayoutRoot;
    private final LinearLayout mLinearListStyle;
    private final MaterialCardView mLayoutCasterLevel;
    private final TextView mTxtCasterLevel;
    private final ImageView mImgHot;
    private final TextView mTxtBottom;
    private final ImageView mImgPreView;
    private final View mViewDivider;

    private final OnRecylerViewListener mOnRecylerViewListener;
    private final DisplayImageOptions mOptions;

    public static LiveShopReviewHolder newInstance(ViewGroup parent, OnRecylerViewListener onRecylerViewListener) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_live, parent, false);

        return new LiveShopReviewHolder(parent, itemView, onRecylerViewListener);
    }

    public LiveShopReviewHolder(ViewGroup parent, View itemView, OnRecylerViewListener onRecylerViewListener) {
        super(itemView);

        mOnRecylerViewListener = onRecylerViewListener;
        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();

        mImgCommerce = itemView.findViewById(R.id.img_commerce);
        mImgIcon = itemView.findViewById(R.id.img_icon);
        mImgEvent = itemView.findViewById(R.id.img_event);
        mImgAdult = itemView.findViewById(R.id.img_adult);
        mIvSecure = itemView.findViewById(R.id.img_secure);
        mTxtBottom =  itemView.findViewById(R.id.text_bottom);
        mIvFan = itemView.findViewById(R.id.img_fan);
        mIvPay = itemView.findViewById(R.id.img_pay);
        mTvSubTitle = itemView.findViewById(R.id.text_nickname);
        mImgPreView = itemView.findViewById(R.id.img_preview);
        mLayoutRoot = itemView.findViewById(R.id.linear_live);
        mLinearListStyle = itemView.findViewById(R.id.linear_list_style);
        mTxtTitle = itemView.findViewById(R.id.text_title);
        mLayoutCasterLevel = itemView.findViewById(R.id.layout_caster_level);
        mTxtCasterLevel = itemView.findViewById(R.id.text_level);
        mImgHot = itemView.findViewById(R.id.img_hot);
        mViewDivider = itemView.findViewById(R.id.view_divider);
    }


    @Override
    public void onBindView(RecVodListListObject.RecVodList recVodList, Context context, int position) {
        if(recVodList.isAdult != null && recVodList.isAdult.equals("1")){
            mImgAdult.setVisibility(View.VISIBLE);
        }else{
            mImgAdult.setVisibility(View.GONE);
        }

        if(recVodList.vCategory != null && recVodList.vCategory.equals(Constant.Commerce.CATEGORY_TYPE_COMMERCE)) {
            mImgCommerce.setVisibility(View.VISIBLE);
        } else {
            mImgCommerce.setVisibility(View.GONE);
        }

        String strBrdcrLvl = recVodList.brdcrLvl;
        if(ObjectUtils.isNumber(strBrdcrLvl)){
            mLayoutCasterLevel.setVisibility(View.VISIBLE);
            mTxtCasterLevel.setBackground(PopkonUtils.getLevelBackground(context, Integer.parseInt(strBrdcrLvl)));
            mTxtCasterLevel.setText(String.format(context.getString(R.string.level_text_caster), Integer.parseInt(strBrdcrLvl)));
        } else {
            mLayoutCasterLevel.setVisibility(View.GONE);
        }

        ImageLoader.getInstance().displayImage(recVodList.vPfileName, mImgIcon, mOptions, new AnimateFirstDisplayListener());
        mTxtTitle.setText(recVodList.vCastTitle);
        mTvSubTitle.setText(recVodList.vNickName);

        mTxtBottom.setVisibility(View.GONE);

//        String strDate = PopkonUtils.getSimpleDate(recVodList.vRegDate, "yyyyMMddHHmmss", "a hh:mm", Locale.US);
//        mTxtBottom.setText(String.format(context.getString(R.string.live_date_count), strDate, PopkonUtils.getNumberFormat(recVodList.vViewCnt)));

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecylerViewListener.onItemClicked(recVodList);
            }
        });
    }
}
