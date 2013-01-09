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
package com.howie.framework.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * 
 * @author howieceo@163.com
 * @time Nov 28, 2012 3:23:44 PM
 */
public class LetterSearchView extends TextView {
	private int choose = -1;
	private Paint paint;
	private OnTouchingLetterChangedListener listener;
	private String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z", "#" };

	public LetterSearchView(Context context) {
		super(context);
		init();
	}

	public LetterSearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		paint = new Paint();
		// Set normal background
		setBackgroundColor(Color.parseColor("#40000000"));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		float height = getHeight();
		float width = getWidth();
		float itemHeight = height / letters.length;

		for (int i = 0; i < letters.length; i++) {
			// Set normal textcolor
			paint.setColor(Color.WHITE);
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			paint.setTextSize(getTextSize());
			paint.setTextAlign(Align.CENTER);
			if (i == choose) {
				// Set selected textcolor
				paint.setColor(Color.parseColor("#3399ff"));
				paint.setFakeBoldText(true);
			}

			float xPos = width / 2;
			// Align Centet in Item
			float yPos = itemHeight * i + itemHeight / 2 + getTextSize() / 3;
			canvas.drawText(letters[i], xPos, yPos, paint);
			paint.reset();
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();
		final int lastChoose = choose;
		choose = (int) (y / getHeight() * letters.length);

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// Set selected background
			setBackgroundColor(Color.parseColor("#88000000"));
			if (lastChoose != choose && choose >= 0 && choose < letters.length) {
				if (listener != null) {
					listener.onTouchingLetterChanged(choose);
				}
			}
			invalidate();
			break;

		case MotionEvent.ACTION_MOVE:
			if (lastChoose != choose && choose >= 0 && choose < letters.length) {
				invalidate();
				if (listener != null) {
					listener.onTouchingLetterChanged(choose);
				}
			}
			break;

		case MotionEvent.ACTION_UP:
			// Set normal background
			if (listener != null) {
				listener.onTouchingUp();
			}
			setBackgroundColor(Color.parseColor("#40000000"));
			choose = -1;
			invalidate();
			break;
		}
		return true;
	}

	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener listener) {
		this.listener = listener;
	}

	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(int position);

		public void onTouchingUp();
	}
}
