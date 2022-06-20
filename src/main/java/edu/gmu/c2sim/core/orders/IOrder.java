package edu.gmu.c2sim.core.orders;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.text.StringTokenizer;

import edu.gmu.c2sim.core.entities.IEntity;
import edu.gmu.c2sim.core.entities.IEntityType.DOMAIN;
import edu.gmu.c2sim.core.geo.SimCoordinate;

public interface IOrder {
	
	/************************* GET AND SET **************************************************/
	
	public String getURI();
	public void setUri(String uri);

	public IEntity getActor();
	public void setActor(IEntity actor);
	
	public IEntity getTarget();
	public void setTarget(IEntity target) ;
	
	public String getType();
	
	public SimCoordinate getMissionLocation();
	public void setMissionLocation(SimCoordinate missionLocation) ;
	
	public long getStartTime();
	public void setStartTime(long startTime);
	
	public long getTimeOnTarget();
	public void setTimeOnTarget(long timeOnTarget);
	
	public long getEffectTimeDuration();
	public void setEffectDuration(long effectDuration);
	
	public boolean isFinished();
	public void setFinished(boolean finished) ;
	
	
	public void setRoute(LinkedList<SimCoordinate> route);
	public SimCoordinate getNextWaypoint();
	
	
	/***************************** INTERFACE MAIN METHODS **************************************************/
	
	public boolean isOptional();
	public void setOptional(boolean optional) ;
	
	public boolean isSucessfull();
	public void setMissionSucessfull (String msg);
	public  void setMissionFail(String reason) ;
	public void setFailReason(String failReason) ;
	
	
	public void orderUpdate(long time_interval_sec,SimCoordinate currentPosition, List<IEntity>entL);
	
	
	public void apply_comm_effects (long duration,List<IEntity>entL);
	
	public void apply_cyber_effects (long duration, IEntity target,List<IEntity>entL);
	
	public void apply_kinect_effects(IEntity target,List<IEntity>entL);
	
	
	
	/******************************	IMPLEMENTED UTILS METHODS			*******************************************/
	
	public static String[] getOrderTypes(DOMAIN domain) {
		List<String> types = new ArrayList<>();
		
		if (domain==DOMAIN.AIR) {
			types.add("FindOrder");
				
				
		}
		
		else if (domain==DOMAIN.LAND) {
			types.add("FindOrder");
			
		}
		
		
		else if (domain==DOMAIN.SEA) {
			types.add("FindOrder");
			
		}
		
		
		else if (domain==DOMAIN.SPACE) {
			types.add("FindOrder");
			
		}
		
		
		else if (domain==DOMAIN.CYBER) {
			
		}
		
		String result [] = new String[types.size()];
		for (int i=0; i<types.size();i++) {
			String typeS = types.get(i);
			result[i]=typeS;
		}
		
		return result;
	};
	
	
	public static IOrder parseFindOrder(StringTokenizer str, IEntity actor,
			Hashtable<String, IEntity> blueTeam,
			Hashtable<String, IEntity> redTeam,
			Hashtable<String, IEntity> greenTeam) {
		String uri = str.next();
		String targetS = str.next();
		IEntity target = null;
		if (blueTeam.containsKey(targetS)) {
			target = blueTeam.get(targetS); 
		}
		else if (redTeam.containsKey(targetS)) {
			target = redTeam.get(targetS); 
		}
		else if (greenTeam.containsKey(targetS)) {
			target = greenTeam.get(targetS); 
		}
		
		String coordinateS = str.next();
		SimCoordinate missionLocation = SimCoordinate.createCoordinate(coordinateS);
		
		long startTime = Long.parseLong(str.next());
		
		long timeOverTarget = Long.parseLong(str.next());
		long effectDuration = Long.parseLong(str.next());
		boolean optional = Boolean.parseBoolean(str.next()); 
		
		IOrder order = new FindOrder(uri, actor, target, missionLocation, 
				startTime, timeOverTarget, effectDuration,optional);
		
		return order;
	}
	
}
