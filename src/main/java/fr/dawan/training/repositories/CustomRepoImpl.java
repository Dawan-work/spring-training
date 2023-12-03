package fr.dawan.training.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import fr.dawan.training.entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class CustomRepoImpl implements ICustomRepo {

	@PersistenceContext
	private EntityManager em;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> searchBy(Map<String, String> params) {
		String req = "FROM Product p WHERE 1 ";
		//La suite de votre traitement
		for (String key : params.keySet()) {
			req += " AND "+ key + "=" + ":"+key; 
		}
		
		Query q = em.createQuery(req);
		//on injecte les valeurs des paramètres 
		for (String key : params.keySet()) {
			q.setParameter(key, params.get(key)); 
		}
		
		return q.getResultList();
	}

}
