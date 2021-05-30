package com.interview.accounts.utility;

import java.util.Optional;

import com.interview.accounts.entities.Account;
import com.interview.accounts.entities.Transaction;

public class Ledger {

	public static Account updateAccount(Optional<Account> tempAccount, Transaction transaction) {
		Account account = tempAccount.get();
		switch (transaction.getTransactionType().toLowerCase()) {
		case "credit":
			if (transaction.getCreditAmount() > 0) {
				Double newBalance = account.getBalance() + transaction.getCreditAmount();
				account.setBalance(newBalance);
			}
			break;
		case "debit":
			if (transaction.getDebitAmount() > 0) {
				Double newBalance = account.getBalance() - transaction.getDebitAmount();
				account.setBalance(newBalance);
			}
			break;
		default:
			break;
		}
		if (transaction.getValueDate().compareTo(account.getBalanceDate()) > 0) {
			account.setBalanceDate(transaction.getValueDate());
		}
		return account;
	}
}
