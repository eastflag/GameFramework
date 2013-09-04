package com.eastflag.gameframework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Bitmap.Config;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

public class RenderingThread extends Thread {
	private AppDirector mAppDirector;
	private GameView mGameView;
	private SurfaceHolder mHolder;
	private volatile boolean canRun = true;
	
	public long currentTime;
	public long deltaTime= 1;
	
	Bitmap virtualBuffer;
	Canvas virtualCanvas;
	Rect dstRect;
	Paint paint;

	
	public RenderingThread(GameView mGameView, SurfaceHolder mHolder){
		this.mGameView = mGameView;
		this.mHolder = mHolder;
		
		mAppDirector = AppDirector.getInstance();
		
		virtualBuffer = Bitmap.createBitmap(mAppDirector.getmVirtualWidth(), mAppDirector.getmVirtualHeight(), Config.ARGB_8888);
		virtualCanvas = new Canvas();
		virtualCanvas.setBitmap(virtualBuffer);
		
		dstRect = new Rect();
	}

	@Override
	public void run(){
		while(canRun){
			Canvas canvas = null;
			while(canRun){
				currentTime = System.currentTimeMillis();
				
				canvas = mHolder.lockCanvas();
				try{
					synchronized(mHolder){
						//객체 상태 갱신
						mGameView.update();
						//객체들 화면에 그리기
						mGameView.present(virtualCanvas);
						
					}
                } finally {
                    if (canvas != null) {
                        //실제 화면해상도에 맞추기
                        canvas.getClipBounds(dstRect);  //실제 디바이스 크기 세팅
                        canvas.drawBitmap(virtualBuffer, null, dstRect, null); //가상 디바이스를 실제 디바이스로 맞추기
                        mHolder.unlockCanvasAndPost(canvas);
                    }
                }
				
				deltaTime = System.currentTimeMillis() - currentTime;
				mAppDirector.setmDeltaTime(deltaTime);
			}
		}
	}

	
	public void setRunning(boolean canRun){
		this.canRun = canRun;
	}
}
