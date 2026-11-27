package fr.diginamic.hello_tp1.repositories;

import org.springframework.data.repository.CrudRepository;

import fr.diginamic.hello_tp1.entities.Utilisateur;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, Long> {
    Utilisateur findByUsername(String username);
}
