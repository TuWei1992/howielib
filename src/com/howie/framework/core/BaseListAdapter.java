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

import java.util.List;

import android.content.Context;
import android.widget.BaseAdapter;

/**
 * @author howieceo@163.com
 * @time Jan 6, 2013 10:14:58 AM
 */
public abstract class BaseListAdapter extends BaseAdapter {
	protected List<Object> list;
	protected Context context;

	public BaseListAdapter(Context context, List<Object> list) {
		this.context = context;
		this.list = list;
	}

	public void addList(List<Object> list) {
		this.list.addAll(list);
		notifyDataSetChanged();
	}

	public void addNewList(List<Object> list) {
		this.list.clear();
		this.list.addAll(list);
		notifyDataSetChanged();
	}

	public void addListAtPosition(int location, List<Object> list) {
		this.list.addAll(location, list);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return list == null ? null : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
