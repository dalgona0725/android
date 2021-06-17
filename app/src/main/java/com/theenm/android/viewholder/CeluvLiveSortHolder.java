package com.theenm.android.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.theenm.android.R;
import com.theenm.android.callback.OnRecylerViewListener;

public class CeluvLiveSortHolder extends BaseViewHolder<Integer> {
    public static final int SPINNER_POSITION_RANKING = 0;
    public static final int SPINNER_POSITION_NEWEST = 1;
    public static final int SPINNER_POSITION_VIEWER = 2;

    public static final int SORT_TYPE_NEWEST = 0;
    public static final int SORT_TYPE_RANKING = 1;
    public static final int SORT_TYPE_VIEWER = 2;

    private Spinner mSpinSorting;
    private int mSortType = 0;

    private OnRecylerViewListener mOnRecylerViewListener;

    public static CeluvLiveSortHolder newInstance(ViewGroup parent, OnRecylerViewListener onRecylerViewListener) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_livetab_sort, parent, false);
        return new CeluvLiveSortHolder(itemView, onRecylerViewListener);
    }

    public CeluvLiveSortHolder(View itemView, OnRecylerViewListener onRecylerViewListener) {
        super(itemView);
        mSpinSorting = itemView.findViewById(R.id.spinner_sorting);
        mOnRecylerViewListener = onRecylerViewListener;
    }


    @Override
    public void onBindView(Integer sortTpye, Context context, int position) {
        mSortType = sortTpye;
        initSortSpinner(context);
    }

    private void initSortSpinner(Context context){
        int selectSortType = SORT_TYPE_NEWEST;
        if (mSortType == SORT_TYPE_NEWEST)
        {
            selectSortType = SPINNER_POSITION_NEWEST;
        }
        else if (mSortType == SORT_TYPE_RANKING)
        {
            selectSortType = SPINNER_POSITION_RANKING;
        }
        else if (mSortType  == SORT_TYPE_VIEWER)
        {
            selectSortType = SPINNER_POSITION_VIEWER;
        }

        String[] types = context.getResources().getStringArray(R.array.sorting_menu);
        ArrayAdapter<String> adapterSorting = new ArrayAdapter<String>(context, R.layout.spinner_sort_text, types);
        mSpinSorting.setAdapter(adapterSorting);
        mSpinSorting.setSelection(selectSortType,false);
        SpinnerInteractionListener spinnerInteractionListener = new SpinnerInteractionListener(context);
        mSpinSorting.setOnTouchListener(spinnerInteractionListener);
        mSpinSorting.setOnItemSelectedListener(spinnerInteractionListener);
    }

    public class SpinnerInteractionListener implements AdapterView.OnItemSelectedListener, View.OnTouchListener {
        private Context mContext;
        boolean userSelect = false;

        public SpinnerInteractionListener(Context context){
            mContext = context;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            userSelect = true;
            return false;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (userSelect) {
                userSelect = false;
                int sortTpye = SORT_TYPE_RANKING;
                if (position == SPINNER_POSITION_RANKING)
                {
                    sortTpye = SORT_TYPE_RANKING;
                }
                else if (position == SPINNER_POSITION_NEWEST)
                {
                    sortTpye = SORT_TYPE_NEWEST;
                }
                else if (position == SPINNER_POSITION_VIEWER)
                {
                    sortTpye = SORT_TYPE_VIEWER;
                }
                mOnRecylerViewListener.onSortList(sortTpye);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
