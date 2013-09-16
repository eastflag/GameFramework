package com.eastflag.gameframework;

import java.io.IOException;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

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
	
	//리소스
	public Bitmap background1, background2, back_cloud;
	public Bitmap player;
	public Bitmap menuNew, menuNewOn;
	
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
		
		//리소스 로딩
		AssetManager am = mMainActivity.getAssets();
		try {
			background1 = BitmapFactory.decodeStream(am.open("background1.png"));
			background2 = BitmapFactory.decodeStream(am.open("background2.jpg"));
			back_cloud = BitmapFactory.decodeStream(am.open("background_2.png"));
			player = BitmapFactory.decodeStream(am.open("player.png"));
			menuNew = BitmapFactory.decodeStream(am.open("btn00.png"));
			menuNewOn = BitmapFactory.decodeStream(am.open("btn01.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public MotionEvent convertEvent(MotionEvent event){
		Log.d("ldk", "x:" + event.getX());
		MotionEvent e = MotionEvent.obtain(event);
		e.setLocation(event.getX() * mVirtualWidth / mWidth, event.getY() * mVirtualHeight / mHeight);
		Log.d("ldk", "calculated x:" + e.getX());
		
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
    //getter and setter------------------------------------
    
    public void calculateRatio(){
        this.ratioX = (float)mVirtualWidth/mScreenWidth;
        this.ratioY = (float)mVirtualHeight/mScreenHeight;
    }
    
}
