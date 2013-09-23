package com.eastflag.gameframework.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.eastflag.gameframework.AppDirector;

public class Missile extends SpriteObject {
	
	private long localTime;
	private boolean mIsDead;

	public Missile(Bitmap bitmap) {
		super(bitmap);
	}

	public void update() {
		localTime += AppDirector.getInstance().getmDeltaTime();
		
		while(localTime>=10) {
			mY -= 3; //10ms에 3px 이동
			if(mY+mHeight<0) {
				mIsDead = true;
				break;
			}
			localTime -= 10;
			dstRect.set(mX, mY, mX+mWidth, mY+mHeight);
		}
	}
	
	public void present(Canvas canvas) {
		super.present(canvas);
	}

	
	public boolean getmIsDead() {
		return mIsDead;
	}
}
