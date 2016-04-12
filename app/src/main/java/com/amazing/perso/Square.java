package com.amazing.perso;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.amazing.DrawableObjects;
import com.amazing.perso.BubulleInfo;

public class Square implements DrawableObjects {

	private float x;
	private float y;
	private int size;
	private int i = 0;
	private int j = 0;
	private int nb = 0;
	private int nbr = 0;
	private int normJump = 0;
	private int mode;
	private float speed;
	private float windowHeight;
	private boolean sparkles = true;
	private Explode[] ex = new Explode[3];
	private Sparkles[] up = new Sparkles[40];
	private int[] manaColor = {Color.argb(240, 255, 255, 255), Color.argb(240, 0, 200, 200)};
	private int[] innerColor = {Color.rgb(0, 0, 0), Color.rgb(255, 255, 255)};
	private static int[] playerColor = { Color.rgb(98, 0, 170),
			Color.rgb(158, 28, 255), Color.rgb(189, 100, 255) };

	public Square(int x, int y, int size) {
		this.size = size;
		this.x = x;
		this.y = y;
		this.mode = 0;
	}
	
	public void setWindow(int h) {
		windowHeight = h;
	}

	public void draw(Paint paint, Canvas board) {
		if (true) {
			if (i % 2 == 0)
				putSparkles(board, i / 2);
			else if (i >= 79)
				i = 0;
			++i;
		}
		nbr = 0;
		while (nbr < 40) {
			if (nbr < 3 && this.ex[nbr] != null)
				this.ex[nbr].draw(paint, board);
			if (this.up[nbr] != null)
				this.up[nbr].draw(paint, board);
			++nbr;
		}
		paint.setColor(manaColor[mode]);
		board.drawRect(10, 10, 50, 10 + 4 * getSize(), paint);
		paint.setColor(manaColor[(mode + 1) % 2]);
		board.drawRect(12, 12, 48, 12 + 4 * getSize() * (float)((nb + normJump) / 3f) - 2, paint);
		board.save();
		board.rotate((windowHeight - getSize() - getY()) * 0.5f, getX() + getSize() / 2, getY() + getSize() / 2);
		paint.setColor(innerColor[(mode + 1) % 2]);
		board.drawRect(getX(), getY(), getSize() + getX(), getSize() + getY(),
				paint);
		paint.setColor(playerColor[nb]);
		board.drawRect(getX() + getSize() / 7, getY() + getSize() / 7, getSize() * 6 / 7 + getX(), getSize() * 6 / 7 + getY(),
				paint);
		board.restore();
	}
	
	private void putSparkles(Canvas board, int n) {
		this.up[n] = new Sparkles(getX(), getY(), speed * 2, getSize(), up[n]);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float f) {
		this.y = f;
	}

	public void setPlayerColor(int nb) {
		this.nb = nb;
	}
	
	public void setNormJump(int nb) {
		this.normJump = nb;
	}
	
	public void setSparkles(boolean val) {
		this.sparkles = val;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public void explode() {
		j += 1;
		ex[j % 3] = new Explode(x, y, speed, size, ex[j % 3]);
	}

	public void setMode(int value) {
		mode = value;
	}
}