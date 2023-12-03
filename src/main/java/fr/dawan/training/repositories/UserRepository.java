package fr.dawan.training.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.dawan.training.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@Query("FROM User u WHERE u.email = :email")
	Optional<User> findByEmail(@Param("email") String email);
	
	Page<User> findAllByFirstNameContainingOrLastNameContainingOrEmailContaining(String firstName, String lastName, String email, Pageable pageable);
	
	long countByFirstNameContainingOrLastNameContainingOrEmailContaining(String firstName, String lastName, String email);
}
