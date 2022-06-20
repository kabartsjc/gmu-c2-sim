package edu.gmu.c2sim.core.entities.effector.sensors;


import org.uncommons.maths.random.GaussianGenerator;
import org.uncommons.maths.random.MersenneTwisterRNG;

import edu.gmu.c2sim.core.entities.IEntityModel;
import edu.gmu.c2sim.core.entities.IEntityType.DOMAIN;


/**https://www.radartutorial.eu/01.basics/Radars%20Accuracy.en.html**/

public class Sensor implements ISensor {
	private String id;
	private double precision = 0.0f;
	private double range = 0.0f;
	private DOMAIN domain;
	
	
	private MersenneTwisterRNG rng;
	private GaussianGenerator generator;

	
	public Sensor(String id, DOMAIN domain, double precision, double range_m) {
		super();
		this.id = id;
		this.precision = precision;
		this.domain = domain;
		this.range = range_m;
	}
	
	
	@Override
	public String getId() {
		return id;
	}
	
	
	@Override
	public double getRange_m() {
		return range;
	}

	@Override
	public double getPrecision() {
		return precision;
	}
	
	@Override
	public DOMAIN getDomain() {
		return domain;
	}

	@Override
	public boolean isSensing(double targetDistance, IEntityModel targetType) {
		double factor = targetType.getVulnerabilityFactor();
		
		double randomError = Math.abs(generator.nextValue());
		
		double propagation_loss = 1/Math.pow(targetDistance,2);
		
		
		double measure_error = (factor)*(randomError+propagation_loss);
		if (measure_error>1)
			measure_error=measure_error/2;
		
		if (range - targetDistance < 0)
			return false;

		if (precision < measure_error)
			return false;
	
		return true;
	}


	@Override
	public void setRandomParams(String distName, double[] params) {
		rng = new MersenneTwisterRNG();

		if (distName.equals("Gaussian")) {
			double mean = params[0];
			double stdv = params[1];
			generator = new GaussianGenerator(mean, stdv, rng);
		}
	}
	
	
	


	

}
