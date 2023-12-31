package fr.dawan.training.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@SuppressWarnings("serial")
@Entity
public class Player3 extends BaseEntity {
	private String name;
	
	@OneToOne //(optional = false)
	@JoinColumn(unique=true)
	private Manager3 manager;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Manager3 getManager() {
		return manager;
	}

	public void setManager(Manager3 manager) {
		this.manager = manager;
	}


}

