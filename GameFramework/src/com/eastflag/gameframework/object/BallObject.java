package com.eastflag.gameframework.object;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

public class BallObject extends Sprite{
	private int mOrder; //순서

	private Paint mCirclePaint, mNumberPaint; //공 색깔, 글자 색깔
	
	public BallObject(int mOrder, int radius){
		this.mOrder = mOrder;
		mWidth = radius * 2;
		mHeight = radius * 2;
		
		//0+radius <= centerX <= 480-radius
		Random r = new Random();
		
		mCirclePaint = new Paint();
		mCirclePaint.setAntiAlias(true);
		//int color = Color.argb(255, r.nextInt(256), r.nextInt(256), r.nextInt(256)); //랜덤 색깔 만들기
		//mCirclePaint.setColor(color);
		mCirclePaint.setARGB(255, r.nextInt(150), r.nextInt(150), r.nextInt(150));
		
		mNumberPaint = new Paint();
		mNumberPaint.setAntiAlias(true);
		mNumberPaint.setColor(Color.WHITE);
		mNumberPaint.setTextAlign(Align.CENTER);
		mNumberPaint.setTextSize(radius);
	}
	
	public void update() {
		
	}
	
	public void present(Canvas canvas){
		float centerX = mX + mWidth/2;
		float centerY = mY + mHeight/2;
		canvas.drawCircle(centerX, centerY, mWidth/2, mCirclePaint);
		canvas.drawText(String.valueOf(mOrder), centerX, centerY + mNumberPaint.getTextSize()*0.4f, mNumberPaint);
	}
	
	public int getCenterX() {
		return mX + mWidth/2;
	}
	
	public int getCenterY() {
		return mY + mHeight/2;
	}

    public int getRadius() {
        return mWidth/2;
    }

}
