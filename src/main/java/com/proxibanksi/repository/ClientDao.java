package com.proxibanksi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proxibanksi.model.Client;

public interface ClientDao extends JpaRepository<Client, Long> {

}
