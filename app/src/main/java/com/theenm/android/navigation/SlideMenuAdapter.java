package com.theenm.android.navigation;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.theenm.android.BuildConfig;
import com.theenm.android.R;
import com.theenm.common.UserData;
import com.theenm.common.util.PopkonUtils;

public class SlideMenuAdapter extends BaseAdapter {

    private Context mContext;
    private final LayoutInflater mLayoutInflater;
    private String[] mNameList;
    private int nSelectedPosition = 0;
    private SelectRowListener m_listener;

    public interface SelectRowListener {
        void onSelect(int position);
    }

    static class ViewHolder {
        TextView mTvName;
        RelativeLayout mRlBody;
        ImageView mIvArrow;
    }

    public SlideMenuAdapter(Context context, String[] accountList, SelectRowListener listener) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mNameList = accountList;
        m_listener = listener;
    }

    @Override
    public int getCount() {
        return mNameList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_slide, parent, false);

            holder = new ViewHolder();
            holder.mRlBody = (RelativeLayout) convertView.findViewById(R.id.rlBody);
            holder.mTvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.mIvArrow = (ImageView) convertView.findViewById(R.id.ivArrow);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (nSelectedPosition == position) {
            holder.mTvName.setTextColor(PopkonUtils.colorSetting(mContext, R.color.accent));
            holder.mIvArrow.setVisibility(View.VISIBLE);

        } else {
            holder.mTvName.setTextColor(PopkonUtils.colorSetting(mContext, R.color.color_626262));
            holder.mIvArrow.setVisibility(View.GONE);
        }

        holder.mTvName.setText(mNameList[position]);

        // 2020-11-25 my.kim 원스토어용 심사 유/무 값으로 구매 부분 숨김처리
        if(position == 4){
            if(BuildConfig.FLAVOR.contains("_skt") && UserData.isUpdate()){
                holder.mRlBody.setVisibility(View.GONE);
            } else{
                holder.mRlBody.setVisibility(View.VISIBLE);
            }
        }

        holder.mRlBody.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onSelectIndex(position);
                notifyDataSetChanged();

                if (nSelectedPosition != 6) {
                    m_listener.onSelect(position);
                }
            }
        });

        return convertView;
    }

    private void onSelectIndex(int index) {
        nSelectedPosition = index;
    }

    public void changeIndex(int index) {
        onSelectIndex(index);
        notifyDataSetChanged();
    }
}
