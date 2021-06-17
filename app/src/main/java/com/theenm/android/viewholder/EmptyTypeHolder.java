package com.theenm.android.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.theenm.android.R;
import com.theenm.common.Constant;
import com.theenm.common.DefineCode;
import com.theenm.common.util.PopkonUtils;

public class EmptyTypeHolder extends BaseViewHolder<String> {
    private final ImageView mImgEmpty;
    private final TextView mtvEmpty;

    public static EmptyTypeHolder newInstance(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_check_empty, parent, false);

        return new EmptyTypeHolder(parent, itemView);
    }

    public EmptyTypeHolder(ViewGroup parent, View itemView) {
        super(itemView);

        RecyclerView.LayoutManager layoutManager = ((RecyclerView) parent).getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            ((GridLayoutManager)layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return  ((GridLayoutManager)layoutManager).getSpanCount();
                }
            });
        }
        mImgEmpty = itemView.findViewById(R.id.img_empty);
        mtvEmpty = itemView.findViewById(R.id.tv_empty);
    }

    @Override
    public void onBindView(String message, final Context context, int position) {
        if(DefineCode.PARTNER_CODE.equals("P-00117") &&
                (getItemViewType() == Constant.ReCyclerType.TYPE_SEARCH_LIVE_EMPTY_LIST.ordinal() ||
                        getItemViewType() == Constant.ReCyclerType.TYPE_SEARCH_VOD_EMPTY_LIST.ordinal())) {
            mImgEmpty.setVisibility(View.VISIBLE);
        } else {
            mImgEmpty.setVisibility(View.GONE);
        }

        if(getItemViewType() == Constant.ReCyclerType.TYPE_LIVE_SHOP_LIVE_EMPTY_LIST.ordinal() ||
                        getItemViewType() == Constant.ReCyclerType.TYPE_LIVE_SHOP_REVIEW_EMPTY_LIST.ordinal()){
            itemView.setPadding(0, PopkonUtils.convertDpToPixels(context, 30), 0, PopkonUtils.convertDpToPixels(context, 30));
        } else {
            itemView.setPadding(0, PopkonUtils.convertDpToPixels(context, 100), 0, 0);
        }
        mtvEmpty.setText(message);
    }
}
