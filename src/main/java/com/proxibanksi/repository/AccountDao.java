package com.proxibanksi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proxibanksi.model.Account;
@Repository
public interface AccountDao extends JpaRepository<Account, Long>  {

}
