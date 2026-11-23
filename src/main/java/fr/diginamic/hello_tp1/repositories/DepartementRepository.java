package fr.diginamic.hello_tp1.repositories;


import org.springframework.data.repository.CrudRepository;

import fr.diginamic.hello_tp1.entities.Departement;

public interface DepartementRepository extends CrudRepository<Departement, Integer>{
    Departement findByCode(String code);
}
