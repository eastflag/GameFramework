package com.eastflag.gameframework.object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.view.MotionEvent;

public class TextButton extends Button{
	private int centerX, centerY;
	private String title;
	
	private Rect mRect;
	private Paint backgroundPaint, backgroundOnPaint, fontPaint;
	
	public TextButton(int centerX, int centerY, int width, int height, String title) {
		this.centerX = centerX;
		this.centerY = centerY;
		mX = centerX - width/2;
		mY = centerY - height/2;
		mWidth = width;
		mHeight = height;
		this.title = title;
		
		mRect = new Rect(mX, mY, mX+mWidth, mY+mHeight);
		backgroundPaint = new Paint();
		backgroundPaint.setColor(Color.CYAN);
		backgroundOnPaint = new Paint();
		backgroundOnPaint.setColor(Color.GREEN);
		fontPaint = new Paint();
		fontPaint.setColor(Color.WHITE);
		fontPaint.setTextAlign(Align.CENTER);
		fontPaint.setTextSize(height/2);
	}
	
	public void present(Canvas canvas) {
		if(isOn) {
			canvas.drawRect(mRect, backgroundOnPaint);
		} else {
			canvas.drawRect(mRect, backgroundPaint);
		}

		canvas.drawText(title, centerX, centerY + (int)(mHeight/2*0.4f), fontPaint);
	}
	
    public boolean isSelected(MotionEvent event) {
    	//버튼 영역 체크
    	if (event.getX() > mX && event.getX() < mX+mWidth && event.getY() > mY && event.getY() < mY+mHeight) {
    	
	        switch(event.getAction()){
	        case MotionEvent.ACTION_DOWN:
	            isOn = true;
	            break;
	        case MotionEvent.ACTION_UP:
	        case MotionEvent.ACTION_CANCEL:
	            isOn = false;
	            return true;
	        }
	    }
        
        return false;
    }
}
