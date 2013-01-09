package com.howie.framework.icangimagecache;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.LruCache;

import com.howie.framework.utils.ImageUtil;
import com.howie.framework.utils.LogUtil;

/**
 * 
 * @author howieceo@163.com
 * @time Sep 16, 2012 2:14:58 PM
 */
public class ImageCache {
	private static final String DEFAULT_CACHE_DIR = ImageCache.class
			.getSimpleName();
	private static final int DEFAULT_MEM_CACHE_SIZE = 1024 * 1024 * 16;
	private static final int DEFAULT_DISK_CACHE_SIZE = 1024 * 1024 * 20;
	private static final boolean DEFAULT_MEM_CACHE_ENABLED = true;
	private static final boolean DEFAULT_DISK_CACHE_ENABLED = true;
	private static final boolean DEFAULT_CLEAR_DISK_CACHE_ON_START = false;

	private DiskLruCache diskCache;
	private LruCache<String, Bitmap> memoryCache;

	public ImageCache(Context context, ImageCacheParams cacheParams) {
		init(context, cacheParams);
	}

	public ImageCache(Context context, String cacheDir) {
		init(context, new ImageCacheParams(cacheDir));
	}

	public static ImageCache findOrCreateCache(final FragmentActivity activity,
			final String cacheDir) {
		return findOrCreateCache(activity, new ImageCacheParams(cacheDir));
	}

	public static ImageCache findOrCreateCache(final FragmentActivity activity,
			ImageCacheParams cacheParams) {

		final RetainFragment mRetainFragment = RetainFragment
				.findOrCreateRetainFragment(activity
						.getSupportFragmentManager());

		ImageCache imageCache = (ImageCache) mRetainFragment.getObject();

		if (imageCache == null) {
			imageCache = new ImageCache(activity, cacheParams);
			mRetainFragment.setObject(imageCache);
		}

		return imageCache;
	}

	public DiskLruCache getDiskCache() {
		return diskCache;
	}

	private void init(Context context, ImageCacheParams cacheParams) {
		final File diskCacheDir = DiskLruCache.getDiskCacheDir(context,
				cacheParams.cacheDir);

		if (cacheParams.diskCacheEnabled) {
			LogUtil.i("Set up DiskCache");
			diskCache = DiskLruCache.openCache(context, diskCacheDir,
					cacheParams.diskCacheSize);
			if (cacheParams.clearDiskCacheOnStart) {
				diskCache.clearCache();
			}
		}

		if (cacheParams.memoryCacheEnabled) {
			LogUtil.i("Set up MemoryCache");
			memoryCache = new LruCache<String, Bitmap>(cacheParams.memCacheSize) {
				@Override
				protected int sizeOf(String key, Bitmap bitmap) {
					return ImageUtil.getBitmapSize(bitmap);
				}
			};
		}
	}

	public void addBitmapToCache(String url, Bitmap bitmap) {
		if (url == null || bitmap == null) {
			return;
		}

		if (memoryCache != null && memoryCache.get(url) == null) {
			memoryCache.put(url, bitmap);
		}
	}

	public Bitmap getBitmapFromMemCache(String data) {
		if (memoryCache != null) {
			final Bitmap memBitmap = memoryCache.get(data);
			if (memBitmap != null) {
				return memBitmap;
			}
		}
		return null;
	}

	public Bitmap getBitmapFromDiskCache(String url) {
		if (diskCache != null) {
			return diskCache.get(url);
		}
		return null;
	}

	public void clearCaches() {
		diskCache.clearCache();
		memoryCache.evictAll();
	}

	public void flushCache() {
		diskCache.flushCache();
	}

	public static class ImageCacheParams {
		public String cacheDir = DEFAULT_CACHE_DIR;
		public int memCacheSize = DEFAULT_MEM_CACHE_SIZE;
		public int diskCacheSize = DEFAULT_DISK_CACHE_SIZE;
		public boolean memoryCacheEnabled = DEFAULT_MEM_CACHE_ENABLED;
		public boolean diskCacheEnabled = DEFAULT_DISK_CACHE_ENABLED;
		public boolean clearDiskCacheOnStart = DEFAULT_CLEAR_DISK_CACHE_ON_START;

		public ImageCacheParams() {
		}

		public ImageCacheParams(String cacheDir) {
			this.cacheDir = cacheDir;
		}
	}
}
