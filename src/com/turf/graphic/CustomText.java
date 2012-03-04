package com.turf.graphic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;

public class CustomText 
{
	private Context mContext;
	private float scale;
	private int fontSizeSmall = 10;
	private int fontSizeBig = 13;
	
	public CustomText(Context context)
	{
		mContext = context;
		scale = mContext.getResources().getDisplayMetrics().density;
	}
	
	private Bitmap createCustomText(String text, int fontSize) {   
		Typeface tf = Typeface.createFromAsset(mContext.getAssets(),"fonts/Insanehours.ttf");
	    Paint paint = new Paint();
	    paint.setAntiAlias(true);
	    paint.setSubpixelText(true);
	    paint.setTypeface(tf);
//	    paint.setStyle(Paint.Style.FILL);
	    paint.setColor(Color.argb(255, 255, 222, 0));
	    paint.setTextSize(fontSize * scale);
	    paint.setTextAlign(Align.LEFT);

	    Paint.FontMetricsInt metrics = paint.getFontMetricsInt();
	    int height = metrics.bottom - metrics.top;

	    Rect bounds = new Rect();
	    paint.getTextBounds(text, 0, text.length(), bounds);
	    int width = bounds.right-bounds.left;

	    Bitmap myBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//	    myBitmap.eraseColor(Color.WHITE);  // Debug
	    Canvas myCanvas = new Canvas(myBitmap);
	    myCanvas.drawText(text, -bounds.left, height - metrics.bottom, paint);
	    return myBitmap;
	}

	public Bitmap createCustomPoints(int value)
	{
		String text = Integer.toString(value);
		return createCustomText(text, fontSizeBig);
	}

	
	public Bitmap createCustomHours(int value)
	{
		String text = "+" + Integer.toString(value);
		return createCustomText(text, fontSizeSmall);
	}
	
	public Bitmap createCustomZones(int value)
	{
		String text = Integer.toString(value) + "Z";
		return createCustomText(text, fontSizeSmall);
	}

	public Bitmap createCustomPlace(int value)
	{
		String text = Integer.toString(value);
		return createCustomText(text, fontSizeSmall);
	}
}