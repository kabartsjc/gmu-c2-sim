package edu.gmu.c2sim.core.entities;

public interface IEntityType {
	public enum DOMAIN { LAND, SEA, AIR, CYBER, SPACE, ALL}
	
public enum SPACE_TYPE { MEO, GEO, LEO}
	
	public enum SEA_TYPE { CRUISER , DESTROYER, SMALL_UNMANNED_SURFACE,
		LARGE_UNMANNED_SURFACE, SUBMARINE }
	
	public enum GROUND_TYPE { HEAVY_COMBAT_PLATFORM, LIGHT_COMBAT_PLATFORM, 
		LONGRANGE_PRECISION_FIRES,LONGRANGE_INDIRECT_FIRES,
		INTEGRATED_AIR_DEFENSE_SYSTEM,ANTI_AIRCRAFT_ARTILLERY_VEHICLE}
	
	public enum CYBER_TYPE { CYBER_PACKAGE, C4ISR_NODE}
	
	public enum AIR_TYPE { FIGHTER_5G, AEW_ATTACK,
		UAS, HELI_ATTACK, HELI_HEAVY,BOMBER,FIGHTER_4G, REFUELING}
	
	
	public String getName();
	public DOMAIN getDomain();
	public String getImageName();
	
	
	public static DOMAIN getDomain(String domainS) {
		if (domainS.equals("LAND"))
			return DOMAIN.LAND;
		else if (domainS.equals("SEA"))
			return DOMAIN.SEA;
		else if (domainS.equals("AIR"))
			return DOMAIN.AIR;
		else if (domainS.equals("CYBER"))
			return DOMAIN.CYBER;
		else if (domainS.equals("SPACE"))
			return DOMAIN.SPACE;
		else
			return DOMAIN.ALL;
		
	}
	

}
