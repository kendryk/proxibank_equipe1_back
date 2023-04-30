package com.proxibanksi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.proxibanksi.model.Account;
import com.proxibanksi.service.IServiceAccount;

@RestController
@RequestMapping("/accounts")
@CrossOrigin("*")
public class RestAccountController {
	
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
	
	private IServiceAccount serviceAccount;
	
	
	
	public RestAccountController(IServiceAccount serviceAccount) {
		
		this.serviceAccount = serviceAccount;
	}



	@GetMapping
	ResponseEntity<Iterable<Account>>getAllAccounts(){
		return ResponseEntity.ok().body(serviceAccount.getAllClientsAccount());
	}
	
	@GetMapping("/audit")
	ResponseEntity<Iterable<Account>>getOverDraft(){
		return ResponseEntity.ok().body(serviceAccount.getAudit());
	}
	

}
