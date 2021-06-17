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
import com.theenm.common.Constant;
import com.theenm.common.DefineCode;
import com.theenm.common.handler.AnimateFirstDisplayListener;
import com.theenm.common.http.schemas.VODListObject;
import com.theenm.common.util.PopkonUtils;

public class VODHolder extends BaseViewHolder<VODListObject> {
    private LinearLayout mLinearVOD;
    private LinearLayout mLinearBottom;
    private ImageView mImg;
    private TextView mTxtTitle;
    private TextView mTxtMiddle;
    private TextView mTxtViewer;
    private TextView mTxtNick;
    private View mDivider;

    private OnRecylerViewListener mOnRecylerViewListener;
    private DisplayImageOptions mOptions;

    public static VODHolder newInstance(ViewGroup parent, OnRecylerViewListener onRecylerViewListener) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_vod, parent, false);

        return new VODHolder(parent, itemView, onRecylerViewListener);
    }

    public VODHolder(ViewGroup parent, View itemView, OnRecylerViewListener onRecylerViewListener) {
        super(itemView);

        mOnRecylerViewListener = onRecylerViewListener;
        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();

        mLinearVOD = itemView.findViewById(R.id.linear_vod);
        mLinearBottom = itemView.findViewById(R.id.ll_bottom);
        mImg = itemView.findViewById(R.id.img_bg);
        mTxtTitle = itemView.findViewById(R.id.text_title);
        mTxtMiddle = itemView.findViewById(R.id.text_middle);
        mTxtNick = itemView.findViewById(R.id.text_nickname);
        mTxtViewer = itemView.findViewById(R.id.text_viewer);
        mDivider = itemView.findViewById(R.id.view_divider);
    }


    @Override
    public void onBindView(VODListObject _VODListObject, Context context, int position) {
        if(DefineCode.PARTNER_CODE.equals("P-00117") && getItemViewType() == Constant.ReCyclerType.TYPE_SEARCH_VOD_ROW.ordinal()){
            mDivider.setVisibility(View.VISIBLE);
        } else {
            mDivider.setVisibility(View.GONE);
        }

        String VODThumnail = _VODListObject.vodThumnail;
        String VODTitle = _VODListObject.vodTitle;
        String VODViewCnt = _VODListObject.viewCnt;
        String VODOwner = _VODListObject.vodOwner;

        ImageLoader.getInstance().displayImage(VODThumnail, mImg, mOptions, new AnimateFirstDisplayListener());

        mTxtTitle.setText(VODTitle);
        if(DefineCode.PARTNER_CODE.equals("P-00117")){
            mTxtMiddle.setVisibility(View.GONE);
            mLinearBottom.setVisibility(View.VISIBLE);
            mTxtNick.setText(VODOwner);
            mTxtViewer.setText(String.format(context.getString(R.string.view_count), PopkonUtils.getNumberFormat(VODViewCnt)));
        } else {
            mTxtMiddle.setVisibility(View.GONE);
            mLinearBottom.setVisibility(View.GONE);
            mTxtMiddle.setText(String.format(context.getString(R.string.view_count), PopkonUtils.getNumberFormat(VODViewCnt)));
        }

        mLinearVOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecylerViewListener.onItemClicked(_VODListObject);
            }
        });
    }
}
