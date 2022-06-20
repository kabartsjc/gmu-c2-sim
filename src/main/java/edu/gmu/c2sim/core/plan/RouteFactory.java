package edu.gmu.c2sim.core.plan;

import java.util.LinkedList;

import edu.gmu.c2sim.core.entities.IEntity;
import edu.gmu.c2sim.core.geo.SimCoordinate;
import edu.gmu.c2sim.core.orders.FindOrder;
import edu.gmu.c2sim.core.orders.IOrder;
import edu.gmu.c2sim.utils.GeoUtils;

public class RouteFactory {
	private static final int DEEP_REFERENCE = 20;
	private static final int PATH_SIZE_REFERENCE = 10;
	
	
	public static LinkedList<SimCoordinate> createRoute (IOrder order){
		if (order instanceof FindOrder)
			return createSearchRoute(order.getMissionLocation(), order.getActor());
		
		return null;
	}

	private static LinkedList<SimCoordinate> createSearchRoute(SimCoordinate referencePosition, IEntity entity) {
		LinkedList<SimCoordinate> route = new LinkedList<>();
		double h_speed = entity.getModel().getSpeedMs();
		double v_speed = entity.getModel().getClimbRateMs();

		int bear_control = 0;

		for (int i = 1; i <= DEEP_REFERENCE; i++) {

			double h_dist = h_speed * 60 * PATH_SIZE_REFERENCE * i;
			double v_dist = v_speed * 60 * PATH_SIZE_REFERENCE * i;

			double bearing = 0.0;
			if (bear_control == 0)
				bearing = 0.0;

			else if (bear_control == 1)
				bearing = 90.0;

			else if (bear_control == 2)
				bearing = 180.0;

			else
				bearing = 270.0;

			SimCoordinate pos = GeoUtils.newPosition(referencePosition, h_dist, v_dist, bearing);
			route.add(pos);
			bear_control++;

			bear_control = bear_control % 4;
		}

		return route;
	}

}
