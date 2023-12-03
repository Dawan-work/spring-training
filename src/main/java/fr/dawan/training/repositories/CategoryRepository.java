package fr.dawan.training.repositories;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.dawan.training.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

	@Cacheable(value = "test")
	Page<Category> findAllByNameContaining(String name, Pageable pageable);
	
	long countByNameContaining(String name);
	
	
}
