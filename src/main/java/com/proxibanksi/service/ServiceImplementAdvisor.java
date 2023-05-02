package com.proxibanksi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.proxibanksi.dtos.TransfertRequestDTO;
import com.proxibanksi.exception.ClientNotFoundException;
import com.proxibanksi.exception.DataNotFoundException;
import com.proxibanksi.exception.TransfertNotFoundException;
import com.proxibanksi.model.Account;
import com.proxibanksi.model.Advisor;
import com.proxibanksi.model.Client;
import com.proxibanksi.model.CurrentAccount;
import com.proxibanksi.model.SavingAccount;
import com.proxibanksi.model.Transfert;
import com.proxibanksi.model.TransfertType;
import com.proxibanksi.repository.AccountDao;
import com.proxibanksi.repository.ClientDao;
import com.proxibanksi.repository.IAdvisorDAO;
import com.proxibanksi.repository.TransfertDao;

@Service("Advisor")
public class ServiceImplementAdvisor implements IServiceAdvisor {

	private static final Logger LOG = LoggerFactory.getLogger(ServiceImplementAdvisor.class);

	private final IAdvisorDAO advisorDAO;
	private final ClientDao clientDAO;
	private final IServiceClient serviceClient;
	private final AccountDao accountDAO;

	/* ************** CONSTRUCTORS ******************* */
	public ServiceImplementAdvisor(IAdvisorDAO advisorDAO, ClientDao clientDAO, IServiceClient serviceClient,
			AccountDao accountDAO, TransfertDao transfertDAO) {
		this.serviceClient = serviceClient;
		this.clientDAO = clientDAO;
		this.advisorDAO = advisorDAO;
		this.accountDAO = accountDAO;

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
			
			// on lui ouvre un compte 
			newClient.addAccount(new CurrentAccount(0.0));
			
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
	public Client deleteClientOfAdvisorById(Long advisorId, Long clientId) throws DataNotFoundException {
		Advisor advisor = advisorDAO.findById(advisorId)
				.orElseThrow(() -> new DataNotFoundException("Advisor not found"));

		Client client = advisor.getClients().stream().filter(c -> c.getId().equals(clientId)).findFirst()
				.orElseThrow(() -> new DataNotFoundException("Client not found"));

		Client clientDelete = client;

		// supprime le client du conseiller
		advisor.removeClient(client);

		// supprime le client de la base de données
		this.serviceClient.removeClientById(clientId);

		return clientDelete;

	}

	@Override
	public Account addSavingsAccountToClient(Long advisorId, Long clientId) throws Exception {
		// récupérer l'advisor correspondant à l'id
		Advisor advisor = this.advisorDAO.findById(advisorId)
				.orElseThrow(() -> new DataNotFoundException("Advisor not found"));

		Client client = advisor.getClients().stream().filter(c -> c.getId().equals(clientId)).findFirst()
				.orElseThrow(() -> new DataNotFoundException("Client not found"));

		// vérifier si le client a déjà un compte épargne
		long count = client.getAccountList().stream().filter(account -> account instanceof SavingAccount).count();

		if (count > 0) {
			throw new Exception("Le client a déjà un compte épargne");
		}

		// créer un nouveau compte épargne et l'ajouter au client
		SavingAccount savingAccount = new SavingAccount(0.0);
		client.addAccount(savingAccount);
		this.clientDAO.save(client);

		return savingAccount;
	}

	@Override
	public List<Transfert> transferBetweenAccounts(Long advisorId, Long clientId,
			TransfertRequestDTO transfertRequestDTO) throws TransfertNotFoundException {

		// Vérification de l'identité de l'advisor
		Client client = clientDAO.findById(clientId)
				.orElseThrow(() -> new TransfertNotFoundException("Client introuvable"));

		if (!client.getAdvisor().getId().equals(advisorId)) {
			throw new TransfertNotFoundException(
					"Le conseiller n'est pas autorisé à effectuer un transfert pour ce client.");
		}

		// récupere des comptes source et destination
		Account sourceAccount = accountDAO.findById(transfertRequestDTO.getAccountSourceId())
				.orElseThrow(() -> new TransfertNotFoundException("compte source introuvable"));

		Account destinationAccount = accountDAO.findById(transfertRequestDTO.getAccountDestinationId())
				.orElseThrow(() -> new TransfertNotFoundException("compte destination introuvable"));

		// on vérife que les comptes ne sont pas les mêmes:
		if (sourceAccount.getId().equals(destinationAccount.getId())) {
			throw new TransfertNotFoundException("Les comptes sources et destinations ne peuvent pas être les mêmes. ");
		}

		// on vérifie que le transfert ne se fait pas d'un compte épargne vers un compte
		// courant ou épargne d'un autre client
		if (sourceAccount instanceof SavingAccount
				&& (destinationAccount instanceof CurrentAccount || destinationAccount instanceof SavingAccount)
				&& !sourceAccount.getOwner().equals(destinationAccount.getOwner())) {
			throw new TransfertNotFoundException(
					"Le transfert d'un compte épargne vers un compte courant ou épargne d'un autre client est interdit.");
		}

		// on vérifie que le transfert ne se fait pas entre deux comptes épargne
		if (sourceAccount instanceof SavingAccount && destinationAccount instanceof SavingAccount) {
			throw new TransfertNotFoundException("Le transfert ne peut pas être effectué entre deux comptes épargne.");
		}

		// on vérife que la demande de transfert n'est pas inferieur à la balance du
		// compte source
		if (sourceAccount.getBalance().compareTo(transfertRequestDTO.getAmount()) < 0) {
			throw new TransfertNotFoundException("Solde insuffisant sur le compte source.");
		}

		return this.transfert(sourceAccount, destinationAccount, transfertRequestDTO);
	}

	@Override
	public List<Transfert> transferBetweenAccountsCurrent(Long advisorId, TransfertRequestDTO transfertRequestDTO)
			throws TransfertNotFoundException {

		// récupere des comptes source et destination
		Account sourceAccount = accountDAO.findById(transfertRequestDTO.getAccountSourceId())
				.orElseThrow(() -> new TransfertNotFoundException("compte source introuvable"));

		Account destinationAccount = accountDAO.findById(transfertRequestDTO.getAccountDestinationId())
				.orElseThrow(() -> new TransfertNotFoundException("compte destination introuvable"));

		// on vérife que les comptes ne sont pas les mêmes:
		if (sourceAccount.getId().equals(destinationAccount.getId())) {
			throw new TransfertNotFoundException("Les comptes sources et destinations ne peuvent pas être les mêmes. ");
		}

		// on vérifie que le transfert ne se fait pas d'un compte épargne vers un compte
		// courant ou épargne d'un autre client
		if (sourceAccount instanceof SavingAccount
				&& (destinationAccount instanceof CurrentAccount || destinationAccount instanceof SavingAccount)
				&& !sourceAccount.getOwner().equals(destinationAccount.getOwner())) {
			throw new TransfertNotFoundException(
					"Le transfert d'un compte épargne vers un compte courant ou épargne d'un autre client est interdit.");
		}

		// on vérifie que le transfert ne se fait pas entre deux comptes épargne
		if (sourceAccount instanceof SavingAccount && destinationAccount instanceof SavingAccount) {
			throw new TransfertNotFoundException("Le transfert ne peut pas être effectué entre deux comptes épargne.");
		}

		// on vérife que la demande de transfert n'est pas inferieur à la balance du
		// compte source
		if (sourceAccount.getBalance().compareTo(transfertRequestDTO.getAmount()) < 0) {
			throw new TransfertNotFoundException("Solde insuffisant sur le compte source.");
		}

		return this.transfert(sourceAccount, destinationAccount, transfertRequestDTO);
	}

	public List<Transfert> transfert(Account sourceAccount, Account destinationAccount,
			TransfertRequestDTO transfertRequestDTO) throws TransfertNotFoundException {

		// creation du transfert:
		Transfert transfertDebit = this.debit(sourceAccount, transfertRequestDTO.getAmount(),
				"Transfer to " + destinationAccount);
		Transfert transfertCredit = this.credit(destinationAccount, transfertRequestDTO.getAmount(),
				"Transfer from " + sourceAccount);

		List<Transfert> transferts = new ArrayList<>();
		transferts.add(transfertDebit);
		transferts.add(transfertCredit);

		return transferts;
	}

	@Override
	public Transfert credit(Account account, double amount, String label) {

		Transfert transfert = new Transfert(amount, label, TransfertType.CREDIT, account);

		account.addTransfert(transfert);

		account.setBalance(account.getBalance() + amount);

		accountDAO.save(account);

		LOG.info("Transfer from account:" + account.getNumber());

		return transfert;
	}

	@Override
	public Transfert debit(Account account, double amount, String label) {

		Transfert transfert = new Transfert(amount, label, TransfertType.DEBIT, account);

		account.addTransfert(transfert);

		account.setBalance(account.getBalance() - amount);

		accountDAO.save(account);

		LOG.info("Transfer to account:" + account.getNumber());

		return transfert;
	}

}
