package fr.dawan.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.training.dto.OrderDto;
import fr.dawan.training.events.CustomEvent;
import fr.dawan.training.events.OrderEvent;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired //objet permettant de déclencher des events
	private ApplicationEventPublisher eventPublisher;
	
	@PostMapping(consumes = "application/json", produces="text/plain")
	public String post(OrderDto order) {
		
//		orderService.preparer(order);
//		invoiceService.facturer(order);
		
		//je publie l'evt sur le bus interne, l'ensemble des listeners sur OrderEvent réagiront
		eventPublisher.publishEvent(new OrderEvent(this,order));
		
		//je publie l'evenement pour le genericListener
		eventPublisher.publishEvent(new CustomEvent<OrderDto>(this, order)); 
		
		return "commande reçue";
	}
}
