package fr.dawan.training.eventListeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import fr.dawan.training.events.CustomEvent;

@Component
public class GenericListener<T> implements ApplicationListener<CustomEvent<T>>{

	private static Logger logger = LoggerFactory.getLogger(GenericListener.class);
	
	//l'annotation @EventListener n'est plus nécessaire
	@Override
	public void onApplicationEvent(CustomEvent<T> event) {
		T dto = event.getWhat();
		
		//traitement générique
		
		logger.info("on generic listener...");
	}

}
