package fr.diginamic.hello_tp1.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diginamic.hello_tp1.dao.VilleDao;
import fr.diginamic.hello_tp1.entities.Ville;
import fr.diginamic.hello_tp1.exceptions.ExceptionFonctionnelle;
import fr.diginamic.hello_tp1.repositories.VilleRepository;

/**
 * Service contenant les règles métier sur les villes.
 */
@Service 
public class VilleService {

    // @Autowired
    // private VilleDao villeDao;


    @Autowired
    private VilleRepository villeRepository;

    /**
     * Renvoie toutes les villes.
     *
     * @return liste des villes
     */
    public List<Ville> extractVilles(){
        return villeRepository.findAll();
    }

    /**
     * Renvoie une ville à partir de son identifiant.
     *
     * @param idVille identifiant de la ville
     * @return la ville trouvée
     * @throws ExceptionFonctionnelle si aucune ville ne correspond à cet identifiant
     */
    public Ville extractVille(Integer idVille)throws ExceptionFonctionnelle{
        Ville ville = villeRepository.findById(idVille).orElse(null);
        if (ville == null) throw new ExceptionFonctionnelle("Aucune ville avec l'id " + idVille);
        return ville;
    }

    /**
     * Renvoie les villes dont le nom commence par une chaîne donnée.
     *
     * @param prefix début du nom recherché
     * @return liste des villes trouvées
     * @throws ExceptionFonctionnelle si aucune ville n'est trouvée
     */
    public List<Ville> extractVilles(String prefix)throws ExceptionFonctionnelle{
        if (villeRepository.findByNomStartingWith(prefix).isEmpty()) {
            throw new ExceptionFonctionnelle("Aucune ville ne commence par " + prefix);
        }
        return villeRepository.findByNomStartingWith(prefix);
    }

    /**
     * Renvoie les villes dont la population est comprise entre un min et un max.
     *
     * @param min population minimum
     * @param max population maximum
     * @return liste des villes trouvées
     * @throws ExceptionFonctionnelle si aucune ville n'est trouvée
     */
    public List<Ville> extractVilles(int min, int max)throws ExceptionFonctionnelle{
        if (villeRepository.findByPopulationBetweenOrderByPopulationDesc(min, max).isEmpty()) {
            throw new ExceptionFonctionnelle("Aucune ville avec une population entre " + min + " et " + max);
        }
        return villeRepository.findByPopulationBetweenOrderByPopulationDesc(min, max);
    }


    /**
     * Renvoie les villes d'un département dont la population est comprise entre un min et un max.
     *
     * @param idDep code du département
     * @param min population minimum
     * @param max population maximum
     * @return liste des villes trouvées
     * @throws ExceptionFonctionnelle si aucune ville n'est trouvée
     */
    public List<Ville> extractVilles(int code, int min, int max)throws ExceptionFonctionnelle{
        if (villeRepository.findByDepartementCodeAndPopulationBetweenOrderByPopulationDesc(code, min, max).isEmpty()) {
            throw new ExceptionFonctionnelle("Aucune ville avec une population entre " + min + " et " + max + " dans le departement " + code);
        }
        return villeRepository.findByDepartementCodeAndPopulationBetweenOrderByPopulationDesc(code, min, max);
    }

    /**
     * Renvoie les n plus grandes villes d'un département.
     *
     * @param idDep code du département
     * @param nbVille nombre de villes à renvoyer
     * @return liste des villes triées par population décroissante
     * @throws ExceptionFonctionnelle si aucune ville n'est trouvée
     */
    public List<Ville> extractVillesDeDep(Integer code, int nbVille)throws ExceptionFonctionnelle{
        if (villeRepository.findByDepartementCodeOrderByPopulationDesc(code,Limit.of(nbVille)).isEmpty()) {
            throw new ExceptionFonctionnelle("Aucune ville dans le département " + code);
        }
        return villeRepository.findByDepartementCodeOrderByPopulationDesc(code,Limit.of(nbVille));
    }

    /**
     * Ajoute une nouvelle ville.
     *
     * @param ville ville à ajouter
     * @return liste de toutes les villes après l'ajout
     * @throws ExceptionFonctionnelle si une ville avec le même nom existe déjà
     */
    @Transactional 
    public List<Ville> insertVille(Ville ville)throws ExceptionFonctionnelle{
        if (villeRepository.existsByNomIgnoreCase(ville.getNom())) {
                throw new ExceptionFonctionnelle("Une ville avec ce nom existe déjà");
        }
        villeRepository.save(ville);
        return villeRepository.findAll();
    }

    /**
     * Modifie une ville existante.
     *
     * @param id identifiant de la ville à modifier
     * @param newVille nouvelles valeurs de la ville
     * @return liste de toutes les villes après modification
     * @throws ExceptionFonctionnelle si aucune ville ne correspond à cet identifiant
     */
    @Transactional
    public List<Ville> updateVille(Integer id, Ville newVille) throws ExceptionFonctionnelle {
        Ville ville = villeRepository.findById(id).orElseThrow(() -> new ExceptionFonctionnelle("Aucune ville avec l'id " + id));
        ville.setNom(newVille.getNom());
        ville.setPopulation(newVille.getPopulation());
        ville.setDepartement(newVille.getDepartement());
        villeRepository.save(ville);
        return villeRepository.findAll();  
    }

    /**
     * Supprime une ville.
     *
     * @param id identifiant de la ville à supprimer
     * @return liste de toutes les villes après suppression
     * @throws ExceptionFonctionnelle si aucune ville ne correspond à cet identifiant
     */
    @Transactional
    public List<Ville> deleteVille(Integer id) throws ExceptionFonctionnelle {
        Ville ville = villeRepository.findById(id).orElseThrow(() -> new ExceptionFonctionnelle("Aucune ville avec l'id " + id));
        villeRepository.delete(ville);
        return villeRepository.findAll();
    }
}
