package fr.dawan.training.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

@SuppressWarnings("serial")
@Entity
public class Supplier extends BaseEntity {
	private String name;

	@ManyToMany(cascade = CascadeType.ALL)
	// @JoinTable permet de personnaliser la table de jointure
	// on pourra également mettre les colonnes
//	@JoinTable(name = "supp_prod", joinColumns = @JoinColumn(name="supp_id"), 
//								    inverseJoinColumns = @JoinColumn(name="prod_id"))
	private Set<Product> products;

	public Supplier() {
		products = new HashSet<>();
	}

	public Set<Long> getProductsIds() {
		Set<Long> result = new HashSet<>();
		if (products != null && products.size() > 0) {
			for (Product p : products) {
				if (p != null)
					result.add(p.getId());
			}
		}
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

}
