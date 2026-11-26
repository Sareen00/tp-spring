package fr.diginamic.hello_tp1.repositories;

import java.util.List;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import fr.diginamic.hello_tp1.entities.Ville;

/**
 * Repository Spring Data des villes.
 */
public interface VilleRepository extends CrudRepository<Ville, Integer> {
    /**
     * @return toutes les villes
     */
    List<Ville> findAll();
    /**
     * @param pageable page et taille demandées
     * @return une page de villes
     */
    Page<Ville> findAll(Pageable pageable);
    /**
     * @param prefix début du nom
     * @return les villes dont le nom commence par prefix
     */
    List<Ville> findByNomStartingWith(String prefix);
    /**
     * @param min population minimum
     * @param max population maximum
     * @return les villes dont la population est entre min et max, par population décroissante
     */
    List<Ville> findByPopulationBetweenOrderByPopulationDesc(Integer min,Integer max);
    /**
     * @param code code du département
     * @param min population minimum
     * @param max population maximum
     * @return les villes du département dont la population est entre min et max, par population décroissante
     */
    List<Ville> findByDepartementCodeAndPopulationBetweenOrderByPopulationDesc(String code, Integer min, Integer max);
    /**
     * @param code code du département
     * @param limit nombre maximum de villes
     * @return les villes les plus peuplées du département
     */
    List<Ville> findByDepartementCodeOrderByPopulationDesc(String code, Limit limit);
    /**
     * @param min population minimum
     * @return les villes dont la population est supérieure à min, par population décroissante
     */
    List<Ville> findByPopulationGreaterThanOrderByPopulationDesc(int min);
    /**
     * @param code code du département
     * @param min population minimum
     * @return les villes du département dont la population est supérieure à min, par population décroissante
     */
    List<Ville> findByDepartementCodeAndPopulationGreaterThanOrderByPopulationDesc(String code, int min);
    /**
     * @param nom nom de la ville (casse ignorée)
     * @return true si une ville porte déjà ce nom
     */
    boolean existsByNomIgnoreCase(String nom);
}
