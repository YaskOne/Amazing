package com.amazing.core;

import android.util.FloatMath;

import com.amazing.Board;
import com.amazing.GameEvents;
import com.amazing.InfoUpdater;

import com.amazing.perso.BubulleInfo;
import com.amazing.perso.Square;
import com.amazing.perso.Trap;
import java.util.Random;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameRuntime implements Runnable, GameEvents {

	private boolean master = true;
	private float gravity = 0f;
	private boolean keepRunning = false;

	private Board mainContext;
	private Square player;
	private Random randGen;
	private InfoUpdater aff;
	private BubulleInfo up;

	private float speed;
	private float playerSpeed;

	private Trap[] trap;

	public int tmp;

	private int score = 0;
	private int mode = 0;
	public int minDelay;
	private final int size = 10;
	private float jumpSpeed;
	private boolean onPause;
	private boolean normJump = false;
	private int okJump;
	private int nbTrap = 7;
	private int endWay = -1;
	private float alpha;

	public GameRuntime(Board mainContext1) {
		this.mainContext = mainContext1;
		randGen = new Random();
		playerSpeed = 0;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void repaint() {
		this.mainContext.postInvalidate();
		try {
			Thread.sleep(5);
		} catch (InterruptedException ex) {
			Logger.getLogger(GameRuntime.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	private void scoreChanged() {
		if (this.aff != null) {
			this.aff.scoreUpdate(this.getScore());
		}
	}

	public void run() {

		int i;
		int bubulleScore = 1;
		try {
			Thread.sleep(500);
		} catch (InterruptedException ex) {
			Logger.getLogger(GameRuntime.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		createPersoSquare();
		createTrap();
		player.setPlayerColor(okJump);
		this.onPause = false;
		while (this.player.getY() <= this.mainContext.getH()
				- this.player.getSize() - 1) {
			this.moveSquare(player);
			player.setSpeed(this.speed);
			repaint();
		}

		keepRunning = true;
		while (isKeepRunning()) {

			while (isKeepRunning() && !onPause) {
				i = 0;
				alpha = alpha + 0.01f;
				speed = speed + (float) this.mainContext.getH() / 11000000f;
				player.setSpeed(this.speed);
				this.moveSquare(player);
				while (i < nbTrap) {
					this.moveTrap(trap[i++], i);
				}
				i = 0;
				while (i < nbTrap) {
					if (this.trap[i++].checkForCollision(player, this)) {
						setKeepRunning(false);
					}
				}
				this.scoreChanged();
				if (score != 0 && score / (25 * bubulleScore) >= 1) {
					addBubulleScore(25 * bubulleScore + "!");
					bubulleScore = bubulleScore * 2;
				}
				repaint();
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException ex) {
				Logger.getLogger(GameRuntime.class.getName()).log(Level.SEVERE,
						null, ex);
			}

		}
		while (this.player.getY() < this.mainContext.getH() + 2 * player.getSize()) {
			this.finalMoveSquare(player);
			repaint();
		}
		i = 0;
		while (i < nbTrap) {
			this.trap[i++] = null;
		}
		this.player = null;
		this.aff.gameOver();
	}

	public void addBubulleScore(String str) {
		this.up = new BubulleInfo(str, (float) this.mainContext.getW() / 3f,
				(float) this.mainContext.getH() / 4f, 170f, up);
		this.mainContext.addDrawable(up);
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		this.score = this.score + score;
	}

	private void createPersoSquare() {
		player = new Square((int) (this.mainContext.getW() / 5),
				this.mainContext.getH() / 2,
				(int) (this.mainContext.getH() / this.size));
		player.setWindow(this.mainContext.getH());
		this.mainContext.addDrawable(player);
		this.jumpSpeed = this.mainContext.getH() / this.size / 6f;
		this.speed = (float) (this.mainContext.getH() / this.size / 31f);
		this.gravity = this.jumpSpeed / 42f;
		this.minDelay = (int) (this.mainContext.getH() / 3f);
	}

	private void createTrap() {
		trap = new Trap[nbTrap];
		int i = 0;

		while (i < nbTrap) {
			trap[i] = new Trap(-200, this.mainContext.getH()
					- (this.mainContext.getH() / this.size),
					(this.mainContext.getH() / this.size));
			this.mainContext.addDrawable(trap[i]);
			++i;
		}
		trap[0].setX(this.mainContext.getW());
		trap[0].setReboundBool(true);
	}

	private void finalMoveSquare(Square a) {
		a.setY(a.getY() - playerSpeed);
		a.setX(a.getX() + speed * endWay);
		this.playerSpeed -= gravity;
	}

	private void moveSquare(Square a) {
		if (a.getY() - playerSpeed <= this.mainContext.getH() - a.getSize()) {
			a.setSparkles(false);
			a.setY(a.getY() - (int) playerSpeed);
			this.playerSpeed -= gravity;
		} else {
			this.playerSpeed = 0;
			a.setSparkles(true);
			a.setY(this.mainContext.getH() - a.getSize());
		}
		if (a.getY() > this.mainContext.getH() - a.getSize() * 3 / 2.7f) {
			player.setNormJump(1);
			if (normJump)
				this.jump();
		}
		else
			player.setNormJump(0);
	}

	private void moveTrap(Trap a, int i) {
		if (a.getX() < (-a.getSize())) {
			a.setX(getStartingPos());
			a.setReboundBool(false);
			a.setBonusBool(false);
			a.setSuperBonusBool(false);
			if (a.getX() % 12 >= 0 && a.getX() % 12 < 5) {
				a.setY(this.mainContext.getH() - a.getSize());
			} else if (a.getX() % 12 >= 5 && a.getX() % 12 < 8) {
				a.setY(this.mainContext.getH() - (int) (a.getSize() * 2.7));
			} else if (a.getX() % 12 >= 8 && a.getX() % 12 < 11) {
				a.setY(this.mainContext.getH() - (int) (a.getSize() * 5.4));
				a.setBonusBool(true);
				a.setReboundBool(true);
			} else {
				a.setY(this.mainContext.getH() - (int) (a.getSize() * 9));
				a.setSuperBonusBool(true);
			}
			if (a.getX() % 5 >= 0 && a.getX() % 5 < 3)
				a.setReboundBool(true);
			if (i == nbTrap - 1 && mode != 1) {
				a.setHardCoreBool(true);
				a.setReboundBool(true);
			}
		}
		a.setX((a.getX() - speed));
		if (a.getGravity() != 0) {
			if (a.getY() - a.getSpeedY() < this.mainContext.getH() - a.getSize()) {
				a.setY(a.getY() - a.getSpeedY());
				a.setSpeedY(a.getSpeedY() - a.getGravity());
			}
			else if (a.getSpeedY() < -jumpSpeed / 10f) {
				a.setSpeedY(a.getSpeedY() * (-2 / 3));
			}
			else {
				a.setY(this.mainContext.getH() - a.getSize());
				a.setGravity(0);
				a.setSpeedY(0);
			}
		}
	}
	
	private int getStartingPos() {
		int x = 0;
		x = check_greater(x);
		x = randGen.nextInt(this.minDelay)
				+ x + minDelay;
		return (x);
	}
	
    private int check_greater(int x) {
    	int i = 0;
    	x = (int) trap[0].getX();
    	while (i + 1 != nbTrap) {
    		if (x < trap[i + 1].getX()) {
    			x = (int) trap[i + 1].getX();
    		}
    		++i;
    	}
		return x;
	}
    
	public void setPlayerSpeed() {
		if (playerSpeed >= 0)
			playerSpeed = -jumpSpeed;
		else {
			player.explode();
			playerSpeed = jumpSpeed;
		}
	}

	public float getPlayerSpeed() {
		return (playerSpeed);
	}

	public float getSpeed() {
		return (speed);
	}

	public void jump() {
		if (keepRunning) {
		if (player.getY() >= this.mainContext.getH() - this.player.getSize() * 3 / 2.7f) {
			this.playerSpeed = jumpSpeed;
			normJump = false;
		}
		else if (okJump > 0) {
			this.playerSpeed = jumpSpeed;
			okJump = okJump - 1;
			player.setPlayerColor(okJump);
		}
		else if (player.getY() > this.mainContext.getH() - player.getSize() * 3)
			normJump = true;
		}
	}
	
	public void setEndWay(int nb) {
		endWay = nb;
	}

	public InfoUpdater getAff() {
		return aff;
	}

	public void setAff(InfoUpdater aff) {
		this.aff = aff;
	}

	public boolean isOnPause() {
		return onPause;
	}

	public void setOnPause(boolean isOnpause) {
		this.onPause = isOnpause;
	}

	public boolean isKeepRunning() {
		return keepRunning;
	}

	public void setKeepRunning(boolean keepRunning) {
		this.keepRunning = keepRunning;
	}

	public void setOkJump() {
		if (okJump < 2) {
			okJump = okJump + 1;
			player.setPlayerColor(okJump);
		}
	}
	
	public void setHardMode(int value, int count) {
		this.trap[0].setMode(value);
		this.mainContext.setMode(value);
		this.player.setMode(value);
		if (count == 0 || count == 15000) {
			this.mode = value;
			if (value == 1) {
				addBubulleScore("x2");
				int i = 0;
				while (i < nbTrap) {
					if (trap[i].getHardCoreBool())
						trap[i].setHardCoreBool(false);
					++i;
				}
				this.setSpeed(this.getSpeed() * 1.2f);
			}
			else {
				this.setSpeed(this.getSpeed() / 1.2f);
				takeEmDown();
			}
		}
	}
	
	private void takeEmDown() {
		int i = 0;
		while (i < nbTrap) {
			if (trap[i].getX() > 0 && trap[i].getX() < this.mainContext.getW() + trap[i].getSize()) {
				trap[i].setGravity(gravity * (randGen.nextInt(5) + 2) / 15f);
				trap[i].setSpeedY(0);
			}
			++i;
		}
		
	}

	public float getAlpha() {
		return alpha;
	}
}
