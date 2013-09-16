package com.eastflag.gameframework.object;

import android.view.MotionEvent;

public abstract class Button {
	protected int mX, mY;
	protected int mWidth, mHeight;
	protected boolean isOn;
	
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
