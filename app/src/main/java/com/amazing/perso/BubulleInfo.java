 package com.amazing.perso;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.amazing.DrawableObjects;
import com.amazing.MainActivity;

public class BubulleInfo implements DrawableObjects {
	private float y;
	private float x;
	private float size;

	private String message;
	private int alpha;
	BubulleInfo info;

	public BubulleInfo(String val, float x, float y, float size, BubulleInfo info) {
		this.size = size;
		this.x = x;
		this.y = y;
		this.message = val;
		this.alpha = 255;
		this.info = info;
	}

	public void draw(Paint paint, Canvas board) {
		if (this.alpha > 10) {
			paint.setColor(Color.argb(alpha, 120, 120, 120));
			paint.setTypeface(MainActivity.getTypeFace());
			paint.setTextSize(size);
			board.drawText(message, this.x, this.y, paint);
			this.alpha -= 5;
			this.y -= 1;
			this.x -= 1;
		}
		else
			this.info = null;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
}
