package com.howie.framework.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

/**
 * 
 * @author howieceo@163.com
 * @time Nov 22, 2012 11:14:23 AM
 */
public class PopMenu {
	protected PopupWindow popupWindow;
	protected Context context;
	protected View convertView;

	public PopMenu(Context context, int backgroundRes, int contentLayout) {
		this.context = context;
		convertView = LayoutInflater.from(context).inflate(contentLayout, null);

		popupWindow = new PopupWindow(convertView, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(context.getResources().getDrawable(
				backgroundRes));
	}

	public void showAsDropDown(View parent) {
		popupWindow.showAsDropDown(parent);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
	}

	protected void dismiss() {
		popupWindow.dismiss();
	}
}
