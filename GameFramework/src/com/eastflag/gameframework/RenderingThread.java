package com.eastflag.gameframework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Bitmap.Config;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
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
		
		//4. 가상 화면 정의
		virtualBuffer = Bitmap.createBitmap(mAppDirector.getmVirtualWidth(), mAppDirector.getmVirtualHeight(), Config.ARGB_8888);
		virtualCanvas = new Canvas();
		virtualCanvas.setBitmap(virtualBuffer);
		
		dstRect = new Rect();
	}

	@Override
	public void run(){
		//3. 델타타임과 FPS 구하기
		//while문 한바퀴가 델타타임을 이해하기
		Canvas canvas = null;
		while(canRun){
			currentTime = System.currentTimeMillis();
			
			//1. 상태 업데이트와 그리기는 일을 시작
			try{
				canvas = mHolder.lockCanvas();  // 도화지를 고정 시키기
				synchronized(mHolder){
					//객체 상태 갱신
					mGameView.update();
					//객체들 화면에 그리기
					mGameView.present(virtualCanvas);
					
				}
            } catch (Exception e) {
            	Log.e("ldk", "exception: " + e.toString());
            } finally {
                if (canvas != null) {
                    //5. 실제 화면해상도에 맞추기
                    canvas.getClipBounds(dstRect);  //실제 디바이스 크기 세팅
                    canvas.drawBitmap(virtualBuffer, null, dstRect, null); //가상 디바이스를 실제 디바이스로 맞추기
                    
                    mHolder.unlockCanvasAndPost(canvas);  //도화지를 떼어서 필름에 갖다 붙이기
                }
            }
			
			deltaTime = System.currentTimeMillis() - currentTime;
			mAppDirector.setmDeltaTime(deltaTime);
		}

	}

	//2. 상태 종료하는 변수 정의
	public void setRunning(boolean canRun){
		this.canRun = canRun;
	}
}
