package com.amazing;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface DrawableObjects {
	public void draw(Paint paint, Canvas board);

	public float getX();

	public float getY();
}
