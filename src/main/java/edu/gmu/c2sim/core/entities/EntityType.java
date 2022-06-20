package edu.gmu.c2sim.core.entities;

public class EntityType implements IEntityType{

	private String id;
	private DOMAIN domain;
	private String imageName;

	public EntityType(String id, DOMAIN domain, String imageName) {
		this.id = id;
		this.domain = domain;
		this.imageName = imageName;
	}

	public void setDomain(DOMAIN domain) {
		this.domain = domain;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getName() {
		return id;
	}
	
	public void setName (String name) {
		this.id=name;
	}

	public String getId() {
		return id;
	}


	public DOMAIN getDomain() {
		return domain;
	}

	public String getImageName() {
		return imageName;
	}
	
	

}
