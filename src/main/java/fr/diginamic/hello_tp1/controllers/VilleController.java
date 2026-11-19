package fr.diginamic.hello_tp1.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hello_tp1.entities.Ville;
import jakarta.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/villes")
public class VilleController {
    private List<Ville> villes;

    @PostConstruct
    public void initData(){
        villes = new ArrayList<>(List.of(
            new Ville("Paris", 2133111),
            new Ville("Marseille", 873076),
            new Ville("Lyon", 522250),
            new Ville("Toulouse", 504078),
            new Ville("Nice", 348085),
            new Ville("Nantes", 323204),
            new Ville("Montpellier", 302454),
            new Ville("Strasbourg", 291313),
            new Ville("Bordeaux", 261804),
            new Ville("Lille", 236710)
        ));
    }

    @PostMapping("/add")
    public ResponseEntity<String> insertVille(@RequestBody Ville newVille){
    // Vérif : ville déjà existante ?
        for (Ville v : villes) {
            if (v.getNom().equalsIgnoreCase(newVille.getNom())) {
                return ResponseEntity.badRequest().body("La ville existe déjà");   // 400
            }
        } 
        villes.add(newVille);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ville ajouté avec succes");   
    }

    @GetMapping
    public List<Ville> getAllVilles() {
        return villes;
    }
}
