package fr.diginamic.hello_tp1.services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diginamic.hello_tp1.dao.DepartementDao;
import fr.diginamic.hello_tp1.entities.Departement;
import fr.diginamic.hello_tp1.entities.Ville;
import fr.diginamic.hello_tp1.exceptions.ExceptionFonctionnelle;

/**
 * Service contenant les règles métier sur les départements.
 */
@Service
public class DepartementService {

    @Autowired
    private DepartementDao departementDao;

    /**
     * Renvoie tous les départements.
     *
     * @return liste des départements
     */
    public List<Departement> extractAll(){
        return departementDao.findAll();
    }

    /**
     * Renvoie un département à partir de son identifiant.
     *
     * @param id identifiant du département
     * @return le département trouvé
     * @throws ExceptionFonctionnelle si aucun département ne correspond à cet identifiant
     */
    public Departement extractById(Integer id)throws ExceptionFonctionnelle{
        Departement Departement = departementDao.findById(id);
        if (Departement == null) throw new ExceptionFonctionnelle("Aucune Departement avec l'id " + id);
        return Departement;
    }

    /**
     * Renvoie un département à partir de son code.
     *
     * @param code code du département
     * @return le département trouvé
     * @throws ExceptionFonctionnelle si aucun département ne correspond à ce code
     */
    public Departement extractByCode(Integer code)throws ExceptionFonctionnelle{
        Departement Departement = departementDao.findByCode(code);
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
    public Departement extractByIdOrCode(Integer id, Integer code) {
        Departement dep = null;
        if (id != null) dep = departementDao.findById(id);
        if (dep == null && code != null) dep = departementDao.findByCode(code);
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
        if (departementDao.findByCode(departement.getCode()) != null) {
            throw new ExceptionFonctionnelle("Un département avec le code " + departement.getCode() + " existe déjà");
        }
        departementDao.insert(departement);
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
        Departement Departement = departementDao.findById(id);
        if (Departement == null) throw new ExceptionFonctionnelle("Aucune Departement avec l'id " + id);
        departementDao.update(id, newDepartement);
    }

    /**
     * Supprime un département.
     *
     * @param id identifiant du département à supprimer
     * @throws ExceptionFonctionnelle si aucun département ne correspond à cet identifiant
     */
    @Transactional 
    public void deleteDepartement(Integer id) throws ExceptionFonctionnelle {
        Departement Departement = departementDao.findById(id);
        if (Departement == null) throw new ExceptionFonctionnelle("Aucune Departement avec l'id " + id);
        departementDao.delete(id);
    }


    /**
     * Crée et enregistre un département à partir de son code.
     *
     * @param code code du département
     * @return le département créé
     */
    @Transactional
    public Departement creerDepartement(Integer code) {
        Departement dep = new Departement();
        dep.setCode(code);
        departementDao.insert(dep);
        return dep;
    }

}
