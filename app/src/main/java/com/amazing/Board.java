package com.amazing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.*;

public class Board extends View implements OnTouchListener, OnClickListener {

	private Paint paint = new Paint();
	private boolean onTouch = true;
	private GameEvents game;

	private int w;
	private int h;
	private DrawableObjects[] matiere;
	private int nbD;
	private int pauseBackground = Color.argb(200, 200, 200, 200);

	private int popOrange = Color.rgb(254, 255, 255);
	private int plasticGrey = Color.rgb(167, 170, 177);
	private int[] black = {Color.rgb(0, 0, 0), Color.rgb(255, 255, 255)};
	private int mode;
	private int movingStrippes;

	@SuppressLint("ClickableViewAccessibility")
	public Board(Context context, AttributeSet attr) {
		super(context, attr);
		this.setOnTouchListener(this);
		this.setOnClickListener(this);
		initAndclean();
	}

	public void initAndclean() {
		this.w = this.getWidth();
		this.h = this.getHeight();
		matiere = new DrawableObjects[20];
		nbD = 0;
		mode = 0;
		this.movingStrippes = -2;

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int pointerIndex = event.getActionIndex();
		int activePointerId = event.getPointerId(pointerIndex);
	    int maskedAction = event.getActionMasked();
		switch (maskedAction) {
		case MotionEvent.ACTION_DOWN: {
			getGame().jump();
			break;
		}
		case MotionEvent.ACTION_POINTER_DOWN: {
		      getGame().jump();
		      break;
		}
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
		case MotionEvent.ACTION_CANCEL:
		}
		return true;
	}
	


	public void addDrawable(DrawableObjects a) {
		this.matiere[nbD] = a;
		++this.nbD;
	}

	private void paintBackground(Canvas canvas) {
 		paint.setColor(black[mode]);
		canvas.drawRect(0, 0, getW(), getH(), paint);
		paint.setColor(black[(mode + 1) % 2]);
		canvas.drawRect(0, getH(), getW(), getH() * 12 / 10, paint);
		if (this.game != null
				&& (this.game.isOnPause() || !this.game.isKeepRunning())) {
			paint.setColor(pauseBackground);
			canvas.drawRect(0, 0, getW(), getH(), paint);
		}
	}

	private void strippes(Canvas canvas) {
		Paint wallpaint = new Paint();
		if (movingStrippes >= this.w) {
			movingStrippes = 0;
		}
		movingStrippes += 2;
		wallpaint.setStyle(Paint.Style.FILL);
		int x = 0;
		int y = 0 - movingStrippes;
		int strippeSize = this.w / 4;
		int nb = 1;
		int invert = 0;
		Path wallpath = new Path();
		while (y < this.h) {

			if (invert % 2 == 0) {
				wallpaint.setColor(popOrange);
			} else {
				wallpaint.setColor(this.plasticGrey);
			}
			wallpath.reset();
			if (x < this.w) {
				wallpath.moveTo(x, y);
				wallpath.lineTo(x + strippeSize, y);
				wallpath.lineTo(0, y + strippeSize * nb);
				wallpath.lineTo(0, y + strippeSize * (nb - 1));
				wallpath.lineTo(x, y);
				x += strippeSize;
				nb += 1;
			} else {
				wallpath.moveTo(x, y);
				wallpath.lineTo(x, y + strippeSize);
				wallpath.lineTo(0, y + strippeSize * nb);
				wallpath.lineTo(0, y + strippeSize * (nb - 1));
				wallpath.lineTo(x, y);
				y += strippeSize;
			}
			invert += 1;
			canvas.drawPath(wallpath, wallpaint);
		}

	}

	private void paintObjects(Canvas canvas) {
		for (DrawableObjects a : this.matiere) {
			if (a != null) {
				a.draw(paint, canvas);
			}
		}
	}

	@Override
	public void onDraw(Canvas canvas) {
		paintBackground(canvas);
		paintObjects(canvas);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		this.setW(w);
		this.setH(h);
		new String("x:" + w + "/y:" + h);
		super.onSizeChanged(w, h, oldw, oldh);
	}

	public int getW() {
		return this.w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return this.h * 9 / 10;
	}

	public void setH(int h) {
		this.h = h;
	}

	public GameEvents getGame() {
		return game;
	}

	public void setGame(GameEvents game) {
		this.game = game;
	}
	
	public void setMode(int value) {
		mode = value;
	}
	
	public int getMode() {
		return mode;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}
}