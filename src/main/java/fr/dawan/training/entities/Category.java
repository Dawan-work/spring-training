package fr.dawan.training.entities;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import fr.dawan.training.annotations.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

//SELECT c FROM Category c 
//    LEFT JOIN FETCH c.products p
//WHERE c.id=1
                   
@SuppressWarnings("serial")
@Entity
@Cacheable
public class Category extends BaseEntity implements Auditable {

	@Column(unique=true)
	private String name;

	@OneToMany(mappedBy = "category")
	private List<Product> products;
	
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Category [name=" + name + ", products=" + products + ", getId()=" + getId() + ", getVersion()="
				+ getVersion() + " hash : "+ hashCode() + "]";
	}
	
	
	
	
	
}
