package fr.diginamic.hello_tp1.repositories;

import org.springframework.data.repository.CrudRepository;

import fr.diginamic.hello_tp1.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
