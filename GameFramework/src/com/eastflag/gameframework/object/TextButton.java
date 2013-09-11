package com.eastflag.gameframework.object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;

public class TextButton {
	private int startX, startY;
	private int centerX, centerY;
	private int width, height;
	private String title;
	
	private Rect mRect;
	private Paint backgroundPaint, fontPaint;
	
	public TextButton(int centerX, int centerY, int width, int height, String title) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.startX = centerX - width/2;
		this.startY = centerY - height/2;
		this.width = width;
		this.height = height;
		this.title = title;
		
		mRect = new Rect(startX, startY, startX+width, startY+height);
		backgroundPaint = new Paint();
		backgroundPaint.setColor(Color.GRAY);
		fontPaint = new Paint();
		fontPaint.setColor(Color.WHITE);
		fontPaint.setTextAlign(Align.CENTER);
		fontPaint.setTextSize(height/2);
	}
	
	public void present(Canvas canvas) {
		canvas.drawRect(mRect, backgroundPaint);
		canvas.drawText(title, centerX, centerY + (int)(height/2*0.4f), fontPaint);
	}
}
