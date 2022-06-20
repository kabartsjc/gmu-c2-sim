package edu.gmu.c2sim.core.entities.effector.weapons;


import org.uncommons.maths.random.GaussianGenerator;
import org.uncommons.maths.random.MersenneTwisterRNG;

import edu.gmu.c2sim.core.entities.IEntityType.DOMAIN;

public class Weapon implements IWeapon{
	private String id;
	private String type;
	private boolean isReusable=false;
	private double precision=0.0f;
	private double range=0.0f;
	private double intensity=0.0f;
	private DOMAIN domain;
	
	private MersenneTwisterRNG rng;
	private GaussianGenerator generator;
	
	
	public Weapon(String id, String type,boolean isReusable, double precision,DOMAIN domain, double range, double intensity) {
		this.id = id;
		this.type=type;
		this.isReusable = isReusable;
		this.precision = precision;
		this.range = range;
		this.domain=domain;
		this.intensity = intensity;
	}

	@Override
	public String getId() {
		return id;
	}

	
	@Override
	public boolean isReusable() {
		return isReusable;
	}

	@Override
	public double getPrecision() {
		return precision;
	}

	@Override
	public double getRange_m() {
		return range;
	}

	@Override
	public double getIntensity() {
		return intensity;
	}

	@Override
	public double calculateEffectApplied() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DOMAIN getDomain() {
		return domain;
	}

	@Override
	public String getType() {
		return type;
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
