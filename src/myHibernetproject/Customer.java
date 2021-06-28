package myHibernetproject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//Declaring the table name here
@Table(name = "customerAccountDetails")

//Class, where we declare, what are the columns we are going to use 
public class Customer {

	// Specifying the column names
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int transactionid;
	private int accountnumber;
	private int balance;
	private int creditedamount;
	private int debitedAmount;

	// Getter and Setter methods for specifying values for each columns individually
	public int getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(int transactionid) {
		this.transactionid = transactionid;
	}

	public int getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(int accountnumber) {
		this.accountnumber = accountnumber;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getCreditedamount() {
		return creditedamount;
	}

	public void setCreditedamount(int creditedamount) {
		this.creditedamount = creditedamount;
	}

	public int getDebitedAmount() {
		return debitedAmount;
	}

	public void setDebitedAmount(int debitedAmount) {
		this.debitedAmount = debitedAmount;
	}

	// Creating an empty constructor here
	public Customer() {
		super();
	}

}
