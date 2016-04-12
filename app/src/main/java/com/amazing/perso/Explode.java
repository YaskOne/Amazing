package com.amazing.perso;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.amazing.DrawableObjects;
import com.amazing.MainActivity;
import com.amazing.perso.Explode;

public class Explode implements DrawableObjects {
	private int i = 0;

	private float y;
	private float x;
	private float moveY;
	private float moveX;
	private float speed;
	private float size;
	private float sparkSize;

	private int alpha;
	private float flee;
	Explode info;
	XCubes[] cubes = new XCubes[17];
	private int color;
	private Random randGen = new Random();

	public Explode(float x, float y, float speed, float size,
			Explode info) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.size = size;
		this.alpha = 255;
		this.info = info;
		while (i < 17) {
			cubes[i] = new XCubes(x, y, size, speed);
			++i;
		}
	}

	public void draw(Paint paint, Canvas board) {
		i = 0;
		if (this.alpha > 10) {
			while (i < 17) {
				moveX = cubes[i].moveX();
				moveY = cubes[i].moveY();
				paint.setColor(Color.argb(cubes[i].getAlpha(), 0, 250, 216));
				paint.setTypeface(MainActivity.getTypeFace());
				board.drawRect(moveX, moveY + size, moveX + cubes[i].getSize(), moveY + size - cubes[i].getSize(), paint);
				++i;
			}
			this.alpha -= 2;
		} else {
			i = 0;
			while (i < 17) {
				this.cubes[i] = null;
				++i;
			}
			this.info = null;
		}
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
}
