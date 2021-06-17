package com.theenm.android.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.theenm.android.BaseFragment;
import com.theenm.android.BuildConfig;
import com.theenm.android.MainActivity;
import com.theenm.android.R;
import com.theenm.android.callback.OnPagerControllListener;
import com.theenm.common.Constant;
import com.theenm.common.DefineCode;
import com.theenm.common.MarketingSDKManager;
import com.theenm.common.SnackbarHelper;
import com.theenm.common.UserData;
import com.theenm.common.dialog.BlockDialog;
import com.theenm.common.dialog.DialogManager;
import com.theenm.common.dialog.LevelUpDialog;
import com.theenm.common.fcm.FCMMain;
import com.theenm.common.http.HttpManager;
import com.theenm.common.http.JsonHttpCallback;
import com.theenm.common.http.NaSignAliveCheck;
import com.theenm.common.http.schemas.CastGetMemberCoinObject;
import com.theenm.common.http.schemas.LevelDataObject;
import com.theenm.common.http.schemas.PaperNewCntObject;
import com.theenm.common.http.schemas.PushOnOffObject;
import com.theenm.common.http.schemas.ResultObject;
import com.theenm.common.http.schemas.SignOnObject;
import com.theenm.common.util.ObjectUtils;
import com.theenm.common.util.PopkonUtils;
import com.theenm.common.util.Preference;
import com.theenm.common.widget.GuidelineAndPolicyDialog;
import com.theenm.common.widget.badgeview.BadgeView;
import com.theenm.fan.FanListFragment;
import com.theenm.login.LoginActivity;
import com.theenm.message.MessageActivity;
import com.theenm.ranking.McRankingFragment;
import com.theenm.setting.SettingActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class NavigationDrawerFragment extends BaseFragment implements OnClickListener, JsonHttpCallback {
    public final static String TAG = NavigationDrawerFragment.class.getName();

    private final int URL_COIN = 1;
    private final int URL_SIGNON = 2;
    private final int URL_SIGN_OFF = 3;
    private final int URL_PUSH_INIT = 4;
    private final int URL_PUSH_SET = 5;
    private final int URL_PUSH_ENABLED = 6;
    private final int URL_LEVEL_DATA= 7;
    private final int URL_MESSAGE_CNT = 8;

    // private final String PUSH_SETTING = "0";
    private final int PUSH_SEARCH = 1;
    private final int PUSH_LOGIN = 2;

    private DrawerLayout mDrawerLayout = null;
    // private LinearLayout mDrawerLinear = null;

    private View mFragmentContainerView = null;
    private TextView mBtnLogin = null;
    private TextView mTextNickname = null;
    private ImageView mImgUser = null;
    private TextView mTextStyle = null;
    private LinearLayout mLinearBroadcast = null;
    private LinearLayout mLinearHome = null;
    private LinearLayout mLinearFanClub = null;
    private LinearLayout mLinearSetting = null;
    private LinearLayout mLinearLevel = null;
    private LinearLayout mLinearPurchase = null;

    private TextView mTextPopkonLoginNotice = null;

    private TextView mTextPopkonCut = null;
    private LinearLayout mLinearMcRank = null;

    private LinearLayout mLayoutPopkonMessage = null;
    private ImageView mImgMessageCnt = null;
    private TextView mTextMesssageView = null;
    private BadgeView mMsgBadgeView = null;

    private boolean mLoginCheck = false;

    private NaSignAliveCheck mNaSignAliveCheck = null;

    private String[] mSlideNameArray;
    private SlideMenuAdapter mSlideMenuAdapter;
    private LinearLayout llLevel;
    private FrameLayout menuBody;
    private View menuType;
    private ListView mSlideLv;
    private RelativeLayout mRlLogOut;
    private TextView mTvDoLogin;
    private RelativeLayout mRlHeader;
    private RelativeLayout mRlLuvBody;
    private TextView mTvMyLuvBuy;

    private MaterialCardView mLayoutServiceLevel;
    private TextView mTxtServiceLevel;
    private AppCompatSeekBar mSeekbarServiceLevel;
    private TextView mTxtServiceProgress;

    private OnPagerControllListener mOnPagerControllListener;

    private LevelUpDialog mLevelUpDialog = null;

    @Override
    public String getFragmentTagName() {
        return TAG;
    }

    @Override
    public void onRefreshList() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.navigation_drawer, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        menuBody = view.findViewById(R.id.ll_body);
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (DefineCode.PARTNER_CODE.equals("P-00117")) {
            menuType = layoutInflater.inflate(R.layout.celuv_type_navigation, null);
        } else {
            menuType = layoutInflater.inflate(R.layout.popkon_type_navigation, null);
        }
        menuBody.addView(menuType);
        //공통
        llLevel = view.findViewById(R.id.ll_level);
        mImgUser = view.findViewById(R.id.img_user);
        mTextNickname = view.findViewById(R.id.text_user);
        mTextPopkonLoginNotice = view.findViewById(R.id.text_popkon_login_notice);
        menuBody.findViewById(R.id.linear_service_center).setOnClickListener(this);
        menuBody.findViewById(R.id.linear_setting).setOnClickListener(this);

        mLayoutServiceLevel = view.findViewById(R.id.layout_service_level);
        mTxtServiceLevel = view.findViewById(R.id.text_level);
        mSeekbarServiceLevel = view.findViewById(R.id.seekbar_progress);
        mSeekbarServiceLevel.setMax(100);
        mSeekbarServiceLevel.setEnabled(false);
        mTxtServiceProgress = view.findViewById(R.id.text_progress);

        if (DefineCode.PARTNER_CODE.equals("P-00117")) {
            //CeluvType
            mSlideLv = menuBody.findViewById(R.id.lv);
            mRlLogOut = menuBody.findViewById(R.id.rlLogOut);
            mTvDoLogin = menuBody.findViewById(R.id.tvDoLogin);
            mRlHeader = menuBody.findViewById(R.id.rlHeader);
            mRlLuvBody = menuBody.findViewById(R.id.rlLuvBody);
            mTextPopkonCut = menuBody.findViewById(R.id.text_popkon_cut);
            mTvMyLuvBuy = menuBody.findViewById(R.id.tvMyLuvBuy);

            mRlHeader.setOnClickListener(this);
            mTvDoLogin.setOnClickListener(this);
            mRlLogOut.setOnClickListener(this);
            mRlLogOut.setOnClickListener(this);
            mTvMyLuvBuy.setOnClickListener(this);

            mSlideNameArray = getResources().getStringArray(R.array.slide_name_array);
            mSlideMenuAdapter = new SlideMenuAdapter(getActivity(), mSlideNameArray, new SlideMenuAdapter.SelectRowListener() {
                @Override
                public void onSelect(int position) {
                    switch (position) {
                        case 0:
                            if (mOnPagerControllListener != null) {
                                mOnPagerControllListener.onSelectedPosition(MainActivity.TAB_PAGE_LIVE);
                            }
                            break;
                        case 1:
                            // 2020-12-02 my.kim 레벨 이동, 2021-04-19 레벨 프래그먼트로 변경
                            if(!UserData.isLogin()) {
                                DialogManager.getInstance(getActivity()).showPopkonDialog(getString(R.string.ask_login),
                                        (dialog, which) -> {
                                            PopkonUtils.moveLoginActivity(getActivity());
                                        },
                                        (dialog, which) -> {});
                            } else {
                                PopkonUtils.moveCallWebPage(getActivity(), Constant.CallWeb.USER_LEVEL);
                            }
                            break;
                        case 2:
                            if (mOnPagerControllListener != null) {
                                mOnPagerControllListener.onSelectedPosition(MainActivity.TAB_PAGE_VOD);
                            }
                            break;
                        case 3:
                            if (UserData.isLogin()) {
                                if (!UserData.isLogin()) {
                                    DialogManager.getInstance(getActivity()).showPopkonDialog(getString(R.string.ask_login),
                                            (dialog, which) -> {
                                                PopkonUtils.moveLoginActivity(getActivity());
                                            },
                                            (dialog, which) -> {});
                                }else {
                                    // 2020-07-09 my.kim 원스토어용 팝콘, ASP 결제 웹으로 이동
                                    PopkonUtils.moveCallWebPage(getActivity(), Constant.CallWeb.PURCHASE_ITEM);
                                }
                            } else {
                                DialogManager.getInstance(getActivity()).showPopkonDialog(getString(R.string.ask_login),
                                        (dialog, which) -> {
                                            PopkonUtils.moveLoginActivity(getActivity());
                                        },
                                        (dialog, which) -> {});
                            }
                            break;
                        case 4:
                            if (UserData.isLogin()) {
                                PopkonUtils.moveCallWebPage(getActivity(), Constant.CallWeb.PURCHASE_COIN_LIST);
                            } else {
                                DialogManager.getInstance(getActivity()).showPopkonDialog(getString(R.string.ask_login),
                                        (dialog, which) -> {
                                            PopkonUtils.moveLoginActivity(getActivity());
                                        },
                                        (dialog, which) -> {});
                            }
                            break;

                    }
                    if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView)) {
                        mDrawerLayout.closeDrawer(mFragmentContainerView);
                    }

                    setSelectSlideMenu(false, position);
                }
            });
            mSlideLv.setAdapter(mSlideMenuAdapter);
        } else {
            mBtnLogin = view.findViewById(R.id.btn_login);
            mTextPopkonCut = view.findViewById(R.id.text_popkon_cut);

            mTextStyle = view.findViewById(R.id.text_style);
            mLinearBroadcast = view.findViewById(R.id.linear_broadcast);
            mLinearFanClub = view.findViewById(R.id.linear_fan_club);
            mLinearMcRank = view.findViewById(R.id.linear_mc_rank);
            mLinearLevel = view.findViewById(R.id.linear_level);
            mLinearPurchase = view.findViewById(R.id.linear_purchase);

            mLayoutPopkonMessage = view.findViewById(R.id.layout_popkon_message);
            mImgMessageCnt = view.findViewById(R.id.img_message_cnt);
            mTextMesssageView = view.findViewById(R.id.text_messsage_view);
            mImgMessageCnt.setOnClickListener(this);
            mTextMesssageView.setOnClickListener(this);

            mBtnLogin.setOnClickListener(this);
            mLinearBroadcast.setOnClickListener(this);
            mLinearFanClub.setOnClickListener(this);
            mLinearMcRank.setOnClickListener(this);
            mLinearLevel.setOnClickListener(this);
            view.findViewById(R.id.linear_home).setOnClickListener(this);
            view.findViewById(R.id.linear_setting).setOnClickListener(this);
            view.findViewById(R.id.linear_style).setOnClickListener(this);
            view.findViewById(R.id.linear_service_center).setOnClickListener(this);

            view.findViewById(R.id.linear_purchase).setOnClickListener(this);

            mLinearMcRank.setVisibility(View.VISIBLE);
        }

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mDrawerLayout != null
                            && mDrawerLayout.isDrawerOpen(mFragmentContainerView))
                        mDrawerLayout.closeDrawer(mFragmentContainerView);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnPagerControllListener = (OnPagerControllListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement HeadlineListener");
        }
    }

    private void initStyleType(boolean isOnClick) {
        int nStyleType = ((MainActivity) getActivity()).getStyleType();

        if (isOnClick) {
            nStyleType = (nStyleType + 1) % 2;
            ((MainActivity) getActivity()).setStyleType(nStyleType);
            Preference.get(getActivity()).setPreference(Preference.KEY_STYLE_TYPE, nStyleType);
            ((MainActivity) getActivity()).init();
        }

        if (nStyleType == MainActivity.STYLE_TYPE_NEW)
            mTextStyle.setText(getString(R.string.old_style));
        else if (nStyleType == MainActivity.STYLE_TYPE_OLD)
            mTextStyle.setText(getString(R.string.new_style));
    }

    public boolean isDrawerOpen() {
        boolean bDrawerOpen = mDrawerLayout.isDrawerOpen(mFragmentContainerView);

        return bDrawerOpen;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        mDrawerLayout.setDrawerShadow(R.drawable.alpha_drawer, GravityCompat.START);

        if (!DefineCode.PARTNER_CODE.equals("P-00117")) {
            if (UserData.mIsCast)
                mLinearBroadcast.setVisibility(View.VISIBLE);
            else
                mLinearBroadcast.setVisibility(View.GONE);

            initStyleType(false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tvMyLuvBuy:
            case R.id.btn_login:
                if (mLoginCheck) {
                    // 2020-07-09 my.kim 원스토어용 팝콘, ASP 결제 웹으로 이동
                    PopkonUtils.moveCallWebPage(getActivity(), Constant.CallWeb.PURCHASE_COIN);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivityForResult(intent, LoginActivity.ID_LOGIN_CHANGE);
                }
                break;

            case R.id.linear_broadcast:
                ((MainActivity) getActivity()).startBroadCast();
                break;

            case R.id.linear_home:
                ((MainActivity) getActivity()).setPageHome();
                break;

            case R.id.linear_mc_rank:
                McRankingFragment mcRankingFragment = McRankingFragment.newInstance();
                mcRankingFragment.addFragment(getActivity(), R.id.fragment_container);
                break;

            case R.id.linear_fan_club:
                FanListFragment fanListFragment = FanListFragment.newInstance();
                fanListFragment.addFragment(getActivity(), R.id.fragment_container);
                break;

            case R.id.linear_setting:
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;

            case R.id.linear_style:
                initStyleType(true);
                break;

            case R.id.linear_purchase:
                if (!UserData.isLogin()) {
                    DialogManager.getInstance(getActivity()).showPopkonDialog(getString(R.string.ask_login),
                            (dialog, which) -> {
                                PopkonUtils.moveLoginActivity(getActivity());
                            },
                            (dialog, which) -> {});
                }else {
                    // 2020-07-09 my.kim 원스토어용 팝콘, ASP 결제 웹으로 이동
                    PopkonUtils.moveCallWebPage(getActivity(), Constant.CallWeb.PURCHASE_ITEM);
                }
                break;

            case R.id.linear_service_center:
                PopkonUtils.moveCallWebPage(getActivity(), Constant.CallWeb.SERVICE_CENTER);
                break;
            case R.id.linear_level:
                // 2020-12-02 my.kim 레벨 이동, 2021-04-19 레벨 프래그먼트로 변경
                if(!UserData.isLogin()){
                    DialogManager.getInstance(getActivity()).showPopkonDialog(getString(R.string.ask_login),
                            (dialog, which) -> {
                                PopkonUtils.moveLoginActivity(getActivity());
                            },
                            (dialog, which) -> {});
                } else {
                    PopkonUtils.moveCallWebPage(getActivity(), Constant.CallWeb.USER_LEVEL);
                }
                break;

            case R.id.text_messsage_view:
            case R.id.img_message_cnt:
                intent = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent);
                break;
            case R.id.tvDoLogin:
                intent = new Intent(getActivity(), LoginActivity.class);

                getActivity().startActivityForResult(intent, LoginActivity.ID_LOGIN_CHANGE);
                break;
            case R.id.rlLogOut:
                if (UserData.isLogin()) {
                    DialogManager.getInstance(getActivity()).showPopkonDialog(getString(R.string.ask_logout),
                            (dialog, which) -> {
                                requestUrlNaSignOff();
                            },
                            (dialog, which) -> {});
                }
                break;
        }

        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView)) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
    }

    public void userLoginInfo(boolean isLogin) {
        if (isLogin) {
            requestUrlNaCoinStatus();

            mTextPopkonLoginNotice.setVisibility(View.GONE);
            llLevel.setVisibility(View.VISIBLE);
            mLayoutServiceLevel.setVisibility(View.VISIBLE);

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    // .displayer(new RoundedBitmapDisplayer(1000)) //rounded
                    // corner bitmap
                    .showImageOnFail(R.drawable.icon_profile_fail).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).build();
            if (DefineCode.PARTNER_CODE.equals("P-00117")) {
                mTvDoLogin.setOnClickListener(null);
                mTvDoLogin.setVisibility(View.GONE);
                mRlLuvBody.setVisibility(View.VISIBLE);
                mRlLogOut.setVisibility(View.VISIBLE);

                // 2020-11-25 my.kim 원스토어용 심사 유/무 값으로 구매 부분 숨김처리
                if(BuildConfig.FLAVOR.contains("_skt") && UserData.isUpdate()){
                    mTvMyLuvBuy.setVisibility(View.GONE);
                } else{
                    mTvMyLuvBuy.setVisibility(View.VISIBLE);
                }
            } else {
                mBtnLogin.setText(String.format(getString(R.string.popkon_purchase), getString(R.string.coin_name)));

                // 2020-11-25 my.kim 원스토어용 심사 유/무 값으로 구매 부분 숨김처리
                if(BuildConfig.FLAVOR.contains("_skt") && UserData.isUpdate()){
                    mBtnLogin.setVisibility(View.GONE);
                    mLinearPurchase.setVisibility(View.GONE);
                } else{
                    mBtnLogin.setVisibility(View.VISIBLE);
                    mLinearPurchase.setVisibility(View.VISIBLE);
                }
                mLayoutPopkonMessage.setVisibility(View.VISIBLE);
                requestUrlPaperNewCnt();
            }
            requestUrlLevelData();
            ImageLoader.getInstance().displayImage(UserData.mUserImgPath, mImgUser, options);
            mTextNickname.setText(UserData.mNickName);

            mLoginCheck = true;
        } else {

            if (DefineCode.PARTNER_CODE.equals("P-00117")) {
                mTvDoLogin.setOnClickListener(this);
                mTvDoLogin.setVisibility(View.VISIBLE);
                mRlLuvBody.setVisibility(View.GONE);
                mRlLogOut.setVisibility(View.GONE);
            }else
            {
                mLayoutPopkonMessage.setVisibility(View.GONE);
                mBtnLogin.setText(getString(R.string.do_login));
                mBtnLogin.setVisibility(View.VISIBLE);

                // 2020-11-25 my.kim 원스토어용 심사 유/무 값으로 구매 부분 숨김처리
                if(BuildConfig.FLAVOR.contains("_skt") && UserData.isUpdate()){
                    mLinearPurchase.setVisibility(View.GONE);
                } else{
                    mLinearPurchase.setVisibility(View.VISIBLE);
                }
            }

            mTextNickname.setText(getString(R.string.nonmember));
            mTextPopkonLoginNotice.setVisibility(View.VISIBLE);
            llLevel.setVisibility(View.GONE);
            mLayoutServiceLevel.setVisibility(View.GONE);

            mImgUser.setImageResource(R.drawable.icon_profile_pic);

            mLoginCheck = false;
        }
    }

    public void requestUrlNaSignOn(String strId, String strPass) {
        UserData.mSignKey = strPass;
        UserData.mUUID = UUID.randomUUID().toString();

        HttpManager.getInstance(getActivity(), this, URL_SIGNON).
                urlNaSignOn(strId,
                        strPass);
    }

    public void requestUrlNaSignOff() {
        Preference.get(getActivity()).setPreference(Preference.KEY_LOGIN_AUTO, false);

        HttpManager.getInstance(getActivity(), this, URL_SIGN_OFF).urlNaSignOff();
    }

    private void requestUrlPushOnOff(int callbackId, int cmdType, String pushKey, String isOn, String pushServiceCode) {
        HttpManager.getInstance(getActivity(), this, callbackId, false).
                urlPushOnOff(cmdType,
                        pushKey,
                        isOn,
                        pushServiceCode);
    }

    private void requestUrlLevelData() {
        HttpManager.getInstance(getActivity(), this, URL_LEVEL_DATA, false).
                urlLevelData("10002",
                        1);
    }

    public void requestUrlNaCoinStatus() {
        HttpManager.getInstance(getActivity(), this, URL_COIN, false).urlNaCoinStatus();
    }

    private void requestUrlPaperNewCnt() {
        HttpManager.getInstance(getActivity(), this, URL_MESSAGE_CNT, false).urlPaperNewCnt();
    }

    @Override
    public void onSuccessCallBack(int nId, String response) {
        try {
            String strRstCode;

            switch (nId) {
                case URL_LEVEL_DATA:
                    LevelDataObject levelDataObject = ObjectUtils.getGson(response, LevelDataObject.class);
                    if (levelDataObject == null) break;

                    strRstCode = levelDataObject.rst.rstCode;
                    if (strRstCode != null && strRstCode.equals("0")) {
                        String lvl = levelDataObject.rst.lvl;
                        String lvlPer = levelDataObject.rst.lvlPer;

                        if(ObjectUtils.isNumber(lvl)){
                            llLevel.setVisibility(View.VISIBLE);
                            mLayoutServiceLevel.setVisibility(View.VISIBLE);
                            mTxtServiceLevel.setBackground(PopkonUtils.getLevelBackground(getActivity(), Integer.parseInt(lvl)));
                            mTxtServiceLevel.setText(String.format(getString(R.string.level_text_service), Integer.parseInt(lvl)));
                            /**
                             * 레벨100일때는 progressBar 100으로 강제로 세팅(레벨%는 0으로 기존과 동일)
                             * http://bts.popkontv.kr/issues/6576
                             */
                            if(lvl.equals("100")) {
                                mSeekbarServiceLevel.setProgress(Integer.parseInt("100"));
                                mTxtServiceProgress.setText(String.format(getString(R.string.level_text_progress), lvlPer));
                            }else
                            {
                                if(ObjectUtils.isNumber(lvlPer)) {
                                    mSeekbarServiceLevel.setProgress(Integer.parseInt(lvlPer));
                                    mTxtServiceProgress.setText(String.format(getString(R.string.level_text_progress), lvlPer));
                                }
                            }
                            //기획 요청으로 기능삭제하였지만 기획 요청으로 다시 보여주는 것으로 변경
                            if(UserData.isLevelUp(lvl)) {
                                mLevelUpDialog = new LevelUpDialog(getActivity(), UserData.getSvcLvl());
                                mLevelUpDialog.show();
                            }
                        } else {
                            llLevel.setVisibility(View.GONE);
                            mLayoutServiceLevel.setVisibility(View.GONE);
                        }


                    }
                    break;
                case URL_SIGNON:
                    SignOnObject signOnObject = ObjectUtils.getGson(response, SignOnObject.class);
                    if (signOnObject == null)
                        break;

                    strRstCode = signOnObject.rst.rstCode;

                    String isBlock = signOnObject.memberBlockCheck.isBlock;
                    Preference preference = Preference.get(getActivity());

                    if (strRstCode != null && strRstCode.equals("0")) {
                        if (isBlock.equals("1")) {
                            showBlockDialog(signOnObject);
                        } else {
                            broadcastLogin(signOnObject, false);
                        }
                    } else if (strRstCode != null && strRstCode.equals("A")) {
                        DialogManager.getInstance(getActivity()).showPopkonDialog(getString(R.string.app_name), signOnObject.rst.rstMsg,
                                (dialog, which) -> {},
                                (dialog, which) -> {});

                        userLoginInfo(false);

                        preference.setPreference(Preference.KEY_LOGIN_AUTO, false);
                        preference.setPreference(Preference.KEY_LOGIN_PASS, "");
                    } else {
                        if (isBlock.equals("1")) {
                            showBlockDialog(signOnObject);
                        }else {
                            SnackbarHelper.make(SnackbarHelper.getRootView(this), getString(R.string.auto_login_fail), Snackbar.LENGTH_SHORT).show();
                        }
                        userLoginInfo(false);

                        preference.setPreference(Preference.KEY_LOGIN_AUTO, false);
                        preference.setPreference(Preference.KEY_LOGIN_PASS, "");
                    }
                    break;
                case URL_PUSH_INIT:
                    UserData.mPushKey = response;
                    requestUrlPushOnOff(URL_PUSH_SET, PUSH_SEARCH, "", "", "F");
                    break;
                case URL_PUSH_SET:
                    PushOnOffObject pushOnOffObject = ObjectUtils.getGson(response, PushOnOffObject.class);

                    strRstCode = pushOnOffObject.pushOnOff.rstCode;
                    if (strRstCode != null && strRstCode.equals("0")) {
                        requestUrlPushOnOff(URL_PUSH_ENABLED, PUSH_LOGIN, UserData.mPushKey, pushOnOffObject.pushOnOff.isOn, "F");
                    } else {
                        SnackbarHelper.make(SnackbarHelper.getRootView(this), pushOnOffObject.pushOnOff.rstMsg, Snackbar.LENGTH_SHORT).show();
                    }
                    break;
                case URL_PUSH_ENABLED:
                    PushOnOffObject pushEnableObject = ObjectUtils.getGson(response, PushOnOffObject.class);
                    if (pushEnableObject == null)
                        break;

                    strRstCode = pushEnableObject.pushOnOff.rstCode;
                    if (strRstCode != null && strRstCode.equals("0")) {
                        SnackbarHelper.make(SnackbarHelper.getRootView(this), getString(R.string.login_success), Snackbar.LENGTH_SHORT).show();
                    } else {
                        SnackbarHelper.make(SnackbarHelper.getRootView(this), pushEnableObject.pushOnOff.rstMsg, Snackbar.LENGTH_SHORT).show();
                    }
                    break;
                case URL_COIN:
                    if (UserData.isLogin()) {
                        CastGetMemberCoinObject memberCoinStatusObject = ObjectUtils.getGson(response, CastGetMemberCoinObject.class);
                        String strAccountCoinValue = memberCoinStatusObject.rst.coin;

                        if (!DefineCode.PARTNER_CODE.equals("P-00117")) {
                            mTextPopkonLoginNotice.setVisibility(View.GONE);
                            llLevel.setVisibility(View.VISIBLE);
                            mLayoutServiceLevel.setVisibility(View.VISIBLE);

                            mLayoutPopkonMessage.setVisibility(View.VISIBLE);
                            mTextPopkonCut.setText(String.format(getResources().getString(R.string.own_popkon), getString(R.string.coin_name), PopkonUtils.getNumberFormat(strAccountCoinValue)));
                        }else
                        {
                            mTextPopkonCut.setText(PopkonUtils.makeMoneyType(Integer.parseInt(strAccountCoinValue)));
                        }
                    }
                    break;

                case URL_SIGN_OFF:
                    ResultObject pgExeObject = ObjectUtils.getGson(response, ResultObject.class);

                    if (pgExeObject.rst.rstCode.equals("0")) {
                        setLogout();
                    }
                    break;
                case URL_MESSAGE_CNT:
                    if (UserData.isLogin()) {
                        mTextPopkonLoginNotice.setVisibility(View.GONE);
                        llLevel.setVisibility(View.VISIBLE);
                        mLayoutServiceLevel.setVisibility(View.VISIBLE);

                        mLayoutPopkonMessage.setVisibility(View.VISIBLE);
                        PaperNewCntObject paperNewCntObject = ObjectUtils.getGson(response, PaperNewCntObject.class);
                        if (paperNewCntObject != null) {
                            strRstCode = paperNewCntObject.rst.rstCode;
                            if (strRstCode != null && "0".equals(strRstCode)) {
                                String msgCnt = paperNewCntObject.rst.paperNewCnt;
                                if (!ObjectUtils.isEmpty(msgCnt)) {
                                    showMsgCnt(msgCnt);
                                }
                            } else {
                                showMsgCnt("0");
                            }
                        } else {
                            showMsgCnt("0");
                        }
                    }
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorCallback(int nId, String strErrorMsg) {
        if (nId == URL_MESSAGE_CNT) {
            // 쪽지 BadgeView 예외처리
            showMsgCnt("0");
        }

        DialogManager.getInstance(getActivity()).showPopkonDialog(strErrorMsg);
    }

    private void showMsgCnt(String msgCnt) {
        if (UserData.isLogin()) {
            if (mMsgBadgeView == null) {
                Activity activity = getActivity();
                if (activity != null) {
                    mMsgBadgeView = PopkonUtils.getMessageBadgeView(activity, mImgMessageCnt);
                    mMsgBadgeView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Activity activity = getActivity();
                            if (activity == null) {
                                return;
                            }
                            Intent intent = new Intent(getActivity(), MessageActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }

            if (mLayoutPopkonMessage != null) {
                mLayoutPopkonMessage.setVisibility(View.VISIBLE);
            }
            if (ObjectUtils.isEmpty(msgCnt) || "0".equals(msgCnt)) {
                mMsgBadgeView.unbind();
                mMsgBadgeView = null;
            } else {
                try {
                    int msgCount = Integer.parseInt(msgCnt);
                    if (msgCount >= 100) {
                        msgCnt = "99+";
                    }
                    Activity activity = getActivity();
                    if (activity != null) {
                        if (mMsgBadgeView == null) {
                            mMsgBadgeView = PopkonUtils.getMessageBadgeView(activity, mImgMessageCnt);
                        }
                        mMsgBadgeView.setBadgeCount(msgCnt);
                    } else {
                        mMsgBadgeView.unbind();
                        mMsgBadgeView = null;
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        } else {
            if (mLayoutPopkonMessage != null) {
                mLayoutPopkonMessage.setVisibility(View.GONE);
            }
            mMsgBadgeView.unbind();
            mMsgBadgeView = null;
        }
    }

    private void broadcastLogin(SignOnObject signOnObject, boolean isCounsel) {
        String strSignId = Preference.get(getActivity()).getPreference(Preference.KEY_LOGIN_ID, "");
        String strAnother = signOnObject.signOn.nickName;
        String strUserImgPath = signOnObject.signOn.pFileName;

        String strPwdCode = signOnObject.signOn.pwdCode;

        String strNickName = signOnObject.signOn.nickName;
        String strIsAdultCheck = signOnObject.signOn.isAdult;
        String strIsLevelUp = signOnObject.signOn.levelUp;
        String strSvcLvl = signOnObject.signOn.svcLvl;
        UserData.mSex = signOnObject.signOn.memberSex;
        UserData.mAccountLevel = signOnObject.signOn.accountLevel;
        UserData.mIsNameCheck = signOnObject.signOn.isNameCheck;

        UserData.setUserInfo(strSignId,
                strUserImgPath,
                "",
                strPwdCode,
                DefineCode.PARTNER_CODE,
                strNickName,
                strIsAdultCheck,
                strIsLevelUp,
                strSvcLvl);
        MarketingSDKManager.eventLogin(getContext());

//cmkim FCM TEST
        FCMMain fcmMain = new FCMMain(getActivity());
        fcmMain.setCallback(this, URL_PUSH_INIT);
        fcmMain.getInstanceIdToken();

        userLoginInfo(true);

        Intent refreshIntent = new Intent(MainActivity.LOGIN_REFRESH_ACTION);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(refreshIntent);

//        View dlgRootView = SnackbarHelper.getRootView(getActivity());
//
//        if (dlgRootView != null)
//            SnackbarHelper.make(dlgRootView, getString(R.string.login_success), Snackbar.LENGTH_SHORT).show();

        Preference preference = Preference.get(getActivity());
        boolean mLockChk = preference.getPreference(Preference.KEY_LOCK, false);

        if (mLockChk && UserData.mSignId != null) {
            /* 잠금 화면을 최상위로 덮게 수정했으므로 주석 처리 */
            // if( MainActivity.mPopCheck )
            ((MainActivity) getActivity()).setLockScreen();

            if(isCounsel) {
                PopkonUtils.moveCallWebPage(getActivity(), Constant.CallWeb.COUNSEL_PAGE);
            }
        } else {
            if (UserData.isLogin() && ! isShowPolicyDialog(isCounsel)){
                if(UserData.isLevelUp()) {
                    mLevelUpDialog = new LevelUpDialog(getActivity(), UserData.getSvcLvl());
                    mLevelUpDialog.setPositiveButton(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ((MainActivity) getActivity()).pushWatch();

                            if(isCounsel) {
                                PopkonUtils.moveCallWebPage(getActivity(), Constant.CallWeb.COUNSEL_PAGE);
                            }
                        }
                    });
                    mLevelUpDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            ((MainActivity) getActivity()).pushWatch();

                            if(isCounsel) {
                                PopkonUtils.moveCallWebPage(getActivity(), Constant.CallWeb.COUNSEL_PAGE);
                            }
                        }
                    });
                    mLevelUpDialog.show();
                } else {
                    ((MainActivity) getActivity()).pushWatch();

                    if(isCounsel) {
                        PopkonUtils.moveCallWebPage(getActivity(), Constant.CallWeb.COUNSEL_PAGE);
                    }
                }
            }
        }

        if (!DefineCode.PARTNER_CODE.equals("P-00117")) {
            mLayoutPopkonMessage.setVisibility(View.VISIBLE);
            requestUrlPaperNewCnt();
        }
    }

    public boolean isShowPolicyDialog(boolean isCounsel)
    {
        Preference preference = new Preference(getActivity());
        String LowDate = preference.getPreference(preference.KEY_CAST_WEEK_DATE, "");

        if( !ObjectUtils.isEmpty(LowDate) )
        {
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyyMMdd");
            String strNow = sdfNow.format(date);

            if( !((Integer.parseInt(LowDate) <= Integer.parseInt(strNow))
                    && UserData.isLogin()) )
                return false;
        }

        boolean onOffEventPush = preference.getPreference(Preference.KEY_AGREE_PUSH_EVENT, false);
        Bundle bundleArgs = new Bundle();
        if( onOffEventPush )
        {
            // Event Push 동의함 > 이벤트 푸쉬 동의 제거
            bundleArgs.putInt(GuidelineAndPolicyDialog.TYPE_SHOWING_EVENTPUSH_BUNDLE_KEY, GuidelineAndPolicyDialog.TYPE_GUIDELINE);
        }
        else
        {
            // Event Push 동의 안함 > 이벤트 푸쉬 동의 노출
            bundleArgs.putInt(GuidelineAndPolicyDialog.TYPE_SHOWING_EVENTPUSH_BUNDLE_KEY, GuidelineAndPolicyDialog.TYPE_GUIDELINE_EVENTPUSH);
        }
        final GuidelineAndPolicyDialog dialog = new GuidelineAndPolicyDialog();
        dialog.setArguments(bundleArgs);
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), "GuidelineAndPolicyDialog");
        dialog.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                if( dialog.getIsAgree() )
                {
                    dialog.dismiss();

                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyyMMdd");
                    String strNow = sdfNow.format(date);

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    cal.add(Calendar.DATE, 7);

                    Preference preference = new Preference(getActivity());
                    preference.setPreference(Preference.KEY_CAST_WEEK_DATE, sdfNow.format(cal.getTime()));

                    if(UserData.isLevelUp()) {
                        mLevelUpDialog = new LevelUpDialog(getActivity(), UserData.getSvcLvl());
                        mLevelUpDialog.setPositiveButton(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ((MainActivity) getActivity()).pushWatch();

                                if(isCounsel) {
                                    PopkonUtils.moveCallWebPage(getActivity(), Constant.CallWeb.COUNSEL_PAGE);
                                }
                            }
                        });
                        mLevelUpDialog.show();
                    } else {
                        ((MainActivity) getActivity()).pushWatch();

                        if(isCounsel) {
                            PopkonUtils.moveCallWebPage(getActivity(), Constant.CallWeb.COUNSEL_PAGE);
                        }
                    }
                }
                else
                {

                    View dlgRootView = SnackbarHelper.getRootView(dialog);
                    if( dlgRootView != null )
                    {
                        SnackbarHelper.make(dlgRootView, getString(R.string.app_name)
                                + " "
                                + getString(R.string.do_agree_guideline), SnackbarHelper.DURATION_TYPE.SHOW_INDEFINITE, getString(R.string.ok), null).show();
                    }
                }

            }
        });

        return true;
    }

    private void showBlockDialog(final SignOnObject signOnObject) {
        final BlockDialog blockDialog = new BlockDialog(getActivity());
        blockDialog.setData(signOnObject);
        blockDialog.setCounselButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (signOnObject.memberBlockCheck.isLoginBlock.equals("0")) {
                    broadcastLogin(signOnObject, true);
                }
            }
        });
        blockDialog.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (signOnObject.memberBlockCheck.isLoginBlock.equals("0")) {
                    broadcastLogin(signOnObject, false);
                }
            }
        });
        blockDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (signOnObject.memberBlockCheck.isLoginBlock.equals("0")) {
                    broadcastLogin(signOnObject, false);
                }
            }
        });

        blockDialog.setCanceledOnTouchOutside(false);
        blockDialog.show();
    }

    /**
     * 해당포지션 선택고정
     *
     * @param pos
     */
    public void setSelectSlideMenu(boolean status, int pos) {

        if(mSlideMenuAdapter != null) {
            if (!status) {
                mSlideMenuAdapter.changeIndex(pos);
            } else { // 메인화면 상단 메뉴를 눌렀을때 좌측 메뉴 선택 되어있는 위치 설정
                if(pos == 0) {
                    mSlideMenuAdapter.changeIndex(0);
                } else if(pos == 1) {
                    mSlideMenuAdapter.changeIndex(0);
                } else if(pos == 2) {
                    mSlideMenuAdapter.changeIndex(2);
                } else if(pos == 3) {
                    mSlideMenuAdapter.changeIndex(0);
                } else {
                    mSlideMenuAdapter.changeIndex(0);
                }
            }
        }
    }

    public void setLogout(){
        Preference.get(getActivity()).setPreference(Preference.KEY_LOGIN_AUTO, false);
        String strSNS = Preference.get(getActivity()).getPreference(Preference.KEY_SNS, "");
        if (!strSNS.equals("")) {
            Preference.get(getActivity()).setPreference(Preference.KEY_LOGIN_ID, "");
            Preference.get(getActivity()).setPreference(Preference.KEY_LOGIN_PASS, "");
            Preference.get(getActivity()).setPreference(Preference.KEY_SNS, "");
        } else {
            Preference.get(getActivity()).setPreference(Preference.KEY_SNS, "");
        }

        MarketingSDKManager.eventLogout(getActivity());
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent(MainActivity.LOGIN_REFRESH_ACTION) );

        UserData.removeInfo();
        UserData.removeActivity();
        userLoginInfo(false);
        mDrawerLayout.closeDrawer(mFragmentContainerView);

        FirebaseMessaging.getInstance().unsubscribeFromTopic("POPKON_EVENT");
    }
}
