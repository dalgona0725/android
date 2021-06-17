package com.theenm.common.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;

import java.util.ArrayList;


public class DialogManager {
    private static DialogManager mInstance;
    private Context mContext;
    private PopkonDialog.Builder mBuilder;
    private PopkonDialog mPopkonDialog;
    private boolean mIsStackable;
    private boolean mIsCancelable;
    private boolean mIsCancelOutside;

    private static ArrayList<DialogManager> mArrayInstance = new ArrayList<>();

    public static DialogManager getInstance(Context context) {
        return getInstance(context, false);
    }

    public static DialogManager getInstance(Context context, boolean isStackable) {
        return getInstance(context, isStackable, true);
    }

    public static DialogManager getInstance(Context context, boolean isStackable, boolean cancelable) {
        return getInstance(context, isStackable, cancelable, false);
    }

    /**
     *
     * @param context Activity의 context
     * @param isStackable 팝업이 연속으로 show되면 팝업을 쌓을지 마지막 하나만 보여줄지 여부 (default : false)
     * @param cancelable 취소가능 여부 (default : true)
     * @param cancelOutside 바탕화면 touch 시에 취소 가능 여부 (default : false)
     * @return
     */
    public static DialogManager getInstance(Context context, boolean isStackable, boolean cancelable, boolean cancelOutside) {
        if (mInstance == null){ // singleton 객체로 생성
            mInstance = new DialogManager(context, isStackable, cancelable, cancelOutside);
        } else {
            if (mInstance.mContext != null && mInstance.mContext.equals(context)) {
                mInstance.setConstructor(context, isStackable, cancelable, cancelOutside);
            } else { // Activity가 변경되면 변경된 Activity로 새로 생성
                mInstance = new DialogManager(context, isStackable, cancelable, cancelOutside);
            }
        }
        return mInstance;
    }

    public DialogManager(Context context, boolean isStackable, boolean cancelable, boolean cancelOutside){
        setConstructor(context, isStackable, cancelable, cancelOutside);
    }

    private void setConstructor(Context context, boolean isStackable, boolean cancelable, boolean cancelOutside){
        if(mBuilder == null) {
            mBuilder = new PopkonDialog.Builder(context);
        }
        mContext = context;
        mIsStackable = isStackable;
        mIsCancelable = cancelable;
        mIsCancelOutside = cancelOutside;
    }

    private PopkonDialog.Builder getBuilder(){
        mBuilder.setStackable(mIsStackable).
                setCancelable(mIsCancelable).
                setCanceledOnTouchOutside(mIsCancelOutside).
                setTitle(null).
                setMessage(null).
                setIsShowEditText(false).
                setEditInputType(InputType.TYPE_CLASS_TEXT).
                setEditTextHint(null).
                setPositiveText(null).
                setNegativeText(null).
                setOnPositiveListener(null).
                setOnNegativeListener(null).
                setOnCancelListener(null).
                setOnDismissListener(null);
        return mBuilder;
    }

    /**
     *       Title 영역 : X
     *       EditText : X
     *       확인 버튼 (Visible : O, Litener : X, 텍스트 설정 : X)
     *       취소 버튼 (Visible : X, Litener : X, 텍스트 설정 : X)
     *       CancelListener : X
     *       DismissListener : X
     */
    public PopkonDialog showPopkonDialog(String message){
        mPopkonDialog = getBuilder().
                setMessage(message).
                build().
                showDialog();

        return mPopkonDialog;
    }

    /**
     *       Title 영역 : X
     *       EditText : X
     *       확인 버튼 (Visible : O, Litener : O, 텍스트 설정 : X)
     *       취소 버튼 (Visible : X, Litener : X, 텍스트 설정 : X)
     *       CancelListener : X
     *       DismissListener : X
     */
    public PopkonDialog showPopkonDialog(String message,
                                         DialogInterface.OnClickListener OnPositiveListener){
        mPopkonDialog = getBuilder().
                setMessage(message).
                setOnPositiveListener(OnPositiveListener).
                build().
                showDialog();
        return mPopkonDialog;
    }

    /**
     *       Title 영역 : X
     *       EditText : X
     *       확인 버튼 (Visible : O, Litener : O, 텍스트 설정 : X)
     *       취소 버튼 (Visible : O, Litener : O, 텍스트 설정 : X)
     *       CancelListener : X
     *       DismissListener : X
     */
    public PopkonDialog showPopkonDialog(String message,
                                         DialogInterface.OnClickListener OnPositiveListener,
                                         DialogInterface.OnClickListener OnNegativeListener){
        mPopkonDialog = getBuilder().
                setMessage(message).
                setOnPositiveListener(OnPositiveListener).
                setOnNegativeListener(OnNegativeListener).
                build().
                showDialog();

        return mPopkonDialog;
    }

    /**
     *       Title 영역 : X
     *       EditText : X
     *       확인 버튼 (Visible : O, Litener : O, 텍스트 설정 : X)
     *       취소 버튼 (Visible : X, Litener : X, 텍스트 설정 : X)
     *       CancelListener : X
     *       DismissListener : O
     */
    public PopkonDialog showPopkonDialog(String message,
                                         DialogInterface.OnClickListener OnPositiveListener,
                                         DialogInterface.OnDismissListener OnDismissListener){
        mPopkonDialog = getBuilder().
                setMessage(message).
                setOnPositiveListener(OnPositiveListener).
                setOnDismissListener(OnDismissListener).
                build().
                showDialog();

        return mPopkonDialog;
    }

    /**
     *       Title 영역 : X
     *       EditText : X
     *       확인 버튼 (Visible : O, Litener : O, 텍스트 설정 : O)
     *       취소 버튼 (Visible : X, Litener : X, 텍스트 설정 : X)
     *       CancelListener : X
     *       DismissListener : X
     */
    public PopkonDialog showPopkonDialog(String message,
                                         String positiveText, DialogInterface.OnClickListener OnPositiveListener){
        mPopkonDialog = getBuilder().
                setMessage(message).
                setPositiveText(positiveText).
                setOnPositiveListener(OnPositiveListener).
                build().
                showDialog();

        return mPopkonDialog;
    }

    /**
     *       Title 영역 : X
     *       EditText : X
     *       확인 버튼 (Visible : O, Litener : O, 텍스트 설정 : O)
     *       취소 버튼 (Visible : O, Litener : O, 텍스트 설정 : O)
     *       CancelListener : X
     *       DismissListener : X
     */
    public PopkonDialog showPopkonDialog(String message,
                                         String positiveText, DialogInterface.OnClickListener OnPositiveListener,
                                         String negativeText, DialogInterface.OnClickListener OnNegativeListener){
        mPopkonDialog = getBuilder().
                setMessage(message).
                setPositiveText(positiveText).
                setNegativeText(negativeText).
                setOnPositiveListener(OnPositiveListener).
                setOnNegativeListener(OnNegativeListener).
                build().
                showDialog();

        return mPopkonDialog;
    }

    /**
     *       Title 영역 : X
     *       EditText : X
     *       확인 버튼 (Visible : O, Litener : O, 텍스트 설정 : X)
     *       취소 버튼 (Visible : O, Litener : O, 텍스트 설정 : X)
     *       CancelListener : O
     *       DismissListener : O
     */
    public PopkonDialog showPopkonDialog(String message,
                                         DialogInterface.OnClickListener OnPositiveListener,
                                         DialogInterface.OnClickListener OnNegativeListener,
                                         DialogInterface.OnCancelListener OnCancelListener){
        mPopkonDialog = getBuilder().
                setMessage(message).
                setOnPositiveListener(OnPositiveListener).
                setOnNegativeListener(OnNegativeListener).
                setOnCancelListener(OnCancelListener).
                build().
                showDialog();

        return mPopkonDialog;
    }

    /**
     *       Title 영역  (isEmpty ? Visible : Gone)
     *       EditText : X
     *       확인 버튼 (Visible : O, Litener : X, 텍스트 설정 : X)
     *       취소 버튼 (Visible : X, Litener : X, 텍스트 설정 : X)
     *       CancelListener : X
     *       DismissListener : X
     */
    public PopkonDialog showPopkonDialog(String title, String message){
        mPopkonDialog = getBuilder().
                setTitle(title).
                setMessage(message).
                setOnPositiveListener(null).
                build().
                showDialog();

        return mPopkonDialog;
    }
    /**
     *       Title 영역  (isEmpty ? Visible : Gone)
     *       EditText : X
     *       확인 버튼 (Visible : O, Litener : O, 텍스트 설정 : X)
     *       취소 버튼 (Visible : O, Litener : O, 텍스트 설정 : X)
     *       CancelListener : X
     *       DismissListener : X
     */
    public PopkonDialog showPopkonDialog(String title, String message,
                                         DialogInterface.OnClickListener OnPositiveListener,
                                         DialogInterface.OnClickListener OnNegativeListener){
        mPopkonDialog = getBuilder().
                setTitle(title).
                setMessage(message).
                setOnPositiveListener(OnPositiveListener).
                setOnNegativeListener(OnNegativeListener).
                build().
                showDialog();

        return mPopkonDialog;
    }

    /**
     *       Title 영역  (isEmpty ? Visible : Gone)
     *       EditText (Visisble : O, InputType 설정 : O, hint 설정 : O)
     *       확인 버튼 (Visible : O, Litener : O, 텍스트 설정 : X)
     *       취소 버튼 (Visible : O, Litener : O, 텍스트 설정 : X)
     *       CancelListener : O
     *       DismissListener : O
     */
    public PopkonDialog showPopkonDialog(String title, String message,
                                         boolean isShowEditText, int editInputType, String editTextHint,
                                         DialogInterface.OnClickListener OnPositiveListener,
                                         DialogInterface.OnClickListener OnNegativeListener){
//        if(mPopkonDialog != null) {
//            mPopkonDialog.dismiss();
//        }
        mPopkonDialog = getBuilder().
                setTitle(title).
                setMessage(message).
                setIsShowEditText(isShowEditText).
                setEditInputType(editInputType).
                setEditTextHint(editTextHint).
                setOnPositiveListener(OnPositiveListener).
                setOnNegativeListener(OnNegativeListener).
                build().
                showDialog();

        return mPopkonDialog;
    }

    public void dismiss() {
        if(mPopkonDialog != null && mPopkonDialog.isShowing()){
            mPopkonDialog.dismiss();
        }
    }
}
