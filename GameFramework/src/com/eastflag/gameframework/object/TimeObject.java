package com.eastflag.gameframework.object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

public class TimeObject extends TextObject {
    private long mTotalTime;
	
    public TimeObject(String title, int fontSize, int fontColor){
        super(title, fontSize, fontColor);
        
        mTotalTime = 0;
    }
    
    public void update(){
        mTotalTime += mAppDirector.getmDeltaTime();
        
        setDisplayTime();
    }
    
    private void setDisplayTime(){
    	long minute = mTotalTime/1000/60;
        long second = mTotalTime/1000%60;
        long ms = mTotalTime%1000;
        title = String.format("%02d:%02d:%03d", minute, second, ms);
    }
    
}
