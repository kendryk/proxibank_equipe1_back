package com.proxibanksi.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Positive;

@Entity
@DiscriminatorValue("saving")
public class SavingAccount extends Account {
	
	private double rate = 3.0;

	/* ************** CONSTRUCTORS ******************* */
	public SavingAccount() {
	}

	public SavingAccount(@Positive Double balance) {
		super(balance);
	}

	/* ************** GETTERS SETTERS ******************* */
	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "SavingAccount [rate=" + rate + "]";
	}
}
