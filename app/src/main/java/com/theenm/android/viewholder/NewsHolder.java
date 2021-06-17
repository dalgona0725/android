package com.theenm.android.viewholder;

import android.content.Context;
import android.text.Html;
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
import com.theenm.common.http.schemas.NewsListObject;

public class NewsHolder extends BaseViewHolder<NewsListObject.NewsList> {
    private final LinearLayout mLayoutNewsList;
    private final ImageView mImgThumbnail;
    private final TextView mTextTitle;
    private final TextView mTextContent;

    private final OnRecylerViewListener mOnRecylerViewListener;
    private final DisplayImageOptions mOptions;

    public static NewsHolder newInstance(ViewGroup parent, OnRecylerViewListener onRecylerViewListener) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);

        return new NewsHolder(parent, itemView, onRecylerViewListener);
    }

    public NewsHolder(ViewGroup parent, View itemView, OnRecylerViewListener onRecylerViewListener) {
        super(itemView);

        mOnRecylerViewListener = onRecylerViewListener;
        mOptions = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.icon_profile_fail)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();

        mLayoutNewsList = itemView.findViewById(R.id.layout_news_list);
        mImgThumbnail = itemView.findViewById(R.id.img_thumbnail);
        mTextTitle = itemView.findViewById(R.id.text_title);
        mTextContent = itemView.findViewById(R.id.text_content);
    }

    @Override
    public void onBindView(NewsListObject.NewsList newsList, Context context, int position) {
        ImageLoader.getInstance().displayImage(newsList.imgUrl, mImgThumbnail, mOptions, new AnimateFirstDisplayListener());
        mTextTitle.setText(Html.fromHtml(newsList.newsTitle));
        mTextContent.setText(Html.fromHtml(newsList.newsContent));

        mLayoutNewsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecylerViewListener.onItemClicked(newsList);
            }
        });
    }
}
