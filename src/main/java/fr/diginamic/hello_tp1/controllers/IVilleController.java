package fr.diginamic.hello_tp1.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;

import fr.diginamic.hello_tp1.dto.VilleDto;
import fr.diginamic.hello_tp1.exceptions.ExceptionFonctionnelle;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

public interface IVilleController {

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
    List<VilleDto> getAllVilles();

    List<VilleDto> extraireAllPaginateVilles(Integer page, int size);

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
    VilleDto getVilleById(int id) throws ExceptionFonctionnelle;

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
    ResponseEntity<VilleDto> insertVille(VilleDto newVilleDto) throws ExceptionFonctionnelle;

    /**
     * Ajoute plusieurs villes en une seule requête.
     *
     * @param dtos liste des villes à ajouter
     * @return message indiquant le nombre de villes ajoutées
     * @throws ExceptionFonctionnelle si une des villes ne peut pas être ajoutée
     */
    ResponseEntity<String> insertVilles(List<@Valid VilleDto> dtos) throws ExceptionFonctionnelle;

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
    ResponseEntity<List<VilleDto>> updateVille(int id,
            VilleDto newVilleDto) throws ExceptionFonctionnelle;

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
    ResponseEntity<List<VilleDto>> deleteVille(int id) throws ExceptionFonctionnelle;

    /**
     * Renvoie les villes dont le nom commence par une chaîne donnée.
     *
     * @param prefix début du nom recherché
     * @return liste des villes trouvées
     * @throws ExceptionFonctionnelle si aucune ville n'est trouvée
     */
    List<VilleDto> getVilleCommencePar(String prefix) throws ExceptionFonctionnelle;

    /**
     * Renvoie les villes dont la population est supérieure ou égale à un minimum.
     *
     * @param min population minimum
     * @return liste des villes trouvées
     * @throws ExceptionFonctionnelle si aucune ville n'est trouvée
     */
    List<VilleDto> getVillePopSuperieur(Integer min) throws ExceptionFonctionnelle;

    /**
     * Renvoie les villes dont la population est comprise entre un min et un max.
     *
     * @param min population minimum
     * @param max population maximum
     * @return liste des villes trouvées
     * @throws ExceptionFonctionnelle si aucune ville n'est trouvée
     */
    List<VilleDto> getVilleEntre(Integer min, Integer max) throws ExceptionFonctionnelle;

}