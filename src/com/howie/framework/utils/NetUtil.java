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

import android.content.Context;
import android.net.ConnectivityManager;

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

		return cm.getActiveNetworkInfo().isConnected();
	}
}
