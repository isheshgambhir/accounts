package com.interview.accounts.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.hateoas.RepresentationModel;

@Entity
public class Account extends RepresentationModel<Account> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long accountNumber;

	@Column(nullable = false)
	private String accountName;

	@Column(nullable = false)
	private String accountType;

	@Column(nullable = false)
	private Date balanceDate;

	@Column(nullable = false)
	private String currency;

	private Double balance;

	@OneToMany(mappedBy = "accountNumber")
	private List<Transaction> transactions;

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Date getBalanceDate() {
		return balanceDate;
	}

	public void setBalanceDate(Date balanceDate) {
		this.balanceDate = balanceDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + ", accountName=" + accountName + ", accountType="
				+ accountType + ", balanceDate=" + balanceDate + ", currency=" + currency + ", balance=" + balance
				+ "]";
	}

}