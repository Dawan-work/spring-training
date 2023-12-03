package fr.dawan.training.dto;

import java.io.Serializable;
import java.util.Set;

@SuppressWarnings("serial") //Value Object
public class ProductDto implements Serializable {

	private long id;
	private int version;
	private String description;
	private double price;
	private String imagePath;
	private long categoryId; 
	private Set<Long> suppliersIds;
	
	public Set<Long> getSuppliersIds() {
		return suppliersIds;
	}
	public void setSuppliersIds(Set<Long> suppliersIds) {
		this.suppliersIds = suppliersIds;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
	
}
