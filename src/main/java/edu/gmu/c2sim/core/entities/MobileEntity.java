package edu.gmu.c2sim.core.entities;

import java.util.LinkedList;
import java.util.List;

import edu.gmu.c2sim.core.entities.effector.sensors.ISensor;
import edu.gmu.c2sim.core.entities.effector.weapons.IWeapon;
import edu.gmu.c2sim.core.geo.SimCoordinate;
import edu.gmu.c2sim.core.orders.IOrder;
import edu.gmu.c2sim.core.physics.IPhysicsEffector;
import edu.gmu.c2sim.core.plan.IPlan;
import edu.gmu.c2sim.core.plan.RouteFactory;

public abstract class MobileEntity implements IEntity {

	protected String alias;
	protected IEntityModel model;

	protected STATUS status;
	protected TEAM team;

	protected String behavior;

	protected Command command = null;

	protected long currentTime_sec;
	protected long initialTime_sec;

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
	public void setStatus (STATUS status) {
		if (status == STATUS.DESTROYED)
			finalizeEntity();
		this.status=status;
	}

	@Override
	public void setInitialPosition(SimCoordinate pos) {
		this.initialPosition = pos;
		this.currentPosition=pos;
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
		return initialTime_sec;
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
	public void init(IPhysicsEffector effect, String distName, double[] params) {
		this.effector=effect;
		this.currentOrder = this.plan.getNextOrder();
		if (currentOrder!=null) {
			LinkedList<SimCoordinate> route = RouteFactory.createRoute(currentOrder);
			this.currentOrder.setRoute(route);
		}
		
		for (ISensor sensor : sensorL) {
			sensor.setRandomParams(distName, params);
		}
		
		for (IWeapon wp : weaponL) {
			wp.setRandomParams(distName, params);
		}
		
		this.status = STATUS.RUNNING;
	}
	
	@Override
	public void update(long time_interval_sec, List<IEntity>targetTeam) {
		currentTime_sec = currentTime_sec + time_interval_sec;
		if (currentTime_sec<initialTime_sec) 
			return;
		
		if ((status==STATUS.DESTROYED || status==STATUS.FINISHED)) 
			return;
		
		move(time_interval_sec);
		execute_order(currentOrder,time_interval_sec,targetTeam);
		update_order_status(currentOrder);
	}
	
	
	protected abstract void move(long time_interval_sec);
	
	public abstract void execute_order(IOrder currentOrder,long time_interval_sec,List<IEntity>targetTeam);
	
	@Override
	public void update_order_status(IOrder currentOrder) {
		if (currentOrder!=null) {
			evaluateOrder(currentOrder);
		}
		
		else {//current_order == null
			System.out.println(alias + " - current order = null");
			
			currentOrder = plan.getNextOrder();
			if (currentOrder!=null) {
				LinkedList<SimCoordinate> route =  RouteFactory.createRoute(currentOrder);
				this.currentOrder.setRoute(route);
				targetPosition = currentOrder.getNextWaypoint();
			}
			
			else {
				System.out.println(alias + " - go to home");
					
				setBackToHome();
			}
		}
				
	}
	
	private void evaluateOrder (IOrder currentOrder) {
		System.out.println(alias + " evaluate order id = "+currentOrder.getURI());
		
		if (currentOrder.isFinished()) {
			System.out.print(alias + "order is finished ");
			if (currentOrder.isSucessfull()) {
				System.out.println(alias + "and sucessfull!");
				
				if (currentOrder.getType().equals("FindOrder"))
					this.command.addToTrackedDB(currentOrder.getTarget());
				
				currentOrder = plan.getNextOrder();
				System.out.println(alias + "have a new order!");
				
			}
			
			//ORDER FAILED
			else {
				if (currentOrder.isOptional()==false) {//order not optional
					System.out.println(alias + "passed to back to home!");
					setBackToHome();
					return;
				}
				
				else {//order optional
					System.out.println(alias + " have a new order");
					currentOrder = plan.getNextOrder();
				}
			}
		}
			
		System.out.println("passei aqui");
		return;
	}

	// TODO:
	@Override
	public void finalizeEntity() {

	}
	
	@Override
	public void updateCommand (String type, IEntity ent) {
		if (type.equals("TRACKED")) {
			command.addToTrackedDB(ent);
		}
	};

	
	/***************************** LOCAL SUPPORT METHODS **************************************************/
	
	public void setPosition(SimCoordinate position) {
		this.currentPosition = position;
	}
	
	public void setBackToHome() {
		status = STATUS.TO_HOME;
	}

	protected abstract void backToHome(long time_interval_sec);
	
}
