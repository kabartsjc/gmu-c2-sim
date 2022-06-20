package edu.gmu.c2sim.core.orders;

import java.util.LinkedList;
import java.util.List;

import edu.gmu.c2sim.core.entities.IEntity;
import edu.gmu.c2sim.core.entities.IEntityType.DOMAIN;
import edu.gmu.c2sim.core.geo.SimCoordinate;

public abstract class SimpleOrder implements IOrder {
	protected String uri;
	protected IEntity actor;
	protected IEntity target;
	protected SimCoordinate missionLocation;
	protected long startTime;
	protected long timeOnTarget;
	protected long effectDuration;
	protected boolean optional;
	protected List<DOMAIN> domains;
	
	protected long currentTime;

	protected boolean finished;
	protected boolean sucessfull;

	protected String failReason;
	protected String sucessfulMsg;

	protected LinkedList<SimCoordinate> route;

	/************************* GET AND SET **************************************************/
	
	@Override
	public String getURI() {
		return uri;
	}

	@Override
	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public IEntity getActor() {
		return actor;
	}

	@Override
	public void setActor(IEntity actor) {
		this.actor = actor;
	}

	@Override
	public IEntity getTarget() {
		return target;
	}

	@Override
	public void setTarget(IEntity target) {
		this.target = target;
	}

	@Override
	public SimCoordinate getMissionLocation() {
		return missionLocation;
	}

	@Override
	public void setMissionLocation(SimCoordinate missionLocation) {
		this.missionLocation = missionLocation;
	}

	@Override
	public long getStartTime() {
		return startTime;
	}

	@Override
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	@Override
	public long getTimeOnTarget() {
		return timeOnTarget;
	}

	@Override
	public void setTimeOnTarget(long timeOnTarget) {
		this.timeOnTarget = timeOnTarget;
	}

	@Override
	public long getEffectTimeDuration() {
		return effectDuration;
	}

	@Override
	public void setEffectDuration(long effectDuration) {
		this.effectDuration = effectDuration;
	}

	@Override
	public boolean isFinished() {
		return finished;
	}
	
	@Override
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
		
	/***************************** INTERFACE MAIN METHODS **************************************************/

	@Override
	public boolean isOptional() {
		return optional;
	}

	@Override
	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	@Override
	public boolean isSucessfull() {
		return sucessfull;
	}

	public void setMissionSucessfull (String msg) {
		sucessfull=true;
		finished=true;
		sucessfulMsg=msg;
		System.out.println(sucessfulMsg);
	}

	public void setMissionFail(String reason) {
		sucessfull = false;
		finished = true;
		failReason = reason;
		System.out.println(reason);
	}
	
	@Override
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}


}
