package edu.gmu.c2sim.core.entities;

import java.util.Hashtable;

import edu.gmu.c2sim.core.entities.IEntity.TEAM;
import edu.gmu.c2sim.core.entities.domain.air.AirForceUnit;
import edu.gmu.c2sim.core.entities.domain.cyber.CyberUnit;
import edu.gmu.c2sim.core.entities.domain.land.GroundForceUnit;
import edu.gmu.c2sim.core.entities.domain.sea.SeaForceUnit;
import edu.gmu.c2sim.core.entities.domain.space.SpaceForceUnit;

public class EntityFactory {
	
	private static Hashtable<String, String> data = getData();
	
	
	public static IEntity createEntity (String entityType, String alias, IEntityModel model,
			TEAM team, Command command, long initialTime) {
		IEntity ent = null;
		String modelType = data.get(entityType);
		
		if (modelType.equals("SEA")) {
			ent = new SeaForceUnit(alias, model, team, command, initialTime);
		}
		
		else if (modelType.equals("SPACE")) {
			ent = new SpaceForceUnit(alias, model, team, command, initialTime);
		}
		
		else if (modelType.equals("LAND")) {
			ent = new GroundForceUnit(alias, model, team, command, initialTime);
		}
		
		else if (modelType.equals("AIR")) {
			ent = new AirForceUnit(alias, model, team, command, initialTime);
		}

		else if (modelType.equals("CYBER")) {
			ent = new CyberUnit(alias, model, team, command, initialTime);
		}
		
		
		return ent;
	}
	
	
	private static Hashtable<String, String> getData(){
		Hashtable<String, String> data = new Hashtable<>();
		data.put("CRUISER", "SEA");
		data.put("DESTROYER", "SEA");
		data.put("SMALL_UNMANNED_SURFACE", "SEA");
		data.put("LARGE_UNMANNED_SURFACE", "SEA");
		data.put("SUBMARINE", "SEA");
		
		data.put("MEO", "SPACE");
		data.put("LEO", "SPACE");
		data.put("GEO", "SPACE");
		
		data.put("HEAVY_COMBAT_PLATFORM", "LAND");
		data.put("LIGHT_COMBAT_PLATFORM", "LAND");
		data.put("LONGRANGE_PRECISION_FIRES", "LAND");
		data.put("LONGRANGE_INDIRECT_FIRES", "LAND");
		data.put("INTEGRATED_AIR_DEFENSE_SYSTEM", "LAND");
		data.put("ANTI_AIRCRAFT_ARTILLERY_VEHICLE", "LAND");
		
		data.put("CYBER_PACKAGE", "CYBER");
		data.put("C4ISR_NODE", "CYBER");
		
		data.put("FIGHTER_5G", "AIR");
		data.put("AEW_ATTACK", "AIR");
		data.put("UAS", "AIR");
		data.put("HELI_ATTACK", "AIR");
		data.put("HELI_HEAVY", "AIR");
		data.put("FIGHTER_4G", "AIR");
		data.put("BOMBER", "AIR");
		data.put("REFUELING", "AIR");
		
		return data;
	}

}
