package fr.dawan.training.events;

import org.springframework.context.ApplicationEvent;

import fr.dawan.training.dto.OrderDto;

@SuppressWarnings("serial")
public class OrderEvent extends ApplicationEvent {

	private OrderDto what;
	
	public OrderEvent(Object source, OrderDto what) {
		super(source);
		this.what = what;
	}

	public OrderDto getWhat() {
		return what;
	}

	public void setWhat(OrderDto what) {
		this.what = what;
	}

	
}
