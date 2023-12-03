package fr.dawan.training.eventListeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import fr.dawan.training.dto.OrderDto;
import fr.dawan.training.events.OrderEvent;

@Component
public class InvoiceListener implements ApplicationListener<OrderEvent>{

	private static Logger logger = LoggerFactory.getLogger(InvoiceListener.class);
	
	@EventListener
	@Override
	public void onApplicationEvent(OrderEvent event) {
		OrderDto dto = event.getWhat();
		//traitement de facturation
		logger.info("on invoice listener..." + dto);
	}

}
