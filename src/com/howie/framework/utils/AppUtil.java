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
package com.howie.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;

/**
 * 
 * @author howieceo@163.com
 * @time Dec 13, 2012 4:07:34 PM
 */
public class AppUtil {
	public static final int DISPLAY_WIDTH = 0;
	public static final int DISPLAY_HEIGHT = DISPLAY_WIDTH + 1;

	public static boolean isApkExist(Context context, String packageName) {
		if (TextUtils.isEmpty(packageName))
			return false;
		try {
			@SuppressWarnings("unused")
			ApplicationInfo info = context.getPackageManager()
					.getApplicationInfo(packageName,
							PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}

	/**
	 * @param activity
	 * @return int数组，int[0] display width ,int[1] display height
	 */
	public static int[] getResolution(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return new int[] { dm.widthPixels, dm.heightPixels };
	}

	public static int px2sp(Context context, double pxValue) {
		float scale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int sp2px(Context context, double spValue) {
		float scale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * scale + 0.5f);
	}

	public static int dip2px(Context context, double dpValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static float px2dip(Context context, double pxValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (float) pxValue / scale + 0.5f;
	}

	/**
	 * Android 2.2 Api level 8
	 * 
	 * @return
	 */
	public static boolean hasFroyo() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	/**
	 * Android 2.3 Api level 9
	 * 
	 * @return
	 */
	public static boolean hasGingerbread() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
	}

	/**
	 * Android 2.3.3 Api level 10
	 * 
	 * @return
	 */
	public static boolean hasGingerbreadMR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1;
	}

	/**
	 * Android 3.0 Api level 11
	 * 
	 * @return
	 */
	public static boolean hasHONEYCOMB() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	/**
	 * Android 4.0 Api level 14
	 * 
	 * @return
	 */
	public static boolean hasIceCreamSandwich() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}

	/**
	 * Android 4.1 Api level 16
	 * 
	 * @return
	 */
	public static boolean hasJellyBean() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	}
}
