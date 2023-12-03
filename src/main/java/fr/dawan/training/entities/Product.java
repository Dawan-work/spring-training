package fr.dawan.training.entities;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@SuppressWarnings("serial")
@Entity
@Table()
public class Product extends BaseEntity {

	@Column(unique = true, nullable = false, columnDefinition = "TEXT")
	@NotNull
	@NotBlank
	private String description;
	
	@ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
	private Set<Supplier> suppliers;
	
	
	//@JsonProperty(value = "prix") modification du nom de la propriété JSON
	@Column(name = "priceht", precision = 2)
	@Min(value = 0)
	private double price;
	
	
	@ManyToOne(cascade = CascadeType.ALL) //plusieurs produits pour 1 catégorie
	//@JoinColumn(name="cat_id")
	private Category category;
	
	
	
	
	//@Lob private byte[] imageBytes; si on souhaite stocker le binaire de l'image
	//private String imageBase64; si on convertit l'image en base64
	private String imagePath; //on stocke le chemin vers l'emplacement de l'image
	
	@Transient
	private String colonneNonMappee;
	
	
	private String notice;
	
	public enum ProductState { EXCELLENT, BON }
	
	@Enumerated(EnumType.STRING)
	private ProductState state;
	
	@Column(columnDefinition = "TEXT")
	private String comments;
	
	@ElementCollection //1 table product_ingredients (PK/FK : product_id, ingredient)
	@CollectionTable(name = "t_prod_ingredients")
	@Column(name = "ingredient")
	private Set<String> ingredients;
	
	@ElementCollection
	@CollectionTable(name="t_prices_promo")
	@MapKeyColumn(name="promotion", length = 70)
	@Column(name="price")
	private Map<String, Double> pricesByPromotion;
	
	public Product() {
		suppliers = new HashSet<>();
	}
	
	public Set<Long> getSuppliersIds(){
		Set<Long> result = new HashSet<>();
		if(suppliers!=null && suppliers.size()>0) {
			for(Supplier s : suppliers)
				result.add(s.getId());
		}
		return result;
	}
	
	
	public Map<String, Double> getPricesByPromotion() {
		return pricesByPromotion;
	}
	public void setPricesByPromotion(Map<String, Double> pricesByPromotion) {
		this.pricesByPromotion = pricesByPromotion;
	}
	public Set<String> getIngredients() {
		return ingredients;
	}
	public void setIngredients(Set<String> ingredients) {
		this.ingredients = ingredients;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Set<Supplier> getSuppliers() {
		return suppliers;
	}
	public void setSuppliers(Set<Supplier> suppliers) {
		this.suppliers = suppliers;
	}
	public ProductState getState() {
		return state;
	}
	public void setState(ProductState state) {
		this.state = state;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getColonneNonMappee() {
		return colonneNonMappee;
	}
	public void setColonneNonMappee(String colonneNonMappee) {
		this.colonneNonMappee = colonneNonMappee;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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
