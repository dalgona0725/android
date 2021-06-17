package com.theenm.android.viewholder;

import android.content.Context;
import android.text.Html;
import android.util.Log;
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


public class PortalLiveHolder extends BaseViewHolder<LiveCastListObject> {
    private final LinearLayout mLinearList;
    private final ImageView mImgIcon;
    private final TextView mTextNicname;
    private final TextView mTextTimeAndCount;
    private final TextView mTextTitle;
    private final ImageView mImgLive;
    private final ImageView mImgPreview;
    private final ImageView mImgSecure;
    private final ImageView mImgEvent;
    private final ImageView mImgFan;
    private final ImageView mImgPay;
    private final ImageView mImgAdult;
    private final ImageView mImgDelete;
    private final ImageView mImgHot;
    private final ImageView mImgCommerce;
    private final LinearLayout colorBgLy;
    private final LinearLayout linearListStyle;
    private final LinearLayout mLieanrIcons;

    private final MaterialCardView mLayoutCasterLevel;
    private final TextView mTxtCasterLevel;

    private final OnRecylerViewListener mOnRecylerViewListener;
    private final DisplayImageOptions mOptions;

    public static PortalLiveHolder newInstance(ViewGroup parent, OnRecylerViewListener onRecylerViewListener) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_portal_live, parent, false);
        return new PortalLiveHolder(itemView, onRecylerViewListener);
    }

    public PortalLiveHolder(View itemView, OnRecylerViewListener onRecylerViewListener) {
        super(itemView);

        mOnRecylerViewListener = onRecylerViewListener;
        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();

        mLinearList = itemView.findViewById(R.id.linear_list);
        mImgIcon = itemView.findViewById(R.id.img_icon);
        mTextNicname = itemView.findViewById(R.id.text_nicname);
        mTextTimeAndCount = itemView.findViewById(R.id.text_time_and_count);
        mTextTitle = itemView.findViewById(R.id.text_title);
        mImgLive = itemView.findViewById(R.id.img_live);
        mImgPreview = itemView.findViewById(R.id.img_preview);
        mImgSecure = itemView.findViewById(R.id.img_secure);
        mImgEvent = itemView.findViewById(R.id.img_event);
        mImgFan = itemView.findViewById(R.id.img_fan);
        mImgPay = itemView.findViewById(R.id.img_pay);
        mImgAdult = itemView.findViewById(R.id.img_adult);
        mImgDelete = itemView.findViewById(R.id.img_delete);
        mImgHot = itemView.findViewById(R.id.img_hot);
        mImgCommerce = itemView.findViewById(R.id.iv_commerce);
        colorBgLy = itemView.findViewById(R.id.colorBgLy);
        linearListStyle = itemView.findViewById(R.id.linear_list_style);
        mLieanrIcons = itemView.findViewById(R.id.linear_icons);
        mLayoutCasterLevel =itemView.findViewById(R.id.layout_caster_level);
        mTxtCasterLevel = itemView.findViewById(R.id.text_level);
    }

    @Override
    public void onBindView(LiveCastListObject liveCastListObject, Context context, int position) {
        String strListDecorateType = liveCastListObject.listDecorateType;

        if(getItemViewType() == Constant.ReCyclerType.TYPE_BOOKMARK_LIVE_ON_DELETE_ROW.ordinal() || getItemViewType() == Constant.ReCyclerType.TYPE_BOOKMARK_LIVE_OFF_DELETE_ROW.ordinal()) {
            mLinearList.setEnabled(false);
            mImgDelete.setVisibility(View.VISIBLE);
            mLieanrIcons.setVisibility(View.GONE);

            mImgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnRecylerViewListener.onBookmarkDeleted(liveCastListObject, position);
                }
            });
        } else {
            mLinearList.setEnabled(true);
            mImgDelete.setVisibility(View.GONE);
            mLieanrIcons.setVisibility(View.VISIBLE);
        }

        ImageLoader.getInstance().displayImage(liveCastListObject.pFileName, mImgIcon, mOptions, new AnimateFirstDisplayListener());

        mImgEvent.getLayoutParams().width = mImgIcon.getLayoutParams().height;
        mImgEvent.getLayoutParams().height = mImgIcon.getLayoutParams().height;
        if(liveCastListObject.anniversaryImg != null) {
            mImgEvent.setVisibility(View.VISIBLE);
            Glide.with(context).load(liveCastListObject.anniversaryImg).into(mImgEvent);
        }else{
            mImgEvent.setVisibility(View.GONE);
        }

        String strBrdcrLvl = liveCastListObject.brdcrLvl;
        if(ObjectUtils.isNumber(strBrdcrLvl)){
            mLayoutCasterLevel.setVisibility(View.VISIBLE);
            mTxtCasterLevel.setBackground(PopkonUtils.getLevelBackground(context, Integer.parseInt(strBrdcrLvl)));
            mTxtCasterLevel.setText(String.format(context.getString(R.string.level_text_caster), Integer.parseInt(strBrdcrLvl)));
        } else {
            mLayoutCasterLevel.setVisibility(View.GONE);
        }

        mTextNicname.setText(liveCastListObject.nickName);

        int castStatus = 0;
        if(liveCastListObject.castStatus == null) {
            castStatus = 0;
        } else {
            castStatus = Integer.parseInt(liveCastListObject.castStatus);
        }

        if(castStatus == 0) {
            if(getItemViewType() == Constant.ReCyclerType.TYPE_BOOKMARK_LIVE_ON_DELETE_ROW.ordinal() || getItemViewType() == Constant.ReCyclerType.TYPE_BOOKMARK_LIVE_ON_ROW.ordinal()) {
                mImgLive.setVisibility(View.VISIBLE);
            } else {
                mImgLive.setVisibility(View.GONE);
            }

            colorBgLy.setVisibility(View.VISIBLE);

            if(liveCastListObject.castListNum != null) {
                if( liveCastListObject.castListNum.equals("2")) {
                    mImgHot.setVisibility(View.VISIBLE);
                    mImgHot.setImageResource(R.drawable.listitem_silver);
                } else if(liveCastListObject.castListNum.equals("3")) {
                    mImgHot.setVisibility(View.VISIBLE);
                    mImgHot.setImageResource(R.drawable.listitem_gold);
                } else if(liveCastListObject.castListNum.equals("4")) {
                    mImgHot.setVisibility(View.VISIBLE);
                    mImgHot.setImageResource(R.drawable.listitem_best);
                } else {
                    mImgHot.setVisibility(View.GONE);
                    mImgHot.setImageResource(0);
                }
            }else{
                mImgHot.setVisibility(View.GONE);
                mImgHot.setImageResource(0);
            }

            if(liveCastListObject.category != null && liveCastListObject.category.equals(Constant.Commerce.CATEGORY_TYPE_COMMERCE)) {
                mImgCommerce.setVisibility(View.VISIBLE);
            } else {
                mImgCommerce.setVisibility(View.GONE);
            }

            if( strListDecorateType != null )
            {
                if( strListDecorateType.equals("1") )
                    linearListStyle.setBackgroundResource(R.drawable.list_deco_portal_01);
                else if( strListDecorateType.equals("2") )
                    linearListStyle.setBackgroundResource(R.drawable.list_deco_portal_02);
                else if( strListDecorateType.equals("3") )
                    linearListStyle.setBackgroundResource(R.drawable.list_deco_portal_03);
                else
                    linearListStyle.setBackgroundResource(0);
            } else {
                linearListStyle.setBackgroundResource(0);
            }

            if( liveCastListObject.isPrivate.equals("0") ) // 0:공개 방송, 1:비공개 방송
                mImgSecure.setVisibility(View.GONE);
            else
                mImgSecure.setVisibility(View.VISIBLE);

            if(liveCastListObject.isAdult != null && liveCastListObject.isAdult.equals("1")){
                mImgAdult.setVisibility(View.VISIBLE);
            }else{
                mImgAdult.setVisibility(View.GONE);
            }

            int castType = Integer.parseInt(liveCastListObject.castType);
            switch( castType )
            {
                case 5: // 5:팬클럽 방송(1:N)
                    mImgFan.setVisibility(View.VISIBLE);
                    mImgPay.setVisibility(View.GONE);
                    break;

                case 7: // 7:유료 방송(1:N)
                    mImgPay.setVisibility(View.VISIBLE);
                    mImgFan.setVisibility(View.GONE);
                    break;

                default:
                    mImgFan.setVisibility(View.GONE);
                    mImgPay.setVisibility(View.GONE);
                    break;
            }

            if (liveCastListObject.isPrivate.equals("1") || castType == 5 || castType == 7 ||
                    liveCastListObject.isAdult.equals("1") || DefineCode.PARTNER_CODE.equals("P-00117") ||
                    getItemViewType() == Constant.ReCyclerType.TYPE_BOOKMARK_LIVE_ON_DELETE_ROW.ordinal() ||
                    getItemViewType() == Constant.ReCyclerType.TYPE_BOOKMARK_LIVE_ON_ROW.ordinal() ||
                    getItemViewType() == Constant.ReCyclerType.TYPE_BOOKMARK_LIVE_OFF_DELETE_ROW.ordinal() ||
                    getItemViewType() == Constant.ReCyclerType.TYPE_BOOKMARK_LIVE_OFF_ROW.ordinal())
                mImgPreview.setVisibility(View.INVISIBLE);
            else
                mImgPreview.setVisibility(View.VISIBLE);

            String strDate = PopkonUtils.getSimpleDate(liveCastListObject.castStartDate, "yyyyMMddHHmmss", "a hh:mm", Locale.US);
            int watchCnt = 0;
            if( ObjectUtils.isNumber(liveCastListObject.watchCnt) )
                watchCnt = Integer.parseInt(liveCastListObject.watchCnt);

            int limitNumber = 10;
            if( ObjectUtils.isNumber(liveCastListObject.limitNumber) )
                limitNumber = Integer.parseInt(liveCastListObject.limitNumber);

            if( watchCnt < limitNumber )
                mTextTimeAndCount.setText(String.format(context.getString(R.string.live_date_count), strDate, PopkonUtils.getNumberFormat(liveCastListObject.watchCnt)));
            else
            {
                String strBottom = String.format("%s | <B><font color=#F8B62A>%s</font></B>", strDate, "Full");
                mTextTimeAndCount.setText(Html.fromHtml(strBottom));
            }

            mTextTitle.setText(liveCastListObject.castTitle);
        } else {
            mImgLive.setVisibility(View.GONE);
            /**
             * 즐겨찾기 화면 미방송일때만 방송종료시간 노출
             */
            if(getItemViewType() == Constant.ReCyclerType.TYPE_BOOKMARK_LIVE_OFF_ROW.ordinal() || getItemViewType() == Constant.ReCyclerType.TYPE_BOOKMARK_LIVE_OFF_DELETE_ROW.ordinal())
            {
                String strDate = PopkonUtils.getSimpleDate(liveCastListObject.castLastDate, "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm", Locale.US);
                mTextTimeAndCount.setText(strDate);
                colorBgLy.setVisibility(View.VISIBLE);
            }else
            {
                colorBgLy.setVisibility(View.GONE);
            }
        }


        mImgPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mOnRecylerViewListener.onPreviewClicked(liveCastListObject);
            }
        });
        mImgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mOnRecylerViewListener.onItemClicked(liveCastListObject);
            }
        });
        /**
         * IOS 즐찾에서 편집시에도 클릭시 방송시청 넘어가고 있어서 기존소스 주석처리
         */
    /*  if(getItemViewType() == Constant.ReCyclerType.TYPE_BOOKMARK_LIVE_ON_DELETE_ROW.ordinal()){
            mImgPreview.setOnClickListener(null);
            mImgIcon.setOnClickListener(null);
        } else {
            mImgPreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    mOnRecylerViewListener.onPreviewClicked(liveCastListObject);
                }
            });
            mImgIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    mOnRecylerViewListener.onItemClicked(liveCastListObject);
                }
            });
        }*/
    }
}
