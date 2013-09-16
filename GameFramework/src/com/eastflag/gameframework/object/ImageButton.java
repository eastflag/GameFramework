package com.eastflag.gameframework.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

public class ImageButton extends Button{
    
    private Bitmap mBitmap, mBitmapOn;
    
    private Rect srcRect, dstRect;

    public ImageButton(Bitmap bitmap, Bitmap bitmapOn) {
        mBitmap = bitmap;
        mBitmapOn = bitmapOn;
        mWidth = bitmap.getWidth();
        mHeight = bitmap.getHeight();
        
        srcRect = new Rect(0, 0, mWidth, mHeight);
        dstRect = new Rect(0, 0, mWidth, mHeight);
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
        
        dstRect.set(mX, mY, mX+mWidth, mY+mHeight);
    }

    public void present(Canvas canvas) {
        if(isOn) {
            canvas.drawBitmap(mBitmapOn, srcRect, dstRect, null);
        } else {
            canvas.drawBitmap(mBitmap, srcRect, dstRect, null);
        }
    }
}
