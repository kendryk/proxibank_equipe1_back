package com.proxibanksi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proxibanksi.model.Advisor;

/**
 * L'interface IAdvisorDAO hérite de l'interface JpaRepository. Une
 * implémentation est automatiquement générée par SpringBoot. Cela lui associe
 * un bean disponible dans le conteneur. Les différentes méthodes présentes dans
 * cette classe permettent la manipulation d'instances de la classe Client au
 * travers d'un dialogue avec la base de données.
 * 
 * 
 * @see JpaRepository
 * @author Macé Cédric
 */
public interface IAdvisorDAO extends JpaRepository<Advisor, Long> {

}
