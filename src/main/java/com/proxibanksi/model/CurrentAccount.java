package com.proxibanksi.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Positive;

@Entity
@DiscriminatorValue("current")
public class CurrentAccount extends Account {

	private String accountStatus = "current";
	private double overdraft = 1000.0;

	/* ************** CONSTRUCTORS ******************* */
	public CurrentAccount() {

	}

	public CurrentAccount( Double balance) {
		super(balance);

	}

	/* ************** GETTERS SETTERS ******************* */
	public double getOverdraft() {
		return overdraft;
	}

	public void setOverdraft(double overdraft) {
		this.overdraft = overdraft;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	@Override
	public String toString() {
		return "CurrentAccount [overdraft=" + overdraft + "]";
	}

}
