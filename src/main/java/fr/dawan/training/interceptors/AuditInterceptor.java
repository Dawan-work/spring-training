package fr.dawan.training.interceptors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.hibernate.Interceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.dawan.training.annotations.Auditable;
import fr.dawan.training.entities.AuditLogEntry;
import fr.dawan.training.repositories.AuditLogEntryRepository;
import jakarta.transaction.Transactional;

@Component
public class AuditInterceptor implements Interceptor {

	@Lazy //chargement tardif du component dans le conteneur Spring
	@Autowired
	private AuditLogEntryRepository repository;
	
//	@Autowired
//	private ObjectMapper objectMapper;

	private Set<Object> entitiesToPersist = new HashSet<>();
	private Map<Object, List<AuditLogEntry>> logsByObjects = new HashMap<Object, List<AuditLogEntry>>();

	public AuditLogEntry createLogEntry(String action, int i, Object entity, Serializable id, Object[] currentState,
			Object[] previousState, String[] propertyNames, Type[] types) {
		AuditLogEntry logEntry = new AuditLogEntry();
		logEntry.setAuditEntryType(action);
		logEntry.setEntityName(entity.getClass().getName());
		if (id != null) {
			logEntry.setEntityId(id.toString());
		}
		logEntry.setFieldName(propertyNames[i]);
		if (previousState != null && previousState[i] != null) {
			logEntry.setOldValue(previousState[i].toString());
		} else {
			logEntry.setOldValue("");
		}

		if (currentState != null && currentState[i] != null) {
			logEntry.setNewValue(currentState[i].toString());
		}

//		try {
//			logEntry.setUser(getUser());
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}

		return logEntry;
	}

	@Override
	public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		if (entity instanceof Auditable) {
			
			//Write a line by property
			for (int i = 0; i < propertyNames.length; i++) {
				if (state[i] != null && !propertyNames[i].contentEquals("version")) {
					try {
						AuditLogEntry logEntry = createLogEntry("DELETE", i, entity, id, null, state, propertyNames,
								types);
						repository.save(logEntry);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
			
			//Write JSON in OldValue
//			try {
//				String oldValue = objectMapper.writeValueAsString(entity);
//				AuditLogEntry logEntry = createLogEntry("DELETE", 0, entity, id, null, new String[] {oldValue}, propertyNames,
//						types);
//				repository.save(logEntry);
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
			
			
		}
	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		if (entity instanceof Auditable) {
			List<AuditLogEntry> logEntries = new ArrayList<AuditLogEntry>();
			for (int i = 0; i < propertyNames.length; i++) {
				if (state[i] != null && !propertyNames[i].contentEquals("version")) {
					try {
						AuditLogEntry logEntry = createLogEntry("SAVE", i, entity, id, state, null, propertyNames,
								types);
						entitiesToPersist.add(entity);
						logEntries.add(logEntry);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
			logsByObjects.put(entity, logEntries);
		}
		return false;
	}
	
	@Override
	public void afterTransactionCompletion(Transaction tx) {
		// System.out.println("afterTransactionCompletion");
		entitiesToPersist.forEach(obj -> {
//			System.out.println(obj);
//			System.out.println(logsByObjects.keySet().toArray()[0]);
//			System.out.println("equals : " + obj.equals(logsByObjects.keySet().toArray()[0]));
//			System.out.println("Objects.equals : " + Objects.equals(obj, logsByObjects.keySet().toArray()[0]));
//			System.out.println("Contains : "+logsByObjects.keySet().contains(obj));
//			System.out.println("CONTAINS : "+logsByObjects.containsKey(obj));
			
			List<AuditLogEntry> logEntries = logsByObjects.get(obj);
			if (logEntries != null) {
				logEntries.forEach(logEntry -> {
					Auditable aud = (Auditable) obj;
					logEntry.setEntityId(String.valueOf(aud.getId()));
					saveLogEntry(logEntry);
				});
			}
		});
		entitiesToPersist.clear();
		logsByObjects.clear();
	}

	@Transactional
	private void saveLogEntry(AuditLogEntry logEntry) {
		repository.saveAndFlush(logEntry);
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		if (entity instanceof Auditable) {
			for (int i = 0; i < propertyNames.length; i++) {
				if (currentState[i] != null && !propertyNames[i].contentEquals("version")) {
					if ((previousState != null && !(currentState[i].toString().equals(previousState[i].toString())))) {
						try {
							AuditLogEntry logEntry = createLogEntry("UPDATE", i, entity, id, currentState,
									previousState, propertyNames, types);
							repository.save(logEntry);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		}
		return false;
	}

}
