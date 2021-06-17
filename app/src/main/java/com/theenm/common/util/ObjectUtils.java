package com.theenm.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.gson.Gson;

import android.content.res.Resources;
import android.util.Log;

/**
 * Created by 20110617 on 2017-01-11.
 */

public class ObjectUtils
{

    private static final String TAG = ObjectUtils.class.getName();

    public static boolean isEmpty(Object object)
    {
        if( object == null )
        {
            return true;
        }
        if( (object instanceof String)
                && (((String) object).trim().length() == 0) )
        {
            return true;
        }
        if( object instanceof Map )
        {
            return ((Map<?, ?>) object).isEmpty();
        }
        if( object instanceof List )
        {
            return ((List<?>) object).isEmpty();
        }
        if( object instanceof Object[] )
        {
            return (((Object[]) object).length == 0);
        }
        return false;
    }

    public static boolean isNumber(String str)
    {
        if( !isEmpty(str) && Pattern.matches("^[0-9]+$", str) )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean equals(Object a, Object b)
    {
        return (a == null) ? (b == null) : a.equals(b);
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value)
    {
        for( Map.Entry<T, E> entry : map.entrySet() )
        {
            if( equals(value, entry.getValue()) )
            {
                return entry.getKey();
            }
        }
        return null;
    }

    public static int parseInt(String s, int defValue)
    {
        try
        {
            return Integer.parseInt(s);
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }

        return defValue;
    }

    public static Date dateFormatParse(String s, String format)
    {
        Date result = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try
        {
            result = simpleDateFormat.parse(s);
        }
        catch( Exception e )
        {
        }

        return result;
    }

    public static String getResString(Resources res, int nId)
    {
        if( res == null )
            return "";

        String strRtn = "";

        try
        {
            strRtn = res.getString(nId);
        }
        catch( Resources.NotFoundException e )
        {
            strRtn = "";
        }

        return strRtn;
    }

    public static <T> T getGson(String response, Class<T> classOfT)
    {
        Gson gson = new Gson();
        Object object = null;

        try
        {
            if( !isEmpty(response) && classOfT != null )
                object = gson.fromJson(response, classOfT);
        }
        catch( Exception e )
        {
            Log.e(TAG, e.getMessage() + "");
        }

        return (T) object;
    }

}
