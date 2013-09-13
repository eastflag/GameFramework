package com.eastflag.gameframework.scene;

import com.eastflag.gameframework.AppDirector;
import com.eastflag.gameframework.object.Background;
import com.eastflag.gameframework.object.TextButton;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

public class MainScene implements IScene {
	private AppDirector mAppDirector;
	
	private Background mBackground, mBackCloud;
	private TextButton mMenuSingle;
	
	public MainScene(){
		mAppDirector = AppDirector.getInstance();
		
		mBackground = new Background(mAppDirector.background2);
		mBackground.setSpeed(0, 1);
		mBackCloud = new Background(mAppDirector.back_cloud);
		mBackCloud.setSpeed(0, -1);
		mMenuSingle = new TextButton(240, 200, 300, 80, "single game");
		
		
	}
	

	@Override
	public void update() {
		mBackground.update();
		mBackCloud.update();
	}

	@Override
	public void present(Canvas virtualCanvas) {
		//virtualCanvas.drawColor(Color.DKGRAY);
		mBackground.present(virtualCanvas);
		mBackCloud.present(virtualCanvas);
		mMenuSingle.present(virtualCanvas);
	}


	@Override
	public void onTouchEvent(MotionEvent event) {
		mAppDirector.getmGameView().changeScene(new StartScene());
	}

}
