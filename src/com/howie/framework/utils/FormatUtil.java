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

import android.text.TextUtils;

/**
 * Parse a string according to the format
 * 
 * @Author howieceo@163.com
 * @TimeAt 2012-4-9 下午8:34:50
 * 
 */
public class FormatUtil {
	public static String appendLineBreak(String str) {
		if (!TextUtils.isEmpty(str)) {
			return str + "\n";
		}
		return "";
	}
}
