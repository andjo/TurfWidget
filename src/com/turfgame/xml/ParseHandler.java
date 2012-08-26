package com.turfgame.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.turfgame.widget.CharStats;

public class ParseHandler extends DefaultHandler
{

	private CharStats charStats = new CharStats();

	public CharStats getCharStats()
	{
		return this.charStats;
	}

	@Override
	public void startDocument() throws SAXException
	{
		this.charStats = new CharStats();
	}

	@Override
	public void startElement(String namespaceURI, String localName,
	                         String qName, Attributes atts) throws SAXException
	{
		if (localName.equals("user")) {

			// Extract stats
			String attrValuePoints = atts.getValue("points");
			String attrValueZones = atts.getValue("zones");
			String attrValueHour = atts.getValue("hour");
			String attrValuePlace = atts.getValue("place");

//			attrValueZones = (int)(Math.random() * 5) + "";
			
			CharStats.setPoints(Integer.parseInt(attrValuePoints));
			CharStats.setZones(Integer.parseInt(attrValueZones));
			CharStats.setHour(Integer.parseInt(attrValueHour));
			CharStats.setPlace(Integer.parseInt(attrValuePlace));
			CharStats.setAccount(true);
		}
	}

}