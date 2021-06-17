package com.theenm.android.adapter;


import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.theenm.android.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class MultiItemAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public final static int TYPE_VODTAB_VOD_LIST = 7; // 메인 LIVE : VOD

    public final static int TYPE_CELUV_NOT_NETWORK = 13; //메인홈 :네트워크연결 안될시

    private List<Row<?>> mRows = new ArrayList<>();
    private Context mContext;

    public MultiItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        //noinspection unchecked
        holder.onBindView(getItem(position), mContext, position);
    }


    @SuppressWarnings("unchecked")
    public <ITEM> ITEM getItem(int position) {
        return (ITEM) mRows.get(position).getItem();
    }


    public void addRow(Row<?> row) {
        this.mRows.add(row);
    }

    public void addRows(List<Row<?>> rows) {
        this.mRows.addAll(rows);
    }


    public void setRow(int position, Row<?> row) {
        this.mRows.set(position, row);
    }


    public void setRows(List<Row<?>> mRows) {
        clear();
        this.mRows.addAll(mRows);
    }


    public void remove(int position) {
        if (getItemCount() - 1 < position) {
            return;
        }
        this.mRows.remove(position);
    }

    public void remove(Row<?> row) {
        this.mRows.remove(row);
    }

    public void clear() {
        this.mRows.clear();
    }

    @Override
    public int getItemCount() {
        return mRows.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mRows.get(position).getItemViewType();
    }

    public static class Row<ITEM> {
        private ITEM item;
        private int itemViewType;

        private Row(ITEM item, int itemViewType) {
            this.item = item;
            this.itemViewType = itemViewType;
        }

        public static <T> Row<T> create(T item, int itemViewType) {
            return new Row<>(item, itemViewType);
        }

        public ITEM getItem() {
            return item;
        }

        public int getItemViewType() {
            return itemViewType;
        }
    }

}
