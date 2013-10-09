package com.eastflag.gameframework.scene;

import java.security.spec.MGF1ParameterSpec;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.eastflag.gameframework.AppDirector;
import com.eastflag.gameframework.object.Background;
import com.eastflag.gameframework.object.Enemy;
import com.eastflag.gameframework.object.Explosion;
import com.eastflag.gameframework.object.Missile;
import com.eastflag.gameframework.object.Player;
import com.eastflag.gameframework.object.Sprite;
import com.eastflag.gameframework.object.SpriteObject;
import com.eastflag.gameframework.object.TextButton;
import com.eastflag.gameframework.object.TextObject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;

public class StartShootScene implements IScene{
	private static final int STATE_START = 1; //게임 시작
	private static final int STATE_OVER = 3; //게임 오버
	private int mState = STATE_START;
//	private Paint mPaint;
	private AppDirector mAppDirector;
	private long localDeltaTime;
	private long ENEMY_DISPLAY_TIME = 3000; // 3초에 한번씩 출현
	
	private Background mBackground, mBackCloud; //백그라운드 배경, 전경
	private SpriteObject leftKeypad, rightKeypad, upKeypad, downKeypad, tapKeypad;
	private Player mPlayer; //플레이어
	private BlockingQueue<Missile> missileList = new ArrayBlockingQueue<Missile>(100);
	private BlockingQueue<Enemy> enemyList = new ArrayBlockingQueue<Enemy>(100);
	private BlockingQueue<Missile> enemyMissileList = new ArrayBlockingQueue<Missile>(100);
	private BlockingQueue<Explosion> explosionList = new ArrayBlockingQueue<Explosion>(100);
	
	private Paint hudPaint;
	
	private TextObject mTextGameover;
	private TextObject mTextScore;
	private int mScore;
	
	public StartShootScene(){
//		mPaint = new Paint();
//		mPaint.setColor(Color.RED);
//		mPaint.setAntiAlias(true);
//		mPaint.setTextSize(30);
		mAppDirector = AppDirector.getInstance();
		
		mBackground = new Background(mAppDirector.background2);
		mBackground.setSpeed(0, 1);
		mBackCloud = new Background(mAppDirector.back_cloud);
		mBackCloud.setSpeed(0, -1);
		
		mPlayer = new Player(mAppDirector.player);
		mPlayer.init(6, 100, 62, 104, 0);
		mPlayer.setPosition(540, 1600, 200, 350);
		
		upKeypad = new SpriteObject(mAppDirector.upTriangle);
		upKeypad.setPosition(150, 1600, 100, 100);
		rightKeypad = new SpriteObject(mAppDirector.rightTriangle);
		rightKeypad.setPosition(250, 1700, 100, 100);
		downKeypad = new SpriteObject(mAppDirector.downTriangle);
		downKeypad.setPosition(150, 1800, 100, 100);
		leftKeypad = new SpriteObject(mAppDirector.leftTriangle);
		leftKeypad.setPosition(50, 1700, 100, 100);
		tapKeypad = new SpriteObject(mAppDirector.circle);
		tapKeypad.setPosition(150, 1700, 100, 100);
		
		//Hud
		hudPaint = new Paint();
		hudPaint.setColor(Color.BLACK);
		hudPaint.setAlpha(150);
		
		mTextGameover = new TextObject("Game Over", 200, Color.YELLOW);
		mTextGameover.setPosition(540, 1000, 0, 0);
		mTextScore = new TextObject(String.format("Score: %5d", mScore), 80, Color.WHITE);
		mTextScore.setPosition(250, 80, 0, 0);
	}

	@Override
	public void update() {
		localDeltaTime += mAppDirector.getmDeltaTime();
		
		mBackground.update();
		mBackCloud.update();
		if(mState == STATE_START)
			mPlayer.update();
		
		for(Missile missile : missileList) {
			missile.update();
			if(missile.getmIsDead())
				missileList.remove(missile);
		}
		
		for(Enemy enemy : enemyList) {
			enemy.update();
			if(enemy.getmIsDead()) {
				enemyList.remove(enemy);
				continue;
			}
			
			if(enemy.isMakeMissile()) {
				Missile enemyMissile = new Missile(mAppDirector.missile2, 3);
				enemyMissile.setPosition(enemy.getmX() + enemy.getmWidth()/2, enemy.getmY()+ enemy.getmHeight(), 70, 70);
				enemyMissileList.add(enemyMissile);
				enemy.setMakeMissile(false);
			}
		}
		
		for(Missile missile : enemyMissileList) {
			missile.update();
			if(missile.getmIsDead())
				missileList.remove(missile);
		}
		
		for(Explosion explosion : explosionList) {
			explosion.update();
			if(explosion.getmIsDead()) explosionList.remove(explosion);
		}
		
		//점수 update
		mTextScore.setTitle(String.format("Score: %5d", mScore));
		
		addEnemy();
		checkCollision();
	}

	@Override
	public void present(Canvas canvas) {
		//회색으로 칠하기
		//canvas.drawColor(Color.DKGRAY);
	    //FPS 와 delta 타임 그리기
		//canvas.drawText("deltaTime:" + AppDirector.getInstance().getmDeltaTime(), 100, 100, mPaint);
		//canvas.drawText("FPS:" + 1000f/AppDirector.getInstance().getmDeltaTime(), 100, 200, mPaint);
		mBackground.present(canvas);
		mBackCloud.present(canvas);
		mPlayer.present(canvas);
		
		upKeypad.present(canvas);
		rightKeypad.present(canvas);
		downKeypad.present(canvas);
		leftKeypad.present(canvas);
		tapKeypad.present(canvas);
		
		for(Missile missile : missileList) {
			missile.present(canvas);
		}
		
		for(Enemy enemy : enemyList) {
			enemy.present(canvas);
		}
		
		for(Missile missile : enemyMissileList) {
			missile.present(canvas);
		}
		
		for(Explosion explosion : explosionList) {
			explosion.present(canvas);
		}
		
		//Hud present
		canvas.drawRect(0, 0, 1080, 160, hudPaint);
		
		mTextScore.present(canvas);
		
		if(mState == STATE_OVER) {
			mTextGameover.present(canvas);
		}
	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		//게임 오버 상태면 터치가 안되게 한다.
		if(mState == STATE_OVER )
			return;
		//down시 어떤 객체에서 down 되었는지,
		//move시 down된 객체에서 move하면서 빠져나갔는지,,
		//up시 초기화.
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			//keypad
			if(upKeypad.isSelected(event) == MotionEvent.ACTION_DOWN){
				mPlayer.startMoving(0, -1);
			}
			if(downKeypad.isSelected(event) == MotionEvent.ACTION_DOWN) {
				mPlayer.startMoving(0, 1);
			}
			if(rightKeypad.isSelected(event) == MotionEvent.ACTION_DOWN) {
				mPlayer.startMoving(1, 0);
			}
			if(leftKeypad.isSelected(event) == MotionEvent.ACTION_DOWN) {
				mPlayer.startMoving(-1, 0);
			}
			//missile tap
			if(tapKeypad.isSelected(event) == MotionEvent.ACTION_DOWN) {
				Missile missile = new Missile(AppDirector.getInstance().missile, -3);
				missile.setPosition(mPlayer.getmX() + mPlayer.getmWidth()/2, mPlayer.getmY(), 50, 50);
				missileList.add(missile);
				mAppDirector.play(2); // 뷰 재사용
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			mPlayer.stopMoving();
			break;
		}
	}
	
	private void addEnemy() {
		while(localDeltaTime >= ENEMY_DISPLAY_TIME) {
			Enemy enemy = new Enemy(mAppDirector.enemy1);
			enemy.init(6, 100, 62, 104, 0);
			Random rand = new Random();
			int width = 200;
			int height = 350;
			int minX = 200/2;
			int maxX = 1080 - 200/2;
			enemy.setPosition(minX + rand.nextInt(maxX-minX), -(height/2), width, height);
			enemyList.add(enemy);
			localDeltaTime -= ENEMY_DISPLAY_TIME;
			
			ENEMY_DISPLAY_TIME = (3 + rand.nextInt(5)) * 1000; //3초에서 7초사이 랜덤 생성
		}
	}

	private void checkCollision() {
		//아군 미사일과 적군 충돌 체크
		for(Missile missile : missileList) {
			for(Enemy enemy : enemyList) {
				if(checkBoxToBox(enemy, missile)){
					enemyList.remove(enemy);
					missileList.remove(missile);
					//점수 증가
					mScore += 100;
					//폭발처리
					addExplosion(enemy);
					break;
				}
			}
		}
		
		//적군과 아군 충돌 체크
		for(Enemy enemy : enemyList) {
			if(checkBoxToBox(enemy, mPlayer)) {
				enemyList.remove(enemy);
				
				mPlayer.getDstRect().set(0,0,0,0); //invisible 처리
				mPlayer.mIsDead = true;
				
				//폭발처리
				addExplosion(mPlayer);
				//게임 종료 처리
				mState = STATE_OVER; 
				showRetryDialog();
				break;
			}
		}
		
		//적군미사일과 아군 충돌 체크
		for(Missile enemyMissile : enemyMissileList) {
			if(checkBoxToBox(enemyMissile, mPlayer)) {
				enemyMissileList.remove(enemyMissile);
				
				mPlayer.getDstRect().set(0,0,0,0); //invisible 처리
				mPlayer.mIsDead = true;
				
				//폭발처리
				addExplosion(mPlayer);
				//게임 종료 처리
				mState = STATE_OVER; 
				showRetryDialog();
				break;
			}
		}
	}
	
	//폭발처리, sprite : 폭발 위치
	private void addExplosion(Sprite sprite) {
		Explosion explosion = new Explosion(mAppDirector.explosion);
		explosion.init(6, 100, 66, 104, 2);
		explosion.setPosition(sprite.getmX() + sprite.getmWidth()/2, sprite.getmY() + sprite.getmHeight()/2, sprite.getmWidth(), sprite.getmHeight());
		explosionList.add(explosion);
		if(mAppDirector.ismIsSound())
			mAppDirector.play(1); //폭발음
	}
	
	public boolean checkBoxToBox(Sprite sprite1, Sprite sprite2) {
		if(sprite1.getDstRect().intersect(sprite2.getDstRect())) {
			return true;
		} else {
			return false;
		}
	}
	
	private void showRetryDialog() {
		Handler mHandler = new Handler(Looper.getMainLooper());
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				mAppDirector.getmMainActivity().retryGame();
			}
		});
	}
	
}
