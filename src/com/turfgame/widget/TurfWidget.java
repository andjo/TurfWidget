package com.turfgame.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.turfgame.alarm.AppService;
import com.turfgame.alarm.OnAlarmReceiver;

public class TurfWidget extends AppWidgetProvider
{

	public static final boolean DEBUG = false;
	public static final String DEBUG_STRING = "TurfWidget DEBUG";
	public static final String PREFS_UPDATE = "com.turfgame.widget.PREFS_UPDATE";
	public static final String WIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE";
	public static final String TURF_LAUNCH = "com.turfgame.widget.TURF_LAUNCH";
	public static final String RESET_ALERT = "com.turfgame.widget.RESET_ALERT";

	public static String ERROR = null;

	@Override
	public void onEnabled(Context context)
	{
		if (DEBUG) {
			Log.v(DEBUG_STRING, "onEnabled");
		}

		super.onEnabled(context);
	}

	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
	                     int[] appWidgetIds)
	{
		if (DEBUG) {
			Log.v(DEBUG_STRING, "onUpdate");
		}
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	public void onReceive(Context context, Intent intent)
	{
		if (DEBUG) {
			Log.v(DEBUG_STRING, "onReceive");
		}

		if (DEBUG) {
			Log.v("onReceive", intent.getAction());
		}

		if (PREFS_UPDATE.equals(intent.getAction()) ||
		    WIDGET_UPDATE.equals(intent.getAction())) {

			Intent serviceIntent = new Intent(context, AppService.class);
			serviceIntent.putExtra("vibrate", false);
			serviceIntent.putExtra("kill", false);
			context.startService(serviceIntent);

			return;
		} else if (TURF_LAUNCH.equals(intent.getAction())) {

			AppService.resetAlert(context);
			// FIXME: Just re-bind event handlers after updateAppWidget in
			// resetAlert().
			Intent serviceIntent = new Intent(context, AppService.class);
			serviceIntent.putExtra("vibrate", false);
			serviceIntent.putExtra("kill", false);
			context.startService(serviceIntent);

			startTurf(context);

			return;
		} else if (RESET_ALERT.equals(intent.getAction())) {
			
			AppService.resetAlert(context);
			// FIXME: Just re-bind event handlers after updateAppWidget in
			// resetAlert().
			Intent serviceIntent = new Intent(context, AppService.class);
			serviceIntent.putExtra("vibrate", false);
			serviceIntent.putExtra("kill", false);
			context.startService(serviceIntent);
		}
		super.onReceive(context, intent);
	}

	@TargetApi(11)
	private void startTurf(Context context)
	{
		String turfPackageName = "se.turfwars.android";
		Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(turfPackageName);
		if (launchIntent == null) {
			turfPackageName = "com.andrimon.turf";
			launchIntent = context.getPackageManager().getLaunchIntentForPackage(turfPackageName);
		}	
		if (launchIntent == null) {
			turfPackageName = "com.andrimon.turf.android";
			launchIntent = context.getPackageManager().getLaunchIntentForPackage(turfPackageName);
		}

		if (launchIntent == null) {
			Toast.makeText(context,
			               "Please install the game Turf to use this function",
			               Toast.LENGTH_LONG).show();
			return;
		}

		// moveTaskToFront is available from API level 11.
		if (Build.VERSION.SDK_INT >= 11) {
			ActivityManager am = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
			java.util.List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(17);
            for (ActivityManager.RunningTaskInfo ti : list) {
                if (ti.topActivity.getPackageName().equals(turfPackageName) &&
                    ti.numRunning > 0) {
                    am.moveTaskToFront(ti.id, 0);
                    return;
                }
            }
		}

		// Older version, or Turf is not running.
		context.startActivity(launchIntent);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds)
	{

		if (DEBUG) {
			Log.v(DEBUG_STRING, "onDeleted");
		}

        // Broadcast to onAlarm so that service is terminated.
		Intent closeService = new Intent(context, OnAlarmReceiver.class);
		closeService.setAction(OnAlarmReceiver.DELETE_WIDGET_SERVICE);
		context.sendBroadcast(closeService);

		super.onDeleted(context, appWidgetIds);
	}

	public void onDisable(Context context)
	{
		if (DEBUG) {
			Log.v(DEBUG_STRING, "onDisable");
		}
		super.onDisabled(context);
	}

	public static synchronized void setError(String error)
	{
		ERROR = error;
	}

	public static synchronized String getError()
	{
		return ERROR;
	}
}
