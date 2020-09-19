package com.turfgame.json;

import android.util.Log;

import com.turfgame.widget.CharStats;
import com.turfgame.widget.TurfWidget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ParseJSON {
	private static final String URL_ADDRESS = "https://api.turfgame.com/v4/users";
	private static final String DEBUG_TAG = "ParseJSON";

	public static CharStats parseJSON(String email) {
		if (email.equals("")) {
			TurfWidget.setError("noEmail");
			Log.d(DEBUG_TAG, "No email");
			return new CharStats();
		}
		if (TurfWidget.DEBUG) {
			Log.d(TurfWidget.DEBUG_STRING, "parseJSON");
			// Simulating somewhat slow network.
			try {
				Log.d(TurfWidget.DEBUG_STRING, "sleeping..");
				Thread.sleep(3000);
				Log.d(TurfWidget.DEBUG_STRING, "..done");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		JSONObject jsonPostObject = new JSONObject();

		try {
			URL url = new URL(URL_ADDRESS);
			jsonPostObject.accumulate("email", email);
			JSONArray jsonPostArray = new JSONArray();
			jsonPostArray.put(jsonPostObject);
			String jsonPostString = jsonPostArray.toString();
			String responseString = null;

			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			try {
				urlConnection.setDoInput(true);
				urlConnection.setDoOutput(true);
				urlConnection.setRequestMethod("POST");
				urlConnection.setRequestProperty("Content-Type", "application/json");
				urlConnection.setRequestProperty("Accept", "application/json");
				OutputStream os = new BufferedOutputStream((urlConnection.getOutputStream()));
				writeStream(os, jsonPostString);

				int statusCode = urlConnection.getResponseCode();
				if (statusCode == 200) {
					InputStream in = new BufferedInputStream(urlConnection.getInputStream());
					responseString = readStream(in);
				} else {
					System.out.println("Server error:" + statusCode + " " + urlConnection.getResponseMessage());
					TurfWidget.setError("server");
				}
			} finally {
				urlConnection.disconnect();
			}

			JSONArray jsonArray = new JSONArray(responseString);

			// Get first and only user from array.
			JSONObject jsonObject = jsonArray.getJSONObject(0);

			CharStats charStats = new CharStats();
			CharStats.setPoints(Integer.parseInt(jsonObject.getString("points")));
			CharStats.setHour(Integer.parseInt(jsonObject.getString("pointsPerHour")));
			// CharStats.setHour(12345); // Layout testing
			CharStats.setPlace(Integer.parseInt(jsonObject.getString("place")));
			// zones is an array, needs to be counted
			JSONArray zoneArray = jsonObject.getJSONArray("zones");
			CharStats.setZones(zoneArray.length());
			// CharStats.setZones(12); // Layout testing
			CharStats.setAccount(true);

			if (TurfWidget.DEBUG) {
				Log.d(DEBUG_TAG, "Success");
			}

			TurfWidget.setError(null);
			return charStats;
		} 
		 catch (ClientProtocolException e) {
			TurfWidget.setError("server");
			Log.e(DEBUG_TAG, "server", e);
		} catch (IOException e) {		
			TurfWidget.setError("internet");
			e.printStackTrace();
			Log.e(DEBUG_TAG, "internet", e);
		} catch (JSONException e) {
			TurfWidget.setError("json");
			Log.e(DEBUG_TAG, "JSONException", e);
		} catch (Exception e) {
			Log.e(DEBUG_TAG, "Error", e);
		}

		return new CharStats();
	}

	private static void writeStream(OutputStream os, String output) throws IOException {
		os.write(output.getBytes());
		os.flush();
	}

	private static String readStream(InputStream is) throws IOException {
		final int bufferSize = 1024;
		final char[] buffer = new char[bufferSize];
		final StringBuilder out = new StringBuilder();
		Reader in = new InputStreamReader(is, "UTF-8");
		for (; ; ) {
			int rsz = in.read(buffer, 0, buffer.length);
			if (rsz < 0)
				break;
			out.append(buffer, 0, rsz);
		}
		return out.toString();
	}
}
