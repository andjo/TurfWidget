package com.turf.alarm;

import com.turf.widget.turf.TurfWidget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class OnAlarmReceiver extends BroadcastReceiver {
	
	public static final String UPDATE_WIDGET_SERVICE = "service.APPWIDGET_UPDATE";
	public static final String DELETE_WIDGET_SERVICE = "service.APPWIDGET_DELETE";
	public static AlarmManager alarmManager;
	public static PendingIntent alarmPedningIntent;
	
	private static Intent serviceIntent;
	@Override
	public void onReceive(Context context, Intent intent) {		
		
		if(TurfWidget.DEBUG){Log.v("OnAlarmReceiver", "Alarm? " + intent.getAction());}
		if(UPDATE_WIDGET_SERVICE.equals(intent.getAction()))
		{		
			serviceIntent = new Intent(context, AppService.class);
			serviceIntent.putExtra("vibrate", true);
			serviceIntent.putExtra("kill", false);
			context.startService(serviceIntent);
		}
		else if(DELETE_WIDGET_SERVICE.equals(intent.getAction()))
		{
			if(alarmManager != null)
			{
				if(TurfWidget.DEBUG) {Log.e("Stop:", "Alarm");}
				alarmPedningIntent.cancel();
				alarmManager.cancel(alarmPedningIntent);	
				serviceIntent = new Intent(context, AppService.class);
				serviceIntent.putExtra("kill", true);
				serviceIntent.putExtra("vibrate", false);
				context.startService(serviceIntent);
			}	
		}	
	}
}
