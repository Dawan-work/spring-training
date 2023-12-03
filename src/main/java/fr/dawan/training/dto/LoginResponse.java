package fr.dawan.training.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LoginResponse implements Serializable {

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
