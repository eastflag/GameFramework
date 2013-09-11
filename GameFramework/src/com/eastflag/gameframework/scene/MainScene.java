package com.eastflag.gameframework.scene;

import com.eastflag.gameframework.AppDirector;
import com.eastflag.gameframework.object.TextButton;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

public class MainScene implements IScene {
	private AppDirector mAppDirector;
	
	private TextButton mMenuSingle;
	
	public MainScene(){
		mAppDirector = AppDirector.getInstance();
		
		mMenuSingle = new TextButton(240, 200, 300, 80, "single game");
		
	}
	

	@Override
	public void update() {
		
	}

	@Override
	public void present(Canvas virtualCanvas) {
		virtualCanvas.drawColor(Color.DKGRAY);
		mMenuSingle.present(virtualCanvas);
	}


	@Override
	public void onTouchEvent(MotionEvent event) {
		mAppDirector.getmGameView().changeScene(new StartScene());
	}

}
