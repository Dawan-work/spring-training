package fr.dawan.training;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import fr.dawan.training.interceptors.AuditInterceptor;

@Component
public class HibernateCustomizer implements HibernatePropertiesCustomizer{

	
	@Autowired
	AuditInterceptor auditInterceptor;
	
	@Override
	public void customize(Map<String, Object> hibernateProperties) {
		hibernateProperties.put("hibernate.session_factory.interceptor", auditInterceptor);
	}

}
