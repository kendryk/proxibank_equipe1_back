package com.proxibanksi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proxibanksi.model.Client;


@Repository
public interface ClientDao extends JpaRepository<Client, Long> {

}
