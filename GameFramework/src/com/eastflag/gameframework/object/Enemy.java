package com.eastflag.gameframework.object;

import java.util.Random;

import android.graphics.Bitmap;

public class Enemy extends SpriteAnimation{
	private long localTime;
	
	private long localMissileTime;
	private long MISSILE_DISPLAY_TIME = 4000; //4초
	private boolean makeMissile;
	
	public enum EnemyType {
		AccelType, LeftType, RightType
	}
	
	private EnemyType mEnemyType;

	public Enemy(Bitmap bitmap, int type) {
		super(bitmap);
		
		//적군 타입을 랜덤 생성
		mEnemyType = EnemyType.values()[type];
	}

	public void update() {
		super.update();
		
		localTime += mAppDirector.getmDeltaTime();
		
		while(localTime >= 10) {
			//타입에 따라 움직임을 변화
			if(mEnemyType == EnemyType.AccelType) {
				if(mY >=900) {
					mY += 3;
				} else {
					mY += 1;
				}
			}
			if(mEnemyType == EnemyType.LeftType) {
				if(mY >=900) {
					mY += 1;
					mX += -1;
				} else {
					mY += 1;
				}
			}
			if(mEnemyType == EnemyType.RightType) {
				if(mY >=900) {
					mY += 1;
					mX += 1;
				} else {
					mY += 1;
				}
			}
			
			
			//화면에서 사라질때 없애기
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
			Random rand = new Random();
			MISSILE_DISPLAY_TIME = (2 + rand.nextInt(3)) * 1000; //2~4초
		}
	}

	public boolean isMakeMissile() {
		return makeMissile;
	}

	public void setMakeMissile(boolean makeMissile) {
		this.makeMissile = makeMissile;
	}
}
