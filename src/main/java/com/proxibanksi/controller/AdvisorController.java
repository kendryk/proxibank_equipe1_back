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

import com.proxibanksi.exception.AdvisorNotFoundException;
import com.proxibanksi.model.Advisor;
import com.proxibanksi.model.Client;
import com.proxibanksi.service.IServiceAdvisor;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/advisors")
@CrossOrigin("*")
public class AdvisorController {

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

	private IServiceAdvisor serviceAdvisor;

	/* ************** CONSTRUCTORS ******************* */
	public AdvisorController(IServiceAdvisor serviceAdvisor) {
		this.serviceAdvisor = serviceAdvisor;
	}

	/* ************ METHODS *************************** */

	/* **** GET **** */
	@GetMapping()
	public List<Advisor> getAdvisors() {
		return this.serviceAdvisor.getAllAdvisors();
	}

	@GetMapping("/{id}")
	public Advisor getAdvisorById(@PathVariable(name = "id") Long advisorId) throws AdvisorNotFoundException {
		return this.serviceAdvisor.getAdvisorById(advisorId);
	}

	@GetMapping("/{advisorId}/listClient")
	public Set<Client> getAllClientsByAdvisorId(@PathVariable Long advisorId) {
		return this.serviceAdvisor.getAllClientsByAdvisorId(advisorId);
	}

	/* **** POST **** */
	@PostMapping
	public Advisor addAdvisor(@Valid @RequestBody Advisor advisor) {
		return this.serviceAdvisor.addAdvisor(advisor);
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

	/* **** PUT **** */
	@PutMapping("/{advisorId}")
	public Advisor updateTheEmployees(@Valid @RequestBody Advisor advisor, @PathVariable Long advisorId) {
		advisor.setId(advisorId);
		return this.serviceAdvisor.updateAdvisor(advisor);
	}

	/* **** DELETE **** */
	@DeleteMapping("/{advisorId}")
	public void deleteClientByID(@PathVariable Long advisorId) {
		this.serviceAdvisor.deleteAdvisorById(advisorId);
	}
}