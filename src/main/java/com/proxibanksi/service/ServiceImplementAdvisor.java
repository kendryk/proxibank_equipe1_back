package com.proxibanksi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.proxibanksi.exception.ClientNotFoundException;
import com.proxibanksi.exception.DataNotFoundException;
import com.proxibanksi.model.Account;
import com.proxibanksi.model.Advisor;
import com.proxibanksi.model.Client;
import com.proxibanksi.repository.IAdvisorDAO;

@Service("Advisor")
public class ServiceImplementAdvisor implements IServiceAdvisor {

	private static final Logger LOG = LoggerFactory.getLogger(ServiceImplementAdvisor.class);

	private IAdvisorDAO advisorDAO;
	private final IServiceClient serviceClient;

	/* ************** CONSTRUCTORS ******************* */
	public ServiceImplementAdvisor(IAdvisorDAO advisorDAO, IServiceClient serviceClient) {
		this.serviceClient = serviceClient;
		this.advisorDAO = advisorDAO;

	}

	/* ************ METHODS *************************** */
	@Override
	public List<Advisor> getAllAdvisors() {

		List<Advisor> advisors = new ArrayList<>();

		try {

			advisors = advisorDAO.findAll();

		} catch (Exception e) {
			LOG.error("error exception : " + e.getMessage());
		}

		return advisors;
	}

	@Override
	public Advisor getAdvisorById(Long advisorId) {
		Advisor advisor = null;
		try {

			advisor = advisorDAO.findById(advisorId).get();

		} catch (Exception e) {
			LOG.error("error exception : " + e.getMessage());
		}

		return advisor;

	}

	@Override
	public Advisor addAdvisor(Advisor advisor) {
		Advisor addedAdvisor = null;
		try {
			addedAdvisor = advisorDAO.save(advisor);
		} catch (Exception e) {
			LOG.error("error exception : " + e.getMessage());
		}

		return addedAdvisor;
	}

	@Override
	public Advisor updateAdvisor(Advisor advisor) {
		Advisor updatedAdvisor = null;

		try {
			updatedAdvisor = advisorDAO.save(advisor);
		} catch (Exception e) {
			LOG.error("error exception : " + e.getMessage());
		}

		return updatedAdvisor;
	}

	@Override
	public void deleteAdvisorById(Long advisorId) {
		try {
			advisorDAO.deleteById(advisorId);
		} catch (Exception e) {
			LOG.error("error exception : " + e.getMessage());
		}

	}

	@Override
	public boolean isAdvisorExist(Long id) {
		return advisorDAO.existsById(id);
	}

	@Override
	public Set<Client> getAllClientsByAdvisorId(Long advisorId) {
		Set<Client> clients = null;

		try {
			Advisor advisor = advisorDAO.findById(advisorId).get();
			clients = advisor.getClients();
		} catch (Exception e) {
			LOG.error("error exception : " + e.getMessage());
		}

		return clients;
	}

	@Override
	public void addClientToAdvisorById(Long advisorId, Client client) {
		try {

			Advisor advisor = advisorDAO.findById(advisorId).get();

			String name = client.getName();
			String firstname = client.getFirstName();
			String address = client.getAdress();
			String postcode = client.getZipCode();
			String city = client.getCity();
			String tel = client.getPhone();

			Client newClient = new Client(name, firstname, address, postcode, city, tel);
			LOG.info("ServiceConseiller : nouveau client :" + newClient);

			Set<Client> listClients = advisor.getClients();

			if (listClients.size() >= advisor.getNumberClientlimit()) {
				List<Advisor> listAdvisors = advisorDAO.findAll();
				for (Advisor a : listAdvisors) {
					if (a.getClients().size() < 10) {
						a.addClient(newClient);
						advisorDAO.save(a);
						return;
					}
				}
			} else {
				advisor.addClient(newClient);
				advisorDAO.save(advisor);
			}

			LOG.info("ServiceConseiller : client rajouté au conseiller en Base de Donnée avec succès");

		} catch (Exception e) {
			LOG.error(e.getMessage());

		}
	}

	public void updateClientOfAdvisorById(Long advisorId, Long clientId, Client client) {
		try {

			// récupérer le conseiller
			Advisor advisor = advisorDAO.findById(advisorId).get();

			// récupérer le client
			Set<Client> clients = advisor.getClients();

			// mettre à jour les informations du client en vérifiant que le client demandé
			// appartient au conseiller
			for (Client c : clients) {
				if (c.getId().equals(clientId)) {
					c.setName(client.getName());
					c.setFirstName(client.getFirstName());
					c.setAdress(client.getAdress());
					c.setZipCode(client.getZipCode());
					c.setCity(client.getCity());
					c.setPhone(client.getPhone());

					advisorDAO.save(advisor);

					LOG.info("ServiceConseiller : client mis à jour avec succès");

					return;
				}
			}

			throw new ClientNotFoundException(
					"Client not found for advisor id=" + advisorId + " and client id=" + clientId);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

	@Override
	public Set<Account> getAccountsByClientId(Long advisorId, Long clientId) {
		Advisor advisor = advisorDAO.findById(advisorId).get();
		if (advisor == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Advisor not found");
		}
		Set<Client> clients = advisor.getClients();
		Client client = clients.stream().filter(c -> c.getId().equals(clientId)).findFirst().orElse(null);
		if (client == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found for this advisor");
		}
		return client.getAccountList();
	}

	@Override
	public void deleteClientOfAdvisorById(Long advisorId, Long clientId) throws DataNotFoundException {
		Advisor advisor = advisorDAO.findById(advisorId)
				.orElseThrow(() -> new DataNotFoundException("Advisor not found"));

		Client client = advisor.getClients().stream().filter(c -> c.getId().equals(clientId)).findFirst()
				.orElseThrow(() -> new DataNotFoundException("Client not found"));

		// supprime le client du conseiller
		advisor.removeClient(client);

		// supprime le client de la base de données
		this.serviceClient.removeClientById(clientId);
	}
}
