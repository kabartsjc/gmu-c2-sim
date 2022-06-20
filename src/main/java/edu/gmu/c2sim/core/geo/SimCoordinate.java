package edu.gmu.c2sim.core.geo;

import java.text.DecimalFormat;

import org.apache.commons.text.StringTokenizer;
import org.jxmapviewer.viewer.GeoPosition;

public class SimCoordinate extends GeoPosition{
	private static final long serialVersionUID = 486498134090524906L;
	private double altitude=0.0;

	public SimCoordinate(double latitude, double longitude, double altitude) {
		super(latitude, longitude);
		if (altitude >0.0)
			this.altitude=altitude;
	}
	
	public SimCoordinate(double latitude, double longitude) {
		super(latitude, longitude);
	}
	
	public static SimCoordinate createCoordinate(String coord) throws NullPointerException , NumberFormatException{
		StringTokenizer str = new StringTokenizer(coord, ",");
		double latitude = Double.parseDouble(str.next());
		double longitude = Double.parseDouble(str.next());
		SimCoordinate pos = new SimCoordinate(latitude,longitude);
		
		if (str.hasNext()) {
			double altitude = Double.parseDouble(str.next());
			pos.setAltitude(altitude);
			
		}
		return pos;
	}
	
	public double getAltitude() {
		return altitude;
	}
	
	public void setAltitude(double alt) {
		this.altitude=alt;
	}
	
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.000");
		
		String latit = df.format(getLatitude());
		String longit = df.format(getLongitude());
		String altit = df.format(getAltitude());
		return latit+","+longit+","+altit;
	}
	
	public static String convertToString(GeoPosition geo) {
		DecimalFormat df = new DecimalFormat("0.000");
		String latit = df.format(geo.getLatitude());
		String longit = df.format(geo.getLongitude());
		return latit+","+longit;
	}
	
	
	public  static String convertToString(SimCoordinate geo) {
		DecimalFormat df = new DecimalFormat("0.000");
		String latit = df.format(geo.getLatitude());
		String longit = df.format(geo.getLongitude());
		return latit+","+longit;
	}
	
	public GeoPosition convert() {
		return (GeoPosition)this;
	}

}
