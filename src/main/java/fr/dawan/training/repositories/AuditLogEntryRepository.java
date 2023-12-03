package fr.dawan.training.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.dawan.training.entities.AuditLogEntry;

@Repository
public interface AuditLogEntryRepository extends JpaRepository<AuditLogEntry, Long>{

	List<AuditLogEntry> findAllByEntityNameAndEntityId(String entityName, String entityId);
}
