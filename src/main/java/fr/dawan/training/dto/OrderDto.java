package fr.dawan.training.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OrderDto implements Serializable {

	private long id;
	private String number;
	private String content;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
