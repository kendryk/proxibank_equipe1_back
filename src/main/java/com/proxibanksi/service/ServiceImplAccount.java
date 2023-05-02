package com.proxibanksi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.proxibanksi.model.Account;
import com.proxibanksi.repository.AccountDao;

@Service
public class ServiceImplAccount implements IServiceAccount {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceImplAccount.class);

	private AccountDao accountDao;

	public ServiceImplAccount(AccountDao accountDao) {

		this.accountDao = accountDao;
	}

	@Override
	public List<Account> getAllClientsAccount() {
		List<Account> accounts = new ArrayList<>();
		try {

			accounts = accountDao.findAll();

		} catch (Exception e) {
			LOGGER.error("error exception : " + e.getMessage());
		}

		return accounts;
	}

	@Override
	public List<Account> getAudit() {
		List<Account> accounts = getAllClientsAccount();
		List<Account> overDraftsAccounts = accounts.stream()
				.filter(c -> c.getBalance() < 0 && Math.abs(c.getBalance()) >= 5000.0).collect(Collectors.toList());

		return overDraftsAccounts;
	}

}
