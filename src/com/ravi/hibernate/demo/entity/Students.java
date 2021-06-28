package com.ravi.hibernate.demo.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Entity
@Table(name="student")
public class Students {

	@Id
	@Column(name="id")
	private int id;
	@Column(name="first_name")
	private String firstname;
	
	@Column(name="lastname")
	private String lastname;

	@Column(name="email")
	private String email;
	
	
	public Students(String firstname, String lastname, String email) {
		
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
	}

	


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	@Override
	public String toString() {
		return "Students [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + "]";
	}
	
}
