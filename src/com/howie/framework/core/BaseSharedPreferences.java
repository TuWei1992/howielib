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
