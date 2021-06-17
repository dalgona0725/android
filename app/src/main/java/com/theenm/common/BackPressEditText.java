package com.theenm.common;

import android.content.Context;
import androidx.appcompat.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;


public class BackPressEditText extends AppCompatEditText {
	private OnBackPressListener onBackPressListener;
	private OnCompleteListener onCompleteListener;

	public BackPressEditText(Context context) {
		super(context);
	}


	public BackPressEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	public BackPressEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}


	@Override
	public boolean onKeyPreIme(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && onBackPressListener != null) {
			onBackPressListener.onBackPress();
		}

		return super.onKeyPreIme(keyCode, event);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_ENTER && onCompleteListener != null) {
			onCompleteListener.onCompletePress();
		}
		return super.onKeyDown(keyCode, event);
	}


	public void setOnBackPressListener(OnBackPressListener listener) {
		onBackPressListener = listener;
	}

	public void setOnCompleteListener(OnCompleteListener listener) {
		onCompleteListener = listener;
	}

	public interface OnBackPressListener {
		public void onBackPress();
	}

	public interface OnCompleteListener {
		public void onCompletePress();
	}
}