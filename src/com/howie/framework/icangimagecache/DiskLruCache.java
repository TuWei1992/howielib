package com.howie.framework.icangimagecache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.howie.framework.utils.ImageUtil;
import com.howie.framework.utils.LogUtil;
import com.howie.framework.utils.TestUtil;

/**
 * 
 * @author howieceo@163.com
 * @time Sep 15, 2012 8:11:44 PM
 */
public class DiskLruCache {
	private static final String CACHE_FILENAME_PREFIX = "cache_";
	private static final int INITIAL_CAPACITY = 32;
	private static final float LOAD_FACTOR = 0.75f;
	private static final float REMOVE_FACTOR = 0.4f;

	private File cacheDir;
	private long maxImagesSize = 1024 * 1024 * 10;

	private final Map<String, String> linkedHashMap = Collections
			.synchronizedMap(new LinkedHashMap<String, String>(
					INITIAL_CAPACITY, LOAD_FACTOR, true));

	private static final FilenameFilter cacheFileFilter = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String filename) {
			return filename.startsWith(CACHE_FILENAME_PREFIX);
		}
	};

	public static DiskLruCache openCache(Context context, File cacheDir,
			long maxByteSize) {
		if (!cacheDir.exists()) {
			cacheDir.mkdir();
		}

		if (cacheDir.isDirectory() && cacheDir.canWrite()
				&& ImageUtil.getUsableSpace(cacheDir) > maxByteSize) {
			return new DiskLruCache(cacheDir, maxByteSize);
		}

		return null;
	}

	private DiskLruCache(File cacheDir, long maxByteSize) {
		this.cacheDir = cacheDir;
		this.maxImagesSize = maxByteSize;
		init();
	}

	private void init() {
		final File[] files = cacheDir.listFiles(cacheFileFilter);

		long time = TestUtil.begin("search files.length=" + files.length);
		for (int i = 0; i < files.length; i++) {
			String path = files[i].getAbsolutePath();
			try {
				String url = URLDecoder.decode(
						path.split(CACHE_FILENAME_PREFIX)[1], "UTF-8");
				put(url, path);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		TestUtil.end(time);
	}

	private void put(String key, String file) {
		linkedHashMap.put(key, file);
	}

	public Bitmap get(String url) {
		synchronized (linkedHashMap) {
			final String filePath = linkedHashMap.get(url);
			if (filePath != null) {
				final File file = new File(filePath);
				file.setLastModified(System.currentTimeMillis());
				LogUtil.i("DishCache exist the image!");
				return BitmapFactory.decodeFile(filePath);
				// return ImageUtil
				// .decodeSampledBitmapFromFile(filePath, 300, 300);
			} else {
				LogUtil.i("DishCache don't exist the image!");
			}
			return null;
		}
	}

	public boolean containsUrl(String url) {
		if (linkedHashMap.containsKey(url)) {
			return true;
		}
		return false;
	}

	public void clearCache() {
		linkedHashMap.clear();
		clearCache(cacheDir);
	}

	private void clearCache(File cacheDir) {
		final File[] files = cacheDir.listFiles(cacheFileFilter);
		for (int i = 0; i < files.length; i++) {
			files[i].delete();
		}
	}

	public static File getDiskCacheDir(Context context, String uniqueName) {
		return new File(context.getCacheDir().getPath() + File.separator
				+ uniqueName);
	}

	private String createFilePath(File cacheDir, String key) {
		try {
			return cacheDir.getAbsolutePath() + File.separator
					+ CACHE_FILENAME_PREFIX + URLEncoder.encode(key, "UTF-8");
		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String createFilePath(String key) {
		return createFilePath(cacheDir, key);
	}

	public File downloadImage(String urlString) {
		HttpURLConnection urlConnection = null;
		BufferedOutputStream out = null;
		final File cacheFile = new File(createFilePath(urlString));

		try {
			final URL url = new URL(urlString);
			urlConnection = (HttpURLConnection) url.openConnection();
			final InputStream in = new BufferedInputStream(
					urlConnection.getInputStream(), ImageUtil.IO_BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(cacheFile),
					ImageUtil.IO_BUFFER_SIZE);

			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}

			put(urlString, cacheFile.getAbsolutePath());
			return cacheFile;

		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
			if (out != null) {
				try {
					out.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public void flushCache() {
		File[] files = cacheDir.listFiles();
		if (files == null) {
			return;
		}

		long dirSize = 0;

		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().startsWith(CACHE_FILENAME_PREFIX)) {
				dirSize += files[i].length();
			}
		}

		if (dirSize > maxImagesSize) {
			LogUtil.i("dirSize > maxImagesSize");
			int removeFactor = (int) ((REMOVE_FACTOR * files.length) + 1);
			Arrays.sort(files, new Comparator<File>() {
				@Override
				public int compare(File lhs, File rhs) {
					if (lhs.lastModified() > rhs.lastModified()) {
						return 1;
					} else if (lhs.lastModified() == rhs.lastModified()) {
						return 0;
					} else {
						return -1;
					}
				}
			});

			for (int i = 0; i < removeFactor; i++) {
				if (files[i].getName().startsWith(CACHE_FILENAME_PREFIX)) {
					files[i].delete();
				}
			}

			LogUtil.i("Remove " + removeFactor + " image file");
		}
	}
}
