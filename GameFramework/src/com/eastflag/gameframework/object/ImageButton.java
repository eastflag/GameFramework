package com.eastflag.gameframework.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

public class ImageButton extends Sprite{
    
    private Bitmap mBitmap, mBitmapOn;
    
    private Rect srcRect;

    public ImageButton(Bitmap bitmap, Bitmap bitmapOn) {
        mBitmap = bitmap;
        mBitmapOn = bitmapOn;
        mWidth = bitmap.getWidth();
        mHeight = bitmap.getHeight();
        
        srcRect = new Rect(0, 0, mWidth, mHeight);
    }

    public void present(Canvas canvas) {
        if(isOn) {
            canvas.drawBitmap(mBitmapOn, srcRect, dstRect, null);
        } else {
            canvas.drawBitmap(mBitmap, srcRect, dstRect, null);
        }
    }
}
