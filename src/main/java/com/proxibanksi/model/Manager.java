package com.proxibanksi.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@DiscriminatorValue("manager")
public class Manager extends Employee{
	
	
	public Manager() {

	}

	public Manager(
			@NotEmpty @Size(min = 2, max = 20) String username,
			@NotEmpty @Size(min = 8, max = 20) String password,
			@NotEmpty @Size(min = 2, max = 50) String name,
			@NotEmpty @Size(min = 2, max = 50) String firstname,
			@NotEmpty Role role) {
		super(username, password, name,firstname,role);
	}
}
