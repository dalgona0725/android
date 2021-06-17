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
import com.theenm.common.util.ObjectUtils;
import com.theenm.common.util.PopkonUtils;

import java.util.Locale;

public class LiveHolder extends BaseViewHolder<LiveCastListObject> {
    private final TextView mTvSubTitle;
    private final TextView mTxtTitle;
    private final ImageView mImgCommerce;
    private final ImageView mImgIcon;
    private final ImageView mImgEvent;
    private final ImageView mImgAdult;
    private final ImageView mIvSecure;
    private final ImageView mIvFan;
    private final ImageView mIvPay;
    private final LinearLayout mLinearListStyle;
    private final MaterialCardView mLayoutCasterLevel;
    private final TextView mTxtCasterLevel;
    private final ImageView mImgHot;
    private final TextView mTxtBottom;
    private final ImageView mImgPreView;
    private final View mViewDivider;

    private final OnRecylerViewListener mOnRecylerViewListener;
    private final DisplayImageOptions mOptions;

    public static LiveHolder newInstance(ViewGroup parent, OnRecylerViewListener onRecylerViewListener) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_live, parent, false);

        return new LiveHolder(parent, itemView, onRecylerViewListener);
    }

    public LiveHolder(ViewGroup parent, View itemView, OnRecylerViewListener onRecylerViewListener) {
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
        mLinearListStyle = itemView.findViewById(R.id.linear_list_style);
        mTxtTitle = itemView.findViewById(R.id.text_title);
        mLayoutCasterLevel = itemView.findViewById(R.id.layout_caster_level);
        mTxtCasterLevel = itemView.findViewById(R.id.text_level);
        mImgHot = itemView.findViewById(R.id.img_hot);
        mViewDivider = itemView.findViewById(R.id.view_divider);
    }


    @Override
    public void onBindView(LiveCastListObject liveList, Context context, int position) {
        if(DefineCode.PARTNER_CODE.equals("P-00117") && getItemViewType() == Constant.ReCyclerType.TYPE_SEARCH_LIVE_ROW.ordinal()){
            mViewDivider.setVisibility(View.VISIBLE);
        } else {
            mViewDivider.setVisibility(View.GONE);
        }

        String strListDecorateType = liveList.listDecorateType;

        if (strListDecorateType != null) {
            if (strListDecorateType.equals("1"))
                mLinearListStyle.setBackgroundResource(R.drawable.list_deco1);
            else if (strListDecorateType.equals("2"))
                mLinearListStyle.setBackgroundResource(R.drawable.list_deco2);
            else if (strListDecorateType.equals("3"))
                mLinearListStyle.setBackgroundResource(R.drawable.list_deco3);
            else
                mLinearListStyle.setBackgroundColor(Color.WHITE);
        } else {
            mLinearListStyle.setBackgroundColor(Color.WHITE);
        }

        if(liveList.anniversaryImg != null) {
            mImgEvent.setVisibility(View.VISIBLE);
            Glide.with(context).load(liveList.anniversaryImg).into(mImgEvent);
        }else{
            mImgEvent.setVisibility(View.GONE);
        }

        if(liveList.category != null && liveList.category.equals(Constant.Commerce.CATEGORY_TYPE_COMMERCE)) {
            mImgCommerce.setVisibility(View.VISIBLE);
        } else {
            mImgCommerce.setVisibility(View.GONE);
        }

        if (liveList.castListNum.equals("2")) {
            mImgHot.setVisibility(View.VISIBLE);
            mImgHot.setImageResource(R.drawable.listitem_silver);
        } else if (liveList.castListNum.equals("3")) {
            mImgHot.setVisibility(View.VISIBLE);
            mImgHot.setImageResource(R.drawable.listitem_gold);
        } else if (liveList.castListNum.equals("4")) {
            mImgHot.setVisibility(View.VISIBLE);
            mImgHot.setImageResource(R.drawable.listitem_best);
        } else {
            mImgHot.setVisibility(View.GONE);
        }

        if (liveList.isPrivate.equals("0")) // 0:공개 방송, 1:비공개 방송
            mIvSecure.setVisibility(View.GONE);
        else
            mIvSecure.setVisibility(View.VISIBLE);

        if(liveList.isAdult != null && liveList.isAdult.equals("1")){
            mImgAdult.setVisibility(View.VISIBLE);
        }else{
            mImgAdult.setVisibility(View.GONE);
        }

        int castType = Integer.parseInt(liveList.castType);
        switch (castType) {
            case 5: // 5:팬클럽 방송(1:N)
                mIvFan.setVisibility(View.VISIBLE);
                mIvPay.setVisibility(View.GONE);
                break;

            case 7: // 7:유료 방송(1:N)
                mIvFan.setVisibility(View.GONE);
                mIvPay.setVisibility(View.VISIBLE);
                break;

            default:
                mIvFan.setVisibility(View.GONE);
                mIvPay.setVisibility(View.GONE);
                break;
        }

        if (liveList.isPrivate.equals("1") || castType == 5 || castType == 7 || liveList.isAdult.equals("1") || DefineCode.PARTNER_CODE.equals("P-00117"))
            mImgPreView.setVisibility(View.GONE);
        else
            mImgPreView.setVisibility(View.VISIBLE);

        String strBrdcrLvl = liveList.brdcrLvl;
        if(ObjectUtils.isNumber(strBrdcrLvl)){
            mLayoutCasterLevel.setVisibility(View.VISIBLE);
            mTxtCasterLevel.setBackground(PopkonUtils.getLevelBackground(context, Integer.parseInt(strBrdcrLvl)));
            mTxtCasterLevel.setText(String.format(context.getString(R.string.level_text_caster), Integer.parseInt(strBrdcrLvl)));
        } else {
            mLayoutCasterLevel.setVisibility(View.GONE);
        }

        ImageLoader.getInstance().displayImage(liveList.pFileName, mImgIcon, mOptions, new AnimateFirstDisplayListener());
        mTxtTitle.setText(liveList.castTitle);
        mTvSubTitle.setText(liveList.nickName);

        String strDate = PopkonUtils.getSimpleDate(liveList.castStartDate, "yyyyMMddHHmmss", "a hh:mm", Locale.US);

        if(getItemViewType() == Constant.ReCyclerType.TYPE_LIVE_SHOP_REVIEW_ROW.ordinal()) {
            mTxtBottom.setVisibility(View.GONE);
        } else {
            int watchCnt = 0;
            if (ObjectUtils.isNumber(liveList.watchCnt))
                watchCnt = Integer.parseInt(liveList.watchCnt);

            int limitNumber = 10;
            if (ObjectUtils.isNumber(liveList.limitNumber))
                limitNumber = Integer.parseInt(liveList.limitNumber);

            if (watchCnt < limitNumber)
                mTxtBottom.setText(String.format(context.getString(R.string.live_date_count), strDate, PopkonUtils.getNumberFormat(liveList.watchCnt)));
            else {
                String strBottom = String.format("<B>%s | </B> <B><font color=#F8B62A>%s</font></B>", strDate, context.getString(R.string.full));
                mTxtBottom.setText(Html.fromHtml(strBottom));
            }
        }

        mImgPreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecylerViewListener.onPreviewClicked(liveList);
            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecylerViewListener.onItemClicked(liveList);
            }
        });
    }
}
