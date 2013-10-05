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
	
	private ImageButton btnBgm, btnSound;
	
	public MainScene(){
		mAppDirector = AppDirector.getInstance();
		
		mMenuShooting = new TextButton("shooting game");
		mMenuShooting.setPosition(540, 600, 800, 150);
		mMenuShooting.setColor(Color.CYAN, Color.GREEN, Color.WHITE);
		
		mMenuBoard = new TextButton("board game");
		mMenuBoard.setPosition(540, 1000, 800, 150);
		mMenuBoard.setColor(Color.CYAN, Color.GREEN, Color.WHITE);
		
		//menuShooting = new ImageButton(mAppDirector.menuNew, mAppDirector.menuNewOn);
		//menuShooting.setPosition(540, 500, 800, 300);
		
		btnBgm = new ImageButton(mAppDirector.bgmOn, mAppDirector.bgmOff);
		btnBgm.setPosition(100, 1800, 128, 128);
		btnBgm.isOn=false;
		
		btnSound = new ImageButton(mAppDirector.soundOn, mAppDirector.soundOff);
		btnSound.setPosition(250, 1800, 128, 128);
		btnSound.isOn = false;
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
		
		btnBgm.present(virtualCanvas);
		btnSound.present(virtualCanvas);
	}


	@Override
	public void onTouchEvent(MotionEvent event) {
		//mAppDirector.getmGameView().changeScene(new StartScene());
		//if(menuShooting.isSelected(event)) {
		//	mAppDirector.getmGameView().changeScene(new StartScene());
		//}
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(mMenuShooting.isSelected(event) == MotionEvent.ACTION_DOWN) {
				mMenuShooting.setIsOn(true);
			}
			if(mMenuBoard.isSelected(event) == MotionEvent.ACTION_DOWN) {
				mMenuBoard.setIsOn(true);
			}
			//환경 설정
			if(btnBgm.isSelected(event) == MotionEvent.ACTION_DOWN) {
				if(btnBgm.isOn){
					btnBgm.isOn=false;
					mAppDirector.setmIsBGM(true);
					mAppDirector.getmMainActivity().playBGM();
				}
				else{
					btnBgm.isOn=true;
					mAppDirector.setmIsBGM(false);
					mAppDirector.getmMainActivity().pauseBGM();
				}
			}
			if(btnSound.isSelected(event) == MotionEvent.ACTION_DOWN) {
				if(btnSound.isOn){
					btnSound.isOn=false;
					mAppDirector.setmIsSound(true);
				}
				else{
					btnSound.isOn = true;
					mAppDirector.setmIsSound(true);
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			if(mMenuShooting.isSelected(event) == MotionEvent.ACTION_UP) {
				mAppDirector.getmGameView().changeScene(new StartShootScene());
			}
			
			if(mMenuBoard.isSelected(event) == MotionEvent.ACTION_UP) {
				mAppDirector.getmGameView().changeScene(new StartShootScene());
			}
			break;
		}
		
		
		
		
	}

}
