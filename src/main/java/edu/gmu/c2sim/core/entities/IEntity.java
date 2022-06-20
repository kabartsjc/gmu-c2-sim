package edu.gmu.c2sim.core.entities;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.StringTokenizer;

import edu.gmu.c2sim.core.entities.effector.sensors.ISensor;
import edu.gmu.c2sim.core.entities.effector.weapons.IWeapon;
import edu.gmu.c2sim.core.geo.SimCoordinate;
import edu.gmu.c2sim.core.orders.IOrder;
import edu.gmu.c2sim.core.physics.IPhysicsEffector;
import edu.gmu.c2sim.core.plan.IPlan;

public interface IEntity {

	public enum STATUS {
		CREATED, RUNNING, DESTROYED, TO_HOME, FAILED, FINISHED
	}

	public enum TEAM {
		RED, BLUE, GREEN
	}

	/************************* GET AND SET **************************************************/
	
	public String getAlias();
	
	public IEntityModel getModel();

	public TEAM getTeam();

	public STATUS getStatus();
	
	public void setStatus (STATUS status);

	public Command getCommand();

	public void setInitialPosition(SimCoordinate pos);

	public SimCoordinate getInitialPosition();

	public SimCoordinate getCurrentPosition();

	public long getInitialTime();

	public String getBehavior();

	public void setBehavior(String behavior);
	
	public void addWeapon(IWeapon weapon);

	public List<IWeapon> getWeaponList();

	public IWeapon getWeapon(String type);

	public void addSensor(ISensor sensor);

	public List<ISensor> getSensorList();
	
	public IPlan getPlan();

	public void setPlan(IPlan plan);

	/***************************** INTERFACE MAIN METHODS **************************************************/
		
	public void init(IPhysicsEffector effect, String distName, double[] params);

	public void update(long time_interval_sec, List<IEntity> targetList);
	
	public void execute_order(IOrder currentOrder, long time_interval_sec,List<IEntity>team);
	
	public void update_order_status(IOrder currentOrder);
	
	public void updateCommand (String type, IEntity ent);
	
	public void finalizeEntity();

	/******************************	IMPLEMENTED UTILS METHODS			*******************************************/

	public static List<String> getList(String msg) {
		List<String> list = new ArrayList<>();
		StringTokenizer str = new StringTokenizer(msg, ",");
		while (str.hasNext()) {
			list.add(str.nextToken());
		}
		return list;
	}

	public static TEAM parseTeam(String team) {
		if (team.equals("RED"))
			return TEAM.RED;
		else if (team.equals("BLUE"))
			return TEAM.BLUE;
		else
			return TEAM.GREEN;
	}

	public static String parseTeam(TEAM team) {
		if (team == TEAM.RED)
			return "RED";
		else if (team == TEAM.BLUE)
			return "BLUE";
		else
			return "GREEN";
	}

	public static String parseStatus(STATUS status) {
		String result = "CREATED";
		if (status == STATUS.CREATED)
			result= "CREATED";
		else if (status == STATUS.RUNNING)
			result=  "RUNNING";
		else if (status == STATUS.DESTROYED)
			result= "DESTROYED";
		else if (status == STATUS.TO_HOME)
			result=  "TO_HOME";
		else if (status == STATUS.FAILED)
			result=  "FAILED";
		else if (status == STATUS.FINISHED)
			result= "FINISHED";
		return result;
	}

}
