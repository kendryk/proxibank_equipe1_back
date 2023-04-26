package com.proxibanksi.service;

import java.util.List;
import java.util.Set;

import com.proxibanksi.model.Advisor;
import com.proxibanksi.model.Client;


public interface IServiceAdvisor {

	List<Advisor> getAllAdvisors();

	Advisor getAdvisorById(Long advisorId);

	Advisor addAdvisor(Advisor advisor);

	Advisor updateAdvisor(Advisor advisor);

	void deleteAdvisorById(Long advisorId);
	
	Set<Client> getAllClientsByAdvisorId(Long advisorId);
	
	void addClientToAdvisorByID(Long advisorId, Client client);
	
}
