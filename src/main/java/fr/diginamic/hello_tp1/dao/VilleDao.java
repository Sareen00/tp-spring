package fr.diginamic.hello_tp1.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import fr.diginamic.hello_tp1.entities.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

/**
 * Accès aux données des villes en base.
 */
@Repository
public class VilleDao {

    @PersistenceContext 
    private EntityManager em;


    /**
     * Récupère toutes les villes.
     *
     * @return liste des villes
     */
    public List<Ville> findAll() {
        return em.createQuery("SELECT v FROM Ville v", Ville.class).getResultList();
    }

    /**
     * Récupère une ville par son identifiant.
     *
     * @param id identifiant de la ville
     * @return la ville, ou null si elle n'existe pas
     */
    public Ville findById(int id) {
        return em.find(Ville.class, id);
    }

    /**
     * Enregistre une nouvelle ville.
     *
     * @param ville ville à enregistrer
     */
    public void insert(Ville ville) {
        em.persist(ville);
    }

    /**
     * Modifie le nom et la population d'une ville existante.
     *
     * @param id identifiant de la ville à modifier
     * @param newVille nouvelles valeurs de la ville
     */
    public void update(int id, Ville newVille) {
        Ville ville = findById(id);
        ville.setNom(newVille.getNom());
        ville.setPopulation(newVille.getPopulation());
    }

    /**
     * Supprime une ville.
     *
     * @param id identifiant de la ville à supprimer
     */
    public void delete(int id) {
        Ville ville = em.find(Ville.class, id);
        em.remove(ville);
    }
    
    /**
     * Récupère les villes dont le nom commence par une chaîne donnée.
     *
     * @param prefix début du nom recherché
     * @return liste des villes trouvées
     */
    public List<Ville> findAllWhoStart(String prefix){
        TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v WHERE v.nom LIKE :prefix", Ville.class);
        query.setParameter("prefix", prefix + "%");
        List<Ville> results = query.getResultList();
        return results;
    }

    /**
     * Récupère les villes dont la population est comprise entre un min et un max.
     *
     * @param min population minimum
     * @param max population maximum
     * @return liste des villes trouvées
     */
    public List<Ville> extraireParPopulation(Integer min, Integer max) {
        TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v WHERE v.population BETWEEN :min AND :max", Ville.class)
            .setParameter("min", min)
            .setParameter("max", max);
        List<Ville> results = query.getResultList();
        return results;
    }
    
    /**
     * Récupère les n plus grandes villes d'un département.
     *
     * @param idDep code du département
     * @param n nombre de villes à renvoyer
     * @return liste des villes triées par population décroissante
     */
    public List<Ville> extrairePlusGrandeVille(Integer idDep,Integer n){
            TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v WHERE v.departement.code = :idDep ORDER BY v.population DESC", Ville.class)
            .setParameter("idDep", idDep)
            .setMaxResults(n);
            return query.getResultList();
    }

    /**
     * Récupère les villes d'un département dont la population est comprise entre un min et un max.
     *
     * @param idDep code du département
     * @param min population minimum
     * @param max population maximum
     * @return liste des villes trouvées
     */
    public List<Ville> extraireParPopulationDeDep(Integer idDep,Integer min, Integer max){
            TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v WHERE v.departement.code = :idDep AND v.population BETWEEN :min AND :max", Ville.class)
            .setParameter("idDep", idDep)
            .setParameter("min", min)
            .setParameter("max", max);
            return query.getResultList();
    }
}