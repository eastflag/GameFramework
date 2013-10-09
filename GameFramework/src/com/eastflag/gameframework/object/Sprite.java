package com.eastflag.gameframework.object;

import com.eastflag.gameframework.AppDirector;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

public abstract class Sprite {
	protected int mX, mY;
	protected int mWidth, mHeight;
	protected Rect dstRect;
	public boolean mIsDead;
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
	
	public Rect getDstRect() {
		return dstRect;
	}
	
	public boolean getmIsDead() {
		return mIsDead;
	}
	//getter--------------------------------------------------

	//return 값 정의
	//-1 : 선택 안됨
	//0 : down
	//2 : move
	//1 : up
    public int isSelected(MotionEvent event) {
    	int action = event.getAction();
    	//버튼 영역 체크
    	int result = -1;
    	if (dstRect.contains((int)event.getX(), (int)event.getY())) {
    	//if (event.getX() > mX && event.getX() < mX+mWidth && event.getY() > mY && event.getY() < mY+mHeight) {
    		Log.d("ldk", "Sprite is selected: " + (action & MotionEvent.ACTION_MASK));
	        switch(action & MotionEvent.ACTION_MASK){
	        
	        case MotionEvent.ACTION_DOWN:
	        	result = MotionEvent.ACTION_DOWN;
	        	break;
	        
	        case MotionEvent.ACTION_MOVE:
	            result = MotionEvent.ACTION_MOVE;
	            break;
	        
	        case MotionEvent.ACTION_UP:	//FALL-THROUGH
	        case MotionEvent.ACTION_CANCEL:
	            result = MotionEvent.ACTION_UP;
	            break;
	            
	        case MotionEvent.ACTION_POINTER_DOWN:
	        	result = MotionEvent.ACTION_POINTER_DOWN;
	        	break;

	        case MotionEvent.ACTION_POINTER_UP:
	        	result = MotionEvent.ACTION_POINTER_UP;
	            break;
	        }
	    }
        
        return result;
    }
    
    /*
    public int isSelectedInMultiTouchMode (MotionEvent event) {
    	Log.d("ldk", "event.getX(1): " + event.getX(1));
    	Log.d("ldk", "event.getY(1): " + event.getY(1));
    	int action = event.getAction();
    	
    	int result = -1;
    	
    	//멀티터치 모드가 아니면 리턴
//    	if(event.getPointerCount() <= 1) {
//    		return result;
//    	}
    	
    	//getX(1)로 두번째 손가락의 터치 포인트 체크
    	Log.d("ldk", "dstRect.left:" + dstRect.left);
    	Log.d("ldk", "dstRect.top:" + dstRect.top);
    	Log.d("ldk", "dstRect.right:" + dstRect.right);
    	Log.d("ldk", "dstRect.bottom:" + dstRect.bottom);
    	
    	
    	if (dstRect.contains((int)event.getX(1), (int)event.getY(1))) {
    		Log.d("ldk", "Sprite is selected in multi touch mode: " + (action & MotionEvent.ACTION_MASK));
	        switch(action & MotionEvent.ACTION_MASK){

	        case MotionEvent.ACTION_POINTER_DOWN:
	        	result = MotionEvent.ACTION_POINTER_DOWN;
	        	break;

	        case MotionEvent.ACTION_POINTER_UP:
	        	result = MotionEvent.ACTION_POINTER_UP;
	            break;
	        }
	    }
        
        return result;
    }
    */
}
