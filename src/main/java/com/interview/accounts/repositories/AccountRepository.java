package com.interview.accounts.repositories;

import org.springframework.data.repository.CrudRepository;

import com.interview.accounts.entities.Account;

// This will be AUTO IMPLEMENTED by Spring into a Bean called accountRepository
// CRUD refers Create, Read, Update, Delete

public interface AccountRepository extends CrudRepository<Account, Long> {
}