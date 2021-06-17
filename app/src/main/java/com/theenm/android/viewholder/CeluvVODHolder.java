package com.theenm.android.viewholder;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.theenm.android.R;
import com.theenm.android.callback.OnRecylerViewListener;
import com.theenm.common.http.schemas.VODListObject;
import com.theenm.common.util.PopkonUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CeluvVODHolder extends BaseViewHolder<VODListObject> {
    public LinearLayout mLlCeluvLiveBody;
    public ImageView mIvThumbnail;
    public TextView mTvCount;
    public TextView mTvTitle;
    public TextView mTvName;
    public TextView mTvDate;

    private OnRecylerViewListener mOnRecylerViewListener;
    private DisplayImageOptions mOptions = null;

    public static CeluvVODHolder newInstance(ViewGroup parent, OnRecylerViewListener onRecylerViewListener) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vodtab_vod, parent, false);
        return new CeluvVODHolder(itemView, onRecylerViewListener);
    }

    public CeluvVODHolder(View itemView, OnRecylerViewListener onRecylerViewListener) {
        super(itemView);

        mOnRecylerViewListener = onRecylerViewListener;
        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();

        mLlCeluvLiveBody = (LinearLayout)itemView.findViewById(R.id.ll_vodtab_vod_body);
        mIvThumbnail = (ImageView)itemView.findViewById(R.id.iv_thumbnail);
        mTvCount = (TextView) itemView.findViewById(R.id.tvViewCount);
        mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        mTvName = (TextView) itemView.findViewById(R.id.text_nickname);
        mTvDate = (TextView) itemView.findViewById(R.id.text_date);
    }

    @Override
    public void onBindView(VODListObject VODList, final Context context, int position) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date setDate = null;
        try {
            setDate = dateFormat.parse(VODList.vSetDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(setDate != null) {
            long lSetDate = setDate.getTime();
            mTvDate.setText(PopkonUtils.getDifferenceTime(lSetDate));
        }
        mTvName.setText(VODList.vodOwner);
        mTvCount.setText(PopkonUtils.getNumberFormat(VODList.viewCnt));

        mTvTitle.setText(VODList.vodTitle);
        mIvThumbnail.setVisibility(View.VISIBLE);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = width * 9 / 16;
        mIvThumbnail.getLayoutParams().height = height;

        //이미지 사이즈에 맞춰 레이아웃 높이를 조절하는 기능
        ImageLoader.getInstance().displayImage(VODList.vodThumnail, mIvThumbnail, mOptions);

        mLlCeluvLiveBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecylerViewListener.onItemClicked(VODList);
            }
        });
    }
}
