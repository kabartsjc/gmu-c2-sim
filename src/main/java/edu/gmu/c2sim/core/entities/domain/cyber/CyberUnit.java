package edu.gmu.c2sim.core.entities.domain.cyber;

import java.util.ArrayList;
import java.util.List;

import edu.gmu.c2sim.core.entities.Command;
import edu.gmu.c2sim.core.entities.IEntity;
import edu.gmu.c2sim.core.entities.IEntityModel;
import edu.gmu.c2sim.core.entities.StaticEntity;
import edu.gmu.c2sim.core.orders.IOrder;

public class CyberUnit extends StaticEntity{
	
	public CyberUnit(String alias, IEntityModel model,TEAM team, Command command, long initialTime ) {
		this.alias=alias;
		this.model=model;
		this.team = team;
		this.command=command;
		this.initialTime=initialTime*60;
		
		weaponL = new ArrayList<>();
		sensorL = new ArrayList<>();
		
		currentTime = 0;
		this.status = STATUS.CREATED;
	}
	
	@Override
	public void setStatus(STATUS status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(long time_interval_sec, List<IEntity> targetList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute_order(IOrder currentOrder, long time_interval_sec, List<IEntity> team) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update_order_status(IOrder currentOrder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCommand(String type, IEntity ent) {
		// TODO Auto-generated method stub
		
	}
	
	

	
}
