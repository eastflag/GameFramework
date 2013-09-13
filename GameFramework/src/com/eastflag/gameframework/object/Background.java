package com.eastflag.gameframework.object;

import com.eastflag.gameframework.AppDirector;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

//0.1초에 1px 아래로 움직인다.
public class Background {
	private Bitmap mBitmap;
	private int mScrollSpeedX = 1; //1px
	private int mScrollSpeedY = 1; //1px
	private int mX, mY; //스크롤한 x, y
	private long mLocalTime;
	private Rect mSrcRect, mDestRect;
	
	public Background(Bitmap bitmap) {
		mBitmap = bitmap;
		
		int width = AppDirector.getInstance().mVirtualWidth;
		int height = AppDirector.getInstance().mVirtualHeight;
		mSrcRect = new Rect(0, 0, 320,  600); //비트맵의 그릴 영역
		mDestRect = new Rect(0, 0, width, height); //화면에 그릴 영역
	}
	
	public void setSpeed(int x, int y){
		mScrollSpeedX = x;
		mScrollSpeedY = y;
	}
	
	public void update() {
		mLocalTime += AppDirector.getInstance().getmDeltaTime();
		while (mLocalTime > 100) {  //mLocalTime이 과부하로 200이상의 값이 들어올때 두번 이상 처리
			if(mLocalTime > 100) {
				mLocalTime -= 100;
				//srcRect를 아래로 한칸 이동
				mX += mScrollSpeedX;
				mY += mScrollSpeedY;
				
				//경계 체크
//				if(320+mX >= mBitmap.getWidth()) {
//					mX = 0;
//				}
				if(mY <0) {
					mY = mBitmap.getHeight() - 600;
				} else if(600+mY > mBitmap.getHeight()) {
					mY = 0;
				}
				
				mSrcRect.set(0 + mX, 0 + mY, 320 + mX, 600 + mY);
			}
			

		}
	}
	
	public void present(Canvas canvas) {
		canvas.drawBitmap(mBitmap, mSrcRect, mDestRect, null);
	}
	
}
