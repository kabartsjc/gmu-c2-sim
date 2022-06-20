package edu.gmu.c2sim.core.entities.effector.sensors;

import edu.gmu.c2sim.core.entities.IEntityModel;
import edu.gmu.c2sim.core.entities.effector.IEffector;

public interface ISensor extends IEffector{
	
	public boolean isSensing(double targetDistance,IEntityModel targetTyoe);

	public void setRandomParams(String distName, double[] params);
	
	
	
	
}
