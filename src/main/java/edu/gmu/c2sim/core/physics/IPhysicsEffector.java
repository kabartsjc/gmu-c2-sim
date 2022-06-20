package edu.gmu.c2sim.core.physics;


public interface IPhysicsEffector {
	//public double getCondition(long currentTime);
	//public double getCondition(long currentTime, SEA_TYPE typeOfEntity);
	public String getConditionName();
	public int getConditionIndex();
	public double getShipSpeedReduction(String shipType);
	public double getFlightSpeedReduction(String airType);
	public double getGroundSpeedReduction();
	
	public void update();
	
	public void configure(String dist, double[] params);

}
