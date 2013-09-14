package com.eastflag.gameframework.scene;

import com.eastflag.gameframework.AppDirector;
import com.eastflag.gameframework.object.Background;
import com.eastflag.gameframework.object.Player;
import com.eastflag.gameframework.object.TextButton;

import android.graphics.Canvas;
import android.view.MotionEvent;

public class MainScene implements IScene {
	private AppDirector mAppDirector;
	
	private Background mBackground, mBackCloud; //백그라운드 배경, 전경
	private TextButton mMenuSingle;  //메뉴
	private Player mPlayer; //플레이어
	
	public MainScene(){
		mAppDirector = AppDirector.getInstance();
		
		mBackground = new Background(mAppDirector.background2);
		mBackground.setSpeed(0, 1);
		mBackCloud = new Background(mAppDirector.back_cloud);
		mBackCloud.setSpeed(0, -1);
		mMenuSingle = new TextButton(240, 200, 300, 80, "single game");
		
		mPlayer = new Player(mAppDirector.player);
		mPlayer.init(6, 100, 62, 104, true);
		mPlayer.setPosition(540, 1600, 200, 350);
	}
	

	@Override
	public void update() {
		mBackground.update();
		mBackCloud.update();
		mPlayer.update();
	}

	@Override
	public void present(Canvas virtualCanvas) {
		//virtualCanvas.drawColor(Color.DKGRAY);
		mBackground.present(virtualCanvas);
		mBackCloud.present(virtualCanvas);
		mMenuSingle.present(virtualCanvas);
		mPlayer.present(virtualCanvas);
	}


	@Override
	public void onTouchEvent(MotionEvent event) {
		mAppDirector.getmGameView().changeScene(new StartScene());
	}

}
