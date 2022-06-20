package edu.gmu.c2sim.core.entities;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import edu.gmu.c2sim.core.dao.EntityModelDao;
import edu.gmu.c2sim.core.dao.EntityTypeModelDao;
import edu.gmu.c2sim.core.entities.IEntityType.DOMAIN;

public interface IEntityModel {
	public String getID();

	public IEntityType getType();

	public int getNumberofPods();

	public double getSpeedMs();

	public double getClimbRateMs();

	public double getCombatRangeM();

	/****
	 * ENTITY PARAMETERS -> DEFINE A SET OF ATTRIBUTES OF AN ENTITY - HOW BIG THE
	 * VALUE IS, BETTER IS THE ATTRIBUTE
	 *****/

	// Define the reliability of information provided by the node, starts with 1
	// (1-0)
	// public double getReliability();

	// Represents the level that is required to be achieved to entity is destroyed
	// state
	public double getDestructionFactor();// it is permanent
	// Represents the level that is required to be achieved to entity is
	// neutralization state

	public double getNeutralizationFactor();// it is for a short time (temporarily).

	// Represents the level that is required to be achieved to entity is supression
	// state
	public double getSupressionFactor(); // it represents when an entity is attacked

	// Represents the level of vulnerability of entity. In other words, it
	// represents the level of endurance of an entity
	public double getVulnerabilityFactor();

	public static List<IEntityModel> loadEntityModel() {
		Hashtable<String, IEntityType> entTypeDb = EntityTypeModelDao.load();
		Hashtable<String, IEntityModel> entModelDb = EntityModelDao.load(entTypeDb);

		List<IEntityModel> entModelL = new ArrayList<>(entModelDb.values());
		List<IEntityModel> modelL = new ArrayList<>();

		int i = 0;
		for (IEntityModel model : entModelL) {
			DOMAIN domain = model.getType().getDomain();
			if (domain == DOMAIN.SPACE) {
				modelL.add(i, model);
			}
		}

		for (IEntityModel model : entModelL) {
			DOMAIN domain = model.getType().getDomain();
			if (domain == DOMAIN.CYBER) {
				modelL.add(i, model);
			}
		}

		for (IEntityModel model : entModelL) {
			DOMAIN domain = model.getType().getDomain();
			if (domain == DOMAIN.AIR) {
				modelL.add(i, model);
			}
		}

		for (IEntityModel model : entModelL) {
			DOMAIN domain = model.getType().getDomain();
			if (domain == DOMAIN.LAND) {
				modelL.add(i, model);
			}
		}

		for (IEntityModel model : entModelL) {
			DOMAIN domain = model.getType().getDomain();
			if (domain == DOMAIN.SEA) {
				modelL.add(i, model);
			}
		}

		return modelL;
	}

	
	
	public static Hashtable<String, IEntityModel> loadEntityModelHashtable(){
		Hashtable<String, IEntityType> entTypeDb = EntityTypeModelDao.load();
		Hashtable<String, IEntityModel> entModelDb = EntityModelDao.load(entTypeDb);
		return entModelDb;
	}

	public static String[] getEntityModelNames(List<IEntityModel> entModelL) {
		String nameL[] = new String[entModelL.size()];

		for (int i = 0; i < entModelL.size(); i++) {
			IEntityModel ent = entModelL.get(i);
			nameL[i] = ent.getID();
		}
		return nameL;
	}

}
