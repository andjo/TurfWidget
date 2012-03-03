package com.turf.graphic;

import java.util.ArrayList;

import com.turf.widget.turf.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class CustomText 
{
	private Context mContext;
	private int standardHeightS = 26;
	private int standardHeightB = 30;
	private ArrayList<Bitmap> ImageUsed;

	private int IntArrayB[] = {
			R.drawable.b_0,
			R.drawable.b_1,
			R.drawable.b_2,
			R.drawable.b_3,
			R.drawable.b_4,
			R.drawable.b_5,
			R.drawable.b_6,
			R.drawable.b_7,
			R.drawable.b_8,
			R.drawable.b_9};
	
	private int IntArrayS[] = {
			R.drawable.s_0,
			R.drawable.s_1,
			R.drawable.s_2,
			R.drawable.s_3,
			R.drawable.s_4,
			R.drawable.s_5,
			R.drawable.s_6,
			R.drawable.s_7,
			R.drawable.s_8,
			R.drawable.s_9};	

	

	public CustomText(Context context)
	{
		mContext = context;
	}

	public Bitmap createCustomPoints(int value)
	{
		ImageUsed = new ArrayList<Bitmap>();			
		int width, height, floatLeft;
		String intString = Integer.toString(value);
		width = 0;
		for(int i = 0; i < intString.length(); i++)
		{	
			String tempChar = Character.toString(intString.charAt(i));
			int tempInt = Integer.valueOf(tempChar);
			Bitmap drawBit = BitmapFactory.decodeResource(mContext.getResources(), IntArrayB[tempInt]);
			width += drawBit.getWidth();
			ImageUsed.add(drawBit);
		}

		height = standardHeightB;   

		Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
		Canvas c = new Canvas(bmp);
		Paint paint = new Paint();

		floatLeft = 0;
		for(int i = 0; i < ImageUsed.size(); i++)
		{				    	
			c.drawBitmap(ImageUsed.get(i), floatLeft, 0, paint);
			floatLeft += ImageUsed.get(i).getWidth();
		}

		return bmp;
	}
	
	public Bitmap createCustomHours(int value)
	{
		ImageUsed = new ArrayList<Bitmap>();
		Bitmap plus = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.s_plus);
		int width, height, floatLeft;
		String intString = Integer.toString(value);
		width = 0;
		for(int i = 0; i < intString.length(); i++)
		{	
			String tempChar = Character.toString(intString.charAt(i));
			int tempInt = Integer.valueOf(tempChar);
			Bitmap drawBit = BitmapFactory.decodeResource(mContext.getResources(), IntArrayS[tempInt]);
			width += drawBit.getWidth();
			ImageUsed.add(drawBit);
		}
		width += plus.getWidth();
		height = standardHeightS;   

		Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
		Canvas c = new Canvas(bmp);
		Paint paint = new Paint();

		floatLeft = 0;
		c.drawBitmap(plus, floatLeft, 0, paint);
		floatLeft = plus.getWidth();
		for(int i = 0; i < ImageUsed.size(); i++)
		{				    	
			c.drawBitmap(ImageUsed.get(i), floatLeft, 0, paint);
			floatLeft += ImageUsed.get(i).getWidth();
		}
		
		return bmp;
	}
	
	public Bitmap createCustomZones(int value)
	{
		ImageUsed = new ArrayList<Bitmap>();
		Bitmap zone = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.s_z);
		
		int width, height, floatLeft;
		String intString = Integer.toString(value);
		width = 0;
		for(int i = 0; i < intString.length(); i++)
		{	
			String tempChar = Character.toString(intString.charAt(i));
			int tempInt = Integer.valueOf(tempChar);
			Bitmap drawBit = BitmapFactory.decodeResource(mContext.getResources(), IntArrayS[tempInt]);
			width += drawBit.getWidth();
			ImageUsed.add(drawBit);
		}
		width += zone.getWidth();
		
		
		height = standardHeightS;   

		Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
		Canvas c = new Canvas(bmp);
		Paint paint = new Paint();

		floatLeft = 0;
		for(int i = 0; i < ImageUsed.size(); i++)
		{				    	
			c.drawBitmap(ImageUsed.get(i), floatLeft, 0, paint);
			floatLeft += ImageUsed.get(i).getWidth();
		}
		c.drawBitmap(zone, floatLeft, 0, paint);
		return bmp;
	}

	public Bitmap createCustomPlace(int value)
	{
		ImageUsed = new ArrayList<Bitmap>();			
		int width, height, floatLeft;
		String intString = Integer.toString(value);
		width = 0;
		for(int i = 0; i < intString.length(); i++)
		{	
			String tempChar = Character.toString(intString.charAt(i));
			int tempInt = Integer.valueOf(tempChar);
			Bitmap drawBit = BitmapFactory.decodeResource(mContext.getResources(), IntArrayS[tempInt]);
			width += drawBit.getWidth();
			ImageUsed.add(drawBit);
		}

		height = standardHeightS;   

		Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
		Canvas c = new Canvas(bmp);
		Paint paint = new Paint();

		floatLeft = 0;
		for(int i = 0; i < ImageUsed.size(); i++)
		{				    	
			c.drawBitmap(ImageUsed.get(i), floatLeft, 0, paint);
			floatLeft += ImageUsed.get(i).getWidth();
		}

		return bmp;
	}
}