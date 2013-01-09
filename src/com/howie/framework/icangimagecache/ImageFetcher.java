package com.howie.framework.icangimagecache;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.howie.framework.icangimagecache.ImageCache.ImageCacheParams;

/**
 * 
 * @author howieceo@163.com
 * @time Sep 16, 2012 2:14:51 PM
 */
public class ImageFetcher extends ImageWorker {
	private static ImageFetcher instance;

	private ImageFetcher(Context context) {
		super(context);
	}

	public static ImageFetcher getInstance(Context context) {
		if (instance == null) {
			instance = new ImageFetcher(context);
			instance.setImageCache(new ImageCache(context,
					new ImageCacheParams()));
		}

		return instance;
	}

	protected Bitmap processBitmap(String url) {
		final File file = imageCache.getDiskCache().downloadImage(url);

		if (file != null) {
			// return ImageUtil.decodeSampledBitmapFromFile(
			// file.getAbsolutePath(), 300, 300);
			return BitmapFactory.decodeFile(file.getAbsolutePath());
		}
		return null;
	}
}
