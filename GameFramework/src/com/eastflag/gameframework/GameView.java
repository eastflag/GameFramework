package com.eastflag.gameframework;

import java.util.ArrayList;

import com.eastflag.gameframework.scene.IScene;
import com.eastflag.gameframework.scene.MainScene;
import com.eastflag.gameframework.scene.StartScene;

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

//SurfaceView :  필름
//Callback : 필름의 상태를 정의하는 인터페이스. 3가지의 필름 상태를 정의
public class GameView extends SurfaceView implements Callback {
	private AppDirector mAppDirector;
	private RenderingThread mThread;
	
	private boolean mSuccess;
	
	private Paint mPaint;
	
	public IScene mIScene;

	public GameView(Context context) {
		super(context);
		
		setFocusable(true);
		
		mAppDirector = AppDirector.getInstance();
		
		//1. 필름과 연필을 애니메이션 작가에게 넘김
		SurfaceHolder mHolder = getHolder();  
		mHolder.addCallback(this);
		mThread = new RenderingThread(this, mHolder); 
		
		//초기화
		mAppDirector.setmGameView(this);
		//최초 씬을 게임 시작씬으로 가정
		mIScene = new MainScene();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {  //필름을 카메라에 끼움
		//2. 애니메이션 작가에게 일을 시킴
	    mThread.setRunning(true);
		mThread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		//3. 애니메이션 작가의 일을 중단 시킴
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
	
	//4. 필름 상태 업데이트
	public void update(){
		mIScene.update();
	}

	//5. 그림 그리기
	public void present(Canvas virtualCanvas){
		mIScene.present(virtualCanvas);
	}
	
	public void changeScene(IScene iScene) {
		mIScene = iScene;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mIScene.onTouchEvent(event);
		return true;
	}
	
	
}