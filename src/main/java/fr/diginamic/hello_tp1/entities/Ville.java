package fr.diginamic.hello_tp1.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Entité représentant une ville rattachée à un département.
 */
@Entity 
public class Ville {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message =  "{ville.nom.notnull}")
    @Size(min = 2, message = "{ville.nom.min}")
    private String nom;
    @Min(value = 10,message =  "{ville.population.min}")
    private Integer population;

    private String userMaj;
    private LocalDateTime dateMaj;



    @ManyToOne
    @JoinColumn (name = "id_departement")
    private Departement departement;

    



    /**
     * Constructeur vide requis par JPA.
     */
    public Ville() {
    }




    /**
     * Construit une ville avec un nom et une population.
     *
     * @param nom nom de la ville
     * @param population nombre d'habitants
     */
    public Ville(String nom, Integer population) {
        this.nom = nom;
        this.population = population;
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
     * @return departement
     */
    public Departement getDepartement() {
        return departement;
    }

    /**
     * @param departement departement à définir
     */
    public void setDepartement(Departement departement) {
        this.departement = departement;
    }




    /**
     * @return nom de l'utilisateur ayant fait la dernière mise à jour
     */
    public String getUserMaj() {
        return userMaj;
    }




    /**
     * @param userMaj utilisateur à définir
     */
    public void setUserMaj(String userMaj) {
        this.userMaj = userMaj;
    }




    /**
     * @return date et heure de la dernière mise à jour
     */
    public LocalDateTime getDateMaj() {
        return dateMaj;
    }



    /**
     * @param dateMaj date de mise à jour à définir
     */
    public void setDateMaj(LocalDateTime dateMaj) {
        this.dateMaj = dateMaj;
    }
    
    
}
