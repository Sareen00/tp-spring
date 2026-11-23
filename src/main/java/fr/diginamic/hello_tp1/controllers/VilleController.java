package fr.diginamic.hello_tp1.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hello_tp1.dto.VilleDto;
import fr.diginamic.hello_tp1.entities.Departement;
import fr.diginamic.hello_tp1.entities.Ville;
import fr.diginamic.hello_tp1.exceptions.ExceptionFonctionnelle;
import fr.diginamic.hello_tp1.services.DepartementService;
import fr.diginamic.hello_tp1.services.VilleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * Contrôleur REST exposant les opérations sur les villes (URL /villes).
 * Les données échangées avec l'extérieur sont des {@link VilleDto}.
 */
@RestController
@RequestMapping("/villes")
public class VilleController {

    @Autowired
    private VilleService villeService;
    @Autowired
    private DepartementService departementService;
    @Autowired 
    private VilleDto villeDto;
    
    /**
     * Renvoie la liste de toutes les villes.
     *
     * @return liste des villes
     */
    @Operation(summary = "Retourne la liste de toutes les villes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Liste des villes au format JSON",
            content={@Content(mediaType = "application/json",array = @ArraySchema(schema = 
                @Schema(implementation = VilleDto.class)
            ))})
    })
    @GetMapping
    public List<VilleDto> getAllVilles() {
        return villeService.extractVilles().stream().map(villeDto::toDto).toList();
    }

    @GetMapping("/paginate/{page}")
    public List<VilleDto> extraireAllPaginateVilles(@PathVariable Integer page, @RequestParam(defaultValue = "10") int size) {
        return villeService.extractVillesPaginate(page, size).stream().map(villeDto::toDto).toList();
    }

    /**
     * Renvoie une ville à partir de son identifiant.
     *
     * @param id identifiant de la ville
     * @return la ville trouvée
     * @throws ExceptionFonctionnelle si aucune ville ne correspond à cet identifiant
     */
    @Operation(summary = "Retourne une ville à partir de son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Récupère une ville au format JSON",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = VilleDto.class))}),
        @ApiResponse(responseCode = "404",
            description = "Ville non trouvée", content = @Content())
    })
    @GetMapping("/{id}")
    public VilleDto getVilleById(@PathVariable int id) throws ExceptionFonctionnelle{
        return villeDto.toDto(villeService.extractVille(id));    
    }


    /**
     * Ajoute une nouvelle ville.
     * Le département est recherché par son identifiant ou son code.
     * S'il n'existe pas et qu'un code est fourni, il est créé.
     *
     * @param newVilleDto ville à ajouter
     * @return message de confirmation avec le statut 201
     * @throws ExceptionFonctionnelle si le département est inconnu ou si la ville existe déjà
     */
    @Operation(summary = "Créer une ville")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Créer une ville au format JSON",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = VilleDto.class))}),
        @ApiResponse(responseCode = "400",
            description = "Ville existe déjà", content = @Content())
    })
    @PostMapping
    public ResponseEntity<VilleDto> insertVille(@Valid @RequestBody VilleDto newVilleDto)throws ExceptionFonctionnelle{
        Departement dep = departementService.extractByIdOrCode(newVilleDto.getIdDepartement(), newVilleDto.getCodeDepartement());
        if (dep == null) {
            if (newVilleDto.getCodeDepartement() == null) throw new ExceptionFonctionnelle("Département inconnue");
            dep = departementService.creerDepartement(newVilleDto.getCodeDepartement());
        }
        Ville ville = villeDto.toBean(newVilleDto);
        ville.setDepartement(dep);
        villeService.insertVille(ville);
        return ResponseEntity.status(HttpStatus.CREATED).body(newVilleDto);
    }


    /**
     * Ajoute plusieurs villes en une seule requête.
     *
     * @param dtos liste des villes à ajouter
     * @return message indiquant le nombre de villes ajoutées
     * @throws ExceptionFonctionnelle si une des villes ne peut pas être ajoutée
     */
    @PostMapping("/liste")
    public ResponseEntity<String> insertVilles(@RequestBody List<@Valid VilleDto> dtos) throws ExceptionFonctionnelle {
        for (VilleDto dto : dtos) {
            insertVille(dto);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(dtos.size() + " villes ajoutées");
    }

    /**
     * Modifie une ville existante.
     *
     * @param id identifiant de la ville à modifier
     * @param newVilleDto nouvelles valeurs de la ville
     * @return liste de toutes les villes après modification
     * @throws ExceptionFonctionnelle si aucune ville ne correspond à cet identifiant
     */
    @Operation(summary = "Met à jour un ville à partir de son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Met à jour une ville au format JSON",
            content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = 
                @Schema(implementation = VilleDto.class)))}),
        @ApiResponse(responseCode = "404",
            description = "Ville n'existe pas", content = @Content())
    })
    @PutMapping("/{id}")
    public ResponseEntity<List<VilleDto>> updateVille(@PathVariable int id, @Valid @RequestBody VilleDto newVilleDto) throws ExceptionFonctionnelle {
        villeService.updateVille(id, villeDto.toBean(newVilleDto));
        return ResponseEntity.ok(villeService.extractVilles().stream().map(villeDto::toDto).toList());
    }

    /**
     * Supprime une ville.
     *
     * @param id identifiant de la ville à supprimer
     * @return liste de toutes les villes après suppression
     * @throws ExceptionFonctionnelle si aucune ville ne correspond à cet identifiant
     */
    @Operation(summary = "Supprime une ville à partir de son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Supprime une ville par identifiant",
            content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = 
                @Schema(implementation = VilleDto.class)))}),
        @ApiResponse(responseCode = "404",
            description = "Ville n'existe pas", content = @Content())
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<List<VilleDto>> deleteVille(@PathVariable int id) throws ExceptionFonctionnelle {
        villeService.deleteVille(id);
        return ResponseEntity.ok(villeService.extractVilles().stream().map(villeDto::toDto).toList());
    }


    /**
     * Renvoie les villes dont le nom commence par une chaîne donnée.
     *
     * @param prefix début du nom recherché
     * @return liste des villes trouvées
     * @throws ExceptionFonctionnelle si aucune ville n'est trouvée
     */
    @GetMapping("/contiennent/{prefix}")
    public List<VilleDto> getVilleCommencePar(@PathVariable String prefix)throws ExceptionFonctionnelle {
        return villeService.extractVilles(prefix).stream().map(villeDto::toDto).toList();
    }
    
    /**
     * Renvoie les villes dont la population est supérieure ou égale à un minimum.
     *
     * @param min population minimum
     * @return liste des villes trouvées
     * @throws ExceptionFonctionnelle si aucune ville n'est trouvée
     */
    @GetMapping("/population/{min}")
    public List<VilleDto> getVillePopSuperieur(@PathVariable Integer min)throws ExceptionFonctionnelle {
        return villeService.extractVilles(min, Integer.MAX_VALUE).stream().map(villeDto::toDto).toList();
    }
    

    /**
     * Renvoie les villes dont la population est comprise entre un min et un max.
     *
     * @param min population minimum
     * @param max population maximum
     * @return liste des villes trouvées
     * @throws ExceptionFonctionnelle si aucune ville n'est trouvée
     */
    @GetMapping("/population/{min}/{max}")
    public List<VilleDto> getVilleEntre(@PathVariable Integer min,@PathVariable Integer max)throws ExceptionFonctionnelle {
        return villeService.extractVilles(min, max).stream().map(villeDto::toDto).toList();
    }

}
