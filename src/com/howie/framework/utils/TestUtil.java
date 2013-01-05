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


/**
 * 
 * @author howieceo@163.com
 * @time Dec 27, 2012 5:33:45 PM
 */
public class TestUtil {
	public static double begin() {
		LogUtil.e("-----------Begin------------");
		return System.currentTimeMillis();
	}

	public static void end(double begin) {
		LogUtil.e("----End-----cost time:"
				+ (System.currentTimeMillis() - begin) / 1000.0 + "s");
		return;
	}
}
