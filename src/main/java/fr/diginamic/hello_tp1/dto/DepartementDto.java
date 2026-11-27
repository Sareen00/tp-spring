package fr.diginamic.hello_tp1.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import fr.diginamic.hello_tp1.entities.Departement;
import fr.diginamic.hello_tp1.entities.Ville;

/**
 * DTO d'un département échangé avec l'extérieur.
 * Les villes sont réduites à leur nom pour éviter la boucle infinie Ville / Departement en JSON.
 */
@Component
public class DepartementDto {

    private Integer id;
    private String code;
    private String nom;
    private List<String> nomVilles;

    /**
     * Convertit une entité Departement en DepartementDto.
     *
     * @param departement entité à convertir
     * @return le DTO correspondant
     */
    public DepartementDto toDto(Departement departement) {
        DepartementDto dto = new DepartementDto();
        dto.setId(departement.getId());
        dto.setCode(departement.getCode());
        dto.setNom(departement.getNom());
        dto.setNomVilles(departement.getVilles().stream().map(Ville::getNom).toList());
        return dto;
    }

    /**
     * Convertit un DepartementDto en entité Departement (sans les villes).
     *
     * @param dto DTO à convertir
     * @return l'entité correspondante
     */
    public Departement toBean(DepartementDto dto) {
        Departement departement = new Departement();
        departement.setId(dto.getId());
        departement.setCode(dto.getCode());
        departement.setNom(dto.getNom());
        return departement;
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
     * @return noms des villes du département
     */
    public List<String> getNomVilles() {
        return nomVilles;
    }

    /**
     * @param nomVilles noms des villes à définir
     */
    public void setNomVilles(List<String> nomVilles) {
        this.nomVilles = nomVilles;
    }
}
