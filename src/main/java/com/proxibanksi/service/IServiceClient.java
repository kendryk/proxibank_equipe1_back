package com.proxibanksi.service;


import java.util.List;
import java.util.Optional;




import com.proxibanksi.model.Client;



public interface IServiceClient {
	
	
	List<Client> getAllClients();
	
	Client createClient(Client client);
	
	Optional<Client> getClientById(Long id);
	
	void removeClientById(Long id);
	
	Client updateClient(Client client);
	
	boolean isClientExist(Long id);
	
	

	
	

}
