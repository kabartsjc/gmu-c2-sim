package edu.gmu.c2sim.core.entities;

import edu.gmu.c2sim.core.entities.IEntity.TEAM;
import edu.gmu.c2sim.core.geo.SimCoordinate;

public class Place implements IPlace{
	private String id;
	private TEAM team;
	private SimCoordinate position;
	
	public Place(String id, TEAM team, SimCoordinate position) {
		super();
		this.id = id;
		this.team = team;
		this.position = position;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public TEAM getTeam() {
		return team;
	}

	@Override
	public SimCoordinate getPosition() {
		return position;
	}

}
