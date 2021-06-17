package com.theenm.common.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.theenm.android.BuildConfig;
import com.theenm.android.R;
import com.theenm.android.adapter.MultiItemAdapter;
import com.theenm.common.Constant;
import com.theenm.common.UserData;
import com.theenm.common.dialog.BlockDialog;
import com.theenm.common.http.HttpManager;
import com.theenm.common.http.schemas.LiveCastListObject;
import com.theenm.common.http.schemas.MemberBlockObject;
import com.theenm.common.http.schemas.SignOnObject;
import com.theenm.common.widget.badgeview.BadgeFactory;
import com.theenm.common.widget.badgeview.BadgeView;
import com.theenm.level.LevelFragment;
import com.theenm.login.FindIdPwActivity;
import com.theenm.login.GuideAndPolicyActivity;
import com.theenm.login.LoginActivity;
import com.theenm.login.MemberjoinActivity;
import com.theenm.service.center.ServiceWebActivity;
import com.theenm.setting.BanwordWebActivity;
import com.theenm.setting.ItemListActivity;
import com.theenm.setting.ItemPurchaseActivity;
import com.theenm.setting.PopkonPurchaseActivity;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static android.content.Context.ACTIVITY_SERVICE;
import static androidx.browser.customtabs.CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION;
import static com.theenm.android.MainActivity.LOGOUT_ACTION;
import static com.theenm.android.MainActivity.REQ_CODE_REFRESH;
import static com.theenm.login.GuideAndPolicyActivity.TERMS_OF_PRIVACY_TYPE;
import static com.theenm.login.GuideAndPolicyActivity.TERMS_OF_USAGE_TYPE;

/**
 * Created by 20110617 on 2017-04-18.
 */

public class PopkonUtils {
    private static final String TAG = PopkonUtils.class.getName();
    public final static int TYPE_WIFI = 1;
    public final static int TYPE_MOBILE = 2;
    public final static int TYPE_NOT_CONNECTED = 0;

    public static boolean checkPackage(Context context, String applicationId) {
        boolean result = false;
        PackageManager packageName = context.getPackageManager();
        List<PackageInfo> installList = packageName.getInstalledPackages(0);

        for (int i = 0; i < installList.size(); i++) {
            if (applicationId.equals(installList.get(i).packageName))
                result = true;
        }
        return result;
    }

	public static void deleteApp(Context context, String applicationId) {
		Uri packageURI = Uri.parse("package:" + applicationId);
		Intent uninstallIntent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageURI);
		context.startActivity(uninstallIntent);
	}

	public static ArrayList<String> getDeleteApps(Context context) {
		ArrayList<String> appArray = new ArrayList<>();

		String comparedAppName = null;
		if (BuildConfig.FLAVOR.equals("popkonTV_google")) {
			comparedAppName = "com.social.media.pop";
		} else {
			String[] array = BuildConfig.APPLICATION_ID.split("\\.");
			if (array.length == 4)
				comparedAppName = array[1] + ".android.air";
			else if (array.length == 5)
				comparedAppName = array[2] + ".android.air";
		}

		if (!ObjectUtils.isEmpty(comparedAppName)) {
			PackageManager packageName = context.getPackageManager();
			List<PackageInfo> installList = packageName.getInstalledPackages(0);

			for (int i = 0; i < installList.size(); i++) {
				String applicationId = installList.get(i).packageName;
                if (applicationId.contains(comparedAppName) && !applicationId.equals(BuildConfig.APPLICATION_ID) && !applicationId.equals("com.social.media.broadcast.aospop"))
					appArray.add(applicationId);
			}
		}
		
		return appArray;
	}

    public static ArrayList<CategoryList> getCategoryList()
    {
        ArrayList<CategoryList> categoryList = new ArrayList<>();

        if( UserData.getIsAdultCheck() )
        {
            for( int i = 0; i < UserData.mCategoryAdult.length; i++ )
            {
                CategoryList category = new CategoryList();

                String strCode = UserData.mCategoryAdult[i].code;

                if( !(strCode.equals("0") || strCode.equals("11")) )
                {
                    category.mLabel = UserData.mCategoryAdult[i].label;
                    category.mCode = UserData.mCategoryAdult[i].code;

                    categoryList.add(category);
                }
            }
        }
        else
        {

            for( int i = 0; i < UserData.mCategoryTeen.length; i++ )
            {
                CategoryList category = new CategoryList();

                String strCode = UserData.mCategoryTeen[i].code;

                if( !strCode.equals("0") || !strCode.equals("11") )
                {
                    category.mLabel = UserData.mCategoryTeen[i].label;
                    category.mCode = UserData.mCategoryTeen[i].code;

                    categoryList.add(category);
                }
            }
        }
        return categoryList;
    }

    public static int getDeviecWidth(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getDeviecHeight(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int getChatMinHeight(Context context){
        return getChatMinHeight(context, false);
    }

    public static int getChatMinHeight(Context context, boolean isPortrait){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int deviceHeight = dm.heightPixels;

        if(isPortrait){
            return deviceHeight * 25 / 100;
        } else {
            return deviceHeight * 4 / 10;
        }
//        return convertDpToPixels(context, 85);
    }

    public static int getChatMaxHeight(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int deviceHeight = dm.heightPixels;
        return deviceHeight * 7 / 10;
    }

    public static int getStatusBarSize(Context context)
    {
        int height = 0;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if( resourceId > 0 )
            height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    public static int getNavigationBarSize(Context context)
    {
        int height = 0;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if( resourceId > 0 )
            height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    public static boolean hasNavigationBar(Context context)
    {
        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        if( !hasMenuKey && !hasBackKey )
            return true;
        else
            return false;
    }

    public static boolean isWifiConnected(Context context)
    {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if( networkInfo.isConnected() )
            return true;
        else
            return false;
    }

    // [start] overloading
    public static String getNumberFormat(String s) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        if (TextUtils.isEmpty(s)) return s;

        try {
            return decimalFormat.format(Integer.parseInt(s));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

	public static String getNumberFormat(int n) {
		DecimalFormat decimalFormat = new DecimalFormat("#,##0");
		try {
			return decimalFormat.format(n);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(n);
	}

	public static String getNumberFormat(long l) {
		DecimalFormat decimalFormat = new DecimalFormat("#,##0");
		try {
			return decimalFormat.format(l);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(l);
	}
	// [end] overloading

    // [start] overloading
    public static String getOriginalNumberFormat(String s) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        if (TextUtils.isEmpty(s)) return s;

        try {
            return decimalFormat.parse(s).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public static String getOriginalNumberFormat(int n) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        try {
            return decimalFormat.parse(String.valueOf(n)).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(n);
    }

    public static String getOriginalNumberFormat(long l) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        try {
            return decimalFormat.parse(String.valueOf(l)).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(l);
    }

	// [start] overloading
	public static String getSimpleDate(String value, String input, String output) {
		try {
			SimpleDateFormat inputFormat = new SimpleDateFormat(input, Locale.getDefault());
			SimpleDateFormat outputFormat = new SimpleDateFormat(output, Locale.getDefault());
			Date date = inputFormat.parse(value);
			return outputFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return value;
		}
	}

	public static String getSimpleDate(String value, String input, String output, Locale locale) {
		try {
			SimpleDateFormat inputFormat = new SimpleDateFormat(input, Locale.getDefault());
			SimpleDateFormat outputFormat = new SimpleDateFormat(output, locale);
			Date date = inputFormat.parse(value);
			return outputFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return value;
		}
	}
	// [end] overloading

    public static class CategoryList implements Serializable {
        public String mLabel = null;
        public String mCode = null;
    }

    /**
     * Intent 에 설정된 Flag 중 FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY 가 있을 경우
     * 최근 실행 목록에서 실행한 것으로 판단한다.
     * 
     * @param intent
     * @return
     */
    public static boolean isActivityLaunchedFromHistory(Intent intent)
    {
        if( intent == null )
            return false;

        return ((intent.getFlags()
                & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) != 0);
    }

    /**
     * DP 값을 해상도와 맞는 Pixel 로 변환한다.
     *
     * @param dp
     * @return
     */
    public static final int convertDpToPixels(Context pContext, float dp) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp
            , pContext.getResources().getDisplayMetrics());
        return px;
    }

    public static final void hideKeyPad(Context pContext, EditText... editTexts) {
        if (pContext == null || editTexts == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) pContext.getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        for (EditText editText : editTexts) {
            if (editText != null) {
                // hide keyboard
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        }
    }

    public static final void showKeyPad(Context pContext, EditText pEditText) {
        if (pContext == null || pEditText == null) {
            return;
        }
        try {
            InputMethodManager imm = (InputMethodManager)pContext.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(pEditText, 0);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    /**
     * 쪽지 기능에 사용될 BadgeView 를 생성하여 리턴한다.
     *
     * @param pContext
     * @param pTargetImgView
     * @return
     */
    public static final BadgeView getMessageBadgeView(Context pContext, ImageView pTargetImgView)
    {
        if (pContext == null || pTargetImgView == null) {
            return null;
        }

        return BadgeFactory.createOval(pContext).setTextColor(Color.WHITE).setWidthAndHeight(20, 20)
            .setBadgeBackground(Color.RED).setTextSize(10).setBadgeGravity(Gravity.RIGHT | Gravity.TOP)
            .setShape(BadgeView.SHAPE_OVAL).setSpace(13, 8).setBadgeCount("0").bind(pTargetImgView);
    }

    /**
     * AUIL 이미지 로더 관련 특정 URI 에 해당하는 메모리 / 디스크 캐시 삭제
     *
     * @param imageUri
     */
    public static final void removeImageCache(String imageUri)
    {
        removeImageMemoryCache(imageUri);
        removeImageDiskCache(imageUri);
    }

    /**
     * AUIL 이미지 로더 관련 특정 URI 에 해당하는 메모리 캐시 삭제
     *
     * @param imageUri
     */
    public static final void removeImageMemoryCache(String imageUri)
    {
        try {
            MemoryCacheUtils.removeFromCache(imageUri, ImageLoader.getInstance().getMemoryCache());
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    /**
     * AUIL 이미지 로더 관련 특정 URI 에 해당하는 디스크 캐시 삭제
     *
     * @param imageUri
     */
    public static final void removeImageDiskCache(String imageUri)
    {
        try {
            DiskCacheUtils.removeFromCache(imageUri, ImageLoader.getInstance().getDiskCache());
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    /*
     * AUIL 이미지 로더 관련 메모리 / 디스크 캐시 모두 삭제
     */
    public static final void removeAllFromImageCache()
    {
        removeAllImageMemoryCache();
        removeAllImageDiskCache();
    }

    /**
     * AUIL 이미지 로더 관련 메모리 캐시 모두 삭제
     */
    public static final void removeAllImageMemoryCache()
    {
        try {
            ImageLoader.getInstance().clearMemoryCache();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    /**
     * AUIL 이미지 로더 관련 디스크 캐시 모두 삭제
     */
    public static final void removeAllImageDiskCache()
    {
        try {
            ImageLoader.getInstance().clearDiskCache();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    /*
     * 시청하려는 방송을 만든 사용자의 아이디와 시청하려는 사용자의 아이디가 같은지 확인
     */
    public static boolean checkWatchSignId(String watchSignId){
        if(watchSignId == null) return false;

        return watchSignId.equals(UserData.mSignId);
    }

    /*
     * 방송 시청 시에 방송 안내 팝업을 보여줄지 여부를 판단
     * 비번방, 성인방이 아니고 wifi에 연결되어 있거나 LTE 상태인데 세팅에서 LTE 팝업을 보여주지 않도록 설정하였을 경우 : 방송 바로 입장
     */
    public static boolean checkEnterPopup(Context context, String isPrivate, String isAdult){
        boolean isShowPopup = false;
        boolean bNetworkCheck = Preference.get(context).getPreference(Preference.KEY_NETWORK_CHECK, true);

        if(isPrivate.equals("0") &&
                isAdult.equals("0") &&
                (isConnectNetwork(context) == PopkonUtils.TYPE_WIFI || (PopkonUtils.isConnectNetwork(context) == PopkonUtils.TYPE_MOBILE && !bNetworkCheck))){
            isShowPopup = false;
        } else {
            isShowPopup = true;
        }
        return isShowPopup;
    }

    /**
     * 앱 재실행 메서드
     *
     * @param pActivity
     */
    public static final void restartApp(Activity pActivity) {
        if (pActivity == null)
            return;

        String pkgName = pActivity.getBaseContext().getPackageName();
        if (TextUtils.isEmpty(pkgName))
            return;

        Intent intent = pActivity.getBaseContext().getPackageManager().
                getLaunchIntentForPackage(pkgName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pActivity.startActivity(intent);
    }

    public static final boolean indexExists(final List list, final int index) {
        if (list == null || list.isEmpty())
            return false;

        return ((index >= 0) && (index < list.size()));
    }

    public static final void makeStatusBarTranslucent(Activity pActivity, boolean makeTranslucent) {
        if (pActivity == null)
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (makeTranslucent) {
                pActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            } else {
                pActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    /**
     * ViewTreeObserver 리스너 해제를 위한 유틸 메서드
     *
     * @param observer
     * @param listener
     */
    public static final void removeOnGlobalLayoutListener(ViewTreeObserver observer, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (observer == null) {
            return ;
        }

        if (observer.isAlive())
        {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                observer.removeGlobalOnLayoutListener(listener);
            } else {
                observer.removeOnGlobalLayoutListener(listener);
            }
        }
    }

    public static int isConnectNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;
            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    //색상세팅
    public static int colorSetting(Context mConext, int color) {
        return ContextCompat.getColor(mConext, color);
    }

    //특정위치 텍스트뷰 색상처리(한가지)
    public static void textColorType(TextView tv, String strContents, int color, int start, int end) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(strContents);
        ssb.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(ssb);
    }

    //특정위치 텍스트뷰 색상처리(두가지)
    public static void textColorType(TextView tv, String strContents, int color, int start, int end, int second_start, int second_end) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(strContents);
        ssb.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new ForegroundColorSpan(color), second_start, second_end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(ssb);
    }

    //텍스트뷰 볼드처리
    public static void textBoldType(TextView tv) {
        if (tv != null) {
            tv.setPaintFlags(tv.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        }
    }

    //닉네임체크
    public static boolean isNick(String nick) {
        if (nick == null)
            return false;
        if (nick.trim().length() < 1)
            return false;
        boolean flag = Pattern.matches("^[가-힣a-zA-Z0-9]*$", nick);
//        boolean flag = Pattern.matches("^[ㄱ-ㅎ가-힣a-zA-Z0-9 ㅏ-ㅣ]*$", nick);
        return flag;
    }

    //이메일체크형태 확인
    public static boolean isEmail(String email) {
        if (email == null)
            return false;
        if (email.trim().length() < 1)
            return false;

        boolean flag;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);

        if (matcher.find()) {
            flag = true;
        } else {
            flag = false;
        }

        return flag;
    }

    //비밀번호체크(특수문자,숫자,문자)
    public static boolean isPassword(String password) {
        if (password == null)
            return false;

        boolean flag;
        Pattern p = Pattern.compile("^[ㄱ-ㅎ가-힣ㅏ-ㅣ]{6,20}$"); // 6자리 ~ 20자리까지 가능---특수문자없이
//        Pattern p = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~/]{6,20}$"); // 6자리 ~ 20자리까지 가능---특수문자없이
//        Pattern p = Pattern.compile("([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9]{6,20}$)");
        Matcher m = p.matcher(password);
        if (m.find()) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    //세자리 자르기
    public static String makeMoneyType(int nMoney) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.KOREAN);
        return nf.format(nMoney);
    }

    private static class TIME_MAXIMUM{
        public static final int SEC = 60;
        public static final int MIN = 60;
        public static final int HOUR = 24;
        public static final int DAY = 30;
        public static final int MONTH = 12;
    }
    public static String getDifferenceTime(long regTime) {
        long curTime = System.currentTimeMillis();
        long diffTime = (curTime - regTime) / 1000 / TIME_MAXIMUM.SEC;
        String msg = null;
        if (diffTime < TIME_MAXIMUM.MIN) {
            msg = diffTime + "분 전";
        } else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
            msg = (diffTime) + "시간 전";
        } else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
            msg = (diffTime) + "일 전";
        } else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
            msg = (diffTime) + "개월 전";
        } else {
            diffTime /= TIME_MAXIMUM.MONTH;
            msg = (diffTime) + "년 전";
        }
        return msg;
    }

    public static void clearAppData(Context context){
        if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
            ((ActivityManager)context.getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData();
        }
    }

    public static void showWatchBlockDialog(Context context, Object object) {
        String isLoginBlock = null;
        if(object instanceof SignOnObject){
            isLoginBlock = ((SignOnObject) object).memberBlockCheck.isLoginBlock;
        }else if(object instanceof MemberBlockObject){
            isLoginBlock = ((MemberBlockObject) object).memberBlockCheck.isLoginBlock;
        }

        BlockDialog blockDialog = new BlockDialog(context);
        blockDialog.setData(object);
        blockDialog.setCounselButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveCallWebPage(context, Constant.CallWeb.COUNSEL_PAGE);
            }
        });
        String finalIsLoginBlock = isLoginBlock;
        blockDialog.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (finalIsLoginBlock.equals("1")) {
                    Intent intent = new Intent(LOGOUT_ACTION);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
            }
        });
        blockDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (finalIsLoginBlock.equals("1")) {
                    Intent intent = new Intent(LOGOUT_ACTION);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
            }
        });
        blockDialog.setCanceledOnTouchOutside(false);
        blockDialog.show();
    }


    public static void moveCallWebPage(Context context, Constant.CallWeb webCallPage) {
        moveCallWebPage(context, webCallPage, null);
    }

    public static void moveCallWebPage(Context context, Constant.CallWeb webCallPage, String bypassURL) {
        if((webCallPage != Constant.CallWeb.JOIN_MEMBER && webCallPage != Constant.CallWeb.FIND_ID_PW) &&
                UserData.isUpdate()) {
            return;
        }

        if((webCallPage == Constant.CallWeb.PURCHASE_COIN || webCallPage == Constant.CallWeb.PURCHASE_ITEM) &&
                (TextUtils.isEmpty(UserData.mIsNameCheck) || UserData.mIsNameCheck.equals("0"))){
            moveCallWebPage(context, Constant.CallWeb.USER_CERTIFICATION);
            return;
        }

        int isHybrid = 0;
        switch (webCallPage){
            case PURCHASE_COIN:
            case PURCHASE_ITEM:
                isHybrid = BuildConfig.FLAVOR.contains("_skt") ? 0 : 1;
                break;
            case FIND_ID_PW:
            case MY_ITEM:
            case SERVICE_CENTER:
            case USE_AGREEMENT:
            case PRIVACY_POLICY:
            case USER_CERTIFICATION:
            case COUNSEL_PAGE:
            case USER_LEVEL:
            case SETTING_BAN_WORD:
                isHybrid = 1;
                break;
            default:
                isHybrid = 0;
                break;
        }

        String strUrlLink = HttpManager.getInstance(context).
                urlCallWeb(webCallPage.ordinal(),
                        null,
                        null,
                        bypassURL,
                        isHybrid,
                        null,
                        null,
                        null);

        if (!ObjectUtils.isEmpty(strUrlLink)) {
            String strUrl = strUrlLink.replace("\\", "");
            int requestCode = 0;
            Intent intent = null;
            switch (webCallPage) {
                case PURCHASE_COIN:
                    if (BuildConfig.FLAVOR.contains("_skt")) {
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strUrl));
                    } else {
                        intent = new Intent(context, PopkonPurchaseActivity.class);
                        intent.putExtra(Constant.BundleKey.CALLWEB_URL, strUrl);
                    }
                    context.startActivity(intent);
                    break;
                case FIND_ID_PW:
                    intent = new Intent(context, FindIdPwActivity.class);
                    intent.putExtra(Constant.BundleKey.CALLWEB_URL, strUrl);
                    context.startActivity(intent);
                    break;
                case PURCHASE_ITEM:
                    if (BuildConfig.FLAVOR.contains("_skt")) {
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strUrl));
                    } else {
                        intent = new Intent(context, ItemPurchaseActivity.class);
                        intent.putExtra(Constant.BundleKey.CALLWEB_URL, strUrl);
                    }
                    context.startActivity(intent);
                    break;
                case MY_ITEM:
                    intent = new Intent(context, ItemListActivity.class);
                    intent.putExtra(Constant.BundleKey.CALLWEB_URL, strUrl);
                    context.startActivity(intent);
                    break;
                case SERVICE_CENTER:  // 고객센터 하이브리드로 변경
                case COUNSEL_PAGE:  // 고객센터 하이브리드로 변경
                    intent = new Intent(context, ServiceWebActivity.class);
                    intent.putExtra(Constant.BundleKey.CALLWEB_URL, strUrlLink);
                    context.startActivity(intent);
                    break;
                case USE_AGREEMENT:
                    intent = new Intent(context, GuideAndPolicyActivity.class);
                    intent.putExtra(Constant.BundleKey.GUIDE_POLICY_TYPE, TERMS_OF_USAGE_TYPE);
                    intent.putExtra(Constant.BundleKey.CALLWEB_URL, strUrl);
                    context.startActivity(intent);
                    break;
                case PRIVACY_POLICY:
                    intent = new Intent(context, GuideAndPolicyActivity.class);
                    intent.putExtra(Constant.BundleKey.GUIDE_POLICY_TYPE, TERMS_OF_PRIVACY_TYPE);
                    intent.putExtra(Constant.BundleKey.CALLWEB_URL, strUrl);
                    context.startActivity(intent);
                    break;
                case USER_CERTIFICATION:
                    intent = new Intent(context, MemberjoinActivity.class);
                    intent.putExtra(Constant.BundleKey.CALLWEB_URL, strUrl);
                    ((Activity) context).startActivityForResult(intent, REQ_CODE_REFRESH);
                    break;
                case USER_LEVEL:
                    LevelFragment levelFragment = LevelFragment.newInstance(strUrl);
                    levelFragment.addFragment(context, R.id.fragment_container);
                    break;
                case SETTING_BAN_WORD: // 2021-06-07 my.kim 채팅금지어 하이브리드로 변경(iOS 통일)
                    intent = new Intent(context, BanwordWebActivity.class);
                    intent.putExtra(Constant.BundleKey.CALLWEB_URL, strUrl);
                    context.startActivity(intent);
                    break;
                case RECEIVE_COIN:
                case USE_COIN:
                case JOIN_MEMBER:
                case DOWNLOAD_PAGE:
                case DIRECT_MOVE_PAGE:
                case PURCHASE_COIN_LIST:
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strUrl));
                    context.startActivity(intent);
                    break;
            }
        }
    }

    public static void moveLoginActivity(Context context){
        moveLoginActivity(context, LoginActivity.ID_LOGIN_CHANGE);
    }

    public static void moveLoginActivity(Context context, int requestCode){
        Intent intent = new Intent(context, LoginActivity.class);
        ((Activity)context).startActivityForResult(intent, requestCode);
    }

    /**
     * Returns a list of packages that support Custom Tabs.
     */
    public static ArrayList<ResolveInfo> getCustomTabsPackages(Context context) {
        PackageManager pm = context.getPackageManager();
        // Get default VIEW intent handler.
        Intent activityIntent = new Intent()
                .setAction(Intent.ACTION_VIEW)
                .addCategory(Intent.CATEGORY_BROWSABLE)
                .setData(Uri.fromParts("http", "", null));

        // Get all apps that can handle VIEW intents.
        List<ResolveInfo> resolvedActivityList = pm.queryIntentActivities(activityIntent, 0);
        ArrayList<ResolveInfo> packagesSupportingCustomTabs = new ArrayList<>();
        for (ResolveInfo info : resolvedActivityList) {
            Intent serviceIntent = new Intent();
            serviceIntent.setAction(ACTION_CUSTOM_TABS_CONNECTION);
//            serviceIntent.setPackage(info.activityInfo.packageName);
            serviceIntent.setPackage("com.android.chrome");
            // Check if this package also resolves the Custom Tabs service.
            if (pm.resolveService(serviceIntent, 0) != null) {
                packagesSupportingCustomTabs.add(info);
            }
        }
        return packagesSupportingCustomTabs;
    }

    public static Drawable getDrawableGradient(Drawable drawable, int startColor, int endColor){
        Drawable mutateDrawable = drawable.mutate();

        int[] GradientColor = new int[] { startColor, endColor};

        LayerDrawable layerDrawable = (LayerDrawable)mutateDrawable;
        GradientDrawable gradientDrawable = (GradientDrawable)layerDrawable.findDrawableByLayerId(R.id.shape_gradient);
        gradientDrawable.setColors(GradientColor);
        gradientDrawable.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);

        return mutateDrawable;
    }

    public static Drawable getLevelBackground(Context context, int level){
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.shape_level_background);

        if(level >= 0 && level < 40) {
            return getDrawableGradient(drawable, ContextCompat.getColor(context, R.color.color_ABBED1), ContextCompat.getColor(context, R.color.color_ABBED1));
        } else if(level >= 40 && level < 60) {
            return getDrawableGradient(drawable, ContextCompat.getColor(context, R.color.color_00AB92), ContextCompat.getColor(context, R.color.color_00AB92));
        } else if(level >= 60 && level < 80) {
            return getDrawableGradient(drawable, ContextCompat.getColor(context, R.color.color_0057EC), ContextCompat.getColor(context, R.color.color_0057EC));
        } else if(level >= 80 && level < 95) {
            return getDrawableGradient(drawable, ContextCompat.getColor(context, R.color.color_FF3366), ContextCompat.getColor(context, R.color.color_FF3366));
        } else if(level >= 95 && level < 100) {
            return getDrawableGradient(drawable, ContextCompat.getColor(context, R.color.color_9405E5), ContextCompat.getColor(context, R.color.color_9405E5));
        } else if(level >= 100) {
            return getDrawableGradient(drawable, ContextCompat.getColor(context, R.color.color_FFBB00), ContextCompat.getColor(context, R.color.color_FF00A1));
        } else {
            return getDrawableGradient(drawable, ContextCompat.getColor(context, R.color.color_FFFFFF), ContextCompat.getColor(context, R.color.color_FFFFFF));
        }
    }

    /**
     * 2021-04-16 my.kim
     * @param strColor 채팅서버 메시지 색상 값
     * @param existColor 기존 사용 색상 값 (채팅서버 값 없을 시 사용)
     * @return
     */
    public static int getChatColor(String strColor, int existColor){
        int chatColor = (strColor == null) ? existColor : Color.parseColor(strColor);
        return chatColor;
    }

    public static List<MultiItemAdapter.Row<?>> setViewTypeCreate(HashMap<Constant.ReCyclerType, Object> items) {
        if(items == null) return null;

        List<MultiItemAdapter.Row<?>> rows = new ArrayList<>();
        Object[] KeySet = items.keySet().toArray();

        Arrays.sort(KeySet);

        for (int i = 0; i < KeySet.length; i++) {
            Constant.ReCyclerType key = (Constant.ReCyclerType)KeySet[i];
            Object value = items.get(key);

            if(key == Constant.ReCyclerType.TYPE_CELUV_LIVE_SORT ||
                    key == Constant.ReCyclerType.TYPE_CELUV_LIVE_FOOTER ||
                    key == Constant.ReCyclerType.TYPE_POPKON_PORTAL_LIVE_MENU ||
                    key == Constant.ReCyclerType.TYPE_LIVE_SHOP_LIVE_SECTION_ROW ||
                    key == Constant.ReCyclerType.TYPE_LIVE_SHOP_REVIEW_SECTION_ROW ||
                    key == Constant.ReCyclerType.TYPE_POPKON_PORTAL_LIVE_SECTION_BEST_ROW ||
                    key == Constant.ReCyclerType.TYPE_POPKON_PORTAL_LIVE_SECTION_BOOKMARK_ROW ||
                    key == Constant.ReCyclerType.TYPE_POPKON_PORTAL_LIVE_MORE ||
                    key == Constant.ReCyclerType.TYPE_POPKON_PORTAL_VOD_MENU ||
                    key == Constant.ReCyclerType.TYPE_POPKON_PORTAL_VOD_SECTION_BEST_ROW ||
                    key == Constant.ReCyclerType.TYPE_POPKON_PORTAL_VOD_MORE ||
                    key == Constant.ReCyclerType.TYPE_POPKON_PORTAL_ADULT_MENU ||
                    key == Constant.ReCyclerType.TYPE_POPKON_PORTAL_ADULT_SECTION_BEST_ROW ||
                    key == Constant.ReCyclerType.TYPE_BOOKMARK_ON_AIR_HEADER_ROW ||
                    key == Constant.ReCyclerType.TYPE_POPKON_PORTAL_ADULT_SECTION_BOOKMARK_ROW ||
                    key == Constant.ReCyclerType.TYPE_POPKON_PORTAL_ADULT_MORE ||
                    key == Constant.ReCyclerType.TYPE_VOD_COMMENT_HEADER_ROW ||
                    key == Constant.ReCyclerType.TYPE_SETTING_HISTORY_PAY_HEADER ||
                    key == Constant.ReCyclerType.TYPE_SETTING_HISTORY_USE_HEADER ||
                    key == Constant.ReCyclerType.TYPE_SETTING_HISTORY_RECEIVE_HEADER ||
                    key == Constant.ReCyclerType.TYPE_BOOKMARK_OFF_AIR_HEADER_ROW ||
                    key == Constant.ReCyclerType.TYPE_SEARCH_LIVE_EMPTY_LIST ||
                    key == Constant.ReCyclerType.TYPE_SEARCH_VOD_EMPTY_LIST ||
                    key == Constant.ReCyclerType.TYPE_LIVE_SHOP_LIVE_EMPTY_LIST ||
                    key == Constant.ReCyclerType.TYPE_LIVE_SHOP_REVIEW_EMPTY_LIST ||
                    key == Constant.ReCyclerType.TYPE_EMPTY_LIST) {
                rows.add(MultiItemAdapter.Row.create(value, key.ordinal()));
            }else {
                for (Object object : (ArrayList<?>) value) {
                    rows.add(MultiItemAdapter.Row.create(object, key.ordinal()));
                }
            }
        }

        return rows;
    }

    public static void removeDuplicatedSignId(LiveCastListObject[] pNewLiveList, ArrayList<LiveCastListObject> compareLiveList) {
        if (pNewLiveList == null || pNewLiveList.length <= 0) {
            return;
        }
        if (compareLiveList == null || compareLiveList.isEmpty()) {
            return;
        }
        try {
            // 이전 ArrayList 와 신규 리스트를 비교하여 중복이되면 이전 ArrayList 에서 삭제만 한다.
            String castSignId = null, targetCastSignId = null;
            LiveCastListObject item = null;
            int size = pNewLiveList.length, lastIdxOfList = 0;
            for (int i=0; i<size; i++) {
                if (pNewLiveList[i] == null) {
                    continue;
                }
                castSignId = pNewLiveList[i].castSignId;
                if (castSignId == null) {
                    continue;
                }
                lastIdxOfList = (compareLiveList != null)? compareLiveList.size() - 1 : -1;
                //int diff = (lastIdxOfList >= 10)? lastIdxOfList - 10 : 0; // 전체 = 0, 마지막 요소에서 10개까지
                int diff = 0;
                for (int j=lastIdxOfList; j>=diff; j--) {
                    item = compareLiveList.get(j);
                    if (item == null) {
                        continue;
                    }
                    targetCastSignId = item.castSignId;
                    if (castSignId.equals(targetCastSignId)) {
                        compareLiveList.remove(j);
                    }
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
}
