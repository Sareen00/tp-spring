package fr.diginamic.hello_tp1.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;

/**
 * Entité représentant un département.
 */
@Entity 
public class Departement {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 2, message = "{departement.nom.min}")
    private String nom;


    private String code;

    @OneToMany (mappedBy = "departement")
    private Set<Ville> villes = new HashSet<>();

    /**
     * Constructeur vide requis par JPA.
     */
    public Departement() {
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
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code code à définir
     */
    public void setCode(String code) {
        this.code = code;
    }

    
    /**
     * @return villes
     */
    public Set<Ville> getVilles() {
        return villes;
    }

    /**
     * @param villes villes à définir
     */
    public void setVilles(Set<Ville> villes) {
        this.villes = villes;
    }
}
