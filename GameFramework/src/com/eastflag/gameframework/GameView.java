package com.eastflag.gameframework;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

//SurfaceView : 성능이 좋은 필름
//Callback : 필름의 상태를 정의하는 인터페이스. 3가지의 필름 상태를 정의
public class GameView extends SurfaceView implements Callback {
	private AppDirector mAppDirector;
	private RenderingThread mThread;
	
	private boolean mSuccess;
	
	private Paint mPaint;

	public GameView(Context context) {
		super(context);
		
		mAppDirector = AppDirector.getInstance();
		mPaint = new Paint();
		mPaint.setColor(Color.RED);
		mPaint.setAntiAlias(true);
		mPaint.setTextSize(30);
		
		SurfaceHolder mHolder = getHolder();  
		mHolder.addCallback(this);
		mThread = new RenderingThread(this, mHolder);  //필름과 연필을 에니메이션 작가에게 넘김
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	    mThread.setRunning(true);
		mThread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mThread.setRunning(false);
		boolean retry = true;
		while (retry) {
			try {
				mThread.join(); // 쓰레드를 정지시킴. 현재 그리고 있는중일수도 있으므로,,,
				retry = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
	}
	
	//상태 업데이트
	public void update(){
		
	}

	//그림 그리기
	public void present(Canvas virtualCanvas){
		//회색으로 칠하기
	    virtualCanvas.drawColor(Color.DKGRAY);
	    //FPS 와 delta 타임 그리기
	    virtualCanvas.drawText("deltaTime:" + mAppDirector.getmDeltaTime(), 100, 100, mPaint);
	    virtualCanvas.drawText("FPS:" + 1000f/mAppDirector.getmDeltaTime(), 100, 200, mPaint);
	}
	
}