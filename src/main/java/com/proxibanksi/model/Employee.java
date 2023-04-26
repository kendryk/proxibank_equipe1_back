package com.proxibanksi.model;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "EMPLOYEE_TYPE")
@Table(name = "EMPLOYEE")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Size(min = 2, max = 20)
	private String username;

	@NotEmpty
	@Size(min = 8, max = 20)
	private String password;

	@NotEmpty
	@Size(min = 2, max = 50)
	private String name;

	@NotEmpty
	@Size(min = 2, max = 50)
	private String firstname;

	@NotEmpty
	private Role role;

	/* ************** CONSTRUCTORS ******************* */
	public Employee() {
	}

	public Employee(@NotEmpty @Size(min = 2, max = 20) String username,
			@NotEmpty @Size(min = 8, max = 20) String password, @NotEmpty @Size(min = 2, max = 50) String name,
			@NotEmpty @Size(min = 2, max = 50) String firstname, @NotEmpty Role role) {

		this.username = username;
		this.password = password;
		this.name = name;
		this.firstname = firstname;
		this.role = role;
	}

	/* ************** GETTERS SETTERS ******************* */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	/* ************** TO STRING ******************* */
	@Override
	public String toString() {
		return "Employee [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name
				+ ", firstname=" + firstname + ", role=" + role + "]";
	}

}
