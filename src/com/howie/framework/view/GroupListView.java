/**
 * Copyright (c) 2012-2013, Howie Lau 刘文豪.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.howie.framework.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * 
 * @author howieceo@163.com
 * @time Jan 5, 2013 1:36:01 PM
 */
public class GroupListView extends ListView implements OnScrollListener {
	private OnScrollListener mOnScrollListener;
	private GroupAdapter mGroupAdapter;
	private View mLabelView;
	private LabelPosition mLabelPosition;

	public GroupListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public GroupListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public GroupListView(Context context) {
		super(context);
		init();
	}

	public void init() {
		super.setOnScrollListener(this);
		mLabelPosition = new LabelPosition();
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (mOnScrollListener != null) {
			mOnScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		buildAndLayout(false);
		if (mOnScrollListener != null) {
			mOnScrollListener.onScroll(view, firstVisibleItem,
					visibleItemCount, totalItemCount);
		}
	}

	@Override
	public void setOnScrollListener(OnScrollListener onScrollListener) {
		mOnScrollListener = onScrollListener;
	}

	public void setAdapter(GroupAdapter adapter) {
		super.setAdapter(adapter);
		mGroupAdapter = adapter;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		buildAndLayout(true);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if (mLabelView != null) {
			drawChild(canvas, mLabelView, getDrawingTime());
		}
	}

	private void buildAndLayout(boolean isLayout) {
		if (mGroupAdapter != null) {
			LabelPosition lp = getLabelPosition();
			View labelView = getLabelView(lp.mGroup, isLayout);
			requestLayout(labelView, lp);
			mLabelView = labelView;
			mLabelPosition = lp;
		}
	}

	private LabelPosition getLabelPosition() {
		int pos = getFirstVisiblePosition() - getHeaderViewsCount();
		View view = getChildAt(0);

		LabelPosition lp = new LabelPosition();
		if (pos > -1 && view != null) {
			lp.mGroup = mGroupAdapter.getDoublePosition(pos).group;
			lp.mNextGroup = mGroupAdapter.getDoublePosition(pos + 1).group;
			lp.mBottom = view.getBottom();
		} else {
			lp.mGroup = -1;
		}
		return lp;
	}

	private View getLabelView(int group, boolean isLayout) {
		View labelView = null;
		if (group >= 0 && group < mGroupAdapter.getGroupCount()) {
			if (isLayout || mLabelView == null
					|| mLabelPosition.mGroup != group) {
				labelView = mGroupAdapter.getGroupLabelView(group, mLabelView,
						this);
				if (labelView != null) {
					if (mLabelView == null || labelView != mLabelView) {
						labelView.setLayoutParams(new LayoutParams(
								getMeasuredWidth(), LayoutParams.WRAP_CONTENT));
					}
					measureChild(labelView, 0, 0);
				}
			} else {
				labelView = mLabelView;
			}
		}
		return labelView;
	}

	private void requestLayout(View labelView, LabelPosition labelPosition) {
		if (labelView != null && !mLabelPosition.equals(labelPosition)) {
			int h = labelView.getMeasuredHeight();
			int t = (labelPosition.mGroup == labelPosition.mNextGroup) ? 0
					: Math.min(0, labelPosition.mBottom - h);
			labelView.layout(0, t, getMeasuredWidth(), t + h);
		}
	}

	private static class LabelPosition {
		private int mGroup;
		private int mNextGroup;
		private int mBottom;
	}

	public abstract static class GroupAdapter extends BaseAdapter {
		private static final int TYPE_TITLE = 0;
		private static final int TYPE_ITEM = TYPE_TITLE + 1;

		/**
		 * Get all group count
		 */
		public abstract int getGroupCount();

		/**
		 * Get the items count in the specify group
		 * 
		 * @param group
		 *            the specify group
		 */
		public abstract int getGroupItemCount(int group);

		/**
		 * Get the group Label View by the specify group
		 * 
		 * @param group
		 *            the specify group
		 * @param convertView
		 * @param parent
		 */
		public abstract View getGroupLabelView(int group, View convertView,
				ViewGroup parent);

		/**
		 * Get the group item View by the specify group and the specify item in
		 * the specify group
		 * 
		 * @param group
		 *            the specify group
		 * @param item
		 *            the specify item in the specify group
		 * @param convertView
		 * @param parent
		 */
		public abstract View getGroupItemView(int group, int item,
				View convertView, ViewGroup parent);

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			DoublePosition dp = getDoublePosition(position);
			if (dp.item < 0) {
				// Group label view
				return getGroupLabelView(dp.group, convertView, parent);
			} else {
				// Group item view
				return getGroupItemView(dp.group, dp.item, convertView, parent);
			}
		}

		@Override
		public int getCount() {
			int all = 0;
			int titleCount = getGroupCount();
			for (int i = 0; i < titleCount; i++) {
				int itemCount = getGroupItemCount(i) + 1;
				all += itemCount;
			}
			return all;
		}

		@Override
		public int getItemViewType(int position) {
			if (getDoublePosition(position).item < 0) {
				return TYPE_TITLE;
			} else {
				return TYPE_ITEM;
			}
		}

		@Override
		public int getViewTypeCount() {
			return 2;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		public DoublePosition getDoublePosition(int position) {
			DoublePosition dp = new DoublePosition();

			int titleCount = getGroupCount();
			dp.group = titleCount;
			for (int i = 0; i < titleCount; i++) {
				int itemCount = getGroupItemCount(i) + 1;
				if (position < itemCount) {
					dp.group = i;
					break;
				}
				position -= itemCount;
			}
			dp.item = position - 1;
			return dp;
		}

		public class DoublePosition {
			int group;
			int item;
		}
	}
}
