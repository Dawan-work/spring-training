package fr.dawan.training.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class AuditLogEntry extends BaseEntity implements Serializable {

	private String entityName;
	private String entityId;
	private LocalDateTime creationDate;
	private String auditEntryType;
	private String fieldName;
	private String oldValue;
	private String newValue;
	
	public AuditLogEntry() {
		creationDate = LocalDateTime.now();
	}
	
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	public String getAuditEntryType() {
		return auditEntryType;
	}
	public void setAuditEntryType(String auditEntryType) {
		this.auditEntryType = auditEntryType;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
	//@ManyToOne
	//private User user;
	
	
	
}
