package edu.gmu.c2sim.utils;

import java.awt.geom.Point2D;

import org.geotools.referencing.GeodeticCalculator;
import org.geotools.referencing.crs.DefaultGeographicCRS;

import edu.gmu.c2sim.core.geo.SimCoordinate;

public class GeoUtils {

	public static boolean isSamePosition(SimCoordinate pos1, SimCoordinate pos2, double hor_error, double vert_error) {
		boolean result = false;

		double alt1 = pos1.getAltitude();
		double alt2 = pos2.getAltitude();

		double vert_diff = Math.abs(alt1 - alt2);
		double horiz_diff = calculateHDistance(pos1, pos2);

		if (vert_diff <= vert_error) {
			// double horiz_diff = calculateHorizontalDistanceMeters(pos1, pos2);
			if (horiz_diff <= hor_error)
				result = true;
		}

		return result;
	};

	public static double calculateHDistance(SimCoordinate p1, SimCoordinate p2) {
		// p1 = φ1,λ1, p2= φ2,λ2
		/*double φ1 = p1.getLatitude();
		double λ1 = p1.getLongitude();

		double φ2 = p2.getLatitude();
		double λ2 = p2.getLongitude();

		DefaultGeographicCRS crs = DefaultGeographicCRS.WGS84;
		GeodeticCalculator calc = new GeodeticCalculator(crs);

		calc.setStartingGeographicPoint(λ1, φ1);
		calc.setDestinationGeographicPoint(λ2, φ2);
		
		double dist =calc.getOrthodromicDistance(); 
		return dist;*/
		
		double dist = 0;
		double lat1 = p1.getLatitude();
		double lon1 = p1.getLongitude();
		double lat2 = p2.getLatitude();
		double lon2 = p2.getLongitude();

		// distance between latitudes and longitudes
		double dLat = Math.toRadians(lat2 - lat1);// var Δφ = (lat2-lat1).toRadians();
		double dLon = Math.toRadians(lon2 - lon1); // var Δλ = (lon2-lon1).toRadians();

		// convert to radians
		lat1 = Math.toRadians(lat1); // var φ1 = lat1.toRadians();
		lat2 = Math.toRadians(lat2); // var φ2 = lat2.toRadians();

		// Math.sin(Δφ/2) * Math.sin(Δφ/2) +
		double aJ = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
		// Math.cos(φ1) * Math.cos(φ2) *
				Math.cos(lat1) * Math.cos(lat2) *
				// Math.sin(Δλ/2) * Math.sin(Δλ/2);
						Math.sin(dLon / 2) * Math.sin(dLon / 2);
		// var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double cJ = 2 * Math.atan2(Math.sqrt(aJ), Math.sqrt(1 - aJ));
		double radius = 6371e3; // (Mean) radius of earth

		dist = radius * cJ;

		return dist;

	}
	
	public static double bearing(SimCoordinate p1, SimCoordinate p2) {
		// p1 = φ1,λ1, p2= φ2,λ2
	/*double φ1 = p1.getLatitude();
		double λ1 = p1.getLongitude();

		double φ2 = p2.getLatitude();
		double λ2 = p2.getLongitude();

		DefaultGeographicCRS crs = DefaultGeographicCRS.WGS84;
		GeodeticCalculator calc = new GeodeticCalculator(crs);

		calc.setStartingGeographicPoint(λ1, φ1);
		calc.setDestinationGeographicPoint(λ2, φ2);

		double brng = calc.getAzimuth();

		return brng;*/
		
		double bearing = 0;

		GeodeticCalculator calc = new GeodeticCalculator();
		double long1 = p1.getLongitude();
		double lat1 = p1.getLatitude();
		
		//calc.setStartingGeographicPoint(lat1,long1);
		calc.setStartingGeographicPoint(long1,lat1);
		
		double long2 = p2.getLongitude();
		double lat2 = p2.getLatitude();
	
		//calc.setDestinationGeographicPoint(lat2,long2);
		calc.setDestinationGeographicPoint(long2,lat2);
		bearing = calc.getAzimuth();

		return bearing;
	}
	
	public static SimCoordinate newPosition(SimCoordinate p1, double distance_mt_horiz,
			double distance_mt_vertical, double bearing) {
		/*double φ1 = p1.getLatitude();
		double λ1 = p1.getLongitude();

		DefaultGeographicCRS crs = DefaultGeographicCRS.WGS84;
		GeodeticCalculator calc = new GeodeticCalculator(crs);

		calc.setStartingGeographicPoint(λ1, φ1);
		calc.setDirection(bearing, distance_mt_horiz);
		
		Point2D dest = calc.getDestinationGeographicPoint();
		
		
		double z = p1.getAltitude();
		z = z+distance_mt_vertical;
		
		SimCoordinate result = new SimCoordinate(dest.getY(), dest.getX(),z); 
		
		return result;*/
		
		/**
		 * https://stackoverflow.com/questions/19352921/how-to-use-direction-angle-and-speed-to-calculate-next-times-latitude-and-longi
		 */
		// var radius = 6371e3; // (Mean) radius of earth
		double radius = 6371e3; // (Mean) radius of earth

		// sinφ2 = sinφ1·cosδ + cosφ1·sinδ·cosθ
		// tanΔλ = sinθ·sinδ·cosφ1 / cosδ−sinφ1·sinφ2
		// see mathforum.org/library/drmath/view/52049.html for derivation

		// var φ1 = toRadians(Number(lat));
		double lat_1_radians = Math.toRadians(p1.getLatitude());

		// var sinφ1 = Math.sin(φ1), cosφ1 = Math.cos(φ1);
		double var_sin_lat_1 = Math.sin(lat_1_radians);
		double var_cos_lat_1 = Math.cos(lat_1_radians);

		// var λ1 = toRadians(Number(lon));
		double long_1_radians = Math.toRadians(p1.getLongitude());

		// var δ = Number(distance) / radius; // angular distance in radians
		double ang_dist_radians = distance_mt_horiz / radius;

		// var sinδ = Math.sin(δ), cosδ = Math.cos(δ);
		double var_sin_ang_dist = Math.sin(ang_dist_radians);
		double var_cos_ang_dist = Math.cos(ang_dist_radians);

		// var θ = toRadians(Number(bearing));
		double bearing_radians = Math.toRadians(bearing);

		// var sinθ = Math.sin(θ), cosθ = Math.cos(θ);
		double var_sin_bear_radians = Math.sin(bearing_radians);
		double var_cos_bear_radians = Math.cos(bearing_radians);

		// var sinφ2 = sinφ1*cosδ + cosφ1*sinδ*cosθ;
		double var_sin_lat2 = var_sin_lat_1 * var_cos_ang_dist
				+ var_cos_lat_1 * var_sin_ang_dist * var_cos_bear_radians;

		// var φ2 = Math.asin(sinφ2);
		double lat2 = Math.asin(var_sin_lat2);

		// var y = sinθ * sinδ * cosφ1;
		double y = var_sin_bear_radians * var_sin_ang_dist * var_cos_lat_1;

		// var x = cosδ - sinφ1 * sinφ2;
		double x = var_cos_ang_dist - var_sin_lat_1 * var_sin_lat2;

		// var λ2 = λ1 + Math.atan2(y, x);
		double long2 = long_1_radians + Math.atan2(y, x);

		// return [toDegrees(φ2), (toDegrees(λ2)+540)%360-180]; // normalise to
		// −180..+180°

		double final_alt = p1.getAltitude() + distance_mt_vertical;
		SimCoordinate result = new SimCoordinate(Math.toDegrees(lat2), ((Math.toDegrees(long2) + 540) % 360 - 180),
				final_alt);

		return result;
	}
	

}
