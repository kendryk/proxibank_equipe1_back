package com.proxibanksi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.proxibanksi.model.Advisor;
import com.proxibanksi.model.Client;
import com.proxibanksi.repository.IAdvisorDAO;


@Service("Advisor")
public class ServiceImplementAdvisor implements IServiceAdvisor {

	private static final Logger LOG = LoggerFactory.getLogger(ServiceImplementAdvisor.class);

	private IAdvisorDAO advisorDAO;

	/* ************** CONSTRUCTORS ******************* */
	public ServiceImplementAdvisor(IAdvisorDAO advisorDAO) {
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
	public void addClientToAdvisorByID(Long advisorId, Client client) {
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

}
