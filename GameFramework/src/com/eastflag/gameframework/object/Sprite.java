package com.eastflag.gameframework.object;

import android.graphics.Rect;
import android.view.MotionEvent;

public abstract class Sprite {
	protected int mX, mY;
	protected int mWidth, mHeight;
	protected boolean isOn;
	
	protected Rect dstRect;
	
	public Sprite(){
		dstRect = new Rect(mX, mY, mWidth, mHeight);
	}
	
    public void setPosition(int x, int y) {
        mX = x;
        mY = y;
    }
    
    public void setPosition(int centerX, int centerY, int width, int height) {
        mX = centerX - width/2;
        mY = centerY - height/2;
        mWidth = width;
        mHeight = height;
        
        dstRect.set(mX, mY, mX+mWidth, mY+mHeight);
    }
	
	//return 값 정의
	//-1 : 선택 안됨
	//0 : down
	//2 : move
	//1 : up
    public int isSelected(MotionEvent event) {
    	//버튼 영역 체크
    	int result = -1;
    	if (event.getX() > mX && event.getX() < mX+mWidth && event.getY() > mY && event.getY() < mY+mHeight) {
	        switch(event.getAction()){
	        case MotionEvent.ACTION_DOWN: 
	        	result = MotionEvent.ACTION_DOWN;
	        	break;
	        case MotionEvent.ACTION_MOVE:
	            isOn = true;
	            result = MotionEvent.ACTION_MOVE;
	            break;
	        case MotionEvent.ACTION_UP:
	        	//FALL-THROUGH
	        case MotionEvent.ACTION_CANCEL:
	            isOn = false;
	            result = MotionEvent.ACTION_UP;
	            break;
	        }
	    }
        
        return result;
    }
}
