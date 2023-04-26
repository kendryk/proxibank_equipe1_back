package com.proxibank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.proxibanksi.model.Advisor;
import com.proxibanksi.model.Client;
import com.proxibanksi.repository.IAdvisorDAO;
import com.proxibanksi.service.IServiceAdvisor;
import com.proxibanksi.service.ServiceImplementAdvisor;

@SpringBootTest
class ProxibanksiServiceAdvisorTests {

	private IServiceAdvisor service;
    private IAdvisorDAO advisorDAO;

    @BeforeEach
    void setUp() {
        advisorDAO = mock(IAdvisorDAO.class);
        service = new ServiceImplementAdvisor(advisorDAO);
    }

    @Test
    void testGetAllAdvisors() {
        List<Advisor> expectedAdvisors = new ArrayList<>();
        Advisor advisor1 = new Advisor();
        Advisor advisor2 = new Advisor();
        expectedAdvisors.add(advisor1);
        expectedAdvisors.add(advisor2);

        when(advisorDAO.findAll()).thenReturn(expectedAdvisors);

        List<Advisor> actualAdvisors = service.getAllAdvisors();

        assertEquals(expectedAdvisors, actualAdvisors);
    }

    @Test
    void testGetAdvisorById() {
        Long advisorId = 1L;
        Advisor expectedAdvisor = new Advisor();
        expectedAdvisor.setId(advisorId);

        when(advisorDAO.findById(advisorId)).thenReturn(Optional.of(expectedAdvisor));

        Advisor actualAdvisor = service.getAdvisorById(advisorId);

        assertEquals(expectedAdvisor, actualAdvisor);
    }

    @Test
    void testAddAdvisor() {
        Advisor advisor = new Advisor();

        when(advisorDAO.save(advisor)).thenReturn(advisor);

        Advisor addedAdvisor = service.addAdvisor(advisor);

        assertEquals(advisor, addedAdvisor);
    }

    @Test
    void testUpdateAdvisor() {
        Advisor advisor = new Advisor();

        when(advisorDAO.save(advisor)).thenReturn(advisor);

        Advisor updatedAdvisor = service.updateAdvisor(advisor);

        assertEquals(advisor, updatedAdvisor);
    }

    @Test
    void testDeleteAdvisorById() {
        Long advisorId = 1L;

        service.deleteAdvisorById(advisorId);

        // Vérifie que la méthode deleteById a été appelée avec l'id de l'advisor en paramètre
        verify(advisorDAO).deleteById(advisorId);
    }

    @Test
    void testGetAllClientsByAdvisorId() {
        Long advisorId = 1L;
        Advisor advisor = new Advisor();
        Set<Client> expectedClients = new HashSet<>();
        Client client1 = new Client();
        Client client2 = new Client();
        expectedClients.add(client1);
        expectedClients.add(client2);
        advisor.setClients(expectedClients);

        when(advisorDAO.findById(advisorId)).thenReturn(Optional.of(advisor));

        Set<Client> actualClients = service.getAllClientsByAdvisorId(advisorId);

        assertEquals(expectedClients, actualClients);
    }

    @Test
    void testAddClientToAdvisorByID() {
        Long advisorId = 1L;
        Advisor advisor = new Advisor();
        Client client = new Client();
        client.setName("Doe");
        client.setFirstName("John");
        client.setAdress("1234 rue des exemples");
        client.setZipCode("75000");
        client.setCity("Paris");
        client.setPhone("0123456789");

        when(advisorDAO.findById(advisorId)).thenReturn(Optional.of(advisor));
    }
}
