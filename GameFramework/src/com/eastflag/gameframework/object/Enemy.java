package com.eastflag.gameframework.object;

import android.graphics.Bitmap;

public class Enemy extends SpriteAnimation{
	private long localTime;
	
	private long localMissileTime;
	private long MISSILE_DISPLAY_TIME = 5000; //5초
	private boolean makeMissile;

	public Enemy(Bitmap bitmap) {
		super(bitmap);
	}

	public void update() {
		super.update();
		
		localTime += mAppDirector.getmDeltaTime();
		
		while(localTime >= 10) {
			mY += 1;
			if(mY>1920 || mX > 1080 || mX < -mWidth) {
				mIsDead = true;
				break;
			}
			localTime -= 10;
			dstRect.set(mX, mY, mX+mWidth, mY + mHeight);
		}
		
		localMissileTime += mAppDirector.getmDeltaTime();
		
		while(localMissileTime >= MISSILE_DISPLAY_TIME) {
			makeMissile = true;
			localMissileTime -= MISSILE_DISPLAY_TIME;
		}
	}

	public boolean isMakeMissile() {
		return makeMissile;
	}

	public void setMakeMissile(boolean makeMissile) {
		this.makeMissile = makeMissile;
	}
}
