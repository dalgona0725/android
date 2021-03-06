/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.theenm.common.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

public class SwipeDismissTouchListener implements View.OnTouchListener {

	private int mSlop;
	private int mMinFlingVelocity;
	private int mMaxFlingVelocity;
	private long mAnimationTime;

	private View mView;
	private DismissCallbacks mCallbacks;
	private View.OnClickListener mClickListener;
	private int mViewWidth = 1; // 1 and not 0 to prevent dividing by zero

	private float mDownX;
	private float mDownY;
	private boolean mSwiping;
	private int mSwipingSlop;
	private Object mToken;
	private VelocityTracker mVelocityTracker;
	private float mTranslationX;

	public interface DismissCallbacks {
		boolean canDismiss(Object token);

		void onDismiss(View view, Object token);
	}

	public SwipeDismissTouchListener(View view, Object token, DismissCallbacks callbacks, View.OnClickListener onClickListener) {
		ViewConfiguration vc = ViewConfiguration.get(view.getContext());
		mSlop = vc.getScaledTouchSlop();
		mMinFlingVelocity = vc.getScaledMinimumFlingVelocity() * 16;
		mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
		mAnimationTime = view.getContext().getResources().getInteger(android.R.integer.config_shortAnimTime);
		mView = view;
		mToken = token;
		mCallbacks = callbacks;
		mClickListener = onClickListener;
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		motionEvent.offsetLocation(mTranslationX, 0);

		if (mViewWidth < 2) {
			mViewWidth = mView.getWidth();
		}

		switch (motionEvent.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			mDownX = motionEvent.getRawX();
			mDownY = motionEvent.getRawY();
			if (mCallbacks.canDismiss(mToken)) {
				mVelocityTracker = VelocityTracker.obtain();
				mVelocityTracker.addMovement(motionEvent);
			}
			return true;

		case MotionEvent.ACTION_UP:
			if (mVelocityTracker == null) {
				break;
			}

			float deltaX = motionEvent.getRawX() - mDownX;
			mVelocityTracker.addMovement(motionEvent);
			mVelocityTracker.computeCurrentVelocity(1000);
			float velocityX = mVelocityTracker.getXVelocity();
			float absVelocityX = Math.abs(velocityX);
			float absVelocityY = Math.abs(mVelocityTracker.getYVelocity());
			boolean dismiss = false;
			boolean dismissRight = false;
			if (Math.abs(deltaX) > mViewWidth / 2 && mSwiping) {
				dismiss = true;
				dismissRight = deltaX > 0;
			} else if (mMinFlingVelocity <= absVelocityX && absVelocityX <= mMaxFlingVelocity
					&& absVelocityY < absVelocityX && absVelocityY < absVelocityX && mSwiping) {
				// dismiss only if flinging in the same direction as dragging
				dismiss = (velocityX < 0) == (deltaX < 0);
				dismissRight = mVelocityTracker.getXVelocity() > 0;
			}
			if (dismiss) {
				// dismiss
				mView.animate().translationX(dismissRight ? mViewWidth : -mViewWidth).alpha(0)
						.setDuration(mAnimationTime).setListener(new AnimatorListenerAdapter() {
							@Override
							public void onAnimationEnd(Animator animation) {
								performDismiss();
							}
						});
			} else if (mSwiping) {
				// cancel
				mView.animate().translationX(0).alpha(1).setDuration(mAnimationTime).setListener(null);
			}
			else
			{
				if( mClickListener != null )
					mClickListener.onClick(mView);
			}
			mVelocityTracker.recycle();
			mVelocityTracker = null;
			mTranslationX = 0;
			mDownX = 0;
			mDownY = 0;
			mSwiping = false;
			break;

		case MotionEvent.ACTION_CANCEL:
			if (mVelocityTracker == null) {
				break;
			}

			mView.animate().translationX(0).alpha(1).setDuration(mAnimationTime).setListener(null);
			mVelocityTracker.recycle();
			mVelocityTracker = null;
			mTranslationX = 0;
			mDownX = 0;
			mDownY = 0;
			mSwiping = false;
			break;

		case MotionEvent.ACTION_MOVE:
			if (mVelocityTracker == null) {
				break;
			}

			mVelocityTracker.addMovement(motionEvent);
			float nX = motionEvent.getRawX() - mDownX;
			float nY = motionEvent.getRawY() - mDownY;
			if (Math.abs(nX) > mSlop && Math.abs(nY) < Math.abs(nX) / 2) {
				mSwiping = true;
				mSwipingSlop = (nX > 0 ? mSlop : -mSlop);
				mView.getParent().requestDisallowInterceptTouchEvent(true);

				MotionEvent cancelEvent = MotionEvent.obtain(motionEvent);
				cancelEvent.setAction(MotionEvent.ACTION_CANCEL
						| (motionEvent.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
				mView.onTouchEvent(cancelEvent);
				cancelEvent.recycle();
			}

			if (mSwiping) {
				mTranslationX = nX;
				mView.setTranslationX(nX - mSwipingSlop);
				//mView.setAlpha( Math.max( 0f, Math.min( 1f, 1f - 2f * Math.abs(nX) / mViewWidth ) ) );
				return true;
			}

			break;
		}

		return true;
	}

	private void performDismiss() {

		final ViewGroup.LayoutParams lp = mView.getLayoutParams();
		final int originalHeight = mView.getHeight();

		ValueAnimator animator = ValueAnimator.ofInt(originalHeight, 1).setDuration(mAnimationTime);

		animator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				mCallbacks.onDismiss(mView, mToken);
				mView.setAlpha(1f);
				mView.setTranslationX(0);
				lp.height = originalHeight;
				mView.setLayoutParams(lp);
			}
		});

		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				lp.height = (Integer) valueAnimator.getAnimatedValue();
				mView.setLayoutParams(lp);
			}
		});

		animator.start();
	}
}
