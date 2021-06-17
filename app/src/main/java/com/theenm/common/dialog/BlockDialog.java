package com.theenm.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.theenm.android.R;
import com.theenm.common.http.schemas.MemberBlockObject;
import com.theenm.common.http.schemas.SignOnObject;
import com.theenm.common.util.PopkonUtils;

public class BlockDialog extends Dialog {
    private Object mObject = null;

    private OnClickListener mPositiveListener = null;
    private OnClickListener mCounselListener = null;

    public BlockDialog(@NonNull Context context) {
        super(context);
    }

    public BlockDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BlockDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.block_dialog);

        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        try
        {
            getWindow().setLayout(width / 15 * 14, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }


        TextView tvRegDate = (TextView) findViewById(R.id.tv_reg_date);
        TextView tvPeriod = (TextView) findViewById(R.id.tv_period);
        TextView tvReason = (TextView) findViewById(R.id.tv_reason);
        TextView btnOk = (TextView) findViewById(R.id.btn_ok);
        TextView CounselGuide = (TextView) findViewById(R.id.tv_counsel_guide);
        TextView btnCounsel = (TextView) findViewById(R.id.btn_counsel);

        String blockRegDate = null;
        String blockDate = null;
        String blockMemo = null;
        String isLoginBlock = null;
        if(mObject instanceof SignOnObject){
            blockRegDate = ((SignOnObject) mObject).memberBlockCheck.blockRegDate;
            blockDate = ((SignOnObject) mObject).memberBlockCheck.blockDate;
            blockMemo = ((SignOnObject) mObject).memberBlockCheck.blockMemo;
            isLoginBlock = ((SignOnObject) mObject).memberBlockCheck.isLoginBlock;
        }else if(mObject instanceof MemberBlockObject){
            blockRegDate = ((MemberBlockObject) mObject).memberBlockCheck.blockRegDate;
            blockDate = ((MemberBlockObject) mObject).memberBlockCheck.blockDate;
            blockMemo = ((MemberBlockObject) mObject).memberBlockCheck.blockMemo;
            isLoginBlock = ((MemberBlockObject) mObject).memberBlockCheck.isLoginBlock;
        }
        String regDate = PopkonUtils.getSimpleDate(blockRegDate, "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss");
        tvRegDate.setText(regDate);
        tvPeriod.setText(blockDate);
        tvReason.setText(blockMemo);

        if(isLoginBlock.equals("0")){
            CounselGuide.setVisibility(View.VISIBLE);
            btnCounsel.setVisibility(View.VISIBLE);
        }else{
            CounselGuide.setVisibility(View.GONE);
            btnCounsel.setVisibility(View.GONE);
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if( mPositiveListener != null )
                    mPositiveListener.onClick(BlockDialog.this, v.getId());
                dismiss();
            }
        });

        btnCounsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mCounselListener != null )
                    mCounselListener.onClick(BlockDialog.this, v.getId());
                dismiss();
            }
        });
    }

    public void setData(Object object){
        mObject = object;
    }

    public void setPositiveButton(OnClickListener listener)
    {
        mPositiveListener = listener;
    }

    public void setCounselButton(OnClickListener listener)
    {
        mCounselListener = listener;
    }
}
