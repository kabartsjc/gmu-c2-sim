package edu.gmu.c2sim.core.entities.effector;

import edu.gmu.c2sim.core.entities.IEntityType.DOMAIN;

public interface IEffector {
	public String getId();
	public double getPrecision();
	public double getRange_m();
	public DOMAIN getDomain();
	

}
