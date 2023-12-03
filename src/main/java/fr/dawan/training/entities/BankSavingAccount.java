package fr.dawan.training.entities;

import jakarta.persistence.Entity;

@Entity
public class BankSavingAccount extends BankAccount {

	private double interestRate;

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	
	
	
	
}
