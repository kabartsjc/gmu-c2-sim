package edu.gmu.c2sim.utils;


import static org.junit.Assert.assertTrue;


import org.junit.jupiter.api.Test;

import edu.gmu.c2sim.core.entities.EntityModel;
import edu.gmu.c2sim.core.entities.EntityType;
import edu.gmu.c2sim.core.entities.IEntityModel;
import edu.gmu.c2sim.core.entities.IEntityType;
import edu.gmu.c2sim.core.entities.IEntityType.DOMAIN;
import edu.gmu.c2sim.core.entities.effector.sensors.Sensor;
import edu.gmu.c2sim.core.physics.WindEffector;

class TestRandomGenerators {

	@Test
	void testWindGenerator() {
		WindEffector windState = WindEffector.getInstance();
		double params[] = new double[2];
		params [0]=2.0;
		params[1]=1.9;
		windState.configure("Gaussian",params);
		
		for (int i=0; i<1000; i++) {
			double value =windState.getShipSpeedReduction("CRUISER");
			String condition = windState.getConditionName();
			System.out.println("i="+i+"--->"+ value+ " --- condition = "+condition);
			assertTrue(value>=0);
		}
	}
	
	@Test
	void testSensing() {
		Sensor sensor = new Sensor("AIR_RADAR_T1",DOMAIN.AIR, 0.9, 50000.0);
		double params[] = new double[2];
		params [0]=2.0;
		params[1]=1.9;
		sensor.setRandomParams("Gaussian",params);
		IEntityType modelType = new EntityType("FIGHTER_5G",DOMAIN.AIR,null);
		
		
		IEntityModel targetType1 = new EntityModel("F5G", modelType, 6, 670.5, 350, 850000, 0.6, 0.3, 0.2, 0.1);
		IEntityModel targetType2 = new EntityModel("F5G", modelType, 6, 670.5, 350, 850000, 0.6, 0.3, 0.2, 0.5);
		IEntityModel targetType3 = new EntityModel("F5G", modelType, 6, 670.5, 350, 850000, 0.6, 0.3, 0.2, 1.0);
		
		
		int ct1 = 0;
		int ct2 =0;
		int ct3=0;
		
		int cf1 = 0;
		int cf2 = 0;
		int cf3 = 0;
		
		//test distance = 10000
		for (int i=0; i<10;i++) {
			boolean result = sensor.isSensing(10000, targetType1);
			if (result==true)
				ct1++;
			else
				cf1++;
			
			result = sensor.isSensing(10000, targetType2);
			if (result==true)
				ct2++;
			else
				cf2++;
			
			result = sensor.isSensing(10000, targetType3);
			if (result==true)
				ct3++;
			else
				cf3++;
			
			
		}
		System.out.println("distance:"+10000);
		System.out.println("counting true - ct1:"+ct1+ " - ct2: "+ct2+ " - ct3: "+ct3);
		System.out.println("counting false - cf1:"+cf1+ " - cf2: "+cf2+ " - cf3: "+cf3);
		
		assertTrue(ct1>=ct2);
		assertTrue(ct2>=ct3);
		
		ct1 = 0;
		ct2 =0;
		ct3=0;
		
		cf1 = 0;
		cf2 = 0;
		cf3 = 0;
		
		//test distance = 20000
		for (int i=0; i<10;i++) {
			boolean result = sensor.isSensing(20000, targetType1);
			if (result==true)
				ct1++;
			else
				cf1++;
			
			result = sensor.isSensing(20000, targetType2);
			if (result==true)
				ct2++;
			else
				cf2++;
			
			result = sensor.isSensing(20000, targetType3);
			if (result==true)
				ct3++;
			else
				cf3++;
			
			
		}
		System.out.println("distance:"+20000);
		System.out.println("counting true - ct1:"+ct1+ " - ct2: "+ct2+ " - ct3: "+ct3);
		System.out.println("counting false - cf1:"+cf1+ " - cf2: "+cf2+ " - cf3: "+cf3);
		assertTrue(ct1>=ct2);
		assertTrue(ct2>=ct3);
		
		ct1 = 0;
		ct2 =0;
		ct3=0;
		
		cf1 = 0;
		cf2 = 0;
		cf3 = 0;
		
		//test distance = 50000
		for (int i=0; i<10;i++) {
			boolean result = sensor.isSensing(50000, targetType1);
			if (result==true)
				ct1++;
			else
				cf1++;
			
			result = sensor.isSensing(50000, targetType2);
			if (result==true)
				ct2++;
			else
				cf2++;
			
			result = sensor.isSensing(50000, targetType3);
			if (result==true)
				ct3++;
			else
				cf3++;
			
			
		}
		System.out.println("distance:"+50000);
		System.out.println("counting true - ct1:"+ct1+ " - ct2: "+ct2+ " - ct3: "+ct3);
		System.out.println("counting false - cf1:"+cf1+ " - cf2: "+cf2+ " - cf3: "+cf3);
		
		assertTrue(ct1>=ct2);
		assertTrue(ct2>=ct3);
		
		ct1 = 0;
		ct2 =0;
		ct3=0;
		
		cf1 = 0;
		cf2 = 0;
		cf3 = 0;
		
		
	}

}
