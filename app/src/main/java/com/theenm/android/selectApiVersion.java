package com.theenm.android;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.theenm.common.DefineCode;
import com.theenm.common.URLInfo;

public class selectApiVersion extends BottomSheetDialogFragment implements View.OnClickListener {
    private View view;
    private RecyclerView mRecyclerView = null;
    private ClickedListener mListener;
    private TextView tv_dev, tv_state, tv_release;

    public interface ClickedListener {
        void setAPiVersion();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof ClickedListener) {

                mListener = (ClickedListener) context;
            } else {
                // Error Code
            }

        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    public static selectApiVersion getInstance() {
        return new selectApiVersion();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.bottom_api_version, null);
        final BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setContentView(view);
        init(view);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void init(View view) {
        tv_dev = view.findViewById(R.id.tv_dev);
        tv_state = view.findViewById(R.id.tv_state);
        tv_release = view.findViewById(R.id.tv_release);
        tv_dev.setOnClickListener(this);
        tv_state.setOnClickListener(this);
        tv_release.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_dev:
                DefineCode.API_MODE = DefineCode.APIMode.DEBUG_MODE;
                URLInfo.API_URL = "http://devsys.popkontv.kr:9001/AS";
                break;
            case R.id.tv_state:
                DefineCode.API_MODE = DefineCode.APIMode.STAGE_MODE;
                URLInfo.API_URL = "http://stagesys.popkontv.com:9001/AS";
                break;
            case R.id.tv_release:
                DefineCode.API_MODE = DefineCode.APIMode.RELEASE_MODE;
                URLInfo.API_URL = "http://api.popkontv.kr:9001/AS";
                break;
        }
        mListener.setAPiVersion();
    }

}

