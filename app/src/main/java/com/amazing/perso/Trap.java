package com.amazing.perso;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.amazing.DrawableObjects;
import com.amazing.core.GameRuntime;

public class Trap extends Square implements DrawableObjects {

	private boolean reboundBool = false;
	private boolean bonusBool = false;
	private boolean superBonusBool = false;
	private boolean hardCoreBool = false;
	private static int[] white = {Color.rgb(255, 255, 255), Color.rgb(0, 0, 0)};
	private static int[] red = {Color.rgb(255, 0, 0), Color.rgb(0, 255, 255)};
	private static int[] cyan = {Color.rgb(100, 100, 255), Color.rgb(155, 155, 0)};
	private static int[] yellow = {Color.rgb(255, 255, 0), Color.rgb(0, 0, 255)};
	private float baseX;
	private float baseY;
	private float baseAlpha;
	private float baseAdd;
	private static int count;
	private static int value;
	private static int mode = 0;
	private float sideSpeed = 0;
	private float speedY = 0;
	private float gravity = 0;
	private Random randGen = new Random();
	private BubulleInfo up;

	public Trap(int x, int y, int size) {
		super(x, y, size);
		baseAlpha = (randGen.nextInt(200) - 100) / 100f;
		baseAdd = (randGen.nextInt(200)) / 15000f;
		this.gravity = 0;
		this.speedY = 0;
		this.count = -1;
		this.mode = 0;
	}

	public boolean checkForCollision(Square player, GameRuntime run) {
		if (count >= 0) {
			--count;
			movingTrap();
		}
		if (count <= 1400 && count % 140 == 0) {
			run.setHardMode(value % 2, count);
			System.out.println("value " + value);
			++value;
		}
		if (player.getX() <= this.getX()
				+ this.getSize()
		&& player.getX() >= this.getX()
				- this.getSize() && player.getY() - run.getPlayerSpeed() / 2 <= this.getY()
				+ this.getSize()
				&& player.getY() - run.getPlayerSpeed() / 2 >= this.getY()
						- this.getSize()) {
			if (this.superBonusBool) {
				this.setX(-200);
				run.setOkJump();
				if (this.hardCoreBool) {
					count = 15000;
					this.value = 0;
					run.setHardMode(1, count);
					return (false);
				}
				run.setScore(20 * (mode + 1));
				addBubulleScore("+" + 20 * (mode + 1));
				return (false);
			}
			if (this.reboundBool) {
				if (player.getY() == this.getY()
						|| (player.getX() - run.getSpeed() < this.getX()
								- this.getSize())) {
					run.setEndWay(-1);
					return (true);
				}
				if (this.hardCoreBool) {
					count = 15000;
					this.value = 0;
					run.setOkJump();
					run.setHardMode(1, count);
				}
				else if (player.getY() >= this.getY() + this.getSize() / 2) {
					if (this.bonusBool) {
						run.setScore(5 * (mode + 1));
						addBubulleScore("+" + 5 * (mode + 1));
					} else {
						run.setScore(1 * (mode + 1));
						addBubulleScore("+" + 1 * (mode + 1));
					}
				} else {
					if (this.bonusBool) {
						run.setScore(10 * (mode + 1));
						addBubulleScore("+" + 10 * (mode + 1));
					} else {
						run.setScore(2 * (mode + 1));
						addBubulleScore("+" + 2 * (mode + 1));
					}
				}
				run.setPlayerSpeed();
				run.setOkJump();
				this.bonusBool = false;
				this.reboundBool = false;
				return (false);
			}
			run.setEndWay(1);
			return true;
		}
		return (false);
	}

	public void draw(Paint paint, Canvas board) {
		if (this.up != null) {
			this.up.draw(paint, board);
		}
		if (hardCoreBool) {
			paint.setColor(Color.GREEN);
			if (superBonusBool)
				board.drawCircle(getX() + getSize() / 2, getY() + getSize() / 2, getSize() / 2,
						paint);
			else
				board.drawRect(getX(), getY(), getSize() + getX(), getSize() + getY(),
					paint);
		}
		else if (superBonusBool) {
			paint.setColor(cyan[mode]);
			board.drawCircle(getX() + getSize() / 2, getY() + getSize() / 2, getSize() / 2,
					paint);
		}
		else if (bonusBool) {
			paint.setColor(yellow[mode]);
			board.drawRect(getX(), getY(), getSize() + getX(), getSize() + getY(),
					paint);
		}
		else if (reboundBool) {
			paint.setColor(red[mode]);
			board.drawRect(getX(), getY(), getSize() + getX(), getSize() + getY(),
					paint);
		}
		else {
			paint.setColor(white[mode]);
			board.drawRect(getX(), getY(), getSize() + getX(), getSize() + getY(),
					paint);
		}		
	}

	private void movingTrap() {
		baseAlpha += baseAdd;
		setX((float) (getX() + Math.cos(baseAlpha) / 5f));
	}

	public void addBubulleScore(String str) {
		this.up = new BubulleInfo(str, this.getX(), this.getY(), 70f, up);
	}

	public void setReboundBool(boolean condition) {
		reboundBool = condition;
	}

	public void setBonusBool(boolean value) {
		bonusBool = value;
	}

	public void setSuperBonusBool(boolean value) {
		superBonusBool = value;
	}
	
	public void setHardCoreBool(boolean value) {
		hardCoreBool = value;
	}
	
	public boolean getHardCoreBool() {
		return hardCoreBool;
	}
	
	public void setMode(int value) {
		mode = value;
	}

	public float getSpeedY() {
		return speedY;
	}

	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}
}
