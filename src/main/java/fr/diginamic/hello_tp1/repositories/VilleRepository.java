package fr.diginamic.hello_tp1.repositories;

import java.util.List;

import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;

import fr.diginamic.hello_tp1.entities.Ville;

public interface VilleRepository extends CrudRepository<Ville, Integer> {
    List<Ville> findAll();
    Page<Ville> findAll(Pageable pageable);
    List<Ville> findByNomStartingWith(String prefix);
    List<Ville> findByPopulationBetweenOrderByPopulationDesc(Integer min,Integer max);
    List<Ville> findByDepartementCodeAndPopulationBetweenOrderByPopulationDesc(Integer code, Integer min, Integer max);
    List<Ville> findByDepartementCodeOrderByPopulationDesc(Integer code, Limit limit);
    List<Ville> findByPopulationGreaterThanOrderByPopulationDesc(int min);
    List<Ville> findByDepartementCodeAndPopulationGreaterThanOrderByPopulationDesc(Integer code, int min);
    boolean existsByNomIgnoreCase(String nom);
}
