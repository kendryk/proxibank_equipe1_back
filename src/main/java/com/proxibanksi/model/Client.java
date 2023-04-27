package com.proxibanksi.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Client {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Size(min = 2, max = 30)
	private String name;

	@NotEmpty
	@Size(min = 2, max = 30)
	private String firstName;

	@NotEmpty
	@Size(max = 100)
	private String adress;

	@NotEmpty
	@Pattern(regexp = "\\d(5)$")
	private String zipCode;

	@NotEmpty
	@Size(max = 30)
	private String city;

	@NotEmpty
	@Pattern(regexp = "\\d(5)$")
	private String phone;

	@OneToMany(mappedBy = "owner", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	private Set<Account> accountList = new HashSet<>();
	
	@ManyToOne(cascade= CascadeType.PERSIST)
	@JoinColumn(name= "owner_id")
	@JsonIgnore
	private Advisor advisor;

	/************ Constructor ************/

	public Client() {

	}

	public Client(String name, String firstName, String adress, String zipCode, String city, String phone) {
		super();
		this.name = name;
		this.firstName = firstName;
		this.adress = adress;
		this.zipCode = zipCode;
		this.city = city;
		this.phone = phone;
	}

	/************ Methods ************/

	public void addAccount(Account account) {
		this.accountList.add(account);
		account.setOwner(this);
	}

	public void RemoveAccount(Account account) {
		this.accountList.remove(account);
		account.setOwner(this);
	}

	/************ Getters & Setters ************/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<Account> getAccountList() {
		return accountList;
	}

	public void setAccountList(Set<Account> accountList) {
		this.accountList = accountList;
	}

	public Advisor getAdvisor() {
		return advisor;
	}

	public void setAdvisor(Advisor advisor) {
		this.advisor = advisor;
	}

	/************ To String ************/
	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + ", firstName=" + firstName + ", adress=" + adress + ", zipCode="
				+ zipCode + ", city=" + city + ", phone=" + phone + "]";
	}

}
