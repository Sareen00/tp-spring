package fr.diginamic.hello_tp1.dto;

import org.springframework.stereotype.Component;

import fr.diginamic.hello_tp1.entities.Ville;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Objet de transfert représentant une ville échangée avec l'extérieur.
 * Contient aussi les méthodes de conversion entre {@link Ville} et VilleDto.
 */
@Component 
public class VilleDto {
    private Integer id;
    @NotBlank(message =  "{ville.nom.notnull}")
    @Size(min = 2, message = "{ville.nom.min}")
    private String nom;
    @Min(value = 10,message =  "{ville.population.min}")
    private Integer population; 

    private Integer idDepartement;
    private Integer codeDepartement;

    /**
     * Convertit une entité Ville en VilleDto.
     *
     * @param ville entité à convertir
     * @return le DTO correspondant
     */
    public VilleDto toDto(Ville ville){
        VilleDto dto = new VilleDto();
        dto.setId(ville.getId());
        dto.setNom(ville.getNom());
        dto.setPopulation(ville.getPopulation());
        if (ville.getDepartement() != null) {
            dto.setIdDepartement(ville.getDepartement().getId());
            dto.setCodeDepartement(ville.getDepartement().getCode());
        }
        return dto;
    }

    /**
     * Convertit un VilleDto en entité Ville (sans l'identifiant ni le département).
     *
     * @param villeDto DTO à convertir
     * @return l'entité correspondante
     */
    public Ville toBean(VilleDto villeDto){
        Ville bean = new Ville();
        bean.setNom(villeDto.getNom());
        bean.setPopulation(villeDto.getPopulation());
        return bean;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id id à définir
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom nom à définir
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return population
     */
    public Integer getPopulation() {
        return population;
    }

    /**
     * @param population population à définir
     */
    public void setPopulation(Integer population) {
        this.population = population;
    }

    /**
     * @return idDepartement
     */
    public Integer getIdDepartement() {
        return idDepartement;
    }

    /**
     * @param idDepartement idDepartement à définir
     */
    public void setIdDepartement(Integer idDepartement) {
        this.idDepartement = idDepartement;
    }

    /**
     * @return codeDepartement
     */
    public Integer getCodeDepartement() {
        return codeDepartement;
    }

    /**
     * @param codeDepartement codeDepartement à définir
     */
    public void setCodeDepartement(Integer codeDepartement) {
        this.codeDepartement = codeDepartement;
    }

    
}
