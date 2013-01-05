package com.howie.framework.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 
 * @author howieceo@163.com
 * @time Oct 25, 2012 6:51:26 PM
 */
public class NetUtil {
	/**
	 * network connection check.
	 * 
	 * @param context
	 */
	public static boolean checkConnection(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();

		if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
			return false;
		}

		return true;
	}
}
