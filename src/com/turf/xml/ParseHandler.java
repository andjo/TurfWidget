package com.turf.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//import android.util.Log;

import com.turf.widget.turf.CharStats;


public class ParseHandler extends DefaultHandler{

//	private boolean widget_tag = false;
	private CharStats charStats = new CharStats();
	

	public CharStats getCharStats() {
		return this.charStats;
	}

	@Override
	public void startDocument() throws SAXException {
		this.charStats = new CharStats();
	}

	@Override
	public void endDocument() throws SAXException {
		// Nothing to do
	}

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException 
	{
		if(atts.getLength() != 0)
		{
			if(localName.equals("widget"))
			{
				//widget_tag = true;
			}
			else if (localName.equals("stats"))
			{
				//Extract stats
				
				String attrValuePoints = atts.getValue("points");
				String attrValueZones = atts.getValue("zones");
				String attrValueHour = atts.getValue("hour");
				String attrValueAlert = atts.getValue("alerts");
				String attrValuePlace = atts.getValue("place");
				CharStats.setPoints(Integer.parseInt(attrValuePoints));
				CharStats.setZones(Integer.parseInt(attrValueZones));
				CharStats.setHour(Integer.parseInt(attrValueHour));
				CharStats.setPlace(Integer.parseInt(attrValuePlace));
				CharStats.setAccount(true);
				CharStats.setAlert(attrValueAlert);
			}
		}
		else
		{
			CharStats.setAccount(false);
		}

	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	throws SAXException {
		if(localName.equals("widget"))
		{
			//widget_tag = true;
		}
		else if (localName.equals("stats"))
		{
			//Do nothing here
		}
	}
}