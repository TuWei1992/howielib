package com.howie.framework.icangimagecache;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * 
 * @author howieceo@163.com
 * @time Sep 15, 2012 6:43:55 PM
 */
public abstract class ImageWorker {
	private static final int FADE_IN_TIME = 150;
	protected ImageCache imageCache;
	private Bitmap loadingBitmap;
	private boolean fadeInBitmap = true;
	private boolean exitTasksEarly = false;
	protected Context context;

	protected ImageWorker(Context context) {
		this.context = context;
	}

	public void loadImage(String url, ImageView imageView) {
		Bitmap bitmap = null;

		if (imageCache != null) {
			bitmap = imageCache.getBitmapFromMemCache(url);
		}

		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		} else if (cancelPotentialWork(url, imageView)) {
			final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
			final AsyncDrawable asyncDrawable = new AsyncDrawable(
					context.getResources(), loadingBitmap, task);
			imageView.setImageDrawable(asyncDrawable);
			task.execute(url);
		}
	}

	public void setLoadingImage(Bitmap bitmap) {
		loadingBitmap = bitmap;
	}

	public void setLoadingImage(int resId) {
		loadingBitmap = BitmapFactory.decodeResource(context.getResources(),
				resId);
	}

	public void setImageCache(ImageCache cacheCallback) {
		imageCache = cacheCallback;
	}

	public ImageCache getImageCache() {
		return imageCache;
	}

	public void setImageFadeIn(boolean fadeIn) {
		fadeInBitmap = fadeIn;
	}

	public void setExitTasksEarly(boolean exitTasksEarly) {
		this.exitTasksEarly = exitTasksEarly;
	}

	protected abstract Bitmap processBitmap(String url);

	public static void cancelWork(ImageView imageView) {
		final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
		if (bitmapWorkerTask != null) {
			bitmapWorkerTask.cancel(true);
		}
	}

	public static boolean cancelPotentialWork(Object data, ImageView imageView) {
		final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

		if (bitmapWorkerTask != null) {
			final Object bitmapData = bitmapWorkerTask.url;
			if (bitmapData == null || !bitmapData.equals(data)) {
				bitmapWorkerTask.cancel(true);
			} else {
				return false;
			}
		}
		return true;
	}

	private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
		if (imageView != null) {
			final Drawable drawable = imageView.getDrawable();
			if (drawable instanceof AsyncDrawable) {
				final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
				return asyncDrawable.getBitmapWorkerTask();
			}
		}
		return null;
	}

	private class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
		private String url;
		private final WeakReference<ImageView> imageViewReference;

		public BitmapWorkerTask(ImageView imageView) {
			imageViewReference = new WeakReference<ImageView>(imageView);
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			url = params[0];
			Bitmap bitmap = null;

			if (imageCache != null && !isCancelled()
					&& getAttachedImageView() != null && !exitTasksEarly) {
				bitmap = imageCache.getBitmapFromDiskCache(url);
			}

			if (bitmap == null && !isCancelled()
					&& getAttachedImageView() != null && !exitTasksEarly) {
				bitmap = processBitmap(params[0]);
			}

			if (bitmap != null && imageCache != null) {
				imageCache.addBitmapToCache(url, bitmap);
			}

			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (isCancelled() || exitTasksEarly) {
				bitmap = null;
			}

			final ImageView imageView = getAttachedImageView();
			if (bitmap != null && imageView != null) {
				setImageBitmap(imageView, bitmap);
			}
		}

		private ImageView getAttachedImageView() {
			final ImageView imageView = imageViewReference.get();
			final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

			if (this == bitmapWorkerTask) {
				return imageView;
			}

			return null;
		}
	}

	private static class AsyncDrawable extends BitmapDrawable {
		private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

		public AsyncDrawable(Resources res, Bitmap bitmap,
				BitmapWorkerTask bitmapWorkerTask) {
			super(res, bitmap);

			bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(
					bitmapWorkerTask);
		}

		public BitmapWorkerTask getBitmapWorkerTask() {
			return bitmapWorkerTaskReference.get();
		}
	}

	@SuppressWarnings("deprecation")
	private void setImageBitmap(ImageView imageView, Bitmap bitmap) {
		if (fadeInBitmap) {
			final TransitionDrawable td = new TransitionDrawable(
					new Drawable[] {
							new ColorDrawable(android.R.color.transparent),
							new BitmapDrawable(context.getResources(), bitmap) });
			imageView.setBackgroundDrawable(new BitmapDrawable(context
					.getResources(), loadingBitmap));

			imageView.setImageDrawable(td);
			td.startTransition(FADE_IN_TIME);
		} else {
			imageView.setImageBitmap(bitmap);
		}
	}
}
