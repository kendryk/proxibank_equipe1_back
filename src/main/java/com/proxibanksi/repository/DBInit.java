package com.proxibanksi.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.proxibanksi.model.Account;
import com.proxibanksi.model.Advisor;
import com.proxibanksi.model.Client;
import com.proxibanksi.model.CurrentAccount;
import com.proxibanksi.model.Role;
import com.proxibanksi.model.SavingAccount;

import jakarta.annotation.PostConstruct;

@Component
public class DBInit {

	@Autowired
	private IAdvisorDAO advisorDAO;

	// Constructor
	public DBInit(IAdvisorDAO advisorDAO) {

		this.advisorDAO = advisorDAO;

	}

	// is executed after injection of dependance
	@PostConstruct
	public void initMockedBD() {

		/************* init Advisor *************/
		Advisor advisor1 = new Advisor("bBanner", "12345678", "Bruce", "Banner", Role.USER);
		Advisor advisor2 = new Advisor("tStark", "56789012", "Tony", "Stark", Role.USER);
		Advisor advisor3 = new Advisor("sRogers", "90123456", "Steve", "Rogers", Role.USER);

		/************* init Clients *************/
		Client client1 = new Client("Diana", "Prince", "2 rue du Paradis", "05062", "Gotham City", "0612345678");
		Client client2 = new Client("Peter", "Parker", "15 avenue des Héros", "06073", "New York", "0623456789");
		Client client3 = new Client("Carol", "Danvers", "8 avenue de la Liberté", "90560", "Los Angeles", "0634567890");
		Client client4 = new Client("Clark", "Kent", "1 rue des Héros", "85676", "Metropolis", "0645678901");
		Client client5 = new Client("Jessica", "Jones", "4 avenue de la Justice", "54321", " New York", "0656789012");
		Client client6 = new Client("Tony", "Stark", "6 rue de la Technologie", "43254", " Los Angeles", "0667890123");
		Client client7 = new Client("Natasha", "Romanoff", "3 avenue des Espions", "34567", "Moscou", "0678901234");
		Client client8 = new Client("Bruce", "Banner", "12 avenue de la Science", "76543", " New York", "0689012345");
		Client client9 = new Client("Thor", "Odinson", "1000 5th Ave", "78965", "Houston", "0689102346");
		Client client10 = new Client("Wanda", "Maximoff", "777 6th Ave", "54762", "San Diego", "0652992325");

		/************* init accounts *************/
		Account CurrentAccount1 = new CurrentAccount(5000.0);
		Account CurrentAccount2 = new CurrentAccount(-3000.0);
		Account CurrentAccount3 = new CurrentAccount(500.0);
		Account CurrentAccount4 = new CurrentAccount(-2000.0);
		Account CurrentAccount5 = new CurrentAccount(3000.0);
		Account CurrentAccount6 = new CurrentAccount(50.0);
		Account CurrentAccount7 = new CurrentAccount(-1500.0);
		Account CurrentAccount8 = new CurrentAccount(2500.0);
		Account CurrentAccount9 = new CurrentAccount(5000.0);
		Account CurrentAccount10 = new CurrentAccount(10000.0);

		Account savingAccount1 = new SavingAccount(25000.0);
		Account savingAccount2 = new SavingAccount(3500.0);
		Account savingAccount3 = new SavingAccount(500.0);
		Account savingAccount4 = new SavingAccount(2000.0);
		Account savingAccount5 = new SavingAccount(3000.0);
		Account savingAccount6 = new SavingAccount(12500.0);
		Account savingAccount7 = new SavingAccount(1500.0);
		Account savingAccount8 = new SavingAccount(22500.0);
		Account savingAccount9 = new SavingAccount(45000.0);
		Account savingAccount10 = new SavingAccount(10000.0);

		client1.addAccount(CurrentAccount1);
		client2.addAccount(CurrentAccount2);
		client3.addAccount(CurrentAccount3);
		client4.addAccount(CurrentAccount4);
		client5.addAccount(CurrentAccount5);
		client6.addAccount(CurrentAccount6);
		client7.addAccount(CurrentAccount7);
		client8.addAccount(CurrentAccount8);
		client9.addAccount(CurrentAccount9);
		client10.addAccount(CurrentAccount10);

		client1.addAccount(savingAccount1);
		client2.addAccount(savingAccount2);
		client3.addAccount(savingAccount3);
		client4.addAccount(savingAccount4);
		client5.addAccount(savingAccount5);
		client6.addAccount(savingAccount6);
		client7.addAccount(savingAccount7);
		client8.addAccount(savingAccount8);
		client9.addAccount(savingAccount9);
		client10.addAccount(savingAccount10);

		// Add client to advisor
		advisor1.addClient(client1);
		advisor1.addClient(client2);
		advisor1.addClient(client4);
		advisor1.addClient(client8);
		advisor1.addClient(client9);
		advisor2.addClient(client3);
		advisor2.addClient(client5);
		advisor2.addClient(client6);
		advisor2.addClient(client7);
		advisor3.addClient(client10);

		advisorDAO.saveAll(List.of(advisor1, advisor2, advisor3));

	}

}
