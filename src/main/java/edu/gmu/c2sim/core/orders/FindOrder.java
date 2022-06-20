package edu.gmu.c2sim.core.orders;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import edu.gmu.c2sim.core.entities.IEntity;
import edu.gmu.c2sim.core.entities.effector.sensors.ISensor;
import edu.gmu.c2sim.core.geo.SimCoordinate;
import edu.gmu.c2sim.utils.GeoUtils;

public class FindOrder extends SimpleOrder {
	
	private static final int SUCESSFULL_PARAMETER=5;

	private LinkedList<SimCoordinate> route_orig;
	
	private Hashtable<String, Integer> detectableEntities;
	
	private int timesDetect=0;

	public FindOrder(String uri, IEntity actor, IEntity target, SimCoordinate missionLocation, long startTime,
			long timeOverTarget, long effectDuration, boolean optional) {
		super.uri = uri;
		super.actor = actor;
		super.target = target;
		super.missionLocation = missionLocation;
		super.startTime = startTime * 60;
		super.timeOnTarget = timeOverTarget * 60;
		super.effectDuration = effectDuration * 60;
		this.currentTime=0;

		sucessfull = false;
		finished = false;

		this.optional = optional;
		
		this.detectableEntities=new Hashtable<>();
	}
	
	/************************* GET AND SET **************************************************/

	@Override
	public String getType() {
		return "FindOrder";
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public void setRoute(LinkedList<SimCoordinate> route) {
		route_orig = route;
		this.route = ((LinkedList<SimCoordinate>) route.clone());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public SimCoordinate getNextWaypoint() {
		SimCoordinate pos = this.route.poll();

		if (pos == null) {
			route = (LinkedList<SimCoordinate>) route_orig.clone();
			pos = this.route.poll();
		}

		return pos;
	}
	
	
	/***************************** INTERFACE MAIN METHODS **************************************************/
	
	@Override
	public void orderUpdate(long time_interval_sec, SimCoordinate currentPosition, List<IEntity> targetList) {
		currentTime = currentTime+time_interval_sec;
		
		if (timeOnTarget!=0 && currentTime>timeOnTarget) {
			//fail
			setMissionFail("FAIL::"+currentTime +":"+this.getActor().getAlias()+":CURRENT TIME >= TIME ON TARGET");
		}
		
		else {
			apply_comm_effects(time_interval_sec,targetList);
			apply_cyber_effects(time_interval_sec, target, targetList);
			apply_kinect_effects(target,targetList);
		}
		
	}

	public void apply_comm_effects (long duration, List<IEntity> entL) {
		for (ISensor sensor : actor.getSensorList()) {
			SimCoordinate actorPos = actor.getCurrentPosition();
			
			SimCoordinate targetPos = target.getCurrentPosition();
			double h_dist =GeoUtils.calculateHDistance(actorPos, targetPos);

			//check the target of mission
			boolean result = sensor.isSensing(h_dist, target.getModel()); 
			
			if (result) {
				timesDetect++;
			}
			if (timesDetect>SUCESSFULL_PARAMETER) {
				setMissionSucessfull("SUCESSFUL::"+currentTime +":"+this.getActor().getAlias());
			}
			
			
			//check opportunistic targets
			for (IEntity ent : entL) {
				if (ent.getAlias().equals(target.getAlias())==false) {
					if (detectableEntities.containsKey(ent.getAlias())){
						int times = detectableEntities.get(ent.getAlias());
						times = times + 1;
						detectableEntities.remove(ent.getAlias());
						if (times>SUCESSFULL_PARAMETER) {
							//update commander opportunistic orders
							actor.updateCommand("TRACKED", ent);
						}
						else {
							detectableEntities.put(ent.getAlias(), times);
						}
						
					}
					
					else {
						detectableEntities.put(ent.getAlias(), 1);
					}
				}
			}
		}

	};
	
	public void apply_cyber_effects (long duration, IEntity target, List<IEntity>entL) {
		return;
	};
	
	public void apply_kinect_effects(IEntity target,List<IEntity>entL) {
		return;
	};
	
	

}
