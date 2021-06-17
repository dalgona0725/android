package com.theenm.common;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {
	String[] strTitle = null;
	private ArrayList<Object> mArray = null;

	public PagerAdapter(FragmentManager fm, ArrayList<Object> array)
	{
		super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
		mArray = array;
	}

	public PagerAdapter(FragmentManager fm, ArrayList<Object> array, String[] strTitle) {
		super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
		mArray = array;
		this.strTitle = strTitle;
	}

	@Override
	public Fragment getItem(int position) {
		return (Fragment) mArray.get(position);
	}

	@Override
	public int getCount() {
		return mArray.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if (strTitle == null) {
			Fragment fragment = (Fragment) mArray.get(position);

			Bundle bundle = fragment.getArguments();
			String strTitle = bundle.getString(Constant.BundleKey.PAGER_LABLE);

			return strTitle;
		} else {
			return strTitle[position];
		}
	}
}