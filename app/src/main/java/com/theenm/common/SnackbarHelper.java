package com.theenm.common;

import android.app.Activity;
import android.app.Dialog;
import androidx.annotation.StringRes;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.theenm.android.R;

/**
 * Snackbar 헬퍼 클래스
 *
 * @author khj0704
 * @since 2018-03-30
 */
public final class SnackbarHelper {

    /**
     * Snackbar 노출 시간 타입
     *
     * SHOW_SHORT = Snackbar.LENGTH_SHORT
     * SHOW_LONG = Snackbar.LENGTH_LONG
     * SHOW_INDEFINITE = Snackbar.LENGTH_INDEFINITE
     */
    public enum DURATION_TYPE {
        SHOW_SHORT,
        SHOW_LONG,
        SHOW_INDEFINITE
    }

    private static final int TEXT_MAX_LINE = 10;
    private SnackbarHelper() {
        // Do Not Generate instances!
    }

    /**
     * Snackbar 를 생성하고 반환한다.
     *
     * @param pTargetView
     *      Snackbar 를 노출할 타겟 부모 뷰
     * @param pMessage
     *      Snackbar 에 노출될 텍스트
     * @return
     *      Snackbar
     */
    public static final Snackbar make(View pTargetView, String pMessage) {
        Snackbar snackbar = Snackbar.make(pTargetView, pMessage, Snackbar.LENGTH_SHORT);

        MyApplication appContext = (MyApplication) pTargetView.getContext().getApplicationContext();
        // 배경색
        int bgColor = appContext.getResources().getColor(R.color.popkon_snackbar_bg_color);
        View view = snackbar.getView();
        view.setBackgroundColor(bgColor);

        // 글자색
        int textColor = appContext.getResources().getColor(R.color.popkon_snackbar_text_color);
        TextView snackBarTextView = (TextView) view.findViewById(R.id.snackbar_text);
        snackBarTextView.setMaxLines(TEXT_MAX_LINE);
        snackBarTextView.setTextColor(textColor);

        return snackbar;
    }

    /**
     * Snackbar 를 생성하고 반환한다.
     *
     * @param pTargetView
     *      Snackbar 를 노출할 타겟 부모 뷰
     * @param pStrMsgId
     *      Snackbar 에 노출될 텍스트 리소스 아이디 (strings.xml)
     * @return
     *      Snackbar
     */
    public static final Snackbar make(View pTargetView, @StringRes int pStrMsgId) {
        MyApplication appContext = (MyApplication) pTargetView.getContext().getApplicationContext();
        String msg = appContext.getString(pStrMsgId);
        Snackbar snackbar = Snackbar.make(pTargetView, msg, Snackbar.LENGTH_SHORT);

        // 배경색
        int bgColor = appContext.getResources().getColor(R.color.popkon_snackbar_bg_color);
        View view = snackbar.getView();
        view.setBackgroundColor(bgColor);

        // 글자색
        int textColor = appContext.getResources().getColor(R.color.popkon_snackbar_text_color);
        TextView snackBarTextView = (TextView) view.findViewById(R.id.snackbar_text);
        snackBarTextView.setMaxLines(TEXT_MAX_LINE);
        snackBarTextView.setTextColor(textColor);

        return snackbar;
    }

    /**
     * Snackbar 를 생성하고 반환한다.
     *
     * @param pTargetView
     *      Snackbar 를 노출할 타겟 부모 뷰
     * @param pMessage
     *      Snackbar 에 노출될 텍스트
     * @param pDuration
     *      Snackbar 노출 시간 (Snackbar.LENGTH_SHORT or Snackbar.LENGTH_LONG or Snackbar.LENGTH_INDEFINITE)
     * @return
     *      Snackbar
     */
    public static final Snackbar make(View pTargetView, String pMessage, int pDuration) {
        Snackbar snackbar = Snackbar.make(pTargetView, pMessage, pDuration);

        MyApplication appContext = (MyApplication) pTargetView.getContext().getApplicationContext();
        // 배경색
        int bgColor = appContext.getResources().getColor(R.color.popkon_snackbar_bg_color);
        View view = snackbar.getView();
        view.setBackgroundColor(bgColor);

        // 글자색
        int textColor = appContext.getResources().getColor(R.color.popkon_snackbar_text_color);
        TextView snackBarTextView = (TextView) view.findViewById(R.id.snackbar_text);
        snackBarTextView.setMaxLines(TEXT_MAX_LINE);
        snackBarTextView.setTextColor(textColor);

        return snackbar;
    }

    /**
     * Snackbar 를 생성하고 반환한다.
     *
     * @param pTargetView
     *      Snackbar 를 노출할 타겟 부모 뷰
     * @param pStrMsgId
     *      Snackbar 에 노출될 텍스트 리소스 아이디 (strings.xml)
     * @param pDuration
     *      Snackbar 노출 시간 (Snackbar.LENGTH_SHORT or Snackbar.LENGTH_LONG or Snackbar.LENGTH_INDEFINITE)
     * @return
     *      Snackbar
     */
    public static final Snackbar make(View pTargetView, @StringRes int pStrMsgId, int pDuration) {
        MyApplication appContext = (MyApplication) pTargetView.getContext().getApplicationContext();
        String msg = appContext.getString(pStrMsgId);
        Snackbar snackbar = Snackbar.make(pTargetView, msg, pDuration);

        // 배경색
        int bgColor = appContext.getResources().getColor(R.color.popkon_snackbar_bg_color);
        View view = snackbar.getView();
        view.setBackgroundColor(bgColor);

        // 글자색
        int textColor = appContext.getResources().getColor(R.color.popkon_snackbar_text_color);
        TextView snackBarTextView = (TextView) view.findViewById(R.id.snackbar_text);
        snackBarTextView.setMaxLines(TEXT_MAX_LINE);
        snackBarTextView.setTextColor(textColor);

        return snackbar;
    }

    /**
     * Snackbar 를 생성하고 반환한다.
     *
     * @param pTargetView
     *      Snackbar 를 노출할 타겟 부모 뷰
     * @param pMessage
     *      Snackbar 에 노출될 텍스트
     * @param pDuration
     *      Snackbar 노출 시간 타입 (enum DURATION_TYPE)
     * @param pBtnText
     *      Snackbar 버튼 텍스트
     * @param pClickListener
     *      Snackbar 버튼 클릭 리스너
     * @return
     *      Snackbar
     */
    public static final Snackbar make(View pTargetView, String pMessage, DURATION_TYPE pDuration
            , String pBtnText, View.OnClickListener pClickListener) {
        Snackbar snackbar = null;
        switch (pDuration) {
            case SHOW_SHORT:
                snackbar = Snackbar.make(pTargetView, pMessage, Snackbar.LENGTH_SHORT);
                break;

            case SHOW_LONG:
                snackbar = Snackbar.make(pTargetView, pMessage, Snackbar.LENGTH_LONG);
                break;

            case SHOW_INDEFINITE:
                snackbar = Snackbar.make(pTargetView, pMessage, Snackbar.LENGTH_INDEFINITE);
                break;

            default:
                snackbar = Snackbar.make(pTargetView, pMessage, Snackbar.LENGTH_SHORT);
                break;
        }

        MyApplication appContext = (MyApplication) pTargetView.getContext().getApplicationContext();
        // 배경색
        int bgColor = appContext.getResources().getColor(R.color.popkon_snackbar_bg_color);
        View view = snackbar.getView();
        view.setBackgroundColor(bgColor);

        // 글자색
        int textColor = appContext.getResources().getColor(R.color.popkon_snackbar_text_color);
        TextView snackBarTextView = (TextView) view.findViewById(R.id.snackbar_text);
        snackBarTextView.setMaxLines(TEXT_MAX_LINE);
        snackBarTextView.setTextColor(textColor);

        // 버튼 텍스트 및 클릭 이벤트 등록
        if (pClickListener != null) {
            //snackbar.setAction(pBtnText, pClickListener).setActionTextColor(Color.WHITE);
            snackbar.setAction(pBtnText, pClickListener);
        } else {
            snackbar.setAction(pBtnText, new EmptyOnClickListener());
        }

        return snackbar;
    }

    /**
     * Activity 에서 Content Root View 를 리턴한다.
     * @param pActivity
     * @return
     */
    public static final View getRootView(Activity pActivity) {
        if (pActivity == null) {
            return null;
        }
        View targetView = null;
        try {
            targetView = pActivity.findViewById(android.R.id.content);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return targetView;
    }

    /**
     * Fragment 에서 Content Root View 를 리턴한다.
     *
     * @param pFragment
     *      android.support.v4.app.Fragment
     *
     * @return
     */
    public static final View getRootView(Fragment pFragment) {
        if (pFragment == null) {
            return null;
        }
        View targetView = null;
        try {
            targetView = pFragment.getView();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return targetView;
    }

    /**
     * Dialog 에서 Content Root View 를 리턴한다.
     *
     * @param pDlg
     *
     * @return
     */
    public static final View getRootView(Dialog pDlg) {
        if (pDlg == null) {
            return null;
        }
        Window window = pDlg.getWindow();
        if (window == null) {
            return null;
        }
        View targetView = null;
        try {
            targetView = window.findViewById(android.R.id.content);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return targetView;
    }

    /**
     * DialogFragment 에서 Content Root View 를 리턴한다.
     *
     * @param pDlgFragment
     *      android.support.v4.app.DialogFragment
     *
     * @return
     */
    public static final View getRootView(DialogFragment pDlgFragment) {
        if (pDlgFragment == null) {
            return null;
        }
        View targetView = null;
        try {
            Dialog dlg = pDlgFragment.getDialog();
            if (dlg == null) {
                return null;
            }
            Window window = dlg.getWindow();
            if (window == null) {
                return null;
            }
            targetView = window.findViewById(android.R.id.content);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return targetView;
    }

    /**
     * DialogFragment 에서 Content Root View 를 리턴한다.
     *
     * @param pDlgFragment
     *      android.app.DialogFragment
     *
     * @return
     */
    public static final View getRootView(android.app.DialogFragment pDlgFragment) {
        if (pDlgFragment == null) {
            return null;
        }
        View targetView = null;
        try {
            Dialog dlg = pDlgFragment.getDialog();
            if (dlg == null) {
                return null;
            }
            Window window = dlg.getWindow();
            if (window == null) {
                return null;
            }
            targetView = window.findViewById(android.R.id.content);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return targetView;
    }

    /**
     * Snackbar 버튼 클릭 이벤트 기본 구현. (setAction() 등록용으로 아무런 동작을 하지 않는다.)
     */
    private static final class EmptyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // Nothing !!!
        }
    }

}
