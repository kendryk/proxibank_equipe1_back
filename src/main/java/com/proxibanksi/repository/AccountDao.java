package com.proxibanksi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proxibanksi.model.Account;

public interface AccountDao extends JpaRepository<Account, Long> {

}
