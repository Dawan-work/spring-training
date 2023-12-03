package fr.dawan.training.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class StringDto implements Serializable{

	private String result;

	public StringDto() {
		
	}
	
	public StringDto(String result) {
		super();
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
}
