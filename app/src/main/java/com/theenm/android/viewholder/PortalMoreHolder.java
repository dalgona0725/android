package com.theenm.android.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.theenm.android.R;
import com.theenm.category.live.CategoryLiveFragment;
import com.theenm.category.vod.CategoryVODFragment;
import com.theenm.common.Constant;


public class PortalMoreHolder extends BaseViewHolder<String> {
    public ImageView mBtnOll;

    public static PortalMoreHolder newInstance(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_portal_more, parent, false);
        return new PortalMoreHolder(itemView);
    }

    public PortalMoreHolder(View itemView) {
        super(itemView);

        mBtnOll = itemView.findViewById(R.id.btn_oll);
    }

    @Override
    public void onBindView(String text, Context context, int position) {
        mBtnOll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(getItemViewType() == Constant.ReCyclerType.TYPE_POPKON_PORTAL_LIVE_MORE.ordinal() ||
                        getItemViewType() == Constant.ReCyclerType.TYPE_POPKON_PORTAL_ADULT_MORE.ordinal()) {
                    CategoryLiveFragment categoryLiveFragment = CategoryLiveFragment.newInstance(0);
                    categoryLiveFragment.addFragment(context, R.id.fragment_container);
                } else if(getItemViewType() == Constant.ReCyclerType.TYPE_POPKON_PORTAL_VOD_MORE.ordinal()) {
                    CategoryVODFragment categoryVODFragment = CategoryVODFragment.newInstance(-1);
                    categoryVODFragment.addFragment(context, R.id.fragment_container);
                }
            }
        });
    }
}
