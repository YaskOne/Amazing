package com.amazing.perso;

import java.util.Random;

public class XCubes {
	float x;
	float y;
	float size;
	float speed;
	float speedX;
	float speedY;
	int alpha;
	
	public XCubes(float x, float y, float size, float speed) {
		this.x = x;
		this.y = y;
		Random randGen = new Random();
		this.alpha = 200 + randGen.nextInt(55);
		this.speedX = speed + (randGen.nextInt(20) - 10);
		this.speedY = (3 + randGen.nextInt(12));
		this.size = (size / 2f) * ((randGen.nextInt(100) + 20 ) / 100f);
	}
	
	public float moveX() {
		if (alpha > 1)
			alpha = alpha - 2;
		x = x - speedX;
		return x;
	}
	
	public float moveY() {
		speedY = speedY - 0.3f;
		y = y - speedY;
		return y;
	}
	
	public int getAlpha() {
		return alpha;
	}
	
	public float getSize() {
		return size;
	}
}
