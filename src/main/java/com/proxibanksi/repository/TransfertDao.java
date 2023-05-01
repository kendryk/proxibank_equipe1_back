package com.proxibanksi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proxibanksi.model.Transfert;

public interface TransfertDao extends JpaRepository<Transfert, Long> {

}
