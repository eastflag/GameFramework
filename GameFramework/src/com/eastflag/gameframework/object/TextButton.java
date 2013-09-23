package com.eastflag.gameframework.object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.view.MotionEvent;

public class TextButton extends Sprite{
	private String title;
	
	private Paint backgroundPaint, backgroundOnPaint, fontPaint;
	
	public TextButton(String title) {
		this.title = title;
	}
	
	public void setColor(int backColor, int backOnColor, int fontColor) {
		backgroundPaint = new Paint();
		backgroundPaint.setColor(backColor);
		backgroundOnPaint = new Paint();
		backgroundOnPaint.setColor(backOnColor);
		fontPaint = new Paint();
		fontPaint.setColor(fontColor);
		fontPaint.setTextAlign(Align.CENTER);
		fontPaint.setTextSize(mHeight/2);
	}
	
	public void present(Canvas canvas) {
		if(isOn) {
			canvas.drawRect(dstRect, backgroundOnPaint);
		} else {
			canvas.drawRect(dstRect, backgroundPaint);
		}

		canvas.drawText(title, mX+mWidth/2, mY+mHeight/2 + (int)(mHeight/2*0.4f), fontPaint);
	}
}
