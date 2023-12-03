package fr.dawan.training.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.dawan.training.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	//FROM Product p WHERE p.description LIKE %:description%
	Page<Product> findAllByDescriptionContaining(String description, Pageable pageable);
	
	long countByDescriptionContaining(String description);
	
	//Requête avec JP-QL
	@Query(value = "FROM Product p WHERE p.category.id = :id")
	List<Product> findAllByCategoryId(@Param("id") long id);
	
	
	
	
	//Requête en SQL natif
	@Query(nativeQuery = true, value = "SELECT * FROM product p "
										+ "INNER JOIN category c ON (p.category_id=c.id) "
										+ "WHERE c.id = :id")
	List<Product> findSQLByCategoryId(@Param("id") long id);
	
}
