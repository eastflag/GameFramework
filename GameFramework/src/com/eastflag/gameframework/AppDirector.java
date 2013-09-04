package com.eastflag.gameframework;

import android.app.Application;

public class AppDirector extends Application {
	//가상 디바이스 : 
	private int mVirtualWidth = 480;
	private int mVirtualHeight = 800;
	
	private int mScreenWidth;
	private int mScreenHeight;
	
	private float ratioX;
	private float ratioY;
	
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
