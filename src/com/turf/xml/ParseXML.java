package com.turf.xml;
//Guide http://www.anddev.org/novice-tutorials-f8/parsing-xml-from-the-net-using-the-saxparser-t353.html

import java.net.ConnectException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.turf.widget.turf.CharStats;
import com.turf.widget.turf.TurfWidget;

import android.util.Log;

public class ParseXML
{
	private static final String URL_ADRESS = "http://www.turfa.se/thirdparty/user.php/";
	private static final String DEBUG_TAG = "ParseXML";
	
	public static CharStats parseXML(String email)
	{
		try
		{
			URL url = new URL(URL_ADRESS + email);
			
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			SAXParser saxParser = parserFactory.newSAXParser();
			
			XMLReader reader = saxParser.getXMLReader();
			
			ParseHandler parseHandler = new ParseHandler();
			reader.setContentHandler(parseHandler);
			
			reader.parse(new InputSource(url.openStream()));
			
			CharStats charStats = parseHandler.getCharStats();
			
			TurfWidget.setError(null);
			return charStats;
		}
		catch (UnknownHostException e) {
			TurfWidget.setError("internet");

			//Log.e(DEBUG_TAG, "XMLQueryErrror?", e);
		}
		catch (ConnectException e)
		{
			TurfWidget.setError("server");

			//Log.e(DEBUG_TAG, "XMLQueryErrror?", e);
		}
		catch (Exception e)
		{
			Log.e(DEBUG_TAG, "XMLQueryErrror?", e);
		}	

		return new CharStats();
	}
}
