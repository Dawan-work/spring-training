package fr.dawan.training.exceptions;

@SuppressWarnings("serial")
public class SamePasswordException extends Exception {

	public SamePasswordException(String message) {
		super(message);
	}
}
