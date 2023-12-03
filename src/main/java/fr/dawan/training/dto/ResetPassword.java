package fr.dawan.training.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ResetPassword implements Serializable {

	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
