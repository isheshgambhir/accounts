package com.interview.accounts.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.hateoas.RepresentationModel;

@Entity
public class Transaction extends RepresentationModel<Transaction> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long transactionNumber;

	@Column(nullable = false)
	private Date valueDate;

	private Double creditAmount;

	private Double debitAmount;

	@Column(nullable = false)
	private String transactionType;

	private String transactionNarrative;

	@Column(nullable = false)
	private Long accountNumber;

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Long getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(Long transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public Double getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}

	public Double getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(Double debitAmount) {
		this.debitAmount = debitAmount;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionNarrative() {
		return transactionNarrative;
	}

	public void setTransactionNarrative(String transactionNarrative) {
		this.transactionNarrative = transactionNarrative;
	}

	@Override
	public String toString() {
		return "Transaction [transactionNumber=" + transactionNumber + ", valueDate=" + valueDate + ", creditAmount="
				+ creditAmount + ", debitAmount=" + debitAmount + ", transactionType=" + transactionType
				+ ", transactionNarrative=" + transactionNarrative + ", accountNumber=" + accountNumber + "]";
	}
}
