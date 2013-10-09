package com.eastflag.gameframework;

import java.io.IOException;
import java.util.HashMap;

import com.eastflag.gameframework.object.Enemy;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

public class AppDirector extends Application {
	
	//가상 디바이스 : FHD로 가정
	public int mVirtualWidth = 1080;
	public int mVirtualHeight = 1920;
	//실제 디바이스 크기
	private int mWidth;
	private int mHeight;
	
	private int mScreenWidth;
	private int mScreenHeight;
	
	private float ratioX;
	private float ratioY;
	
	//사운드
	private SoundPool mSoundPool;
	private HashMap<SoundEffect, Integer> mSoundPoolMap;
	private AudioManager mAudioManager;
	
	//리소스
	//MainScene
	public Bitmap background1, background2, back_cloud;
	public Bitmap menuNew, menuNewOn;
	public Bitmap bgmOn, bgmOff, soundOn, soundOff;
	//StartShoot
	public Bitmap circle, upTriangle, rightTriangle, downTriangle, leftTriangle;
	public Bitmap player; 
	public Bitmap enemy [] = new Bitmap[Enemy.EnemyType.values().length];
	public Bitmap missile, missile2;
	public Bitmap explosion;
	
	public enum SoundEffect {
		EXPLOSION, PLAYER_MISSILE, ENEMY_MISSILE, MISSION_START, MISSION_SUCCESS, MISSION_FAIL
	}
	
	//singleton pattern-------------------------------------
	private AppDirector(){
		
	}

	private static AppDirector _instance;
	
	public static AppDirector getInstance(){
		if(_instance == null){
			_instance = new AppDirector();
		}
		
		return _instance;
	}
	//singleton pattern-------------------------------------
	
	public void initialize() {
		//실제 디바이스 크기 구하기
		//Display display = ((WindowManager)mMainActivity.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		mMainActivity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);;
		mWidth = outMetrics.widthPixels;
		mHeight = outMetrics.heightPixels;
		Log.d("ldk", "mWidth:" + mWidth);
		
		//사운드 로딩
		mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		mSoundPoolMap = new HashMap<SoundEffect, Integer>();
		mAudioManager = (AudioManager)mMainActivity.getSystemService(Context.AUDIO_SERVICE);
		
		mSoundPoolMap.put(SoundEffect.EXPLOSION, mSoundPool.load(mMainActivity, R.raw.explosion, 1)); //폭발음
		mSoundPoolMap.put(SoundEffect.PLAYER_MISSILE, mSoundPool.load(mMainActivity, R.raw.missile, 1)); //아군 미사일
		mSoundPoolMap.put(SoundEffect.ENEMY_MISSILE, mSoundPool.load(mMainActivity, R.raw.missile_enemy, 1)); //적군 미사일
		mSoundPoolMap.put(SoundEffect.MISSION_START, mSoundPool.load(mMainActivity, R.raw.mission_start, 1)); //미션 시작
		mSoundPoolMap.put(SoundEffect.MISSION_SUCCESS, mSoundPool.load(mMainActivity, R.raw.mission_success, 1)); //미션 성공
		mSoundPoolMap.put(SoundEffect.MISSION_FAIL, mSoundPool.load(mMainActivity, R.raw.mission_fail, 1)); //미션 실패

		
		//리소스 로딩
		AssetManager am = mMainActivity.getAssets();
		try {
			bgmOn = BitmapFactory.decodeStream(am.open("bgm_on.png"));
			bgmOff = BitmapFactory.decodeStream(am.open("bgm_off.png"));
			soundOn = BitmapFactory.decodeStream(am.open("sound_on.png"));
			soundOff = BitmapFactory.decodeStream(am.open("sound_off.png"));
			
			background1 = BitmapFactory.decodeStream(am.open("background1.png"));
			background2 = BitmapFactory.decodeStream(am.open("background2.jpg"));
			back_cloud = BitmapFactory.decodeStream(am.open("background_2.png"));
			player = BitmapFactory.decodeStream(am.open("player.png"));
			menuNew = BitmapFactory.decodeStream(am.open("btn00.png"));
			menuNewOn = BitmapFactory.decodeStream(am.open("btn01.png"));
			circle = BitmapFactory.decodeStream(am.open("circle.png"));
			
			upTriangle = BitmapFactory.decodeStream(am.open("triangle.png"));
			Matrix m = new Matrix();
			m.postRotate(90, upTriangle.getWidth()/2, upTriangle.getHeight()/2);
			rightTriangle = Bitmap.createBitmap(upTriangle, 0, 0, upTriangle.getWidth(), upTriangle.getHeight(), m, false);
			m.postRotate(90, upTriangle.getWidth()/2, upTriangle.getHeight()/2);
			downTriangle = Bitmap.createBitmap(upTriangle, 0, 0, upTriangle.getWidth(), upTriangle.getHeight(), m, false);
			m.postRotate(90, upTriangle.getWidth()/2, upTriangle.getHeight()/2);
			leftTriangle = Bitmap.createBitmap(upTriangle, 0, 0, upTriangle.getWidth(), upTriangle.getHeight(), m, false);
			
			missile = BitmapFactory.decodeStream(am.open("missile_1.png"));
			missile2 = BitmapFactory.decodeStream(am.open("missile_2.png"));
			enemy[Enemy.EnemyType.AccelType.ordinal()] = BitmapFactory.decodeStream(am.open("enemy1.png"));
			enemy[Enemy.EnemyType.LeftType.ordinal()] = BitmapFactory.decodeStream(am.open("enemy2.png"));
			enemy[Enemy.EnemyType.RightType.ordinal()] = BitmapFactory.decodeStream(am.open("enemy3.png"));
			explosion = BitmapFactory.decodeStream(am.open("explosion.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void play(SoundEffect sf){
		mSoundPool.play((Integer)mSoundPoolMap.get(sf),  1f,  1f, 1, 0, 1f);
	}
	
	public MotionEvent convertEvent(MotionEvent event){
		//Log.d("ldk", "x:" + event.getX());
		MotionEvent e = MotionEvent.obtain(event);
		if(event.getPointerCount() > 1) { //multi touch
			e.setLocation(event.getX(1) * mVirtualWidth / mWidth, event.getY(1) * mVirtualHeight / mHeight);
		} else { //single touch
			e.setLocation(event.getX() * mVirtualWidth / mWidth, event.getY() * mVirtualHeight / mHeight);
		}
		//Log.d("ldk", "calculated x:" + e.getX());
		
		return e;
	}

    //getter and setter------------------------------------
	private GameView mGameView;
	private MainActivity mMainActivity;
	private long mDeltaTime;

	public GameView getmGameView() {
		return mGameView;
	}
	public void setmGameView(GameView mGameView) {
		this.mGameView = mGameView;
	}
	public MainActivity getmMainActivity() {
		return mMainActivity;
	}
	public void setmMainActivity(MainActivity mMainActivity) {
		this.mMainActivity = mMainActivity;
	}
	public long getmDeltaTime() {
		return mDeltaTime;
	}
	public void setmDeltaTime(long mDeltaTime) {
		this.mDeltaTime = mDeltaTime;
	}
	public int getmVirtualWidth() {
		return mVirtualWidth;
	}
	public void setmVirtualWidth(int mVirtualWidth) {
		this.mVirtualWidth = mVirtualWidth;
	}
	public int getmVirtualHeight() {
		return mVirtualHeight;
	}
	public void setmVirtualHeight(int mVirtualHeight) {
		this.mVirtualHeight = mVirtualHeight;
	}
    public int getmScreenWidth() {
        return mScreenWidth;
    }
    public void setmScreenWidth(int mScreenWidth) {
        this.mScreenWidth = mScreenWidth;
    }
    public int getmScreenHeight() {
        return mScreenHeight;
    }
    public void setmScreenHeight(int mScreenHeight) {
        this.mScreenHeight = mScreenHeight;
    }
	
	public float getRatioX() {
        return ratioX;
    }
    public void setRatioX(float ratioX) {
        this.ratioX = ratioX;
    }
    public float getRatioY() {
        return ratioY;
    }
    public void setRatioY(float ratioY) {
        this.ratioY = ratioY;
    }
    
    public boolean getBGM() {
    	SharedPreferences pref = mMainActivity.getSharedPreferences("ShootGame",0);
    	boolean mBGM = pref.getBoolean("BGM", true);
		
		return mBGM;
	}

	public void setBGM(boolean mIsBGM) {
		SharedPreferences pref = mMainActivity.getSharedPreferences("ShootGame",0);
		SharedPreferences.Editor edit = pref.edit();
		edit.putBoolean("BGM", mIsBGM);
		edit.commit();
		
		//this.mIsBGM = mIsBGM;
	}

	public boolean getSound() {
		SharedPreferences pref = mMainActivity.getSharedPreferences("ShootGame",0);
    	boolean mSound = pref.getBoolean("Sound", true);
		
		return mSound;
	}

	public void setSound(boolean mIsSound) {
		SharedPreferences pref = mMainActivity.getSharedPreferences("ShootGame",0);
		SharedPreferences.Editor edit = pref.edit();
		edit.putBoolean("Sound", mIsSound);
		edit.commit();
		
		//this.mIsSound = mIsSound;
	}
    //getter and setter------------------------------------
    


	public void calculateRatio(){
        this.ratioX = (float)mVirtualWidth/mScreenWidth;
        this.ratioY = (float)mVirtualHeight/mScreenHeight;
    }
    
}
