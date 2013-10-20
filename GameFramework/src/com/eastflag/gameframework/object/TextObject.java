package com.eastflag.gameframework.object;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;

public class TextObject extends Sprite{
	protected String title;
	private Paint fontPaint;
	private int fontSize;
	
	public TextObject(String title, int fontSize, int fontColor) {
		this.title = title;
		this.fontSize = fontSize;
		fontPaint = new Paint();
		fontPaint.setTextAlign(Align.CENTER);
		fontPaint.setTextSize(fontSize);
		fontPaint.setColor(fontColor);
	}
	
	public void present(Canvas canvas) {
		canvas.drawText(title, mX+mWidth/2, mY+mHeight/2 + fontSize*0.4f, fontPaint);
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

}
