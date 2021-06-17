package com.theenm.android.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.theenm.android.callback.OnRecylerViewListener;
import com.theenm.android.viewholder.BaseViewHolder;
import com.theenm.android.viewholder.EmptyTypeHolder;
import com.theenm.android.viewholder.LiveShopReviewHolder;
import com.theenm.android.viewholder.LiveShopSectionHolder;
import com.theenm.bookmark.viewholder.BookMarkHeaderHolder;
import com.theenm.fan.viewholder.FanChannelHolder;
import com.theenm.android.viewholder.CeluvHomeTopLiveHolder;
import com.theenm.android.viewholder.CeluvHomeLiveHolder;
import com.theenm.android.viewholder.CeluvHomeVODHolder;
import com.theenm.android.viewholder.CeluvLiveFooterHolder;
import com.theenm.fan.viewholder.FanInfoRankHolder;
import com.theenm.fan.viewholder.FanInfoVODHolder;
import com.theenm.android.viewholder.LiveHolder;
import com.theenm.android.viewholder.CeluvLiveSortHolder;
import com.theenm.ranking.viewholder.MCRankingHolder;
import com.theenm.android.viewholder.NewsHolder;
import com.theenm.android.viewholder.PortalVODHolder;
import com.theenm.android.viewholder.PortalLiveHolder;
import com.theenm.android.viewholder.PortalMenuHolder;
import com.theenm.android.viewholder.PortalMoreHolder;
import com.theenm.android.viewholder.PortalSectionHolder;
import com.theenm.android.viewholder.VODHolder;
import com.theenm.android.viewholder.CeluvVODHolder;
import com.theenm.common.Constant;
import com.theenm.message.viewholder.MessageHolder;
import com.theenm.setting.viewholder.HistoryHeaderHolder;
import com.theenm.setting.viewholder.HistoryListHolder;
import com.theenm.setting.viewholder.PushListHolder;
import com.theenm.vod.viewholder.VODCommentHolder;
import com.theenm.vod.viewholder.VODHeaderHolder;
import com.theenm.watch.viewholder.CommerceListHolder;

public class RecyclerAdapter extends MultiItemAdapter {
    private OnRecylerViewListener mOnRecylerViewListener;

    public RecyclerAdapter(Context context) {
        super(context);
    }

    public void setOnRecylerViewListener(OnRecylerViewListener onRecylerViewListener){
        mOnRecylerViewListener = onRecylerViewListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constant.ReCyclerType.TYPE_CELUV_HOME_HIGHLIGHT_LIVE_ROW.ordinal()) {
            return CeluvHomeTopLiveHolder.newInstance(parent, mOnRecylerViewListener);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_CELUV_HOME_LIVE_ROW.ordinal()) {
            return CeluvHomeLiveHolder.newInstance(parent, mOnRecylerViewListener);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_CELUV_HOME_HOTCLIP_ROW.ordinal()) {
            return CeluvHomeVODHolder.newInstance(parent, mOnRecylerViewListener);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_CELUV_HOME_VOD_ROW.ordinal()) {
            return CeluvHomeVODHolder.newInstance(parent, mOnRecylerViewListener);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_CELUV_LIVE_SORT.ordinal()) {
            return CeluvLiveSortHolder.newInstance(parent, mOnRecylerViewListener);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_CELUV_LIVE_ROW.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_POPKON_LIST_LIVE_ROW.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_SEARCH_LIVE_ROW.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_LIVE_SHOP_LIVE_ROW.ordinal()) {
            return LiveHolder.newInstance(parent, mOnRecylerViewListener);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_LIVE_SHOP_REVIEW_ROW.ordinal()) {
            return LiveShopReviewHolder.newInstance(parent, mOnRecylerViewListener);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_CELUV_LIVE_FOOTER.ordinal()) {
            return CeluvLiveFooterHolder.newInstance(parent);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_CELUV_VOD_ROW.ordinal()) {
            return CeluvVODHolder.newInstance(parent, mOnRecylerViewListener);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_LIVE_SHOP_LIVE_SECTION_ROW.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_LIVE_SHOP_REVIEW_SECTION_ROW.ordinal()) {
            return LiveShopSectionHolder.newInstance(parent, mOnRecylerViewListener);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_POPKON_LIST_VOD_ROW.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_SEARCH_VOD_ROW.ordinal()) {
            return VODHolder.newInstance(parent, mOnRecylerViewListener);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_SEARCH_FAN_ROW.ordinal()) {
            return FanChannelHolder.newInstance(parent, mOnRecylerViewListener);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_POPKON_PORTAL_LIVE_MENU.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_POPKON_PORTAL_VOD_MENU.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_POPKON_PORTAL_ADULT_MENU.ordinal()) {
            return PortalMenuHolder.newInstance(parent);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_POPKON_PORTAL_LIVE_SECTION_BEST_ROW.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_POPKON_PORTAL_LIVE_SECTION_BOOKMARK_ROW.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_POPKON_PORTAL_VOD_SECTION_BEST_ROW.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_POPKON_PORTAL_ADULT_SECTION_BEST_ROW.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_POPKON_PORTAL_ADULT_SECTION_BOOKMARK_ROW.ordinal() ) {
            return PortalSectionHolder.newInstance(parent, mOnRecylerViewListener);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_POPKON_PORTAL_LIVE_BEST_ROW.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_POPKON_PORTAL_LIVE_BOOKMARK_ROW.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_POPKON_PORTAL_ADULT_BEST_ROW.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_POPKON_PORTAL_ADULT_BOOKMARK_ROW.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_BOOKMARK_LIVE_ON_ROW.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_BOOKMARK_LIVE_OFF_ROW.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_BOOKMARK_LIVE_ON_DELETE_ROW.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_BOOKMARK_LIVE_OFF_DELETE_ROW.ordinal()) {
            return PortalLiveHolder.newInstance(parent, mOnRecylerViewListener);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_POPKON_PORTAL_VOD_BEST_ROW.ordinal()) {
            return PortalVODHolder.newInstance(parent, mOnRecylerViewListener);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_POPKON_PORTAL_LIVE_MORE.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_POPKON_PORTAL_VOD_MORE.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_POPKON_PORTAL_ADULT_MORE.ordinal()) {
            return PortalMoreHolder.newInstance(parent);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_POPKON_FAN_VOD_ROW.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_POPKON_FAN_VOD_SUB_ROW.ordinal()) {
            return FanInfoVODHolder.newInstance(parent, mOnRecylerViewListener);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_POPKON_FAN_RANK_ROW.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_WATCH_HOT_FAN_RANK_ROW.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_WATCH_FAN_LEVEL_RANK_ROW.ordinal()) {
            return FanInfoRankHolder.newInstance(parent);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_NEWS_ROW.ordinal()) {
            return NewsHolder.newInstance(parent, mOnRecylerViewListener);
        }
        else if(viewType == Constant.ReCyclerType.TYPE_VOD_COMMENT_HEADER_ROW.ordinal()) {
            return VODHeaderHolder.newInstance(parent, mOnRecylerViewListener);
        }
        else if(viewType == Constant.ReCyclerType.TYPE_VOD_COMMENT_ROW.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_VOD_COMMENT_REPLY_ROW.ordinal()) {
            return VODCommentHolder.newInstance(parent, mOnRecylerViewListener);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_MC_RANKING_ROW.ordinal()) {
            return MCRankingHolder.newInstance(parent, mOnRecylerViewListener);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_PAPER_RECEIVED_ROW.ordinal()) {
            return MessageHolder.newInstance(parent, mOnRecylerViewListener);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_SETTING_PUSH_LIST.ordinal()) {
            return PushListHolder.newInstance(parent, mOnRecylerViewListener);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_SETTING_HISTORY_PAY_HEADER.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_SETTING_HISTORY_USE_HEADER.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_SETTING_HISTORY_RECEIVE_HEADER.ordinal()) {
            return HistoryHeaderHolder.newInstance(parent);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_SETTING_HISTORY_PAY.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_SETTING_HISTORY_USE.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_SETTING_HISTORY_RECEIVE.ordinal()) {
            return HistoryListHolder.newInstance(parent, mOnRecylerViewListener);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_COMMERCE_LIST_ROW.ordinal()) {
            return CommerceListHolder.newInstance(parent, mOnRecylerViewListener);
        }
        else if (viewType == Constant.ReCyclerType.TYPE_SEARCH_LIVE_EMPTY_LIST.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_SEARCH_VOD_EMPTY_LIST.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_LIVE_SHOP_LIVE_EMPTY_LIST.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_LIVE_SHOP_REVIEW_EMPTY_LIST.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_EMPTY_LIST.ordinal()) {
            return EmptyTypeHolder.newInstance(parent);
        }
        else if(viewType == Constant.ReCyclerType.TYPE_BOOKMARK_ON_AIR_HEADER_ROW.ordinal() ||
                viewType == Constant.ReCyclerType.TYPE_BOOKMARK_OFF_AIR_HEADER_ROW.ordinal()) {
            return BookMarkHeaderHolder.newInstance(parent);
        }
        return null;
    }
}
