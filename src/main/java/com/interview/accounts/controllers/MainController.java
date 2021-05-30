package com.interview.accounts.controllers;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.interview.accounts.entities.Account;
import com.interview.accounts.entities.Transaction;
import com.interview.accounts.repositories.AccountRepository;
import com.interview.accounts.repositories.TransactionRepository;
import com.interview.accounts.utility.Ledger;
import com.interview.accounts.utility.Validator;

@RestController
@RequestMapping(path = "/api/v1")
public class MainController {
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private TransactionRepository transactionRepository;

	@PostMapping(path = "/accounts")
	public Account addNewAccount(@RequestBody Account newAccount) {
		newAccount.setAccountNumber(null);
		newAccount = Validator.validateAccount(newAccount);
		if (newAccount == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Causes - Account name is not blank, Account type is either Savings or Current, and Currency is not blank");
		return accountRepository.save(newAccount);
	}

	@GetMapping(path = "/accounts")
	public Iterable<Account> getAllAccounts() {
		Iterable<Account> allAccounts = accountRepository.findAll();
		for (Account account : allAccounts) {
			account.add(linkTo(methodOn(MainController.class).getAccount(account.getAccountNumber())).withSelfRel());
			account.add(
					linkTo(methodOn(MainController.class).getAllTransactionsByAccountNumber(account.getAccountNumber()))
							.withRel("transactions"));
		}
		return allAccounts;
	}

	@GetMapping(path = "/accounts/{id}")
	public ResponseEntity<Account> getAccount(@PathVariable Long id) {
		Optional<Account> tempAccount = accountRepository.findById(id);
		if (tempAccount.isPresent()) {
			Account foundAccount = tempAccount.get();
			foundAccount.add(linkTo(methodOn(MainController.class).getAccount(id)).withSelfRel());
			foundAccount.add(linkTo(methodOn(MainController.class).getAllTransactionsByAccountNumber(id))
					.withRel("transactions"));
			foundAccount.add(linkTo(methodOn(MainController.class).getAllAccounts()).withRel("allAccounts"));
			return new ResponseEntity<>(foundAccount, HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No Such Account");
		}
	}

	@GetMapping(path = "accounts/{id}/transactions")
	public Iterable<Transaction> getAllTransactionsByAccountNumber(@PathVariable Long id) {
		Optional<Account> tempAccount = accountRepository.findById(id);
		if (tempAccount.isPresent()) {
			Iterable<Transaction> allTransactions = transactionRepository.findByAccountNumber(id);
			for (Transaction transaction : allTransactions) {
				transaction.add(linkTo(methodOn(MainController.class).getAccount(id)).withRel("account"));
			}
			return allTransactions;
		}
		throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No Such Account");

	}

	@PostMapping(path = "accounts/{id}/transactions")
	public Transaction addNewTransaction(@RequestBody Transaction transaction, @PathVariable Long id) {
		transaction = Validator.validateTransaction(transaction, id);
		if (transaction == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Causes - AccountNumber must be same as the one in path, type can be only debit or credit");
		Optional<Account> tempAccount = accountRepository.findById(id);
		if (tempAccount.isPresent() && transaction.getAccountNumber() == id) {
			Account updatedAccount = Ledger.updateAccount(tempAccount, transaction);
			accountRepository.save(updatedAccount);
			Transaction savedTransaction = transactionRepository.save(transaction);
			savedTransaction.add(linkTo(methodOn(MainController.class).getAccount(id)).withRel("account"));
			return savedTransaction;
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Causes - AccountNumber does not exist");
	}
}