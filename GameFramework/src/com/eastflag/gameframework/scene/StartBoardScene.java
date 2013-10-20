package com.eastflag.gameframework.scene;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.Random;

import com.eastflag.gameframework.AppDirector;
import com.eastflag.gameframework.object.BallObject;
import com.eastflag.gameframework.object.TextObject;
import com.eastflag.gameframework.object.TimeObject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

public class StartBoardScene implements IScene{
	private AppDirector mAppDirector;
	
	private static final int STATE_READY = 0; //게임 준비
	private static final int STATE_START = 1; //게임 시작
	private static final int STATE_OVER = 3; //게임 오버
	private int mState = STATE_READY;
	
	private final int radius = 80;
	private int mCurrentIndex =0;
	private final int mFirstIndex = 1;
	private final int mLastIndex = 10;
	private final int HUD_HEIGHT = 160;
	
	private Paint hudPaint;
	
	private ArrayList<BallObject> mBalls = new ArrayList<BallObject>();
	private TimeObject mTimeObject;
	private TextObject mTextCount;
	private TextObject mTextGameOver;
	
	private long readyTime;
	
	public StartBoardScene() {
		mAppDirector = AppDirector.getInstance();
		
		for(int i=0; i<=9; i++) {
			BallObject ball = new BallObject(i+1, radius);
			Random r = new Random();
			int centerX = r.nextInt(mAppDirector.mVirtualWidth - radius*2) + radius;
			int centerY = r.nextInt(mAppDirector.mVirtualHeight - HUD_HEIGHT - radius*2) + HUD_HEIGHT + radius;
			ball.setPosition(centerX - radius, centerY - radius);
			mBalls.add(ball);
		}
		
		mTimeObject = new TimeObject("00:00:00", HUD_HEIGHT/2, Color.WHITE);
		mTimeObject.setPosition(800, HUD_HEIGHT/2, -1, -1);
		
		mTextCount = new TextObject(String.format("Count: %2d", 0), HUD_HEIGHT/2, Color.WHITE);
		mTextCount.setPosition(200, HUD_HEIGHT/2);
		
		mTextGameOver = new TextObject("WIN", 200, Color.YELLOW);
		mTextGameOver.setPosition(540, 1000, 0, 0);
		
		//Hud
		hudPaint = new Paint();
		hudPaint.setColor(Color.BLACK);
		hudPaint.setAlpha(150);
	}

	@Override
	public void update() {
		switch(mState) {
		case STATE_READY:
			readyTime += mAppDirector.getmDeltaTime();
			//0.5초마다 공을 하나씩 찍어주기 위해서 시간설정
			while(readyTime >= 200) {
				++mCurrentIndex;
				readyTime -= 200;
				if(mAppDirector.getSound()) {
					mAppDirector.play(AppDirector.SoundEffect.DDAGUI);
				}
			}
			break;
		case STATE_START:
			for(BallObject ball : mBalls) {
				ball.update();
			}
			mTimeObject.update();
			if(mBalls.size() == 0) {
				mState = STATE_OVER;
			}
			break;
		}
		
	}

	@Override
	public void present(Canvas canvas) {
		canvas.drawColor(Color.LTGRAY);
		
		switch(mState) {
		case STATE_READY:
			if(mCurrentIndex >= 1) {
				for(int i=0; i < mCurrentIndex ; ++i) {
					mBalls.get(i).present(canvas);
				}
			}
			if(mCurrentIndex >= mLastIndex) {
				mState = STATE_START;
			}
			break;

		case STATE_START:
			for(BallObject ball : mBalls) {
				ball.present(canvas);
			}
			
			break;
		case STATE_OVER:
			mTextGameOver.present(canvas);
		}
		
		//Hud present
		canvas.drawRect(0, 0, 1080, 160, hudPaint);
		mTextCount.present(canvas);
		mTimeObject.present(canvas);
	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		if(mBalls.size() == 0)
			return;
		
        if(event.getAction() == MotionEvent.ACTION_UP){
            //mCurrentIndex 와 현재 터치 포인트와의 거리가 반지름보다 작은지 체크
            double x = Math.pow(mBalls.get(mCurrentIndex-1).getCenterX()  - event.getX(), 2) ;
            double y = Math.pow(mBalls.get(mCurrentIndex-1).getCenterY() - event.getY(), 2);
            
            if( x + y < Math.pow(mBalls.get(mCurrentIndex-1).getRadius(), 2)) { //touch
                Log.d("ldk", "touch");
                mBalls.remove(mCurrentIndex-1);
                mCurrentIndex--;
				if(mAppDirector.getSound()) {
					mAppDirector.play(AppDirector.SoundEffect.GUN);
				}
				mTextCount.setTitle(String.format("Count: %2d", mLastIndex-mCurrentIndex));
            }
        }
		
	}

}
