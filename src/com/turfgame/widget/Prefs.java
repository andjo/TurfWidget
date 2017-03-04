package com.turfgame.widget;

import com.turfgame.widget.R;

//import android.accounts.Account;
//import android.accounts.AccountManager;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.widget.RemoteViews;

public class Prefs extends PreferenceActivity
{
	private static final String OPT_VIBRATE = "vibrate";
	private static final boolean OPT_VIBRATE_DEF = true;

	private static final String OPT_NOTIFY = "notify";
	private static final boolean OPT_NOTIFY_DEF = true;
	
	private static final String OPT_LAYOUT = "layout";
	private static final String OPT_LAYOUT_DEF = "widget";

	private static final String OPT_USER_EMAIL = "user_email";
	private static String OPT_USER_EMAIL_DEF = "";

	private static final String OPT_UPDATE_FREQ = "update_freq";
	private static final String OPT_UPDATE_FREQ_DEF = "1800000";

	private static String CONFIGURE_ACTION = "android.appwidget.action.APPWIDGET_CONFIGURE";

    @Override
    protected boolean isValidFragment (String fragmentName)
    {
        // Log.d(TurfWidget.DEBUG_STRING, "********** isValidFragment called *************");
        if(Prefs.class.getName().equals(fragmentName))
            return true;
        return false;
    }

	@SuppressWarnings("deprecation") // TODO Fix warning
    @Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Context context = getBaseContext();
		if (getUserEmail(context).equals("")) {
			setUserEmail(getPhoneEmail(context), context);
		}
		addPreferencesFromResource(R.xml.preferences);
	}

	private static String getPhoneEmail(Context context)
	{
		String mail = "";
//		boolean hasAccount = false;
//		AccountManager accountManager = AccountManager.get(context);
//		for (Account a : accountManager.getAccounts()) {
//			if (a.type.equals("com.google")) {
//				mail = a.name;
//				hasAccount = true;
//				break;
//			}
//		}

		return mail;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			onBackPressed();
		}

		return (super.onKeyDown(keyCode, event));
	}

	@Override
	public void onBackPressed()
	{
		if (CONFIGURE_ACTION.equals(getIntent().getAction())) {
			Intent intent = getIntent();
			Bundle extrasBundle = intent.getExtras();

			if (extrasBundle != null) {
				int id = extrasBundle.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
				                             AppWidgetManager.INVALID_APPWIDGET_ID);
				AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
				RemoteViews views = new RemoteViews(getPackageName(),
				                                    R.layout.widget);

				widgetManager.updateAppWidget(id, views);

				Intent result = new Intent();

				result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
				setResult(RESULT_OK, result);
			}
		}
		Intent update = new Intent(this, TurfWidget.class);
		update.setAction(TurfWidget.PREFS_UPDATE);
		sendBroadcast(update);
		super.onBackPressed();
	}

	public static boolean getVibrate(Context context)
	{
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(OPT_VIBRATE,
		                                                                         OPT_VIBRATE_DEF);
	}

	public static boolean getNotify(Context context)
	{
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(OPT_NOTIFY,
		                                                                         OPT_NOTIFY_DEF);
	}
	
	public static String getLayout(Context context)
	{
		return PreferenceManager.getDefaultSharedPreferences(context).getString(OPT_LAYOUT,
		                                                                        OPT_LAYOUT_DEF);
	}

	public static String getUserEmail(Context context)
	{
		return PreferenceManager.getDefaultSharedPreferences(context).getString(OPT_USER_EMAIL,
		                                                                        OPT_USER_EMAIL_DEF);
	}

	public static void setUserEmail(String email, Context context)
	{
		Editor editSound = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editSound.putString(OPT_USER_EMAIL, email);
		editSound.commit();
	}

	public static String getUpdateFreq(Context context)
	{
        return PreferenceManager.getDefaultSharedPreferences(context).getString(OPT_UPDATE_FREQ,
                                                                                OPT_UPDATE_FREQ_DEF);
    }

}
