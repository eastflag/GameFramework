package com.eastflag.gameframework.scene;

import com.eastflag.gameframework.AppDirector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

public class MainScene implements IScene {
	private AppDirector mAppDirector;
	
	public MainScene(){
		mAppDirector = AppDirector.getInstance();
	}
	

	@Override
	public void update() {
		
	}

	@Override
	public void present(Canvas virtualCanvas) {
		virtualCanvas.drawColor(Color.DKGRAY);
	}


	@Override
	public void onTouchEvent(MotionEvent event) {
		mAppDirector.getmGameView().changeScene(new StartScene());
	}

}
