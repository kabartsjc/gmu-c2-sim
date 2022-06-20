package edu.gmu.c2sim.core.entities.domain.space;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.gmu.c2sim.core.entities.Command;
import edu.gmu.c2sim.core.entities.IEntity;
import edu.gmu.c2sim.core.entities.IEntityModel;
import edu.gmu.c2sim.core.entities.MobileEntity;
import edu.gmu.c2sim.core.geo.SimCoordinate;
import edu.gmu.c2sim.core.orders.IOrder;

public class SpaceForceUnit extends MobileEntity{
	
	public SpaceForceUnit(String alias, IEntityModel model,TEAM team, Command command, long initialTime ) {
		this.alias=alias;
		this.model=model;
		this.team = team;
		this.command=command;
		this.initialTime_sec=initialTime*60;
		
		weaponL = new ArrayList<>();
		sensorL = new ArrayList<>();
		
		currentTime_sec = 0;
		this.status = STATUS.CREATED;
	}

	@Override
	protected void move(long time_interval_sec) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute_order(IOrder currentOrder, long time_interval_sec, List<IEntity> targetTeam) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	protected void backToHome(long time_interval_sec) {
		// TODO Auto-generated method stub
		
	}
	
	

	

}
