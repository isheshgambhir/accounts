package com.interview.accounts.repositories;

//This will be AUTO IMPLEMENTED by Spring into a Bean called transactionRepository
//CRUD refers Create, Read, Update, Delete

import org.springframework.data.repository.CrudRepository;

import com.interview.accounts.entities.*;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
	Iterable<Transaction> findByAccountNumber(Long accountNumber);
}