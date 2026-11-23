package fr.diginamic.hello_tp1.controllers;

import fr.diginamic.hello_tp1.services.VilleService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hello_tp1.dto.VilleDto;
import fr.diginamic.hello_tp1.entities.Departement;
import fr.diginamic.hello_tp1.exceptions.ExceptionFonctionnelle;
import fr.diginamic.hello_tp1.services.DepartementService;
import jakarta.validation.Valid;

/**
 * Contrôleur REST exposant les opérations sur les départements (URL /departements).
 */
@RestController
@RequestMapping("/departements")
public class DepartementController {

    @Autowired 
    private VilleService villeService;
    @Autowired
    private DepartementService departementService;
    @Autowired 
    private VilleDto villeDto;


    /**
     * Renvoie la liste de tous les départements.
     *
     * @return liste des départements
     */
    @GetMapping
    public Iterable<Departement> getAllDepartements() {
        return departementService.extractAll();
    }

    /**
     * Renvoie un département à partir de son identifiant.
     *
     * @param id identifiant du département
     * @return le département trouvé
     * @throws ExceptionFonctionnelle si aucun département ne correspond à cet identifiant
     */
    @GetMapping("/{id}")
    public Departement getDepartementById(@PathVariable int id) throws ExceptionFonctionnelle{
        return departementService.extractById(id);    
    }


    /**
     * Ajoute un nouveau département.
     *
     * @param newDepartement département à ajouter
     * @return message de confirmation avec le statut 201
     * @throws ExceptionFonctionnelle si un département avec le même code existe déjà
     */
    @PostMapping
    public ResponseEntity<String> insertDepartement(@Valid @RequestBody Departement newDepartement)throws ExceptionFonctionnelle{
        departementService.insertDepartement(newDepartement);
        return ResponseEntity.status(HttpStatus.CREATED).body("Departement ajoutée avec succès");
    }


    /**
     * Modifie un département existant.
     *
     * @param id identifiant du département à modifier
     * @param newDepartement nouvelles valeurs du département
     * @return message de confirmation
     * @throws ExceptionFonctionnelle si aucun département ne correspond à cet identifiant
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateDepartement( @PathVariable int id,@Valid @RequestBody Departement newDepartement) throws ExceptionFonctionnelle {
        departementService.updateDepartement(id,newDepartement);
        return ResponseEntity.status(HttpStatus.CREATED).body("Departement suprimée avec succès");
    }


    /**
     * Supprime un département.
     *
     * @param id identifiant du département à supprimer
     * @return message de confirmation
     * @throws ExceptionFonctionnelle si aucun département ne correspond à cet identifiant
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartement(@PathVariable int id) throws ExceptionFonctionnelle {
        departementService.deleteDepartement(id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Departement modifiée avec succès");
    }



    /**
     * Renvoie les n plus grandes villes d'un département.
     *
     * @param code code du département
     * @param nb nombre de villes à renvoyer
     * @return liste des villes triées par population décroissante
     * @throws ExceptionFonctionnelle si aucune ville n'est trouvée
     */
    @GetMapping("/population/{code}/{nb}")
    public List<VilleDto> getPlusGrandeVille(@PathVariable Integer code,@PathVariable Integer nb)throws ExceptionFonctionnelle {
        return villeService.extractVillesDeDep(code, nb).stream().map(villeDto::toDto).toList();
    }
    
    /**
     * Renvoie les villes d'un département dont la population est comprise entre un min et un max.
     *
     * @param code code du département
     * @param min population minimum
     * @param max population maximum
     * @return liste des villes trouvées
     * @throws ExceptionFonctionnelle si aucune ville n'est trouvée
     */
    @GetMapping("/population/{code}/{min}/{max}")
    public List<VilleDto> getVilleEntre(@PathVariable Integer code,@PathVariable Integer min,@PathVariable Integer max)throws ExceptionFonctionnelle {
        return villeService.extractVilles(code, min, max).stream().map(villeDto::toDto).toList();
    }


}
