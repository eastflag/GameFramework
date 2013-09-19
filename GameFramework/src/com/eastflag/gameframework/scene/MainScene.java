package com.eastflag.gameframework.scene;

import com.eastflag.gameframework.AppDirector;
import com.eastflag.gameframework.object.Background;
import com.eastflag.gameframework.object.ImageButton;
import com.eastflag.gameframework.object.Player;
import com.eastflag.gameframework.object.TextButton;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

public class MainScene implements IScene {
	private AppDirector mAppDirector;
	
	private TextButton mMenuShooting, mMenuBoard;  //메뉴
	//private ImageButton menuShooting;
	
	public MainScene(){
		mAppDirector = AppDirector.getInstance();
		
		mMenuShooting = new TextButton("shooting game");
		mMenuShooting.init(Color.CYAN, Color.GREEN, Color.WHITE);
		mMenuShooting.setPosition(540, 400, 600, 100);
		mMenuBoard = new TextButton("board game");
		mMenuBoard.init(Color.CYAN, Color.GREEN, Color.WHITE);
		mMenuBoard.setPosition(540, 800, 600, 100);
		//menuShooting = new ImageButton(mAppDirector.menuNew, mAppDirector.menuNewOn);
		//menuShooting.setPosition(540, 500, 800, 300);
	}
	

	@Override
	public void update() {
	}

	@Override
	public void present(Canvas virtualCanvas) {
		virtualCanvas.drawColor(Color.LTGRAY);
		mMenuShooting.present(virtualCanvas);
		mMenuBoard.present(virtualCanvas);
		//menuShooting.present(virtualCanvas);
	}


	@Override
	public void onTouchEvent(MotionEvent event) {
		//mAppDirector.getmGameView().changeScene(new StartScene());
		//if(menuShooting.isSelected(event)) {
		//	mAppDirector.getmGameView().changeScene(new StartScene());
		//}
		
		if(mMenuShooting.isSelected(event) == MotionEvent.ACTION_UP) {
			mAppDirector.getmGameView().changeScene(new StartShootScene());
		}
		
		if(mMenuBoard.isSelected(event) == MotionEvent.ACTION_UP) {
			mAppDirector.getmGameView().changeScene(new StartShootScene());
		}
	}

}
