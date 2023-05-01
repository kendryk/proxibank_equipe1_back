package com.proxibanksi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.proxibanksi.dtos.TransfertRequestDTO;
import com.proxibanksi.model.Account;
import com.proxibanksi.model.Advisor;
import com.proxibanksi.model.Client;
import com.proxibanksi.model.Transfert;
import com.proxibanksi.service.IServiceAdvisor;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/advisors")
@CrossOrigin("*")
public class RestAdvisorController {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {

		HashMap<String, String> errors = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach(e -> {
			String fieldName = ((FieldError) e).getField();
			String errorMessage = e.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	private final IServiceAdvisor serviceAdvisor;

	/* ************** CONSTRUCTORS ******************* */
	public RestAdvisorController(IServiceAdvisor serviceAdvisor) {
		this.serviceAdvisor = serviceAdvisor;
	}

	/* ************ METHODS *************************** */

	/* **** GET **** */
	@GetMapping()
	public List<Advisor> getAdvisors() {
		return this.serviceAdvisor.getAllAdvisors();
	}

	@GetMapping("/{advisorId}/clients/{clientId}/accounts")
	public Set<Account> getAccountsByClientId(@PathVariable Long advisorId, @PathVariable Long clientId) {
		return this.serviceAdvisor.getAccountsByClientId(advisorId, clientId);
	}

	@GetMapping("/{advisorId}")
	public ResponseEntity<Advisor> getAdvisorById(@PathVariable Long advisorId) {
		try {
			Advisor advisor = this.serviceAdvisor.getAdvisorById(advisorId);
			return new ResponseEntity<Advisor>(advisor, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Advisor>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{advisorId}/listClients")
	public ResponseEntity<Set<Client>> getAllClientsByAdvisorId(@PathVariable Long advisorId) {
		try {
			Set<Client> clients = this.serviceAdvisor.getAllClientsByAdvisorId(advisorId);
			return new ResponseEntity<Set<Client>>(clients, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Set<Client>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/* **** POST **** */
	@PostMapping
	public ResponseEntity<Advisor> addAdvisor(@Valid @RequestBody Advisor advisor) {
		try {
			Advisor addedAdvisor = this.serviceAdvisor.addAdvisor(advisor);
			return new ResponseEntity<Advisor>(addedAdvisor, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Advisor>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/{advisorId}/addClient")
	public ResponseEntity<Client> addClientToConseiller(@PathVariable Long advisorId, @RequestBody Client client) {
		try {
			this.serviceAdvisor.addClientToAdvisorById(advisorId, client);
			return new ResponseEntity<Client>(client, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/{advisorId}/clients/{clientId}/savingsAccount")
	public ResponseEntity<Account> addSavingsAccountToClient(@PathVariable Long advisorId,
			@PathVariable Long clientId) {
		try {
			Account account = this.serviceAdvisor.addSavingsAccountToClient(advisorId, clientId);
			return new ResponseEntity<Account>(account, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Account>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/{advisorId}/clients/{clientId}/accounts/transferBetweenAccounts")
	public ResponseEntity<List<Transfert>> transferBetweenAccounts(@PathVariable Long advisorId, @PathVariable Long clientId,
			@RequestBody TransfertRequestDTO transfertRequestDTO) {
		try {
			List<Transfert> transfert = this.serviceAdvisor.transferBetweenAccounts(advisorId, clientId, transfertRequestDTO);

			return new ResponseEntity<List<Transfert>>(transfert, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<List<Transfert>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/* **** PUT **** */

	@PutMapping("/{advisorId}/updateClient/{clientId}")
	public ResponseEntity<Client> updateClientOfAdvisorById(@PathVariable Long advisorId, @PathVariable Long clientId,
			@RequestBody Client client) {
		try {
			this.serviceAdvisor.updateClientOfAdvisorById(advisorId, clientId, client);
			return new ResponseEntity<Client>(client, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{advisorId}")
	public ResponseEntity<Advisor> updateTheAdvisor(@Valid @RequestBody Advisor advisor, @PathVariable Long advisorId) {
		try {
			advisor.setId(advisorId);
			Advisor updatedAdvisor = this.serviceAdvisor.updateAdvisor(advisor);
			return new ResponseEntity<Advisor>(updatedAdvisor, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Advisor>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/* **** DELETE **** */
	@DeleteMapping("/{advisorId}")
	public ResponseEntity<String> deleteAdvisorById(@PathVariable Long advisorId) {
		try {
			this.serviceAdvisor.deleteAdvisorById(advisorId);
			return new ResponseEntity<>("Advisor " + advisorId + " deleted.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{advisorId}/deleteClient/{clientId}")
	public ResponseEntity<Client> deleteClientOfAdvisorById(@PathVariable Long advisorId, @PathVariable Long clientId) {
		try {
			Client clientdelete = this.serviceAdvisor.deleteClientOfAdvisorById(advisorId, clientId);
			return new ResponseEntity<Client>(clientdelete, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}