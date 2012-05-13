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
		paint.setColor(Color.argb(255, 255, 222, 0));
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

	private Bitmap createCustomTextHourZones(String text, int fontSize)
	{
		String prefix = "+";
		String sufix = "Z";
		String fullText = prefix + text + sufix;
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
		paint.setColor(Color.argb(204, 255, 255, 255));
		myCanvas.drawText(prefix, -bounds.left, posY, paint);
		float curWidth = paint.measureText(prefix);
		// Text
		paint.setColor(Color.argb(255, 255, 222, 0));
		myCanvas.drawText(text, -bounds.left + curWidth, posY, paint);
		curWidth = paint.measureText(prefix + text);
		// Z
		paint.setColor(Color.argb(204, 255, 255, 255));
		myCanvas.drawText(sufix, -bounds.left + curWidth, posY, paint);

		return myBitmap;
	}

	public Bitmap createCustomPoints(int value)
	{
		String text = Integer.toString(value);
		return createCustomText(text, fontSizeBig);
	}

	public Bitmap createCustomHourZones(int hour, int zones)
	{
		String text = Integer.toString(hour) + " " + Integer.toString(zones);
		return createCustomTextHourZones(text, fontSizeSmall);
	}

	public Bitmap createCustomPlace(int value)
	{
		String text = Integer.toString(value);
		return createCustomText(text, fontSizeSmall);
	}
}
