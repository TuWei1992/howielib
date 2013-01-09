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
