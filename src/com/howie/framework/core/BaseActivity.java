package com.howie.framework.core;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * The initViews(),initData() of Child activity that extends BaseActivity will
 * auto be invoked after setContentView();
 * 
 * @author howieceo@163.com
 * @time Nov 9, 2012 10:16:52 AM
 */
public abstract class BaseActivity extends Activity implements OperationInit {
	// the default value that jump to target activity without request code
	public static final int NO_REQUEST_CODE = -1;

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);

		initViews();
		initData();
	}

	/**
	 * @param resId
	 */
	public void showShortToast(int resId) {
		Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 
	 * @param msg
	 *            String that to show
	 */
	public void showShortToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 
	 * @param resId
	 */
	public void showLongToast(int resId) {
		Toast.makeText(this, resId, Toast.LENGTH_LONG).show();
	}

	/**
	 * 
	 * @param msg
	 *            String that to show
	 */
	public void showLongToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Jump to the target activity
	 * 
	 * @param targetActivity
	 */
	public void showActivity(Class<?> targetActivity) {
		showActivity(targetActivity, new Bundle(), NO_REQUEST_CODE);
	}

	/**
	 * Jump to the target activity with the requestCode
	 * 
	 * @param targetActivity
	 * @param requestCode
	 */
	public void showActivity(Class<?> targetActivity, int requestCode) {
		showActivity(targetActivity, new Bundle(), requestCode);
	}

	/**
	 * Jump to the target activity with Bundle data
	 * 
	 * @param targetActivity
	 * @param bundle
	 */
	public void showActivity(Class<?> targetActivity, Bundle bundle) {
		showActivity(targetActivity, bundle, NO_REQUEST_CODE);
	}

	/**
	 * Jump to the target activity with Bundle data and the requestCode
	 * 
	 * @param targetActivity
	 * @param bundle
	 * @param requestCode
	 * @throws ClassCastException
	 */
	public void showActivity(Class<?> targetActivity, Bundle bundle,
			int requestCode) {
		Intent intent = new Intent(this, targetActivity);
		intent.putExtras(bundle);
		if (requestCode == NO_REQUEST_CODE) {
			startActivity(intent);
		} else {
			startActivityForResult(intent, requestCode);
		}
	}

	/**
	 * 
	 * @param title
	 *            allow String type,int Resource type. if is null,show
	 *            ProgressDialog without title
	 * @param message
	 *            allow String type,int Resource type. if is null,show
	 *            ProgressDialog without message
	 * @param indeterminate
	 * @param cancelable
	 * @param cancelListener
	 * @return ProgressDialog
	 */
	public ProgressDialog showProgressDialog(Object title, Object message,
			boolean indeterminate, boolean cancelable,
			OnCancelListener cancelListener) {
		ProgressDialog dialog = new ProgressDialog(this);

		// Default to provide a OnCancelListener
		if (cancelListener == null) {
			cancelListener = new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
				}
			};
		}

		if (title != null) {
			if (title instanceof String) {
				dialog.setTitle(String.valueOf(title));
			} else {
				dialog.setTitle((Integer) title);
			}
		}

		if (message != null) {
			if (message instanceof String) {
				dialog.setMessage(String.valueOf(message));
			} else {
				dialog.setMessage(getString((Integer) message));
			}
		}

		dialog.setOnCancelListener(cancelListener);
		dialog.setCancelable(cancelable);
		dialog.setIndeterminate(indeterminate);
		dialog.show();
		return dialog;
	}

	/**
	 * 
	 * @param title
	 *            allow String type,int Resource type. if is null,show dialog
	 *            without title
	 * @param message
	 *            allow String type,int Resource type. if is null,show dialog
	 *            without message
	 * @param items
	 *            allow String type,int Resource type. if is null,show dialog
	 *            without listview items
	 * @param checkedItems
	 *            if is null,show dialog without listview items checked
	 * @param positiveBtnContent
	 *            allow String type,int Resource type. if is null,show dialog
	 *            without PositiveButton
	 * @param negativeBtnContent
	 *            allow String type,int Resource type. if is null,show dialog
	 *            without NegativeButton
	 * @param onClickListener
	 *            allowed to be null
	 * @param onMultiChoiceClickListener
	 *            allowed to be null
	 */
	public void showDialog(Object title, Object message, Object items,
			boolean[] checkedItems, Object positiveBtnContent,
			Object negativeBtnContent, OnClickListener onClickListener,
			OnMultiChoiceClickListener onMultiChoiceClickListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// Default to provide a OnClickListener
		if (onClickListener == null) {
			onClickListener = new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			};
		}

		// Default to provide a onMultiChoiceClickListener
		if (onMultiChoiceClickListener == null) {
			onMultiChoiceClickListener = new OnMultiChoiceClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which,
						boolean isChecked) {
				}
			};
		}

		if (title != null) {
			if (title instanceof String) {
				builder.setTitle(String.valueOf(title));
			} else {
				builder.setTitle((Integer) title);
			}
		}

		if (message != null) {
			if (message instanceof String) {
				builder.setMessage(String.valueOf(message));
			} else {
				builder.setMessage((Integer) message);
			}
		}

		if (checkedItems == null) {
			if (items != null) {
				if (items instanceof String[]) {
					builder.setItems((String[]) items, onClickListener);
				} else {
					builder.setItems((Integer) items, onClickListener);
				}
			}
		} else {
			if (items != null) {
				if (items instanceof String[]) {
					builder.setMultiChoiceItems((String[]) items, checkedItems,
							onMultiChoiceClickListener);
				} else {
					builder.setMultiChoiceItems((Integer) items, checkedItems,
							onMultiChoiceClickListener);
				}
			}
		}

		if (positiveBtnContent != null) {
			if (positiveBtnContent instanceof String) {
				builder.setPositiveButton(String.valueOf(positiveBtnContent),
						onClickListener);
			} else {
				builder.setPositiveButton((Integer) positiveBtnContent,
						onClickListener);
			}
		}

		if (negativeBtnContent != null) {
			if (negativeBtnContent instanceof String) {
				builder.setNegativeButton(String.valueOf(negativeBtnContent),
						onClickListener);
			} else {
				builder.setNegativeButton((Integer) negativeBtnContent,
						onClickListener);
			}
		}

		builder.show();
	}
}
