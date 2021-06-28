package myHibernetproject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//Declaring the admindetails table here
@Table(name = "AdminDetails")

//Class, where we declare, what are the columns we are going to use 
public class Admin {

	// Specifying the column names
	@Id
	@Column(name = "AdminUserId")
	private String adminuserid;
	@Column(name = "AdminPassword")
	private String adminPassword;

	// Creating an empty constructor here
	public Admin() {
	}

	// Getter and Setter methods for specifying values for each columns individually
	public String getAdminuserid() {
		return adminuserid;
	}

	public void setAdminuserid(String adminuserid) {
		this.adminuserid = adminuserid;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public Admin(String adminuserid, String adminPassword) {
		this.adminuserid = adminuserid;
		this.adminPassword = adminPassword;
	}

}
