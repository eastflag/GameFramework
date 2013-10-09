package com.eastflag.gameframework.object;

import com.eastflag.gameframework.AppDirector;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Player extends SpriteAnimation {
	
	private long localTime = 0;
	private boolean isMoving;
	private int directionX = 0;
	private int directionY = 0;

    public Player(Bitmap bitmap) {
        super(bitmap);
    }

    @Override
	public void update() {
		super.update();

		if(isMoving) {
			localTime += AppDirector.getInstance().getmDeltaTime();
			while(localTime >= 10) { //10ms동안 1px 움직임, 5px움직일려면 direction * 배수
		    	mX += directionX * 3;
		    	mY += directionY * 3;
		    	
		    	if(mX<0) 
		    		mX = 0;
		    	if(mY<0)
		    		mY = 0;
		    	if(mX+mWidth>AppDirector.getInstance().getmVirtualWidth()) 
		    		mX  = AppDirector.getInstance().getmVirtualWidth() - mWidth;
		    	if(mY+mHeight>AppDirector.getInstance().getmVirtualHeight())
		    		mY = AppDirector.getInstance().getmVirtualHeight() - mHeight;
		    	
		    	dstRect.set(mX, mY, mX+mWidth, mY+mHeight);
		    	localTime -= 10;
			}
		}
	}

	public void startMoving(int x, int y) {
		directionX = x;
		directionY = y;
		isMoving = true;
    }
	
	public void stopMoving() {
		localTime = 0;
		isMoving = false;
	}
}
