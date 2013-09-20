package com.eastflag.gameframework.scene;

import com.eastflag.gameframework.AppDirector;
import com.eastflag.gameframework.object.Background;
import com.eastflag.gameframework.object.Player;
import com.eastflag.gameframework.object.Sprite;
import com.eastflag.gameframework.object.SpriteObject;
import com.eastflag.gameframework.object.TextButton;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class StartShootScene implements IScene{
	
//	private Paint mPaint;
	private AppDirector mAppDirector;
	
	private Background mBackground, mBackCloud; //백그라운드 배경, 전경
	private SpriteObject leftKeypad, rightKeypad, upKeypad, downKeypad, tapKeypad;
	private Player mPlayer; //플레이어
	
	public StartShootScene(){
//		mPaint = new Paint();
//		mPaint.setColor(Color.RED);
//		mPaint.setAntiAlias(true);
//		mPaint.setTextSize(30);
		mAppDirector = AppDirector.getInstance();
		
		mBackground = new Background(mAppDirector.background2);
		mBackground.setSpeed(0, 1);
		mBackCloud = new Background(mAppDirector.back_cloud);
		mBackCloud.setSpeed(0, -1);
		
		mPlayer = new Player(mAppDirector.player);
		mPlayer.init(6, 100, 62, 104, true);
		mPlayer.setPosition(540, 1600, 200, 350);
		
		upKeypad = new SpriteObject(mAppDirector.upTriangle);
		upKeypad.setPosition(150, 1400, 100, 100);
		rightKeypad = new SpriteObject(mAppDirector.rightTriangle);
		rightKeypad.setPosition(250, 1550, 100, 100);
		downKeypad = new SpriteObject(mAppDirector.downTriangle);
		downKeypad.setPosition(150, 1700, 100, 100);
		leftKeypad = new SpriteObject(mAppDirector.leftTriangle);
		leftKeypad.setPosition(50, 1550, 100, 100);
		tapKeypad = new SpriteObject(mAppDirector.circle);
		tapKeypad.setPosition(150, 1550, 100, 100);
	}

	@Override
	public void update() {
		mBackground.update();
		mBackCloud.update();
		mPlayer.update();
	}

	@Override
	public void present(Canvas canvas) {
		//회색으로 칠하기
		//canvas.drawColor(Color.DKGRAY);
	    //FPS 와 delta 타임 그리기
		//canvas.drawText("deltaTime:" + AppDirector.getInstance().getmDeltaTime(), 100, 100, mPaint);
		//canvas.drawText("FPS:" + 1000f/AppDirector.getInstance().getmDeltaTime(), 100, 200, mPaint);
		mBackground.present(canvas);
		mBackCloud.present(canvas);
		mPlayer.present(canvas);
		
		upKeypad.present(canvas);
		rightKeypad.present(canvas);
		downKeypad.present(canvas);
		leftKeypad.present(canvas);
		tapKeypad.present(canvas);
	}

	@Override
	public void onTouchEvent(MotionEvent event) {
//		if(upKeypad.isSelected(event) == MotionEvent.ACTION_HOVER_MOVE)
//			mPlayer.setPosition(x, y);
		
	}

}
