package com.turf.widget.turf;

public class CharStats
{
	private static boolean accountExist = false;
	private static int points = 0;
	private static int zones = 0;
	private static int hour = 0;
	private static int place = 0;
	private static boolean alert = false;
	private static int prevZones = 0;
	
	public static synchronized int getPoints()
	{
		return points;
	}
	public static synchronized void setPoints(int points)
	{
		CharStats.points = points;
	}
	public static synchronized int getZones()
	{
		return zones;
	}
	public static synchronized void setZones(int zones)
	{
		CharStats.zones = zones;
	}
	public static synchronized int getHour()
	{
		return hour;
	}
	public static synchronized void setHour(int hour)
	{
		CharStats.hour = hour;
	}
	public static synchronized boolean isAlert()
	{
		return alert;
	}
	public static synchronized void setAlert(boolean alert)
	{
		CharStats.alert = alert;
		if (!alert) {
			prevZones = getZones();
		}
	}
	
	public static synchronized boolean isAccount()
	{
		return accountExist;
	}
	
	public static synchronized void setAccount(Boolean account)
	{
		accountExist = account;
	}
	public static boolean getPrevZonesAlert()
	{
		if(prevZones == 0 || prevZones < getZones())
		{
			prevZones = getZones();
			return false;
		}
		
		if(prevZones > getZones())
		{
			return true;
		}
		
		return false;
	}
	
	public static void setPlace(int place)
	{
		CharStats.place = place;
	}
	public static int getPlace()
	{
		return place;
	}
}
