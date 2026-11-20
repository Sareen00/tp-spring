package fr.diginamic.hello_tp1.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hello_tp1.dao.VilleDao;
import fr.diginamic.hello_tp1.entities.Ville;
import fr.diginamic.hello_tp1.exceptions.ExceptionFonctionnelle;
import jakarta.validation.Valid;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/villes")
public class VilleController {
    private final VilleDao villeDao;


    VilleController(VilleDao villeDao) {
        this.villeDao = villeDao;
    }
    
    
    
    @PostMapping
    public ResponseEntity<String> insertVille(@Valid @RequestBody Ville newVille)throws ExceptionFonctionnelle{
        if (villeDao.villeExiste(newVille)) {
            throw new ExceptionFonctionnelle("La ville d'id " + newVille.getNom() + "existe déjà)");
        }
        villeDao.insert(newVille);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ville ajouté avec succes");   
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateVille( @PathVariable int id,@Valid @RequestBody Ville newVille) throws ExceptionFonctionnelle {
        if (!villeDao.update(id, newVille)) {
            throw new ExceptionFonctionnelle("La ville d'id " + id + " n'existe pas");
        }
        return ResponseEntity.ok("Ville mise à jour avec succès");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) throws ExceptionFonctionnelle {
        if (!villeDao.delete(id)) {
            throw new ExceptionFonctionnelle("La ville d'id " + id + " n'existe pas");
        }
        return ResponseEntity.ok("Ville supprimée avec succès");
    }

    @GetMapping
    public List<Ville> getAllVilles() {
        return villeDao.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVilleById(@PathVariable int id) throws ExceptionFonctionnelle{
        if (villeDao.findById(id) == null) {
            throw new ExceptionFonctionnelle("La ville d'id " + id + "n'existe pas");
        }
        return ResponseEntity.ok(villeDao.findById(id));     
    
    }

    @GetMapping("/contiennent/{prefix}")
    public List<Ville> getVilleCommencePar(@PathVariable String prefix)throws ExceptionFonctionnelle {
        List<Ville> resultat = villeDao.findAllWhoStart(prefix);
        if (resultat.isEmpty()) {
            throw new ExceptionFonctionnelle("Aucune ville dont le nom commance par " + prefix + "n'a été trouvé");
        }
        return resultat;    
    }
    
    @GetMapping("/population/{min}")
    public List<Ville> getVillePopSuperieur(@PathVariable Integer min)throws ExceptionFonctionnelle {
        List<Ville> resultat = villeDao.extraireParPopulation(min, Integer.MAX_VALUE);
        if (resultat.isEmpty()) {
            throw new ExceptionFonctionnelle("Aucune ville n'a une population supérieur à " + min);
        }
        return resultat;    
    }
    

    @GetMapping("/population/{min}/{max}")
    public List<Ville> getVilleEntre(@PathVariable Integer min,@PathVariable Integer max)throws ExceptionFonctionnelle {
        if (min>max) {
            throw new ExceptionFonctionnelle(min + " supérieur à " + max);
        }
        List<Ville> resultat = villeDao.extraireParPopulation(min, max);
        if (resultat.isEmpty()) {
            throw new ExceptionFonctionnelle("Aucune ville n'a une population comprise entre " + min +" et "+ max);
        }
        return resultat;    
    }

}
