package com.proxibanksi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proxibanksi.model.Client;
import com.proxibanksi.repository.ClientDao;

@Service
public class ServiceImplClient implements IServiceClient {

	@Autowired
	private ClientDao clientDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceImplClient.class);

	/************ Constructor ************/
	public ServiceImplClient(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	/************ Methods ************/

	// get all clients
	@Override
	public List<Client> getAllClients() {
		List<Client> clients = new ArrayList<>();
		try {
			clients = clientDao.findAll();
			LOGGER.info("ClientServiceImpl : la liste de clients ont été récupérés avec succès");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return clients;
	}

	// Create a new client
	@Override
	public Client createClient(Client client) {
		try {
			clientDao.save(client);
			LOGGER.info("ClientServiceImpl : Client sauvegardé avec succès dans la base de données!!!!");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return client;

	}

	// get a client by his id
	@Override
	public Optional<Client> getClientById(Long id) {

		return clientDao.findById(id);
	}

	// remove a client from the list
	@Override
	public void removeClientById(Long id) {
		try {
			clientDao.deleteById(id);
			LOGGER.info("ClientServiceImpl : Client supprimé avec succès dans la base de données!!!!");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	@Override
	public Client updateClient(Client client) {

		try {
			clientDao.save(client);
			LOGGER.info("ClientServiceImpl : Client modfié avec succès dans la base de données!!!!");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return client;
	}

	@Override
	public boolean isClientExist(Long id) {
		return clientDao.existsById(id);
	}

}
