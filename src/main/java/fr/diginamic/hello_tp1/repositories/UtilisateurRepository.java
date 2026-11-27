package fr.diginamic.hello_tp1.repositories;

import org.springframework.data.repository.CrudRepository;

import fr.diginamic.hello_tp1.entities.Utilisateur;

/**
 * Repository Spring Data des utilisateurs.
 */
public interface UtilisateurRepository extends CrudRepository<Utilisateur, Long> {
    /**
     * Recherche un utilisateur par son username.
     *
     * @param username identifiant de connexion
     * @return l'utilisateur, ou null s'il n'existe pas
     */
    Utilisateur findByUsername(String username);
}
