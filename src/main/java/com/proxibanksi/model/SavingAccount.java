package com.proxibanksi.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("saving")
public class SavingAccount extends Account {

	private String accountStatus = "saving";
	private double rate = 3.0;

	/* ************** CONSTRUCTORS ******************* */
	public SavingAccount() {

	}

	public SavingAccount(Double balance) {
		super(balance);

	}

	/* ************** GETTERS SETTERS ******************* */
	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	@Override
	public String toString() {
		return "SavingAccount [rate=" + rate + "]";
	}

}
