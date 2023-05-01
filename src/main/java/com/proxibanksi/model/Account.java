package com.proxibanksi.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ACCOUNT_TYPE")
@Table(name = "ACCOUNT")
public class Account {

	private static int nbAccountOpen = 0;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	protected String number;


	protected Double balance;

	@PastOrPresent
	protected LocalDateTime created;

	protected TypeCard typecard;

	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "owner_id")
	@JsonIgnore
	protected Client owner;
	
	@OneToMany(mappedBy = "account", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Transfert> transferts = new HashSet<>();

	/* ************** CONSTRUCTORS ******************* */
	public Account() {
	}

	public Account( Double balance) {
		nbAccountOpen++;
		this.number = getAccountNumberAsString();
		this.balance = balance;
		this.created = LocalDateTime.now();

	}

	/* ************** METHODS ******************* */

	public String getAccountNumberAsString() {
		return String.format("%05d", nbAccountOpen);
	}

	public void addTransfert(Transfert transfert) {
		transferts.add(transfert);
		transfert.setAccount(this);;
	}

	public void removeTransfert(Transfert transfert) {
		transferts.remove(transfert);
		transfert.setAccount(null);;
	}

	/* ************** GETTERS SETTERS ******************* */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public TypeCard getTypecard() {
		return typecard;
	}

	public void setTypecard(TypeCard typecard) {
		this.typecard = typecard;
	}

	public Client getOwner() {
		return owner;
	}

	public void setOwner(Client owner) {
		this.owner = owner;
	}

	/* ************** TO STRING ******************* */

	@Override
	public String toString() {
		return "Account [id=" + id + ", number=" + number + ", balance=" + balance + ", created=" + created + "]";
	}

}
