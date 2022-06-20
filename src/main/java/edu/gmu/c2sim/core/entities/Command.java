package edu.gmu.c2sim.core.entities;

import java.util.Hashtable;

public class Command {
	private String name;
	
	private Hashtable<String, IEntity> commandedForces;
	
	private Hashtable<String,IEntity> trakedEnemies;
	private Hashtable<String,IEntity> targetEnemies;
	private Hashtable<String,IEntity> engagedEnemies;
	private Hashtable<String,IEntity> assedEnemies;
	
	
	
	public Command(String name) {
		this.name=name;
		commandedForces = new Hashtable<>();
		trakedEnemies= new Hashtable<>();
		targetEnemies= new Hashtable<>();
		engagedEnemies= new Hashtable<>();
		assedEnemies= new Hashtable<>();
	}
	
	public void addCommandedForces(IEntity ent) {
		this.commandedForces.put(ent.getAlias(), ent);
	}
	
	public void addToTrackedDB(IEntity enemy) {
		this.trakedEnemies.put(enemy.getAlias(), enemy);
	}
	
	public void addToTargedDB(IEntity enemy) {
		this.targetEnemies.put(enemy.getAlias(), enemy);
	}
	
	public void addToEngageddDB(IEntity enemy) {
		this.engagedEnemies.put(enemy.getAlias(), enemy);
	}
	
	public void addToAssedDB(IEntity enemy) {
		this.assedEnemies.put(enemy.getAlias(), enemy);
	}

	public String getName() {
		return name;
	}

	public Hashtable<String, IEntity> getCommandedForces() {
		return commandedForces;
	}

	public Hashtable<String, IEntity> getTrakedEnemies() {
		return trakedEnemies;
	}

	public Hashtable<String, IEntity> getTargetEnemies() {
		return targetEnemies;
	}

	public Hashtable<String, IEntity> getEngagedEnemies() {
		return engagedEnemies;
	}

	public Hashtable<String, IEntity> getAssedEnemies() {
		return assedEnemies;
	}

		
	

}
