package com.proxibanksi.service;

import java.util.List;
import java.util.Set;

import com.proxibanksi.dtos.TransfertRequestDTO;
import com.proxibanksi.exception.DataNotFoundException;
import com.proxibanksi.exception.TransfertNotFoundException;
import com.proxibanksi.model.Account;
import com.proxibanksi.model.Advisor;
import com.proxibanksi.model.Client;
import com.proxibanksi.model.Transfert;

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

	Client deleteClientOfAdvisorById(Long advisorId, Long clientId) throws DataNotFoundException;

	Account addSavingsAccountToClient(Long advisorId, Long clientId) throws Exception;

//	List<Transfert> transfert(Long advisorId, Long clientId, TransfertRequestDTO transfertRequestDTO) throws TransfertNotFoundException;

	Transfert credit(Account account, double amount, String label);

	Transfert debit(Account account, double amount, String label);

	List<Transfert> transfert(Account sourceAccount, Account destinationAccount,
			TransfertRequestDTO transfertRequestDTO) throws TransfertNotFoundException;
	
	List<Transfert> transferBetweenAccounts(Long advisorId, Long clientId,TransfertRequestDTO transfertRequestDTO) throws TransfertNotFoundException;
}
