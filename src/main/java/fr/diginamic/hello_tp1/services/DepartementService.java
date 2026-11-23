package fr.diginamic.hello_tp1.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diginamic.hello_tp1.dao.DepartementDao;
import fr.diginamic.hello_tp1.entities.Departement;
import fr.diginamic.hello_tp1.entities.Ville;
import fr.diginamic.hello_tp1.exceptions.ExceptionFonctionnelle;
import fr.diginamic.hello_tp1.repositories.DepartementRepository;

/**
 * Service contenant les règles métier sur les départements.
 */
@Service
public class DepartementService {

    // @Autowired
    // private DepartementDao departementDao;
    @Autowired
    private DepartementRepository departementRepository;

    /**
     * Renvoie tous les départements.
     *
     * @return liste des départements
     */
    public Iterable<Departement> extractAll(){
        return departementRepository.findAll();
    }

    /**
     * Renvoie un département à partir de son identifiant.
     *
     * @param id identifiant du département
     * @return le département trouvé
     * @throws ExceptionFonctionnelle si aucun département ne correspond à cet identifiant
     */
    public Departement extractById(Integer id)throws ExceptionFonctionnelle{
        Departement departement = departementRepository.findById(id).orElseThrow(() -> new ExceptionFonctionnelle("Aucune ville avec l'id " + id));
        return departement;
    }

    /**
     * Renvoie un département à partir de son code.
     *
     * @param code code du département
     * @return le département trouvé
     * @throws ExceptionFonctionnelle si aucun département ne correspond à ce code
     */
    public Departement extractByCode(String code)throws ExceptionFonctionnelle{
        Departement Departement = departementRepository.findByCode(code);
        if (Departement == null) throw new ExceptionFonctionnelle("Aucun Departement avec ce code " + code);
        return Departement;
    }

    /**
     * Recherche un département par son identifiant, puis par son code.
     *
     * @param id identifiant du département (peut être null)
     * @param code code du département (peut être null)
     * @return le département trouvé, ou null s'il n'existe pas
     */
    public Departement extractByIdOrCode(Integer id, String code) {
        Departement dep = null;
        if (id != null) dep = departementRepository.findById(id).orElse(null);
        if (dep == null && code != null) dep = departementRepository.findByCode(code);
        return dep;
    }

    /**
     * Ajoute un nouveau département.
     *
     * @param departement département à ajouter
     * @throws ExceptionFonctionnelle si un département avec le même code existe déjà
     */
    @Transactional
    public void insertDepartement(Departement departement) throws ExceptionFonctionnelle {
        if (departementRepository.findByCode(departement.getCode()) != null) {
            throw new ExceptionFonctionnelle("Un département avec le code " + departement.getCode() + " existe déjà");
        }
        departementRepository.save(departement);
    }

    /**
     * Modifie un département existant.
     *
     * @param id identifiant du département à modifier
     * @param newDepartement nouvelles valeurs du département
     * @throws ExceptionFonctionnelle si aucun département ne correspond à cet identifiant
     */
    @Transactional
    public void updateDepartement(Integer id, Departement newDepartement) throws ExceptionFonctionnelle {
        Departement departement = departementRepository.findById(id).orElse(null);
        if (departement == null) throw new ExceptionFonctionnelle("Aucune Departement avec l'id " + id);
        departement.setCode(newDepartement.getCode());
        departement.setNom(newDepartement.getNom());
        departement.setVilles(newDepartement.getVilles());
        departementRepository.save(newDepartement);
    }

    /**
     * Supprime un département.
     *
     * @param id identifiant du département à supprimer
     * @throws ExceptionFonctionnelle si aucun département ne correspond à cet identifiant
     */
    @Transactional 
    public void deleteDepartement(Integer id) throws ExceptionFonctionnelle {
        Departement Departement = departementRepository.findById(id).orElseThrow(() -> new ExceptionFonctionnelle("Aucun département avec l'id " + id));
        departementRepository.delete(Departement);
    }


    /**
     * Crée et enregistre un département à partir de son code.
     *
     * @param code code du département
     * @return le département créé
     */
    @Transactional
    public Departement creerDepartement(String code) {
        Departement dep = new Departement();
        dep.setCode(code);
        departementRepository.save(dep);
        return dep;
    }

}
