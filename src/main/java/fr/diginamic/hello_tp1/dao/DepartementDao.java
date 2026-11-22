package fr.diginamic.hello_tp1.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import fr.diginamic.hello_tp1.entities.Departement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Accès aux données des départements en base.
 */
@Repository 
public class DepartementDao {

    @PersistenceContext 
    private EntityManager em;


    /**
     * Récupère tous les départements.
     *
     * @return liste des départements
     */
    public List<Departement> findAll() {
        return em.createQuery("SELECT d FROM Departement d", Departement.class).getResultList();
    }

    /**
     * Récupère un département par son identifiant.
     *
     * @param id identifiant du département
     * @return le département, ou null s'il n'existe pas
     */
    public Departement findById(int id) {
        return em.find(Departement.class, id);
    }

/**
 * Récupère un département par son code.
 *
 * @param code code du département
 * @return le département, ou null s'il n'existe pas
 */
public Departement findByCode(Integer code) {
    List<Departement> res = em.createQuery("SELECT d FROM Departement d WHERE d.code = :code", Departement.class).setParameter("code", code).getResultList();
    return res.isEmpty() ? null : res.get(0);
}

    /**
     * Enregistre un nouveau département.
     *
     * @param Departement département à enregistrer
     */
    public void insert(Departement Departement) {
        em.persist(Departement);
    }

    /**
     * Modifie le nom d'un département existant.
     *
     * @param id identifiant du département à modifier
     * @param newDepartement nouvelles valeurs du département
     */
    public void update(int id, Departement newDepartement) {
        Departement Departement = findById(id);
        Departement.setNom(newDepartement.getNom());
    }

    /**
     * Supprime un département.
     *
     * @param id identifiant du département à supprimer
     */
    public void delete(int id) {
        Departement Departement = em.find(Departement.class, id);
        em.remove(Departement);
    }
}
