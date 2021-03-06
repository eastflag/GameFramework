package com.eastflag.gameframework.object;

import com.eastflag.gameframework.AppDirector;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class SpriteAnimation extends Sprite {
    private Bitmap bitmap;
    private Rect srcRect;
    private int frameCount, frameTime, width, height; //프레임갯수, 한프레임당 시간, 프레임 넓이, 높이
    private int currentFrame;// 현재 프레임 위치, 0부터 시작
    private long localDeltaTime;
    private int isRepeat; //1: 반복, 2: 반복안함, 계속 그림, 3: 반복안함, 삭제
    
    public SpriteAnimation (Bitmap bitmap){
        this.bitmap = bitmap;

        currentFrame = 0;
        srcRect = new Rect(0,0,0,0);
        localDeltaTime = 0;
    }
    
    public void init(int frameCount, int frameTime, int width, int height, int isRepeat) {
        this.frameCount = frameCount;
        this.frameTime = frameTime;
        this.width = width;
        this.height = height;
        this.isRepeat = isRepeat;
    }
    
    public void update(){
    	if(frameCount == 0) {  //정지 영상이면 update 안함.
    		return;
    	}
    	
        localDeltaTime += AppDirector.getInstance().getmDeltaTime();
        
        while(localDeltaTime>frameTime) {
            if(localDeltaTime>frameTime){
                currentFrame += 1;
                localDeltaTime-=frameTime;
                if(currentFrame>=frameCount){
                    if(isRepeat == 0) currentFrame=0;
                    else if(isRepeat == 1) currentFrame -=1;
                    else mIsDead = true;
                }
            }
        }
        
        srcRect.left =  width * currentFrame;
        srcRect.right = width * currentFrame + width;
        srcRect.top = 0;
        srcRect.bottom = height;
    }
    
    public void present(Canvas canvas){
    	if(!mIsDead)
    		canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
    
}
