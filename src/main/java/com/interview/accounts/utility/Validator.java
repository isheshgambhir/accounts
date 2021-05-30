package com.interview.accounts.utility;

import java.sql.Date;
import java.util.ArrayList;

import com.interview.accounts.entities.Account;
import com.interview.accounts.entities.Transaction;

public class Validator {

	public static Transaction validateTransaction(Transaction transaction, Long accountNumber) {
		String transactionType = transaction.getTransactionType().toLowerCase();
		ArrayList<String> validTypes = new ArrayList<String>(2);
		validTypes.add("credit");
		validTypes.add("debit");
		if (transactionType != null && validTypes.contains(transactionType)) {
			if (transaction.getAccountNumber() == null) {
				transaction.setAccountNumber(accountNumber);
			}
			if (transactionType.equals("credit")) {
				transaction.setDebitAmount(0.0);
			} else
				transaction.setCreditAmount(0.0);
			if (transaction.getValueDate() == null)
				transaction.setValueDate(sqlDateToday());
			return transaction;
		}
		return null;
	}

	public static Date sqlDateToday() {
		return Date.valueOf(java.time.LocalDate.now().toString());
	}

	public static Account validateAccount(Account newAccount) {

		if (newAccount.getAccountName() != null && newAccount.getAccountName() != "") {
			String type = newAccount.getAccountType().toLowerCase();
			String currency = newAccount.getCurrency().toLowerCase();

			if (newAccount.getBalance() == null)
				newAccount.setBalance(0.0);

			if (newAccount.getBalanceDate() == null) {
				newAccount.setBalanceDate(sqlDateToday());
			}
			if ((type.equals("savings") || type.equals("current")) && currency != null && currency != "") {
				return newAccount;
			}
		}
		return null;
	}
}
