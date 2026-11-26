package fr.diginamic.hello_tp1.services.interfaces;

import java.util.List;

import fr.diginamic.hello_tp1.entities.Ville;
import fr.diginamic.hello_tp1.exceptions.ExceptionFonctionnelle;

/**
 * Contrat du service métier des villes.
 */
public interface IVilleService {

    /**
     * Renvoie toutes les villes.
     *
     * @return liste des villes
     */
    List<Ville> extractVilles();

    /**
     * Renvoie une page de villes.
     *
     * @param page numéro de la page (commence à 0)
     * @param size nombre de villes par page
     * @return liste des villes de la page demandée
     */
    List<Ville> extractVillesPaginate(Integer page, Integer size);

    /**
     * Renvoie une ville à partir de son identifiant.
     *
     * @param idVille identifiant de la ville
     * @return la ville trouvée
     * @throws ExceptionFonctionnelle si aucune ville ne correspond à cet identifiant
     */
    Ville extractVille(Integer idVille) throws ExceptionFonctionnelle;

    /**
     * Renvoie les villes dont le nom commence par une chaîne donnée.
     *
     * @param prefix début du nom recherché
     * @return liste des villes trouvées
     * @throws ExceptionFonctionnelle si aucune ville n'est trouvée
     */
    List<Ville> extractVilles(String prefix) throws ExceptionFonctionnelle;

    /**
     * Renvoie les villes dont la population est comprise entre un min et un max.
     *
     * @param min population minimum
     * @param max population maximum
     * @return liste des villes trouvées
     * @throws ExceptionFonctionnelle si aucune ville n'est trouvée
     */
    List<Ville> extractVilles(Integer min, Integer max) throws ExceptionFonctionnelle;

    /**
     * Renvoie les villes d'un département dont la population est comprise entre un min et un max.
     *
     * @param idDep code du département
     * @param min population minimum
     * @param max population maximum
     * @return liste des villes trouvées
     * @throws ExceptionFonctionnelle si aucune ville n'est trouvée
     */
    List<Ville> extractVilles(String code, Integer min, Integer max) throws ExceptionFonctionnelle;

    /**
     * Renvoie les n plus grandes villes d'un département.
     *
     * @param idDep code du département
     * @param nbVille nombre de villes à renvoyer
     * @return liste des villes triées par population décroissante
     * @throws ExceptionFonctionnelle si aucune ville n'est trouvée
     */
    List<Ville> extractVillesDeDep(String code, Integer nbVille) throws ExceptionFonctionnelle;

    /**
     * Ajoute une nouvelle ville.
     *
     * @param ville ville à ajouter
     * @return liste de toutes les villes après l'ajout
     * @throws ExceptionFonctionnelle si une ville avec le même nom existe déjà
     */
    List<Ville> insertVille(Ville ville) throws ExceptionFonctionnelle;

    /**
     * Modifie une ville existante.
     *
     * @param id identifiant de la ville à modifier
     * @param newVille nouvelles valeurs de la ville
     * @return liste de toutes les villes après modification
     * @throws ExceptionFonctionnelle si aucune ville ne correspond à cet identifiant
     */
    List<Ville> updateVille(Integer id, Ville newVille) throws ExceptionFonctionnelle;

    /**
     * Supprime une ville.
     *
     * @param id identifiant de la ville à supprimer
     * @return liste de toutes les villes après suppression
     * @throws ExceptionFonctionnelle si aucune ville ne correspond à cet identifiant
     */
    List<Ville> deleteVille(Integer id) throws ExceptionFonctionnelle;

}