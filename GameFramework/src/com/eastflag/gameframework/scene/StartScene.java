package com.eastflag.gameframework.scene;

import com.eastflag.gameframework.AppDirector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class StartScene implements IScene{
	
	private Paint mPaint;
	
	public StartScene(){
		mPaint = new Paint();
		mPaint.setColor(Color.RED);
		mPaint.setAntiAlias(true);
		mPaint.setTextSize(30);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void present(Canvas canvas) {
		//회색으로 칠하기
		canvas.drawColor(Color.DKGRAY);
	    //FPS 와 delta 타임 그리기
		canvas.drawText("deltaTime:" + AppDirector.getInstance().getmDeltaTime(), 100, 100, mPaint);
		canvas.drawText("FPS:" + 1000f/AppDirector.getInstance().getmDeltaTime(), 100, 200, mPaint);
	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

}
