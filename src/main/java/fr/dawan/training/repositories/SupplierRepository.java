package fr.dawan.training.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.dawan.training.entities.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

	//Ensemble des fournisseurs d'un produit dont id est en param
	@Query("FROM Supplier s LEFT JOIN FETCH s.products p WHERE p.id= :prodId")
	List<Supplier> findAllByProductId(@Param("prodId") long productId);
	
	@Query("FROM Supplier s LEFT JOIN FETCH s.products WHERE s.id= :id") 
	Optional<Supplier> findById(@Param("id") long id);
	
	Page<Supplier> findAllByNameContaining(String name, Pageable pageable);
	
	long countByNameContaining(String name);
	
	//chargement "tardif"
	//
	
	
	
}
