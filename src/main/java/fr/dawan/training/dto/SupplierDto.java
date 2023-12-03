package fr.dawan.training.dto;

import java.io.Serializable;
import java.util.Set;

@SuppressWarnings("serial")
public class SupplierDto implements Serializable {

	private long id;
	private int version;
	private String name;
	private Set<Long> productsIds;
	
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
	public Set<Long> getProductsIds() {
		return productsIds;
	}
	public void setProductsIds(Set<Long> productsIds) {
		this.productsIds = productsIds;
	}
	
	
	
}
