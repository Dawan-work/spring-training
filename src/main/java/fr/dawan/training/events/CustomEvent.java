package fr.dawan.training.events;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class CustomEvent<T> extends ApplicationEvent {

	private T what;
	
	public CustomEvent(Object source, T what) {
		super(source);
		this.what = what;
	}

	public T getWhat() {
		return what;
	}

	public void setWhat(T what) {
		this.what = what;
	}

	
}
