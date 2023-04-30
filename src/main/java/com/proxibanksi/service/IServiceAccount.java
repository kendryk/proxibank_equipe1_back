package com.proxibanksi.service;

import java.util.List;

import com.proxibanksi.model.Account;

public interface IServiceAccount {

	List<Account> getAllClientsAccount();

	List<Account> getAudit();

}
