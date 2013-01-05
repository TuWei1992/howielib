package com.howie.framework.utils;

import android.util.Log;

/**
 * You can invoke LogUtil.enableLog(true) to log info,it will return the Thread
 * id,class name,and the position of LogUtil.i()/e()/w() as Tag. (e.g.) 11-08
 * 17:17:11.488: E/[1:com.howie.demo.app.DemoApp.java:41](10307): log test
 * 
 * @author howieceo@163.com
 * @time Aug 26, 2012 2:44:52 PM
 */
public class LogUtil {
	private static final String TAG = Log.class.getSimpleName();
	private static final int LOG_LEVEL = Log.DEBUG;

	/**
	 * Whether the Log function is enabled or not enabled
	 */
	private static boolean sLogEnabled;

	public static void enableLog(boolean enable) {
		LogUtil.sLogEnabled = enable;
	}

	public static void d(Object str) {
		if (sLogEnabled && LOG_LEVEL <= Log.DEBUG) {
			Log.d(getTagInfo(), str.toString());
		}
	}

	public static void i(Object str) {
		if (sLogEnabled && LOG_LEVEL <= Log.INFO) {
			Log.i(getTagInfo(), str.toString());
		}
	}

	public static void w(Object str) {
		if (sLogEnabled && LOG_LEVEL <= Log.WARN) {
			Log.w(getTagInfo(), str.toString());
		}
	}

	public static void e(Object str) {
		if (sLogEnabled && LOG_LEVEL <= Log.ERROR) {
			Log.e(getTagInfo(), str.toString());
		}
	}

	private static String getTagInfo() {
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();

		for (StackTraceElement st : sts) {
			if (st.isNativeMethod()) {
				continue;
			}

			if (st.getClassName().equals(Thread.class.getName())) {
				continue;
			}

			if (st.getClassName().equals(LogUtil.class.getName())) {
				continue;
			}

			StringBuilder sb = new StringBuilder("[");
			sb.append(Thread.currentThread().getId()).append(":")
					.append(st.getFileName()).append(":")
					.append(st.getLineNumber()).append("]");
			return sb.toString();
		}

		return TAG;
	}
}