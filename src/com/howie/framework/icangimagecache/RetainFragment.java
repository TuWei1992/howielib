package com.howie.framework.icangimagecache;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * 
 * @author howieceo@163.com
 * @time Sep 15, 2012 8:14:08 PM
 */
public class RetainFragment extends Fragment {
	private static final String TAG = RetainFragment.class.getSimpleName();
	private Object object;

	public RetainFragment() {
	}

	public static RetainFragment findOrCreateRetainFragment(FragmentManager fm) {
		RetainFragment retainFragment = (RetainFragment) fm
				.findFragmentByTag(TAG);

		if (retainFragment == null) {
			retainFragment = new RetainFragment();
			fm.beginTransaction().add(retainFragment, TAG).commit();
		}

		return retainFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Object getObject() {
		return object;
	}

	@Override
	public void onDestroy() {
		if (object instanceof ImageCache) {
			ImageCache imageCache = (ImageCache) object;
			if (imageCache != null) {
				imageCache.flushCache();
			}
		}
		super.onDestroy();
	}
}
