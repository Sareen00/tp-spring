package fr.diginamic.hello_tp1.services.interfaces;

import fr.diginamic.hello_tp1.entities.Departement;
import fr.diginamic.hello_tp1.exceptions.ExceptionFonctionnelle;

public interface IDepartementService {

    /**
     * Renvoie tous les départements.
     *
     * @return liste des départements
     */
    Iterable<Departement> extractAll();

    /**
     * Renvoie un département à partir de son identifiant.
     *
     * @param id identifiant du département
     * @return le département trouvé
     * @throws ExceptionFonctionnelle si aucun département ne correspond à cet identifiant
     */
    Departement extractById(Integer id) throws ExceptionFonctionnelle;

    /**
     * Renvoie un département à partir de son code.
     *
     * @param code code du département
     * @return le département trouvé
     * @throws ExceptionFonctionnelle si aucun département ne correspond à ce code
     */
    Departement extractByCode(String code) throws ExceptionFonctionnelle;

    /**
     * Recherche un département par son identifiant, puis par son code.
     *
     * @param id identifiant du département (peut être null)
     * @param code code du département (peut être null)
     * @return le département trouvé, ou null s'il n'existe pas
     */
    Departement extractByIdOrCode(Integer id, String code);

    /**
     * Ajoute un nouveau département.
     *
     * @param departement département à ajouter
     * @throws ExceptionFonctionnelle si un département avec le même code existe déjà
     */
    void insertDepartement(Departement departement) throws ExceptionFonctionnelle;

    /**
     * Modifie un département existant.
     *
     * @param id identifiant du département à modifier
     * @param newDepartement nouvelles valeurs du département
     * @throws ExceptionFonctionnelle si aucun département ne correspond à cet identifiant
     */
    void updateDepartement(Integer id, Departement newDepartement) throws ExceptionFonctionnelle;

    /**
     * Supprime un département.
     *
     * @param id identifiant du département à supprimer
     * @throws ExceptionFonctionnelle si aucun département ne correspond à cet identifiant
     */
    void deleteDepartement(Integer id) throws ExceptionFonctionnelle;

    /**
     * Crée et enregistre un département à partir de son code.
     *
     * @param code code du département
     * @return le département créé
     */
    Departement creerDepartement(String code);

}