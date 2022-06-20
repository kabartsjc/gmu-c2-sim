package edu.gmu.c2sim.core.entities.domain.sea;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.gmu.c2sim.core.entities.Command;
import edu.gmu.c2sim.core.entities.IEntity;
import edu.gmu.c2sim.core.entities.IEntityModel;
import edu.gmu.c2sim.core.entities.MobileEntity;
import edu.gmu.c2sim.core.geo.SimCoordinate;
import edu.gmu.c2sim.core.orders.IOrder;

public class SeaForceUnit extends MobileEntity {

	public SeaForceUnit(String alias, IEntityModel model,TEAM team, Command command, long initialTime ) {
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
	
	/*
	@Override
	public LinkedList<SimCoordinate> calculateRoute() {
		LinkedList<SimCoordinate> route = new LinkedList<>();
		double h_speed = this.getModel().getSpeedMs();

		int j = 0;

		for (int i = 1; i < 20; i++) {
			double h_dist = h_speed * i;
			SimCoordinate pos = null;
			double bearing = 0.0;

			if (j == 0)
				bearing = 0.0;

			else if (j == 1)
				bearing = 90.0;

			else if (j == 2)
				bearing = 180.0;

			else
				bearing = 270.0;

			pos = GeoUtils.newPosition(this.initialPosition, h_dist, 0, bearing);

			route.add(pos);

			j++;

			j = j % 4;

		}

		return route;
	}
	

	
	public void move(long time_interval_sec, double speedEffector) {
		/*if (targetPosition == null)
			targetPosition = currentOrder.getNextWaypoint(currentTime);

		double bearing = GeoUtils.bearing(currentPosition, targetPosition);
		double speed = this.getType().getSpeedMs();

		speed = speed - speedEffector;

		double dist_hor = speed * time_interval_sec;

		double dist_vert = this.getType().getClimbRate() * time_interval_sec;

		currentPosition = GeoUtils.newPosition(currentPosition, dist_hor, dist_vert, bearing);

		double hor_err = dist_hor * 0.1;
		double vert_err = dist_vert * 0.1;
		boolean isDone = GeoUtils.isSamePosition(currentPosition, targetPosition, hor_err, vert_err);

		/*if (isDone)
			targetPosition = currentOrder.getNextWaypoint(currentTime);*/
	/*}

	@Override
	protected void backToHome(long time_interval_sec, double speedEffector) {
		// TODO Auto-generated method stub
		
	}*/

}
