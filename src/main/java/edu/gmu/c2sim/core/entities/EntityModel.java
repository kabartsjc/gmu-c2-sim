package edu.gmu.c2sim.core.entities;

public class EntityModel implements IEntityModel{

	private String modelId;
	  
	private EntityType modelType;
	private int numberOfPods;
	private double speedMs;
	private double climbRate;
	public double combatRange_m;
	private double destructionFactor;
	private double neutralizationFactor;
	private double supressionFactor;
	private double vulnerabilityFactor;
	
	public EntityModel() {
		
	}
	
	public EntityModel (String name) {
		this.modelId=name;
	}
	
	public EntityModel(String modelId, IEntityType modelType,  int numberOfPods, double speedMs,
			double climbRate, double combatRange_m, double destructionFactor, double neutralizationFactor,
			double supressionFactor, double vulnerabilityFactor) {
		super();
		this.modelId = modelId;
		this.modelType = (EntityType)modelType;
		this.numberOfPods = numberOfPods;
		this.speedMs = speedMs;
		this.climbRate = climbRate;
		this.combatRange_m = combatRange_m;
		this.destructionFactor = destructionFactor;
		this.neutralizationFactor = neutralizationFactor;
		this.supressionFactor = supressionFactor;
		this.vulnerabilityFactor = vulnerabilityFactor;
	}
	
	@Override
	public String getID() {
		return modelId;
	}
	@Override
	public IEntityType getType() {
		return modelType;
	}
	
	@Override
	public int getNumberofPods() {
		return numberOfPods;
	}
	@Override
	public double getSpeedMs() {
		return speedMs;
	}
	@Override
	public double getClimbRateMs() {
		return climbRate;
	}
	@Override
	public double getCombatRangeM() {
		return combatRange_m;
	}
	@Override
	public double getDestructionFactor() {
		return destructionFactor;
	}
	@Override
	public double getNeutralizationFactor() {
		return neutralizationFactor;
	}
	@Override
	public double getSupressionFactor() {
		return supressionFactor;
	}
	@Override
	public double getVulnerabilityFactor() {
		return vulnerabilityFactor;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public void setModelType(IEntityType modelType) {
		this.modelType = (EntityType)modelType;
	}

	
	
}
