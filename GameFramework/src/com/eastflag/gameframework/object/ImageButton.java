package com.eastflag.gameframework.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

public class ImageButton extends Sprite{
	public boolean isOn;
    private Bitmap mBitmap, mBitmapMute;
    
    private Rect srcRect;

    public ImageButton(Bitmap bitmap, Bitmap bitmapMute) {
        mBitmap = bitmap;
        mBitmapMute = bitmapMute;
        mWidth = bitmap.getWidth();
        mHeight = bitmap.getHeight();
        
        srcRect = new Rect(0, 0, mWidth, mHeight);
    }

    public void present(Canvas canvas) {
        if(isOn) {
            canvas.drawBitmap(mBitmapMute, srcRect, dstRect, null);
        } else {
            canvas.drawBitmap(mBitmap, srcRect, dstRect, null);
        }
    }
    
}
