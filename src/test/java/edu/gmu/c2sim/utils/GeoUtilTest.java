package edu.gmu.c2sim.utils;

import static org.junit.Assert.*;


import edu.gmu.c2sim.core.geo.SimCoordinate;

import org.junit.Test;

public class GeoUtilTest {

	// P1 - 53°19′14″N, 001°43′47″W
	private SimCoordinate pos1 = new SimCoordinate(53.32055556, 1.72972222, 0.0f);

	// P2 - 57°38′38″N , 003°04′12″W
	private SimCoordinate pos2 = new SimCoordinate(57.64388889, 3.07000000, 0.0f);

	SimCoordinate pos3 = new SimCoordinate(39.099912, -94.581213, 0);
	SimCoordinate pos4 = new SimCoordinate(38.627089, -90.200203, 0);

	@Test
	public void testSamePosition() {
		boolean result = GeoUtils.isSamePosition(pos1, pos1, 0, 0);
		assertEquals(true, result);
	}

	@Test
	public void testDistance() {
		// p1-p2 = 488.1km
		double result = GeoUtils.calculateHDistance(pos1, pos2) / 1000;
		assertEquals(488.100, result, 1);
	}

	@Test
	public void testBearing() {
		// bearing(p1-p2) =350.58638889f 350° 35′ 11″
		double bearing = GeoUtils.bearing(pos3, pos4);
		assertEquals(96.51, bearing, 0.1);

		bearing = GeoUtils.bearing(pos1, pos2);
		assertEquals(9.58, bearing, 1);
	}

	@Test
	public void testNewPosition() {
		SimCoordinate npos = GeoUtils.newPosition(pos1, 20000, 100, 126);
		assertEquals(npos.getLatitude(), 53.21, 0.1);
		assertEquals(npos.getLongitude(), 1.97, 0.1);
		assertEquals(npos.getAltitude(), 100.0, 1);

		npos = GeoUtils.newPosition(pos3, 20000, 100, 126);
		assertEquals(npos.getLatitude(), 38.99, 0.1);
		assertEquals(npos.getLongitude(), -94.39, 0.1);
		assertEquals(npos.getAltitude(), 100.0, 1);

	}

	/*@Test
	public void testLandOrOcean() throws IOException {
		
		List<String> fileL = new ArrayList<>();
		String folder=Variables.CONFIG_FOLDER+"shape/";
		fileL.add(folder+"GSHHS_c_L1.shp");
		fileL.add(folder+"GSHHS_f_L1.shp");
		fileL.add(folder+"GSHHS_h_L1.shp");
		fileL.add(folder+"GSHHS_i_L1.shp");
		fileL.add(folder+"GSHHS_l_L1.shp");
		
		
		for (String fileS : fileL) {
			File file = new File(fileS);
			if (file.exists())
				System.out.println(fileS + " exist and parse!");

			ShapefileDataStore dataStore = new ShapefileDataStore (file.toURI ().toURL () );
		    FeatureSource<SimpleFeatureType, SimpleFeature> featureSource = 
		        dataStore.getFeatureSource ();
		    String geomAttrName = featureSource.getSchema ()
		        .getGeometryDescriptor ().getLocalName ();

		    ResourceInfo resourceInfo = featureSource.getInfo ();
		    CoordinateReferenceSystem crs = resourceInfo.getCRS ();
		    Hints hints = GeoTools.getDefaultHints ();
		    hints.put ( Hints.JTS_SRID, 4326 );
		    hints.put ( Hints.CRS, crs );

		    FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2 ( hints );
		    GeometryFactory gf = JTSFactoryFinder.getGeometryFactory ( hints );

		    org.locationtech.jts.geom.Coordinate land = new org.locationtech.jts.geom.Coordinate (1.399354, 42.867969);
		    org.locationtech.jts.geom.Point pointLand = gf.createPoint(land);
		    org.locationtech.jts.geom.Coordinate water = new org.locationtech.jts.geom.Coordinate (-2.894228, 51.1952001);
		    org.locationtech.jts.geom.Point pointWater = gf.createPoint ( water );

		    Intersects filter = ff.intersects ( ff.property ( geomAttrName ), 
		        ff.literal ( pointLand ) );
		    FeatureCollection<SimpleFeatureType, SimpleFeature> featuresLand = featureSource
		            .getFeatures ( filter );
		    int a = featuresLand.toArray().length;
		    
		    System.out.println(a);

		    filter = ff.intersects ( ff.property ( geomAttrName ), 
		        ff.literal ( pointWater ) );
		    FeatureCollection<SimpleFeatureType, SimpleFeature>  features = featureSource.getFeatures ( filter );
		    int b = features.toArray().length;
			   
		    System.out.println(b);

		}
			
	}*/

}
