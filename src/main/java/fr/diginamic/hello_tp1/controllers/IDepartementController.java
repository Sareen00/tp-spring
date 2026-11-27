package fr.diginamic.hello_tp1.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;

import fr.diginamic.hello_tp1.dto.DepartementDto;
import fr.diginamic.hello_tp1.dto.VilleDto;
import fr.diginamic.hello_tp1.entities.Departement;
import fr.diginamic.hello_tp1.exceptions.ExceptionFonctionnelle;

/**
 * Contrat du contrôleur REST des départements.
 */
public interface IDepartementController {

    /**
     * Renvoie la liste de tous les départements.
     *
     * @return liste des départements
     */
    List<DepartementDto> getAllDepartements();

    /**
     * Renvoie un département à partir de son identifiant.
     *
     * @param id identifiant du département
     * @return le département trouvé
     * @throws ExceptionFonctionnelle si aucun département ne correspond à cet identifiant
     */
    DepartementDto getDepartementById(int id) throws ExceptionFonctionnelle;

    /**
     * Ajoute un nouveau département.
     *
     * @param newDepartement département à ajouter
     * @return message de confirmation avec le statut 201
     * @throws ExceptionFonctionnelle si un département avec le même code existe déjà
     */
    ResponseEntity<String> insertDepartement(Departement newDepartement) throws ExceptionFonctionnelle;

    /**
     * Modifie un département existant.
     *
     * @param id identifiant du département à modifier
     * @param newDepartement nouvelles valeurs du département
     * @return message de confirmation
     * @throws ExceptionFonctionnelle si aucun département ne correspond à cet identifiant
     */
    ResponseEntity<String> updateDepartement(int id, Departement newDepartement) throws ExceptionFonctionnelle;

    /**
     * Supprime un département.
     *
     * @param id identifiant du département à supprimer
     * @return message de confirmation
     * @throws ExceptionFonctionnelle si aucun département ne correspond à cet identifiant
     */
    ResponseEntity<String> deleteDepartement(int id) throws ExceptionFonctionnelle;

    /**
     * Renvoie les n plus grandes villes d'un département.
     *
     * @param code code du département
     * @param nb nombre de villes à renvoyer
     * @return liste des villes triées par population décroissante
     * @throws ExceptionFonctionnelle si aucune ville n'est trouvée
     */
    List<VilleDto> getPlusGrandeVille(String code, Integer nb) throws ExceptionFonctionnelle;

    /**
     * Renvoie les villes d'un département dont la population est comprise entre un min et un max.
     *
     * @param code code du département
     * @param min population minimum
     * @param max population maximum
     * @return liste des villes trouvées
     * @throws ExceptionFonctionnelle si aucune ville n'est trouvée
     */
    List<VilleDto> getVilleEntre(String code, Integer min, Integer max) throws ExceptionFonctionnelle;

}