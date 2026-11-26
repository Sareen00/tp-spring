package fr.diginamic.hello_tp1.repositories;


import org.springframework.data.repository.CrudRepository;

import fr.diginamic.hello_tp1.entities.Departement;

/**
 * Repository Spring Data des départements.
 */
public interface DepartementRepository extends CrudRepository<Departement, Integer>{
    /**
     * Recherche un département par son code.
     *
     * @param code code du département
     * @return le département, ou null s'il n'existe pas
     */
    Departement findByCode(String code);
}
