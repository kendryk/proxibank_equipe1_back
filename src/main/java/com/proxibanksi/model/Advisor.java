package com.proxibanksi.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@DiscriminatorValue("advisor")
public class Advisor extends Employee {

	private Integer numberClientlimit = 10;

	@OneToMany(mappedBy = "advisor", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Client> clients = new HashSet<>();

	/* ************** CONSTRUCTORS ******************* */
	public Advisor() {

	}

	public Advisor(
			@NotEmpty @Size(min = 2, max = 20) String username,
			@NotEmpty @Size(min = 8, max = 20) String password,
			@NotEmpty @Size(min = 2, max = 50) String name,
			@NotEmpty @Size(min = 2, max = 50) String firstname,
			@NotEmpty Role role) {
		super(username, password, name,firstname,role);
	}

	/* ************** METHODS ******************* */

	public void addClient(Client client) {
		clients.add(client);
		client.setAdvisor(this);
	}

	public void removeClient(Client client) {
		clients.remove(client);
		client.setAdvisor(null);
	}
	/* ************** GETTERS SETTERS ******************* */

	public Integer getNumberClientlimit() {
		return numberClientlimit;
	}

	public void setNumberClientlimit(Integer numberClientlimit) {
		this.numberClientlimit = numberClientlimit;
	}

	public Set<Client> getClients() {
		return clients;
	}

	public void setClients(Set<Client> clients) {
		this.clients = clients;
	}
	
	
	
	
}
