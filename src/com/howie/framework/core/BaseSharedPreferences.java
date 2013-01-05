package com.howie.framework.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

/**
 * 
 * @author howieceo@163.com
 * @time Jan 4, 2013 11:59:04 AM
 */
public class BaseSharedPreferences {
	private SharedPreferences sharedPreferences;

	public BaseSharedPreferences(Context context, String tag) {
		sharedPreferences = context.getSharedPreferences(tag,
				Context.MODE_PRIVATE);
	}

	public void setOnSharedPreferenceChangeListener(
			OnSharedPreferenceChangeListener listener) {
		sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
	}

	/**
	 * 
	 * @param String
	 *            key
	 * @param String
	 *            value
	 * @return boolean
	 */
	public boolean putString(String key, String value) {
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
		return editor.commit();
	}

	/**
	 * 
	 * @param String
	 *            key
	 * @param long value
	 * @return boolean
	 */
	public boolean putLong(String key, long value) {
		Editor editor = sharedPreferences.edit();
		editor.putLong(key, value);
		return editor.commit();
	}

	/**
	 * 
	 * @param String
	 *            key
	 * @param int value
	 * @return boolean
	 */
	public boolean putInt(String key, int value) {
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		return editor.commit();
	}

	/**
	 * 
	 * @param String
	 *            key
	 * @return String
	 */
	public String getString(String key) {
		return sharedPreferences.getString(key, "");
	}

	/**
	 * 
	 * @param String
	 *            key
	 * @return long
	 */
	public long getLong(String key) {
		return sharedPreferences.getLong(key, 0);
	}

	/**
	 * 
	 * @param String
	 *            key
	 * @return int
	 */
	public int getInt(String key) {
		return sharedPreferences.getInt(key, 0);
	}

	/**
	 * 
	 * @param String
	 *            key
	 * @return boolean
	 */
	public boolean contains(String key) {
		return sharedPreferences.contains(key);
	}

	/**
	 * 
	 * @param String
	 *            key
	 * @return boolean
	 */
	public boolean remove(String key) {
		Editor editor = sharedPreferences.edit();
		editor.remove(key);
		return editor.commit();
	}

	/**
	 * SharedPreferences clear
	 * 
	 * @return void
	 */
	public void clear() {
		Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.commit();
	}
}
