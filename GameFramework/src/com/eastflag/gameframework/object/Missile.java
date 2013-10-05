package com.eastflag.gameframework.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.eastflag.gameframework.AppDirector;

public class Missile extends SpriteObject {
	
	private long localTime;
	private long speed;

	public Missile(Bitmap bitmap, long speed) {
		super(bitmap);
		this.speed = speed;
	}

	public void update() {
		localTime += AppDirector.getInstance().getmDeltaTime();
		
		while(localTime>=10) {
			mY += speed; //10ms에 3px 이동
			if(mY+mHeight<0 || mY > 1920) {
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
}
