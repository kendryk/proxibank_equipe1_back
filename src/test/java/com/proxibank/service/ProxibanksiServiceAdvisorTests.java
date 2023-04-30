package com.proxibank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.proxibanksi.model.Advisor;
import com.proxibanksi.repository.IAdvisorDAO;
import com.proxibanksi.service.ServiceImplementAdvisor;

@SpringBootTest
class ProxibanksiServiceAdvisorTests {

	@InjectMocks
	ServiceImplementAdvisor serviceImplementAdvisor;

	@Mock
	IAdvisorDAO advisorDAO;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}
   
	@Test
	void testGetAllAdvisors() {
		List<Advisor> advisors = new ArrayList<>();
		Advisor advisor1 = new Advisor();
		Advisor advisor2 = new Advisor();
		advisors.add(advisor1);
		advisors.add(advisor2);

		when(advisorDAO.findAll()).thenReturn(advisors);

		List<Advisor> result = serviceImplementAdvisor.getAllAdvisors();

		assertEquals(advisors, result);
	}
	
	
    
}
