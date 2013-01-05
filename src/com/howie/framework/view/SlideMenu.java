package com.howie.framework.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * 
 * @author howieceo@163.com
 * @time Nov 12, 2012 2:53:53 PM
 */
public class SlideMenu extends ViewGroup {
	private final static int DURATION = 200;
	private View mLeftView;
	private View mRightView;
	private int mLeftViewWidth;
	private SlideMenuOpenListener slideMenuOpenListener;
	private SlideMenuCloseListener slideMenuCloseListener;
	private Animation mAnimation;

	private boolean mIsOpened;
	private SlideMenuListener mListener;

	public SlideMenu(Context context) {
		this(context, null);
	}

	public SlideMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public boolean isSlideMenuOpened() {
		return mIsOpened;
	}

	public void toggleSlideMenu() {
		if (mRightView.getAnimation() != null) {
			return;
		}

		if (mIsOpened) {
			// opened, make close animation
			mAnimation = new TranslateAnimation(0, -mLeftViewWidth, 0, 0);
			mAnimation.setAnimationListener(slideMenuCloseListener);
		} else {
			// not opened, make open animation
			mAnimation = new TranslateAnimation(0, mLeftViewWidth, 0, 0);
			mAnimation.setAnimationListener(slideMenuOpenListener);
		}

		mAnimation.setDuration(DURATION);
		mAnimation.setFillAfter(true);
		mAnimation.setFillEnabled(true);
		mRightView.startAnimation(mAnimation);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		mLeftView.layout(l, 0, l + mLeftViewWidth,
				0 + mLeftView.getMeasuredHeight());
		if (mIsOpened) {
			mRightView.layout(l + mLeftViewWidth, 0, r + mLeftViewWidth, b);
		} else {
			mRightView.layout(l, 0, r, b);
		}
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
	}

	@Override
	public void onMeasure(int w, int h) {
		super.onMeasure(w, h);
		super.measureChildren(w, h);
		mLeftViewWidth = mLeftView.getMeasuredWidth();
	}

	@Override
	public void onFinishInflate() {
		super.onFinishInflate();
		mLeftView = getChildAt(0);
		mRightView = getChildAt(1);

		if (mLeftView == null || mRightView == null) {
			throw new NullPointerException(
					"slidemenu must hava left layout and right layout");
		}

		slideMenuOpenListener = new SlideMenuOpenListener(mLeftView, mRightView);
		slideMenuCloseListener = new SlideMenuCloseListener(mLeftView,
				mRightView);
	}

	public class SlideMenuOpenListener implements Animation.AnimationListener {

		private View slideMenuView;
		private View contentView;

		public SlideMenuOpenListener(View slideMenuView, View contentView) {

			this.slideMenuView = slideMenuView;
			this.contentView = contentView;
		}

		public void onAnimationRepeat(Animation animation) {
		}

		public void onAnimationStart(Animation animation) {
			slideMenuView.setVisibility(View.VISIBLE);
		}

		public void onAnimationEnd(Animation animation) {
			contentView.clearAnimation();
			mIsOpened = !mIsOpened;
			requestLayout();
			if (mListener != null) {
				mListener.onSlideMenuOpened();
			}
		}
	}

	public class SlideMenuCloseListener implements Animation.AnimationListener {

		private View slideMenuView;
		private View contentView;

		public SlideMenuCloseListener(View slideMenuView, View contentView) {

			this.slideMenuView = slideMenuView;
			this.contentView = contentView;
		}

		public void onAnimationStart(Animation animation) {
		}

		public void onAnimationEnd(Animation animation) {
			contentView.clearAnimation();
			slideMenuView.setVisibility(View.INVISIBLE);
			mIsOpened = !mIsOpened;
			requestLayout();
			if (mListener != null) {
				mListener.onSlideMenuClosed();
			}
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}
	}

	public void setSlideMenuListener(SlideMenuListener listener) {
		mListener = listener;
	}

	public interface SlideMenuListener {

		public void onSlideMenuOpened();

		public void onSlideMenuClosed();
	}
}
