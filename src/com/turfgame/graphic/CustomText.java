package com.turfgame.graphic;

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
	private int fontSizeSmall = 16;
	private int fontSizeBig = 20;
	private int lightColor = Color.argb(240, 255, 255, 255);
	private int grayColor = Color.argb(240, 200, 200, 200);
	private int yellowColor = Color.argb(255, 255, 222, 0);

	public CustomText(Context context) {
		mContext = context;
		scale = mContext.getResources().getDisplayMetrics().density;
	}

	private Bitmap createCustomText(String text, int fontSize)
	{
		Typeface tf = Typeface.createFromAsset(mContext.getAssets(),
		                                       "fonts/Insanehours2.ttf");
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setSubpixelText(true);
		paint.setTypeface(tf);
//		paint.setStyle(Paint.Style.FILL);
		paint.setColor(grayColor);
		paint.setTextSize(fontSize * scale);
		paint.setTextAlign(Align.LEFT);
		Paint.FontMetricsInt metrics = paint.getFontMetricsInt();
		int height = metrics.bottom - metrics.top;

		Rect bounds = new Rect();
		paint.getTextBounds(text, 0, text.length(), bounds);
		int width = bounds.right - bounds.left;

		Bitmap myBitmap = Bitmap.createBitmap(width, height,
		                                      Bitmap.Config.ARGB_8888);
//		myBitmap.eraseColor(Color.WHITE); // Debug
		Canvas myCanvas = new Canvas(myBitmap);
		myCanvas.drawText(text, -bounds.left, height - metrics.bottom, paint);
		
		return myBitmap;
	}

	private Bitmap createCustomTextWithSuffix(String text, String suffix, int fontSize)
	{
		Typeface tf = Typeface.createFromAsset(mContext.getAssets(),
		                                       "fonts/Insanehours2.ttf");
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setSubpixelText(true);
		paint.setTypeface(tf);
//		paint.setStyle(Paint.Style.FILL);
		paint.setColor(yellowColor);
		paint.setTextSize(fontSize * scale);
		paint.setTextAlign(Align.LEFT);
		String fulltext = text+suffix;
		Paint.FontMetricsInt metrics = paint.getFontMetricsInt();
		int height = metrics.bottom - metrics.top;

		Rect bounds = new Rect();
		paint.getTextBounds(fulltext, 0, fulltext.length(), bounds);
		int width = bounds.right - bounds.left;

		Bitmap myBitmap = Bitmap.createBitmap(width, height,
		                                      Bitmap.Config.ARGB_8888);
//		myBitmap.eraseColor(Color.WHITE); // Debug
		Canvas myCanvas = new Canvas(myBitmap);
		myCanvas.drawText(text, -bounds.left, height - metrics.bottom, paint);
		
		// P
		int posY = height - metrics.bottom;
		paint.setColor(lightColor);
		float curWidth = paint.measureText(text);
		paint.setTextSize((fontSize-2) * scale);
		myCanvas.drawText(suffix, -bounds.left + curWidth, posY, paint);
		return myBitmap;
	}

	private Bitmap createCustomTextWithPrefix(String text, String prefix, int fontSize)
	{
		String fullText = prefix + text;
		Typeface tf = Typeface.createFromAsset(mContext.getAssets(),
		                                       "fonts/Insanehours2.ttf");
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setSubpixelText(true);
		paint.setTypeface(tf);
//		paint.setStyle(Paint.Style.FILL);
		paint.setTextSize(fontSize * scale);
		paint.setTextAlign(Align.LEFT);

		Paint.FontMetricsInt metrics = paint.getFontMetricsInt();
		int height = metrics.bottom - metrics.top;

		Rect bounds = new Rect();
		paint.getTextBounds(fullText, 0, fullText.length(), bounds);
		int width = bounds.right - bounds.left;

		Bitmap myBitmap = Bitmap.createBitmap(width, height,
		                                      Bitmap.Config.ARGB_8888);
//		myBitmap.eraseColor(Color.WHITE); // Debug
		Canvas myCanvas = new Canvas(myBitmap);

		int posY = height - metrics.bottom;
		// Plus
		paint.setColor(lightColor);
		paint.setTextSize((fontSize-2) * scale);
		myCanvas.drawText(prefix, -bounds.left, posY, paint);
		float curWidth = paint.measureText(prefix);
		// Text
		paint.setColor(yellowColor);
		paint.setTextSize(fontSize * scale);
		myCanvas.drawText(text, -bounds.left + curWidth, posY, paint);

		return myBitmap;
	}
	
	public Bitmap createCustomPoints(int value)
	{
		String text = Integer.toString(value);
		return createCustomTextWithSuffix(text, "p", fontSizeBig);
	}

	public Bitmap createCustomHour(int hour)
	{
		String text = Integer.toString(hour);
		return createCustomTextWithPrefix(text, "+", fontSizeSmall);
	}
	
	public Bitmap createCustomZones(int zones)
	{
		String text = Integer.toString(zones);
		return createCustomTextWithSuffix(text, "z", fontSizeSmall);
	}

	public Bitmap createCustomPlace(int value)
	{
		String text = Integer.toString(value);
		return createCustomText(text, fontSizeSmall);
	}
}
