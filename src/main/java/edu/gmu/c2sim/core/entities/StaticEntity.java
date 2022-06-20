package edu.gmu.c2sim.core.entities;

import java.util.List;

import edu.gmu.c2sim.core.entities.effector.sensors.ISensor;
import edu.gmu.c2sim.core.entities.effector.weapons.IWeapon;
import edu.gmu.c2sim.core.geo.SimCoordinate;
import edu.gmu.c2sim.core.orders.IOrder;
import edu.gmu.c2sim.core.physics.IPhysicsEffector;
import edu.gmu.c2sim.core.plan.IPlan;

public abstract class StaticEntity implements IEntity {

	protected String alias;
	protected IEntityModel model;

	protected STATUS status;
	protected TEAM team;

	protected String behavior;

	protected Command command = null;

	protected long currentTime;
	protected long initialTime;

	protected SimCoordinate initialPosition;
	protected SimCoordinate currentPosition;
	protected SimCoordinate targetPosition = null;

	protected IPlan plan;

	protected List<IWeapon> weaponL;
	protected List<ISensor> sensorL;
	
	protected IPhysicsEffector effector;

	protected IOrder currentOrder;
	
/************************* GET AND SET **************************************************/
	
	@Override
	public String getAlias() {
		return alias;
	}
	
	@Override
	public IEntityModel getModel() {
		return model;
	}

	@Override
	public TEAM getTeam() {
		return team;
	}
	
	@Override
	public Command getCommand() {
		return command;
	}
	
	@Override
	public STATUS getStatus() {
		return status;
	}

	@Override
	public void setInitialPosition(SimCoordinate pos) {
		this.initialPosition = pos;
	}

	@Override
	public SimCoordinate getInitialPosition() {
		return initialPosition;
	}

	@Override
	public SimCoordinate getCurrentPosition() {
		if (currentPosition == null)
			return initialPosition;
		else
			return currentPosition;
	}

	@Override
	public long getInitialTime() {
		return initialTime;
	}

	@Override
	public String getBehavior() {
		return behavior;
	}

	@Override
	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}

	@Override
	public void addWeapon(IWeapon weapon) {
		this.weaponL.add(weapon);
	}

	@Override
	public List<IWeapon> getWeaponList() {
		return weaponL;
	}
	
	@Override
	public IWeapon getWeapon(String type) {
		for (IWeapon wp : this.weaponL) {
			if (wp.getType().equals(type))
				return wp;
		}

		return null;
	}
	
	@Override
	public void addSensor(ISensor sensor) {
		this.sensorL.add(sensor);
	}

	@Override
	public List<ISensor> getSensorList() {
		return sensorL;
	}

	@Override
	public IPlan getPlan() {
		return plan;
	}

	@Override
	public void setPlan(IPlan plan) {
		this.plan = plan;
	}


	/***************************** INTERFACE MAIN METHODS **************************************************/


	@Override
	public void init(IPhysicsEffector effector,String distName, double[] params) {
		//TODO: FAZER
	}
	
	

	// TODO:
	@Override
	public void finalizeEntity() {

	}

	



}
