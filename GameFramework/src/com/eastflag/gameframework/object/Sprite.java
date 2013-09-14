package com.eastflag.gameframework.object;

import com.eastflag.gameframework.AppDirector;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {
    private Bitmap bitmap;
    private Rect srcRect;
    private Rect dstRect;
    private int mX, mY; //시작점
    private int mWidth, mHeight; //화면에 보여질 넓이, 높이
    private int frameCount, frameTime, width, height; //프레임갯수, 한프레임당 시간, 프레임 넓이, 높이
    private int currentFrame;// 현재 프레임 위치, 0부터 시작
    private long localDeltaTime;
    private boolean isRepeat;
    
    public Sprite (Bitmap bitmap){
        this.bitmap = bitmap;

        currentFrame = 0;
        srcRect = new Rect(0,0,0,0);
        localDeltaTime = 0;
        
        mWidth = bitmap.getWidth();
        mHeight = bitmap.getHeight();
    }
    
    public void init(int frameCount, int frameTime, int width, int height, boolean isRepeat) {
        this.frameCount = frameCount;
        this.frameTime = frameTime;
        this.width = width;
        this.height = height;
        mWidth = width; //최초에는 스프라이트의 넓이 높이로 초기화.
        mHeight = height;
        this.isRepeat = isRepeat;
    }
    
    public void setPosition(int x, int y) {
        mX = x;
        mY = y;
    }
    
    public void setPosition(int centerX, int centerY, int width, int height) {
        mX = centerX - width/2;
        mY = centerY - height/2;
        mWidth = width;
        mHeight = height;
    }
    
    public void update(){
        localDeltaTime += AppDirector.getInstance().getmDeltaTime();
        
        while(localDeltaTime>frameTime) {
            if(localDeltaTime>frameTime){
                currentFrame += 1;
                localDeltaTime-=frameTime;
                if(currentFrame>=frameCount){
                    if(isRepeat) currentFrame=0;
                    else currentFrame -=1;
                }
            }
        }
        
        srcRect.left =  width * currentFrame;
        srcRect.right = width * currentFrame + width;
        srcRect.top = 0;
        srcRect.bottom = height;
    }
    
    public void present(Canvas canvas){
        dstRect = new Rect(mX, mY, mX+mWidth, mY+mHeight);
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}
