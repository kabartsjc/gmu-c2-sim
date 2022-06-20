package edu.gmu.c2sim.core.entities.domain.air;

import java.util.ArrayList;
import java.util.List;

import edu.gmu.c2sim.core.entities.Command;
import edu.gmu.c2sim.core.entities.IEntity;
import edu.gmu.c2sim.core.entities.IEntityModel;
import edu.gmu.c2sim.core.entities.MobileEntity;
import edu.gmu.c2sim.core.orders.IOrder;
import edu.gmu.c2sim.utils.GeoUtils;

public class AirForceUnit extends MobileEntity {

	public AirForceUnit(String alias, IEntityModel model, TEAM team, Command command, long initialTime) {
		this.alias = alias;
		this.model = model;
		this.team = team;
		this.command = command;
		this.initialTime_sec = initialTime * 60;

		weaponL = new ArrayList<>();
		sensorL = new ArrayList<>();

		currentTime_sec = 0;
		this.status = STATUS.CREATED;
	}

	/***************************** INTERFACE MAIN METHODS **************************************************/

	@Override
	public void move(long time_interval_sec) {
		
		if (status == STATUS.TO_HOME) {
			backToHome(time_interval_sec);
		}

		else {
			if (currentOrder == null)
				return;
			
			else {
				if (targetPosition == null)
					targetPosition = currentOrder.getNextWaypoint();
				
				if (targetPosition==null) {
					currentOrder.setMissionFail("There is no more waypoint in the route to achieve!");
					return;
				}
				
				double vert_difference = targetPosition.getAltitude() - currentPosition.getAltitude();
				
				double v_speed = this.getModel().getClimbRateMs();

				
				double v_err = v_speed * time_interval_sec*0.1;
				double v_dist = 0;

				if (Math.abs(vert_difference) > v_err) {
					v_dist = v_speed*time_interval_sec;
				}
				
				
				double h_speed = this.getModel().getSpeedMs();
				
				//diference between the models
				double eff_red_speed = effector.getFlightSpeedReduction(this.model.getType().getName());
				h_speed = h_speed - eff_red_speed;
				
				double h_dist = h_speed * time_interval_sec;
				
				double bearing = GeoUtils.bearing(currentPosition, targetPosition);
				
				currentPosition = GeoUtils.newPosition(currentPosition, h_dist, v_dist, bearing);
			
				double h_err = h_dist *time_interval_sec *0.1;
				
				boolean isDone = GeoUtils.isSamePosition(currentPosition, targetPosition, h_err, v_err);

				if (isDone) {
					System.out.println(alias+":passei no done");

					targetPosition = currentOrder.getNextWaypoint();
				}
			}
		}
	}
	
	@Override
	public void execute_order(IOrder currentOrder, long time_interval_sec,List<IEntity>targetTeam) {
		currentOrder.orderUpdate(time_interval_sec, currentPosition,targetTeam);
	};
	
	
	/***************************** LOCAL SUPPORT METHODS **************************************************/

	
	@Override
	protected void backToHome(long time_interval_sec) {
		if (status!=STATUS.FINISHED) {
			targetPosition=initialPosition;
			status = STATUS.TO_HOME;
			
			double bearing = GeoUtils.bearing(currentPosition, targetPosition);

			double h_speed = this.getModel().getSpeedMs();
			double v_speed = this.getModel().getClimbRateMs();

			double eff_red_speed = effector.getFlightSpeedReduction(this.model.getType().getName());

			h_speed = h_speed - eff_red_speed;

			double h_dist = h_speed * time_interval_sec;
			double v_dist = v_speed * time_interval_sec;

			currentPosition = GeoUtils.newPosition(currentPosition, h_dist, v_dist, bearing);

			double h_err = h_dist * 0.1*time_interval_sec;
			double v_err = v_dist * 0.1*time_interval_sec;

			boolean isDone = GeoUtils.isSamePosition(currentPosition, targetPosition, h_err, v_err);
			if (isDone) {
				System.out.println(alias+":FINISHED");
				status=STATUS.FINISHED;
				finalizeEntity();
			}
		}
	}

	

	
	

}
