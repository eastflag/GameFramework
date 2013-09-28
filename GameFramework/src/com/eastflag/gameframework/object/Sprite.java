package com.eastflag.gameframework.object;

import com.eastflag.gameframework.AppDirector;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

public abstract class Sprite {
	protected int mX, mY;
	protected int mWidth, mHeight;
	protected boolean isOn;
	protected Rect dstRect;
	protected AppDirector mAppDirector;
	
	public Sprite(){
		dstRect = new Rect(mX, mY, mWidth, mHeight);
		mAppDirector = AppDirector.getInstance();
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
    
    //getter-----------------------------------------------------------
	public int getmX() {
		return mX;
	}

	public int getmY() {
		return mY;
	}

	public int getmWidth() {
		return mWidth;
	}

	public int getmHeight() {
		return mHeight;
	}
	//getter--------------------------------------------------

	//return 값 정의
	//-1 : 선택 안됨
	//0 : down
	//2 : move
	//1 : up
    public int isSelected(MotionEvent event) {
    	//버튼 영역 체크
    	int result = -1;
    	if (event.getX() > mX && event.getX() < mX+mWidth && event.getY() > mY && event.getY() < mY+mHeight) {
    		Log.d("ldk", "action is " + event.getAction());
	        switch(event.getAction()){
	        case MotionEvent.ACTION_DOWN:
	        	result = MotionEvent.ACTION_DOWN;
	        	isOn = true;
	        	break;
	        case MotionEvent.ACTION_MOVE:
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
