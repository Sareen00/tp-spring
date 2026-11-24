package fr.diginamic.hello_tp1.controllers;

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
import fr.diginamic.hello_tp1.services.interfaces.IDepartementService;
import fr.diginamic.hello_tp1.services.interfaces.IVilleService;
import jakarta.validation.Valid;

/**
 * Contrôleur REST exposant les opérations sur les départements (URL /departements).
 */
@RestController
@RequestMapping("/departements")
public class DepartementController implements IDepartementController {

    @Autowired 
    private IVilleService villeService;
    @Autowired
    private IDepartementService departementService;
    @Autowired 
    private VilleDto villeDto;


    @Override
    @GetMapping
    public Iterable<Departement> getAllDepartements() {
        return departementService.extractAll();
    }

    @Override
    @GetMapping("/{id}")
    public Departement getDepartementById(@PathVariable int id) throws ExceptionFonctionnelle{
        return departementService.extractById(id);    
    }

    @Override
    @PostMapping
    public ResponseEntity<String> insertDepartement(@Valid @RequestBody Departement newDepartement)throws ExceptionFonctionnelle{
        departementService.insertDepartement(newDepartement);
        return ResponseEntity.status(HttpStatus.CREATED).body("Departement ajoutée avec succès");
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<String> updateDepartement( @PathVariable int id,@Valid @RequestBody Departement newDepartement) throws ExceptionFonctionnelle {
        departementService.updateDepartement(id,newDepartement);
        return ResponseEntity.status(HttpStatus.CREATED).body("Departement suprimée avec succès");
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartement(@PathVariable int id) throws ExceptionFonctionnelle {
        departementService.deleteDepartement(id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Departement modifiée avec succès");
    }

    @Override
    @GetMapping("/population/{code}/{nb}")
    public List<VilleDto> getPlusGrandeVille(@PathVariable Integer code,@PathVariable Integer nb)throws ExceptionFonctionnelle {
        return villeService.extractVillesDeDep(code, nb).stream().map(villeDto::toDto).toList();
    }
    
    @Override
    @GetMapping("/population/{code}/{min}/{max}")
    public List<VilleDto> getVilleEntre(@PathVariable Integer code,@PathVariable Integer min,@PathVariable Integer max)throws ExceptionFonctionnelle {
        return villeService.extractVilles(code, min, max).stream().map(villeDto::toDto).toList();
    }

}
