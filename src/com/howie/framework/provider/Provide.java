package com.howie.framework.provider;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;

/**
 * 
 * @author howieceo@163.com
 * @time Dec 6, 2012 2:13:26 PM
 */
public class Provide {
	private static final Uri SMS_ALL_URI = Uri.parse("content://sms/");

	/**
	 * Get call logs that after the specify time
	 * 
	 * @param context
	 * @param time
	 * @return ArrayList<ModCallLog>
	 */
	public static ArrayList<ModCallLog> getLogByTime(Context context, long time) {
		ArrayList<ModCallLog> list = new ArrayList<ModCallLog>();
		Cursor cursor = context.getContentResolver().query(
				CallLog.Calls.CONTENT_URI,
				new String[] { CallLog.Calls.NUMBER, CallLog.Calls.TYPE,
						CallLog.Calls.DATE, CallLog.Calls.DURATION },
				CallLog.Calls.DATE + ">?",
				new String[] { String.valueOf(time) },
				CallLog.Calls.DEFAULT_SORT_ORDER);

		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToPosition(i);
			String number = cursor.getString(cursor
					.getColumnIndex(CallLog.Calls.NUMBER));
			long date = cursor.getLong(cursor
					.getColumnIndex(CallLog.Calls.DATE));
			long duration = cursor.getLong(cursor
					.getColumnIndex(CallLog.Calls.DURATION));
			int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));

			list.add(new ModCallLog(number, date, duration, type));
		}

		cursor.close();
		return list;
	}

	/**
	 * Get sms logs that after the specify time
	 * 
	 * @param context
	 * @param time
	 * @return ArrayList<ModSmsLog>
	 */
	public static ArrayList<ModSmsLog> getSmsLogByTime(Context context,
			long time) {
		ArrayList<ModSmsLog> list = new ArrayList<ModSmsLog>();
		Cursor cursor = context.getContentResolver().query(SMS_ALL_URI, null,
				"date>?", new String[] { String.valueOf(time) }, "date DESC");

		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToPosition(i);
			String body = cursor.getString(cursor.getColumnIndex("body"));
			String address = cursor.getString(cursor.getColumnIndex("address"));
			long date = cursor.getLong(cursor.getColumnIndex("date"));
			int type = cursor.getInt(cursor.getColumnIndex("type"));
			list.add(new ModSmsLog(address, body, date, type));
		}

		cursor.close();
		return list;
	}
}
