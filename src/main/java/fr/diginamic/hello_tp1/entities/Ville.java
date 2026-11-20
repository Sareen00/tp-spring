package fr.diginamic.hello_tp1.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Ville {

    private Integer id;
    @NotBlank(message =  "{ville.nom.notnull}")
    @Size(min = 2, message = "{ville.nom.min}")
    private String nom;
    @Min(value = 10,message =  "{ville.population.min}")
    private Integer population;

    public Ville(String nom, Integer population) {
        this.nom = nom;
        this.population = population;
    }




    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public Integer getPopulation() {
        return population;
    }
    public void setPopulation(Integer population) {
        this.population = population;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ville)) return false;
        Ville autre = (Ville) o;
        return nom.equalsIgnoreCase(autre.nom);
    }
    
    @Override
    public int hashCode() {
        return nom.toLowerCase().hashCode();
    }
    
}
