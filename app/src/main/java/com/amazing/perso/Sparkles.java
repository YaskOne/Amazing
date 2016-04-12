package com.amazing.perso;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.amazing.DrawableObjects;
import com.amazing.MainActivity;
import com.amazing.perso.BubulleInfo;

public class Sparkles implements DrawableObjects {
	private float y;
	private float x;
	private float speed;
	private float size;
	private float sparkSize;

	private int alpha;
	private int omega;
	private float flee;
	Sparkles info;
	private int color;
	private Random randGen = new Random();

	public Sparkles(float x, float y, float speed, float size,
			Sparkles info) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.size = size;
		this.sparkSize = (size / 2f) * ((randGen.nextInt(100) + 20 ) / 100f);
		this.omega = randGen.nextInt(100);
		this.flee = (randGen.nextInt(200)) / 200f;
		if ((flee * 10) % 2 == 0)
			flee = 0;
		this.info = info;
		alpha = randGen.nextInt(200);
	}

	public void draw(Paint paint, Canvas board) {
		if (alpha > 1 && x + size > 0) {
			alpha = alpha - 1;
			paint.setColor(Color.argb(alpha, 0, 250, 216));
			paint.setTypeface(MainActivity.getTypeFace());
			board.drawRect(x, y + size, x + sparkSize, y + size - sparkSize, paint);
			this.size -= 0.1f;
			this.x -= speed * 1.3f;
			this.y -= flee;
		} else
			this.info = null;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
}
