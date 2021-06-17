package com.theenm.android.viewholder;

import android.content.Context;
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
import com.theenm.common.handler.AnimateFirstDisplayListener;
import com.theenm.common.http.schemas.FanChannelObject;
import com.theenm.common.util.PopkonUtils;


public class PortalVODHolder extends BaseViewHolder<FanChannelObject> {
    private final LinearLayout mLinearList;
    private final ImageView mImgIcon;
    private final TextView mTextNickname;
    private final TextView mTextTimeAndCount;
    private final TextView mTextTitle;

    private final OnRecylerViewListener mOnRecylerViewListener;
    private final DisplayImageOptions mOptions;

    public static PortalVODHolder newInstance(ViewGroup parent, OnRecylerViewListener onRecylerViewListener) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_portal_fan, parent, false);
        return new PortalVODHolder(itemView, onRecylerViewListener);
    }

    public PortalVODHolder(View itemView, OnRecylerViewListener onRecylerViewListener) {
        super(itemView);

        mOnRecylerViewListener = onRecylerViewListener;
        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();

        mLinearList = itemView.findViewById(R.id.linear_list);
        mImgIcon = itemView.findViewById(R.id.img_icon);
        mTextNickname = itemView.findViewById(R.id.text_nicname);
        mTextTimeAndCount = itemView.findViewById(R.id.text_time_and_count);
        mTextTitle = itemView.findViewById(R.id.text_title);
    }

    @Override
    public void onBindView(FanChannelObject fanChannelObject, Context context, int position) {
        String strYear = fanChannelObject.vMaxDate.substring(0, 4);
        String strMonth = fanChannelObject.vMaxDate.substring(5, 7);
        String strDey = fanChannelObject.vMaxDate.substring(8, 10);

        String strMaxDate = String.format(context.getString(R.string.vod_max_date), strYear, strMonth, strDey);

        mTextTimeAndCount.setText(String.format(context.getString(R.string.live_date_count), strMaxDate, PopkonUtils.getNumberFormat(fanChannelObject.vViewCnt)));

        ImageLoader.getInstance().displayImage(fanChannelObject.vPfileName, mImgIcon, mOptions, new AnimateFirstDisplayListener());
        mTextTitle.setText(String.format(context.getString(R.string.someone_fan_channel), fanChannelObject.vNickName));
        mTextNickname.setText(String.format(context.getString(R.string.contents_count), fanChannelObject.vVodCnt));
        mImgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecylerViewListener.onItemClicked(fanChannelObject);
            }
        });
    }
}
