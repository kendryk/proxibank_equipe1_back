package com.proxibanksi.service;

import java.util.List;
import java.util.Set;

import com.proxibanksi.exception.DataNotFoundException;
import com.proxibanksi.model.Account;
import com.proxibanksi.model.Advisor;
import com.proxibanksi.model.Client;

public interface IServiceAdvisor {

	List<Advisor> getAllAdvisors();

	Advisor getAdvisorById(Long advisorId);

	Advisor addAdvisor(Advisor advisor);

	Advisor updateAdvisor(Advisor advisor);

	void deleteAdvisorById(Long advisorId);

	Set<Client> getAllClientsByAdvisorId(Long advisorId);

	void addClientToAdvisorById(Long advisorId, Client client);

	void updateClientOfAdvisorById(Long advisorId, Long clientId, Client client);

	Set<Account> getAccountsByClientId(Long advisorId, Long clientId);

	boolean isAdvisorExist(Long id);

	void deleteClientOfAdvisorById(Long advisorId, Long clientId) throws DataNotFoundException;

}
