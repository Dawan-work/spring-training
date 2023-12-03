package fr.dawan.training.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CategoryDto implements Serializable{

	private long id;
	private int version;
	private String name;
	
	public CategoryDto() {
		
	}
	
	
	
	public CategoryDto(long id, int version, String name) {
		super();
		this.id = id;
		this.version = version;
		this.name = name;
	}



	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
