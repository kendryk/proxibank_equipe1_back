package com.proxibanksi.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

import com.proxibanksi.model.Client;
import com.proxibanksi.service.IServiceClient;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clients")
public class RestClientController {

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

	private IServiceClient serviceClient;

	/************ Constructor ************/
	public RestClientController(IServiceClient serviceClient) {

		this.serviceClient = serviceClient;
	}

	/************ Methods ************/

	// Get method

	@GetMapping
	ResponseEntity<Iterable<Client>> getClients() {

		return ResponseEntity.ok().body(serviceClient.getAllClients());
	}

	// Post method

	@PostMapping("/{id}")
	ResponseEntity<Client> createClient(@Valid @RequestBody Client client) {
		Client createClient = serviceClient.createClient(client);
		return ResponseEntity.created(URI.create("/clients/" + createClient.getId())).body(createClient);
	}

	// Put method

	@PutMapping
	public ResponseEntity<Client> updateClient(@Valid @RequestBody Client client) {
		if (client.getId() != null && serviceClient.isClientExist(client.getId())) {
			Client updateClient = serviceClient.updateClient(client);
			return ResponseEntity.ok(updateClient);

		}
		Client saveClient = serviceClient.createClient(client);
		return ResponseEntity.created(URI.create("/client/" + saveClient.getId())).body(saveClient);

	}

	// Delete Method
	@DeleteMapping("/{id}")
	public ResponseEntity<Client> removeClient(@PathVariable Long id) {
		serviceClient.removeClientById(id);
		return ResponseEntity.ok().build();
	}

}
