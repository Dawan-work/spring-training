package fr.dawan.training.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MyInterceptor implements HandlerInterceptor {

	private static Logger ROOT_LOGGER = LoggerFactory.getLogger(MyInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//traitement à faire avec la requête entrante
		
		//ROOT_LOGGER.info("INSIDE INTERCEPTOR....");
		
		return true;
	}

	
}
