package fr.diginamic.hello_tp1.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hello_tp1.entities.Ville;
import jakarta.annotation.PostConstruct;

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
    private List<Ville> villes;
    private int nextId = 1;

    @PostConstruct
    public void initData() {
        villes = new ArrayList<>();
        ajouter(new Ville("Paris", 2133111));
        ajouter(new Ville("Marseille", 873076));
        ajouter(new Ville("Lyon", 522250));
        ajouter(new Ville("Toulouse", 504078));
        ajouter(new Ville("Nice", 348085));
        ajouter(new Ville("Nantes", 323204));
        ajouter(new Ville("Montpellier", 302454));
        ajouter(new Ville("Strasbourg", 291313));
        ajouter(new Ville("Bordeaux", 261804));
        ajouter(new Ville("Lille", 236710));
    }

    @PostMapping
    public ResponseEntity<String> insertVille(@RequestBody Ville newVille){
        for (Ville v : villes) {
            if (v.getNom().equalsIgnoreCase(newVille.getNom())) {
                return ResponseEntity.badRequest().body("La ville existe déjà");   // 400
            }
        } 
        ajouter(newVille);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ville ajouté avec succes");   
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateVille(@PathVariable int id, @RequestBody Ville newVille) {
        for (Ville ville : villes) {
            if (ville.getId() == id) {
                ville.setNom(newVille.getNom());
                ville.setPopulation(newVille.getPopulation());
                return ResponseEntity.ok("Ville mise à jour avec succès");     
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La ville n'existe pas");  
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) {
        boolean supprimee = villes.removeIf(v -> v.getId() == id);
        if (supprimee) {
            return ResponseEntity.ok("Ville supprimée avec succès");            
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La ville n'existe pas");
    }

    @GetMapping
    public List<Ville> getAllVilles() {
        return villes;
    }

    @GetMapping("/{id")
    public Ville getVilleById(@RequestParam Integer id){
        for (Ville ville : villes) {
            if (ville.getId() == id) {
                return ville;
            }
        }
        return null;
    }


    private Ville ajouter(Ville ville) {
        ville.setId(nextId++);
        villes.add(ville);
        return ville;
    }
}
