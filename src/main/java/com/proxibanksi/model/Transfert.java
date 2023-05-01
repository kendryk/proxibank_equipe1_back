package com.proxibanksi.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Transfert {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private double amount;
	
	private LocalDateTime created;
	
	private String label;
	
	private TransfertType transfertType;
	
	@ManyToOne(cascade= CascadeType.PERSIST)
	@JoinColumn(name= "account_id")
	@JsonIgnore
	private Account account;

	/************ Constructor ************/
	public Transfert() {
		
	}
	public Transfert(double amount, String label, TransfertType transfertType, Account account) {
		
		this.amount = amount;
		this.created = LocalDateTime.now();;
		this.label = label;
		this.transfertType = transfertType;
		this.account = account;
	}
	
	
	/* ************** GETTERS SETTERS ******************* */
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public TransfertType getTransfertType() {
		return transfertType;
	}
	public void setTransfertType(TransfertType transfertType) {
		this.transfertType = transfertType;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	
	
	/* ************** TO STRING ******************* */
	
	@Override
	public String toString() {
		return "Transfert [id=" + id + ", amount=" + amount + ", created=" + created + ", label=" + label
				+ ", transfertType=" + transfertType + ", account=" + account + "]";
	}
	
	
	
}
