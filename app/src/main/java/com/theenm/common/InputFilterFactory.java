package com.theenm.common;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Pattern;

/**
 * Created by khj0704 on 2018-05-30.
 */
public final class InputFilterFactory
{

    private InputFilterFactory() {
        // Do NOT generate instances!
    }

    public enum Type {
        INPUT_FILTER_ID_TYPE,
        INPUT_FILTER_PWD_TYPE,
        INPUT_FILTER_NICKNAME_TYPE,
        INPUT_FILTER_PHONE_NUMBER_TYPE
    }

    /**
     * 타입에 맞는 InputFilter 를 리턴한다.
     *
     * @param pType
     * @return
     */
    public static final InputFilter getInputFilterByType(final Type pType)
    {
        switch (pType) {
            case INPUT_FILTER_ID_TYPE:
                return new FilterIdAlphaNum();

            case INPUT_FILTER_PWD_TYPE:
                return new FilterPassword();

            case INPUT_FILTER_NICKNAME_TYPE:
                return new FilterNickName();

            case INPUT_FILTER_PHONE_NUMBER_TYPE:
                return new FilterPhoneNumber();
        }

        return new DefaultInputFilter();
    }

    /**
     * InputFilter 배열을 리턴한다.
     * InputFilter 를 적용할 경우 xml 속성에 maxLength 속성이 동작하지 않는다.
     * 따라서 텍스트 필터와 최대 길이 필터를 배열로 묶어서 등록해야 한다.
     *
     * @param pInputFilter
     * @param maxLength
     * @return
     */
    public static final InputFilter[] getInputFilterArray(final InputFilter pInputFilter, final int maxLength)
    {
        if (pInputFilter != null) {
            InputFilter[] filterArray = new InputFilter[2];
            filterArray[0] = new InputFilter.LengthFilter(maxLength);
            filterArray[1] = pInputFilter;
            return filterArray;
        } else {
            InputFilter[] lengthFilter = new InputFilter[1];
            lengthFilter[0] = new InputFilter.LengthFilter(maxLength);
            return lengthFilter;
        }
    }

    /**
     * 타입에 맞는 InputFilter 배열을 리턴한다.
     *
     * @param pType
     * @param maxLength
     * @return
     */
    public static final InputFilter[] getInputFilterArray(final Type pType, final int maxLength)
    {
        if (pType != null) {
            InputFilter[] filterArray = new InputFilter[2];
            filterArray[0] = new InputFilter.LengthFilter(maxLength);
            filterArray[1] = getInputFilterByType(pType);
            return filterArray;
        } else {
            InputFilter[] lengthFilter = new InputFilter[1];
            lengthFilter[0] = new InputFilter.LengthFilter(maxLength);
            return lengthFilter;
        }
    }

    /**
     * 아이디 - 영문, 숫자만 포함
     */
    private static final class FilterIdAlphaNum implements InputFilter
    {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
        {
            // Must be - android:inputType="textNoSuggestions"
            Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");
            for( int i = start; i < end; i++ )
            {
                char c = source.charAt(i);
                if( (!ps.matcher(Character.toString(c)).matches()) )
                {
                    // source 변경을 허용하지 않는다.
                    return "";
                }
            }
            // source 그대로 허용
            return null;
        }
    };

    /**
     * 한글, 이모지 입력 막기 (: 영어, 숫자, 특수문자)
     */
    public static final class FilterPassword implements InputFilter
    {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
        {
            // 천지인 자판 : middle dot 문제 > \u318D\u119E\u11A2\u2022\u2025\u00B7\uFE55
            Pattern ps = Pattern.compile("^[가-힣ㄱ-ㅎㅏ-ㅣ\\u318D\\u119E\\u11A2\\u2022\\u2025\\u00B7\\uFE55]+$");
            for( int i = start; i < end; i++ )
            {
                // 공백 체크
                char c = source.charAt(i);
                if (c == ' ') {
                    // source 변경을 허용하지 않는다.
                    return "";
                }
                // 글자 타입 체크
                int type = Character.getType(c);
                if( type == Character.SURROGATE
                    /*|| type == Character.UPPERCASE_LETTER*/
                    || type == Character.OTHER_SYMBOL
                    || ps.matcher(Character.toString(c)).matches() )
                {
                    // source 변경을 허용하지 않는다.
                    return "";
                }
            }
            // source 그대로 허용
            return null;
        }
    };

    /**
     * 닉네임 필터 - 공백 입력을 막는다.
     */
    private static final class FilterNickName implements InputFilter
    {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
        {
            for( int i = start; i < end; i++ )
            {
                char c = source.charAt(i);
                if( c == ' ' )
                {
                    // source 변경을 허용하지 않는다.
                    return "";
                }
            }
            // source 그대로 허용
            return null;
        }
    };

    /**
     * 숫자, 하이픈만 포함
     */
    private static final class FilterPhoneNumber implements InputFilter
    {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
        {
            Pattern ps = Pattern.compile("^[0-9\\-]+$");
            for( int i = start; i < end; i++ )
            {
                char c = source.charAt(i);
                if( (!Character.isDigit(c))
                    && (!ps.matcher(Character.toString(c)).matches()) )
                {
                    return "";
                }
            }

            return null;
        }
    };

    /**
     * Empty Input Filter
     */
    private static final class DefaultInputFilter implements InputFilter
    {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            // 아무 것도.. 정말 간절히 아무 것도 안한다. NullPointerException 방지를 위해 사용된다.
            return null;
        }
    }

}
